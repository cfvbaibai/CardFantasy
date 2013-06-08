package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.PlayerInfo;

public class Player {
    private PlayerInfo playerInfo;
    private Deck deck;
    private Hand hand;
    private Grave grave;
    private Field field;
    private int hp;
    
    public Player(PlayerInfo playerInfo, StageInfo stage) {
        this.playerInfo = playerInfo;
        this.deck = prepareDeck();
        this.hand = new Hand(stage.getRule());
        this.grave = new Grave();
        this.field = new Field(this);
        this.hp = playerInfo.getMaxHP();
    }
    
    private PlayerInfo getPlayerInfo() {
        return this.playerInfo;
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

    public int getLife() {
        return this.hp;
    }
    
    public int getMaxLife() {
        return this.playerInfo.getMaxHP();
    }

    public String getId() {
        return this.getPlayerInfo().getId();
    }

    public void setLife(int hp) throws HeroDieSignal {
        this.hp = hp;
        if (this.hp <= 0) {
            throw new HeroDieSignal(this);
        }
    }
    
    private Deck prepareDeck() {
        Collection <Card> cards = this.getPlayerInfo().getCards();
        List<CardInfo> cardInfos = new ArrayList<CardInfo>();
        for (Card card : cards) {
            cardInfos.add(new CardInfo(card, this));
        }
        return new Deck(cardInfos);
    }

    public String getShortDesc() {
        return String.format("<%s>", this.playerInfo.getId());
    }
}
