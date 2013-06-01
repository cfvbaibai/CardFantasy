package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;

public class CardInfo {
    private Card card;
    private int hp;
    private int at;
    private int summonDelay;
    private CardStatus status;
    private Player owner;

    public CardInfo(Card card, Player owner) {
        this.card = card;
        this.hp = card.getMaxHP();
        this.at = card.getInitAT();
        this.summonDelay = card.getSummonSpeed();
        this.status = CardStatus.normal();
        this.owner = owner;
    }
    
    public int getPosition() {
        Field field = owner.getField();
        for (int i = 0; i < field.size(); ++i) {
            if (field.getCard(i) == this) {
                return i;
            }
        }
        return -1;
    }
    
    public Player getOwner() {
        return this.owner;
    }

    public Card getCard() {
        return this.card;
    }

    public int getAT() {
        return this.at;
    }
    
    public void setAT(int at) {
        this.at = at;
    }
    
    public int getHP() {
        return this.hp;
    }
    
    public void setHP(int hp) {
        this.hp = hp;
    }

    public int getSummonDelay() {
        return this.summonDelay;
    }

    public void setSummonDelay(int summonDelay) {
        this.summonDelay = summonDelay;
    }
    
    public CardStatus getStatus() {
        return status;
    }

    public void setStatus(CardStatus status) {
        this.status = status;
    }

    public String getShortDesc() {
        int position = this.getPosition();
        return String.format("<%s>.<%s%s>", this.getOwner().getId(), this.getCard().getName(), position < 0 ? "" : String.valueOf(position));
    }

    public void reset() {
        this.hp = this.card.getMaxHP();
        this.at = this.card.getInitAT();
        this.status = CardStatus.normal();
    }

    public void resetSummonDelay() {
        this.setSummonDelay(this.card.getSummonSpeed());
    }

    public List <Feature> getAllFeatures() {
        return this.card.getAllFeatures();
    }
    
    public List<Feature> getUsableFeaturesOf(FeatureType type) {
        List<Feature> features = new ArrayList<Feature>(4);
        for (Feature feature : getAllFeatures()) {
            if (feature.getType() == type && feature.getUnlockLevel() <= this.getCard().getLevel()) {
                features.add(feature);
            }
        }
        return features;
    }
 }
