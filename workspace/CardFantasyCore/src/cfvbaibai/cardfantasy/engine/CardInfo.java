package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.NonSerializable;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.CardFeature;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.data.Race;

public class CardInfo extends EntityInfo {
    @NonSerializable
    private Card card;
    private int hp;
    private int summonDelay;
    @NonSerializable
    private CardStatus status;
    @NonSerializable
    private Player owner;
    @NonSerializable
    private List<FeatureInfo> features;
    // Used to record the previous position after card dies.
    private int cachedPosition;
    private boolean deadOnce;

    @NonSerializable
    private Map<FeatureType, List<FeatureEffect>> effects;
    //private boolean firstRound;

    public CardInfo(Card card, Player owner) {
        this.card = card;
        this.hp = card.getMaxHP();
        this.summonDelay = card.getSummonSpeed();
        this.status = new CardStatus();
        this.owner = owner;
        this.effects = new HashMap<FeatureType, List<FeatureEffect>>();
        this.features = new ArrayList<FeatureInfo>();
        for (CardFeature feature : card.getAllFeatures()) {
            this.features.add(new FeatureInfo(this, feature));
        }
        this.cachedPosition = -1;
        this.deadOnce = false;
    }
    
    public CardFeature getExtraFeature() {
        return card.getExtraFeature();
    }
    
/*    private boolean isFirstRound() {
        return firstRound;
    }*/
    
    public boolean hasDeadOnce() {
        return this.deadOnce;
    }
    
    void setDeadOnce(boolean deadOnce) {
        this.deadOnce = deadOnce;
    }
    
    //public void setFirstRound(boolean firstRound) {
    //    this.firstRound = firstRound;
    //}

    public void addEffect(FeatureEffect effect) {
        FeatureType type = effect.getCause().getFeature().getType();
        if (!effects.containsKey(type)) {
            effects.put(type, new LinkedList<FeatureEffect>());
        }
        this.effects.get(type).add(effect);
        if (effect.getType() == FeatureEffectType.MAXHP_CHANGE) {
            this.setHP(this.getHP() + effect.getValue());
        }
    }

    public List<FeatureEffect> getEffectsCausedBy(FeatureType cause) {
        List<FeatureEffect> result = effects.get(cause);
        if (result == null) {
            return new ArrayList<FeatureEffect>();
        } else {
            return result;
        }
    }

    public int getPosition() {
        Field field = owner.getField();
        for (int i = 0; i < field.size(); ++i) {
            if (field.getCard(i) == this) {
                cachedPosition = i;
                return i;
            }
        }
        return cachedPosition;
    }

    public Player getOwner() {
        return this.owner;
    }

    private Card getCard() {
        return this.card;
    }

    public int getInitAT() {
        return this.card.getInitAT();
    }
    
    private int getSpecificLevelEffectAT(FeatureTag tag) {
        int at = 0;
        for (List<FeatureEffect> effects : this.effects.values()) {
            for (FeatureEffect effect : effects) {
                if (effect.getType() == FeatureEffectType.ATTACK_CHANGE &&
                    effect.getCause().getType().containsTag(tag)) {
                    at += effect.getValue();
                }
            }
        }
        return at;
    }
    
    public int getLevel0AT() {
        return this.getInitAT() + this.getSpecificLevelEffectAT(FeatureTag.原始攻击加成);
    }

    public int getLevel1AT() {
        return this.getLevel0AT() + this.getSpecificLevelEffectAT(FeatureTag.基础攻击加成);
    }
    
    public int getLevel2AT() {
        return this.getLevel1AT() + this.getSpecificLevelEffectAT(FeatureTag.额外攻击加成);
    }

    public int getLevel3AT() {
        return this.getLevel2AT() + this.getSpecificLevelEffectAT(FeatureTag.独立攻击加成);
    }
    
    public int getCurrentAT() {
        return this.getLevel3AT();
    }

    public int getHP() {
        return this.hp;
    }
    
    private void setHP(int hp) {
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

    public boolean removeStatus(CardStatusType type) {
        return this.status.remove(type);
    }

    @Override
    public String getShortDesc() {
        if (this.getPosition() >= 0) {
            return String.format("<%s>.<%s>.[%d]", this.getOwner().getId(), this.getCard().getUniqueName(), this.getPosition());
        } else {
            return String.format("<%s>.<%s>", this.getOwner().getId(), this.getCard().getUniqueName());
        }
    }

    public void reset() {
        this.hp = this.card.getMaxHP();
        this.status = new CardStatus();
        this.effects.clear();
        this.setDeadOnce(false);
    }

    public void resetSummonDelay() {
        this.setSummonDelay(this.card.getSummonSpeed());
    }

    private List<FeatureInfo> getAllFeatures() {
        return this.features;
    }

    public Race getRace() {
        return getCard().getRace();
    }

    public List<FeatureInfo> getUsableSummonFeatures() {
        List<FeatureInfo> features = new ArrayList<FeatureInfo>();
        for (FeatureInfo feature : this.getAllUsableFeatures()) {
            CardFeature cardFeature = (CardFeature)feature.getFeature();
            if (cardFeature.isSummonFeature()) {
                features.add(feature);
            }
        }
        return features;
    }

    public List<FeatureInfo> getUsableDeathFeatures() {
        List<FeatureInfo> features = new ArrayList<FeatureInfo>();
        for (FeatureInfo feature : this.getAllUsableFeatures()) {
            CardFeature cardFeature = (CardFeature)feature.getFeature();
            if (cardFeature.isDeathFeature()) {
                features.add(feature);
            }
        }
        return features;
    }

    public List<FeatureInfo> getNormalUsableFeatures() {
        List<FeatureInfo> features = new ArrayList<FeatureInfo>();
        for (FeatureInfo feature : this.getAllUsableFeatures()) {
            CardFeature cardFeature = (CardFeature)feature.getFeature();
            if (!cardFeature.isDeathFeature() && !cardFeature.isSummonFeature()) {
                features.add(feature);
            }
        }
        return features;
    }
    
    public List<FeatureInfo> getAllUsableFeatures() {
        return getUsableFeatures();
    }

    private List<FeatureInfo> getUsableFeatures() {
        List<FeatureInfo> features = new ArrayList<FeatureInfo>(4);
        for (FeatureInfo feature : this.getAllFeatures()) {
            CardFeature cardFeature = (CardFeature)feature.getFeature();
            if (cardFeature.getUnlockLevel() <= this.getCard().getLevel()) {
                features.add(feature);
            }
        }
        return features;
    }

    public void removeEffect(FeatureEffect effect) {
        List<FeatureEffect> result = this.effects.get(effect.getCause().getFeature().getType());
        if (result == null) {
            return;
        }
        result.remove(effect);
        if (effect.getType() == FeatureEffectType.MAXHP_CHANGE && this.getHP() > this.getMaxHP()) {
            this.setHP(this.getMaxHP());
        }
    }

    public List<FeatureEffect> getEffects() {
        List<FeatureEffect> result = new ArrayList<FeatureEffect>();
        for (List<FeatureEffect> effects : this.effects.values()) {
            result.addAll(effects);
        }
        return result;
    }

    public List<FeatureEffect> getEffectsCausedBy(FeatureInfo feature) {
        if (feature.getOwner() == null) {
            throw new CardFantasyRuntimeException("feature.getOwner() is null");
        }
        List<FeatureEffect> result = new ArrayList<FeatureEffect>();
        for (FeatureEffect effect : this.getEffects()) {
            if (effect.getCause().equals(feature)) {
                result.add(effect);
            }
        }
        return result;
    }

    public int getMaxHP() {
        int actualMaxHP = this.card.getMaxHP();
        for (FeatureEffect effect : this.getEffects()) {
            if (effect.getType() == FeatureEffectType.MAXHP_CHANGE) {
                actualMaxHP += effect.getValue();
            }
        }
        return actualMaxHP;
    }
    
    public String getId() {
        return this.card.getId();
    }

    public String getUniqueName() {
        return this.card.getUniqueName();
    }
    
    public String getName() {
        return this.card.getName();
    }

    public int getLevel() {
        return this.card.getLevel();
    }

    public String getEffectsDesc() {
        List<FeatureEffect> effects = this.getEffects();
        if (effects.isEmpty()) {
            return "-";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("【");
        for (FeatureEffect effect : effects) {
            if (effect.getType() == FeatureEffectType.ATTACK_CHANGE) {
                sb.append("攻击变化");
            }
            else if (effect.getType() == FeatureEffectType.MAXHP_CHANGE) {
                sb.append("HP变化");
            } else if (effect.getType() == FeatureEffectType.SKILL_USED) {
                sb.append("技能已使用");
            } else {
                throw new CardFantasyRuntimeException("Unknown feature effect type: " + effect.getType().name());
            }
            sb.append("(");
            sb.append(effect.getValue());
            sb.append("):");
            sb.append(effect.getCause().getFeature().getType().name());
            sb.append(effect.getCause().getFeature().getLevel());
            if (!effect.isEternal()) {
                sb.append(":");
                sb.append(effect.getSource().getShortDesc());
            }
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("】");
        return sb.toString();
    }

    public boolean isDead() {
        Field field = owner.getField();
        for (int i = 0; i < field.size(); ++i) {
            if (field.getCard(i) == this) {
                return false;
            }
        }
        return true;
    }

    public boolean isWeak() {
        return this.getStatus().containsStatus(CardStatusType.虚弱);
    }

    /**
     * Damage is less than 0 indicates a heal. It also return a negative number as actual heal. 
     * @param damage
     * @return actual damage.
     */
    public int applyDamage(int damage) {
        if (damage > 0) {
            int originalHP = this.getHP();
            if (originalHP < damage) {
                this.setHP(0);
                return originalHP;
            } else {
                this.setHP(originalHP - damage);
                return damage;
            }
        } else if (damage == 0) {
            return 0;
        } else {
            int lostHP = this.getMaxHP() - this.getHP();
            int healHP = -damage;
            if (lostHP < healHP) {
                this.setHP(this.getMaxHP());
                return -lostHP;
            } else {
                this.setHP(this.getHP() + healHP);
                return -healHP;
            }
        }
    }

    public int getOriginalMaxHP() {
        return this.card.getMaxHP();
    }

    public boolean containsUsableFeaturesWithTag(FeatureTag tag) {
        for (FeatureInfo feature : this.getAllUsableFeatures()) {
            CardFeature cardFeature = (CardFeature) feature.getFeature();
            if (cardFeature.getType().containsTag(tag)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isSameType(CardInfo victim, CardInfo defender) {
        return victim.getCard().getName().equals(defender.getCard().getName());
    }

    public int getLostHP() {
        return this.getMaxHP() - this.getHP();
    }

    public void refreshPosition() {
        getPosition();
    }

    public boolean isWounded() {
        return this.getHP() < this.getMaxHP();
    }

    public int getSummonSpeed() {
        return this.card.getSummonSpeed();
    }
    
    public boolean containsUsableFeature(FeatureType type) {
        List<FeatureInfo> features = this.getAllUsableFeatures();
        for (FeatureInfo featureInfo : features) {
            if (featureInfo.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public int getStar() {
        return this.card.getStar();
    }

    public boolean hasUsed(FeatureInfo featureInfo) {
        for (FeatureEffect effect : this.getEffects()) {
            if (effect.getCause() == featureInfo && effect.getType() == FeatureEffectType.SKILL_USED) {
                return true;
            }
        }
        return false;
    }
    
    public void setUsed(FeatureInfo featureInfo) {
        this.addEffect(new FeatureEffect(FeatureEffectType.SKILL_USED, featureInfo, 0, true));
    }
}