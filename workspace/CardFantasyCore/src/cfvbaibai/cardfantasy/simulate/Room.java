package cfvbaibai.cardfantasy.simulate;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Rune;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.game.DeckBuilder;
import cfvbaibai.cardfantasy.game.DeckStartupInfo;
import cfvbaibai.cardfantasy.game.DummyGameUI;
import cfvbaibai.cardfantasy.test.TestGameBuilder;

public class Room {
    private List<Team> teams;
    private int maxTeams;
    private int oneMatchRounds;
    private int heroLv;
    private Randomizer random;
    private int getMaxCost() {
        return PlayerInfo.getMaxCost(this.heroLv);
    }

    public Room(int maxTeams, int oneMatchRounds, int heroLv) {
        if (maxTeams < 2) {
            throw new IllegalArgumentException("maxTeams must be greater than 1");
        }
        this.maxTeams = maxTeams;
        this.teams = new ArrayList<Team>(this.maxTeams);
        this.oneMatchRounds = oneMatchRounds;
        this.heroLv = heroLv;
        this.random = Randomizer.getRandomizer();
    }

    public void initialize() {
        for (int i = 0; i < maxTeams; ++i) {
            System.out.println("Creating team " + i + "...");
            teams.add(new Team(this.getMaxCost()));
        }
        System.out.flush();
    }

    public void initializeFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        String line = null;
        while ((line = reader.readLine()) != null) {
            DeckStartupInfo dsi = DeckBuilder.multiBuild(line);
            Team team = new Team(dsi, this.getMaxCost());
            teams.add(team);
        }
        reader.close();
    }

    public List<Team> getTeams() {
        return new ArrayList<Team>(this.teams);
    }

    public void iterate(int iterations) {
        for (int i = 0; i < iterations; ++i) {
            int indexA = random.next(0, teams.size());
            int indexB = indexA;
            while (indexA == indexB) {
                indexB = random.next(0, teams.size());
            }
            Team teamA = teams.get(indexA);
            Team teamB = teams.get(indexB);
            PlayerInfo pA = new PlayerInfo(true, "A", this.heroLv, null, 100, teamA.getRunes(), teamA.getCards());
            PlayerInfo pB = new PlayerInfo(true, "B", this.heroLv, null, 100, teamB.getRunes(), teamB.getCards());
            int winsA = 0;
            int winsB = 0;
            for (int m = 0; m < this.oneMatchRounds; ++m) {
                try {
                    GameResult result = TestGameBuilder.play(pA, pB, new DummyGameUI());
                    if (result.getWinner().getId().equals(pA.getId())) {
                        ++winsA;
                    } else {
                        ++winsB;
                    }
                } catch (Exception e) {
                    System.out.println("Team A: " + teamA.getTeamDesc());
                    System.out.println("Team B: " + teamB.getTeamDesc());
                    throw new CardFantasyRuntimeException("Error!", e);
                }
            }
            if (winsA > winsB) {
                this.evolve(teamA, teamB);
            } else {
                this.evolve(teamB, teamA);
            }
        }
    }
    
    public void evolve(Team winner, Team loser) {
        this.teams.remove(loser);
        Team winnerChild = null;
        while (true) {
            winnerChild = winner.clone();
            winnerChild.mutate();
            if (winnerChild.isCostOK()) {
                break;
            }
        }
        this.teams.add(winnerChild);
    }
}

class Team implements Cloneable {
    private static CardDataStore store = CardDataStore.loadDefault();
    private Card[] cards;
    private Rune[] runes;
    private int maxCost;

    public Team(int maxCost) {
        this.maxCost = maxCost;
        this.cards = new Card[10];
        while (true) {
            for (int i = 0; i < 10; ++i) {
                Card card = getRandomCard();
                this.cards[i] = card;
            }
            if (isCostOK()) {
                break;
            }
        }
        this.runes = new Rune[4];
        for (int i = 0; i < 4; ++i) {
            Rune rune = createRuneFromData(RuneData.getRandomRune());
            this.runes[i] = rune;
        }
    }
    
    public Team(DeckStartupInfo dsi, int maxCost) {
        this.maxCost = maxCost;
        this.cards = new Card[10];
        this.runes = new Rune[4];
        List<Card> sourceCards = dsi.getCards();
        for (int i = 0; i < 10; ++i) {
            this.cards[i] = sourceCards.get(i);
        }
        List<Rune> sourceRunes = dsi.getRunes();
        for (int i = 0; i < 4; ++i) {
            this.runes[i] = sourceRunes.get(i);
        }
    }

    private Card getRandomCard() {
        while (true) {
            Card card = createCardFromData(store.getRandomCard());
            if (Integer.parseInt(card.getId()) >= 5000) {
                continue;
            }
            if (card.getStar() < 4) {
                continue;
            }
            return card;
        }
    }

    public boolean isCostOK() {
        int cost = 0;
        for (Card card : this.cards) {
            cost += card.getCost();
        }
        return this.maxCost >= cost;
    }

    public static Card createCardFromData(CardData cardData) {
        Card card = new Card(cardData);
        card.growToLevel(10);
        return card;
    }
    
    public static Rune createRuneFromData(RuneData runeData) {
        Rune rune = new Rune(runeData, 0);
        rune.growToLevel(4);
        return rune;
    }

    public List<Card> getCards() {
        List<Card> result = new ArrayList<Card>();
        for (int i = 0; i < 10; ++i) {
            result.add(this.cards[i]);
        }
        return result;
    }

    public List<Rune> getRunes() {
        List<Rune> result = new ArrayList<Rune>();
        for (int i = 0; i < 4; ++i) {
            result.add(this.runes[i]);
        }
        return result;
    }
    
    public Team clone() {
        Team cloned = new Team(this.maxCost);
        cloned.cards = new Card[10];
        for (int i = 0; i < 10; ++i) {
            cloned.cards[i] = this.cards[i];
        }
        cloned.runes = new Rune[4];
        for (int i = 0; i < 4; ++i) {
            cloned.runes[i] = this.runes[i];
        }
        return cloned;
    }
    
    public void mutate() {
        Randomizer random = Randomizer.getRandomizer();
        for (int i = 0; i < 5; ++i) {
            int mutateTypeIndex = random.next(0, 10);
            boolean mutateCard = mutateTypeIndex < 8;
            if (mutateCard) {
                int mutatedCardIndex = random.next(0, 10);
                Card newCard = getRandomCard();
                this.cards[mutatedCardIndex] = newCard;
            } else {
                int mutatedRuneIndex = random.next(0, 4);
                Rune newRune = createRuneFromData(RuneData.getRandomRune());
                this.runes[mutatedRuneIndex] = newRune;
            }
        }
    }
    
    public String getTeamDesc() {
        StringBuffer sb = new StringBuffer();
        for (Card card : this.cards) {
            sb.append(card.getName());
            sb.append(",");
        }
        for (Rune rune : this.runes) {
            sb.append(rune.getName());
            sb.append(",");
        }
        return sb.toString();
    }
}