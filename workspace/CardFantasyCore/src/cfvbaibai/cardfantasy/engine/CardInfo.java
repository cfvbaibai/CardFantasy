package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.data.Race;

public class CardInfo {
    private Card card;
    private int hp;
    private int at;
    private int summonDelay;
    private CardStatus status;
    private Player owner;
    
    private Map<FeatureType, List<FeatureEffect>> effects;

    public CardInfo(Card card, Player owner) {
        this.card = card;
        this.hp = card.getMaxHP();
        this.at = card.getInitAT();
        this.summonDelay = card.getSummonSpeed();
        this.status = new CardStatus();
        this.owner = owner;
        this.effects = new HashMap<FeatureType, List<FeatureEffect>>();
    }
    
    public void addEffect(FeatureEffect effect) {
        if (!effects.containsKey(effect.getCause())) {
            effects.put(effect.getCause(), new LinkedList<FeatureEffect>());
        }
        this.effects.get(effect.getCause()).add(effect);
    }
    
    public void removeEffectsCausedBy(FeatureType cause) {
        effects.remove(cause);
    }
    
    public List<FeatureEffect> getEffectsCauseBy(FeatureType cause) {
        return effects.get(cause);
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
        int actualAT = this.getOriginalAT();
        for (List<FeatureEffect> effects : this.effects.values()) {
            for (FeatureEffect effect : effects) {
                if (effect.getType() == FeatureEffectType.ATTACK_CHANGE) {
                    actualAT += effect.getValue();
                }
            }
        }
        return actualAT;
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

    public void addStatus(CardStatusItem statusItem) {
        this.status.add(statusItem);
    }
    
    public void removeStatus(CardStatusType type) {
        this.status.remove(type);
    }

    public String getShortDesc() {
        int position = this.getPosition();
        return String.format("<%s>.<%s%s>", this.getOwner().getId(), this.getCard().getName(), position < 0 ? "" : String.valueOf(position));
    }

    public void reset() {
        this.hp = this.card.getMaxHP();
        this.at = this.card.getInitAT();
        this.status = new CardStatus();
        this.effects.clear();
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

    public Race getRace() {
        return getCard().getRace();
    }

    public int getOriginalAT() {
        return this.at;
    }

    public List<Feature> getUsableFeatures() {
        List<Feature> features = new ArrayList<Feature>(4);
        for (Feature feature : getAllFeatures()) {
            if (feature.getUnlockLevel() <= this.getCard().getLevel()) {
                features.add(feature);
            }
        }
        return features;
    }

    public void removeEffect(FeatureEffect effect) {
        this.effects.remove(effect);
    }

    public List<FeatureEffect> getEffects() {
        List<FeatureEffect> result = new ArrayList<FeatureEffect>();
        for (List<FeatureEffect> effects : this.effects.values()) {
            result.addAll(effects);
        }
        return result;
    }
 }
