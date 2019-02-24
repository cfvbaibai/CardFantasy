package cfvbaibai.cardfantasy.game;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.Combination;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.Indenture;
import cfvbaibai.cardfantasy.data.Rune;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Player;

public class DeckStartupInfo {
    private List<Rune> runes;
    private List<Card> cards;
    private List<Indenture> indentures;
    public DeckStartupInfo() {
        this.runes = new ArrayList<Rune>();
        this.cards = new ArrayList<Card>();
        this.indentures = new ArrayList<Indenture>();
    }
    public DeckStartupInfo(List<Rune> runes, List<Card> cards) {
        this.runes = new ArrayList<Rune>(runes);
        this.cards = new ArrayList<Card>(cards);
        this.indentures = new ArrayList<Indenture>();
    }
    public DeckStartupInfo(List<Rune> runes, List<Card> cards,List<Indenture> indentures) {
        this.runes = new ArrayList<Rune>(runes);
        this.cards = new ArrayList<Card>(cards);
        this.indentures = new ArrayList<Indenture>(indentures);
    }
    public List <Rune> getRunes() {
        return new ArrayList<Rune>(this.runes);
    }
    public List <Card> getCards() {
        return new ArrayList<Card>(this.cards);
    }
    public void addCard(Card card) {
        this.cards.add(card);
    }
    public void addRune(Rune rune) {
        this.runes.add(rune);
    }

    public List <Indenture> getIndentures() {
        return new ArrayList<Indenture>(this.indentures);
    }
    public void addIndentures(Indenture indenture) {
        this.indentures.add(indenture);
    }

    public List<DeckStartupInfo> generateCombinations(int runeCount, int cardCount) {
        if (runeCount > runes.size()) {
            runeCount = runes.size();
        }
        if (cardCount > cards.size()) {
            cardCount = cards.size();
        }
        List<List<Rune>> runeCombs = Combination.calculate(runeCount, runes);
        System.out.println("runeCombs.size() = " + runeCombs.size());
        // Add one special entry: No runes.
        if (runeCombs.isEmpty()) {
            runeCombs.add(new ArrayList<Rune>());
        }
        List<List<Card>> cardCombs = Combination.calculate(cardCount, cards);
        System.out.println("cardCombs.size() = " + cardCombs.size());
        List<DeckStartupInfo> decks = new ArrayList<DeckStartupInfo>(runeCombs.size() * cardCombs.size());
        for (int r = 0; r < runeCombs.size(); ++r) {
            for (int c = 0; c < cardCombs.size(); ++c) {
                decks.add(new DeckStartupInfo(runeCombs.get(r), cardCombs.get(c)));
            }
        }
        return decks;
    }

    public CardInfo getCardInfo(int index, Player owner) {
        return new CardInfo(this.cards.get(index), owner);
    }

    public List<CardInfo> getCardInfos(Player owner) {
        List<CardInfo> result = new ArrayList<CardInfo>(this.cards.size());
        for (int i = 0; i < this.cards.size(); ++i) {
            result.add(this.getCardInfo(i, owner));
        }
        return result;
    }
}
