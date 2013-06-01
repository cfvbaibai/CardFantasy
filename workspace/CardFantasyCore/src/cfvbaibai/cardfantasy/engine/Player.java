package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.data.PlayerInfo;

public class Player {
    private PlayerInfo playerInfo;
    private Deck deck;
    private Hand hand;
    private Grave grave;
    private Field field;
    private int life;
    
    public Player(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
        this.deck = playerInfo.prepareDeck();
        this.hand = new Hand();
        this.grave = new Grave();
        this.field = new Field();
        this.life = playerInfo.getMaxLife();
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
        return this.life;
    }

    public String getId() {
        return this.getPlayerInfo().getId();
    }
}
