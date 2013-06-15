package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.RuneData;

public class Player {
    private PlayerInfo playerInfo;
    private Deck deck;
    private Hand hand;
    private Grave grave;
    private Field field;
    private RuneBox runeBox;
    private int hp;
    
    public Player(PlayerInfo playerInfo, StageInfo stage) {
        this.playerInfo = playerInfo;
        this.deck = prepareDeck(stage.getRandomizer());
        this.hand = new Hand(stage.getRule());
        this.grave = new Grave();
        this.field = new Field(this);
        this.runeBox = new RuneBox(this, playerInfo.getRunes());
        this.hp = playerInfo.getMaxHP();
    }
    
    private PlayerInfo getPlayerInfo() {
        return this.playerInfo;
    }
    
    public RuneBox getRuneBox() {
        return this.runeBox;
    }
    
    public Hand getHand() {
        return this.hand;
    }
    
    public Deck getDeck() {
        return this.deck;
    }
    
    public Grave getGrave() {
        return this.grave;
    }
    
    public Field getField() {
        return this.field;
    }

    public int getHP() {
        return this.hp;
    }
    
    public int getMaxHP() {
        return this.playerInfo.getMaxHP();
    }

    public String getId() {
        return this.getPlayerInfo().getId();
    }

    public void setHP(int hp) throws HeroDieSignal {
        this.hp = hp;
        if (this.hp <= 0) {
            throw new HeroDieSignal(this);
        }
    }
    
    private Deck prepareDeck(Randomizer randomizer) {
        Collection <Card> cards = this.getPlayerInfo().getCards();
        List<CardInfo> cardInfos = new ArrayList<CardInfo>();
        for (Card card : cards) {
            cardInfos.add(new CardInfo(card, this));
        }
        return new Deck(cardInfos, randomizer);
    }

    public String getShortDesc() {
        return String.format("<%s>", this.playerInfo.getId());
    }

    public RuneInfo getActiveRuneOf(RuneData runeData) {
        RuneInfo rune = this.getRuneBox().getRuneOf(runeData);
        if (rune == null) {
            return null;
        } else if (rune.isActivated()) {
            return rune;
        } else {
            return null;
        }
    }
}
