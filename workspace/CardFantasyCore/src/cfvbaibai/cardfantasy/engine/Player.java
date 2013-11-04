package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.Legion;
import cfvbaibai.cardfantasy.data.LegionFeature;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.RuneData;

public class Player extends EntityInfo {
    private PlayerInfo playerInfo;
    private Deck deck;
    private Hand hand;
    private Grave grave;
    private Field field;
    private OutField outField;
    private RuneBox runeBox;
    private Map <Race, FeatureInfo> legionBuffFeatures;
    private int hp;
    
    public Player(PlayerInfo playerInfo, StageInfo stage) {
        this.playerInfo = playerInfo;
        this.deck = prepareDeck(stage.getRandomizer());
        this.hand = new Hand(stage.getRule());
        this.grave = new Grave();
        this.field = new Field(this);
        this.outField = new OutField();
        this.runeBox = new RuneBox(this, playerInfo.getRunes());
        this.hp = playerInfo.getMaxHP();
        this.legionBuffFeatures = new HashMap <Race, FeatureInfo>();
        if (playerInfo.getLegion() != null) {
            Legion legion = playerInfo.getLegion();
            LegionInfo legionInfo = new LegionInfo(legion, this);
            for (Race race : Race.values()) {
                legionBuffFeatures.put(race, new FeatureInfo(legionInfo, LegionFeature.create(legion, race)));
            }
        }
    }
    
    public List<CardInfo> getAllCards() {
        List<CardInfo> cards = new ArrayList<CardInfo>();
        cards.addAll(this.deck.getCards());
        cards.addAll(this.hand.getCards());
        cards.addAll(this.grave.getCards());
        cards.addAll(this.field.getCards());
        cards.addAll(this.outField.getCards());
        List<CardInfo> result = new ArrayList<CardInfo>();
        for (CardInfo card : cards) {
            if (card != null) {
                result.add(card);
            }
        }
        return result;
    }
    
    public int getMaxCost() {
        return this.playerInfo.getMaxCost();
    }
    
    public int getCardSlot() {
        return this.playerInfo.getCardSlot();
    }
    
    public int getRuneSlot() {
        return this.playerInfo.getRuneSlot();
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
    
    public OutField getOutField() {
        return this.outField;
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
    
    public FeatureInfo getLegionBuffFeature(Race race) {
        if (this.legionBuffFeatures.containsKey(race)) {
            return this.legionBuffFeatures.get(race);
        } else {
            return null;
        }
    }

    public int getLevel() {
        return this.getPlayerInfo().getLevel();
    }

    @Override
    public CardStatus getStatus() {
        return new CardStatus();
    }

    @Override
    public Player getOwner() {
        return this;
    }
}
