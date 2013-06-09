package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.data.Race;

public class CardInfo {
    private Card card;
    private int hp;
    private int at;
    private int summonDelay;
    private CardStatus status;
    private Player owner;
    private List<FeatureInfo> features;
    // Used to record the previous position after card dies.
    private int cachedPosition;

    private Map<FeatureType, List<FeatureEffect>> effects;
    private boolean firstRound;

    public CardInfo(Card card, Player owner) {
        this.card = card;
        this.hp = card.getMaxHP();
        this.at = card.getInitAT();
        this.summonDelay = card.getSummonSpeed();
        this.status = new CardStatus();
        this.owner = owner;
        this.effects = new HashMap<FeatureType, List<FeatureEffect>>();
        this.features = new ArrayList<FeatureInfo>();
        for (Feature feature : card.getAllFeatures()) {
            this.features.add(new FeatureInfo(this, feature));
        }
        this.cachedPosition = -1;
    }
    
    public boolean isFirstRound() {
        return firstRound;
    }
    
    public void setFirstRound(boolean firstRound) {
        this.firstRound = firstRound;
    }

    public void addEffect(FeatureEffect effect) {
        FeatureType type = effect.getCause().getType();
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

    public void removeStatus(CardStatusType type) {
        this.status.remove(type);
    }

    public String getShortDesc(boolean includePosition) {
        if (includePosition) {
            return String.format("<%s>.<%s>.[%d]", this.getOwner().getId(), this.getCard().getId(), this.getPosition());
        } else {
            return String.format("<%s>.<%s>", this.getOwner().getId(), this.getCard().getId());
        }
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

    private List<FeatureInfo> getAllFeatures() {
        return this.features;
    }

    public Race getRace() {
        return getCard().getRace();
    }

    public int getOriginalAT() {
        return this.at;
    }

    public List<FeatureInfo> getUsableSummonFeatures() {
        return getUsableFeatures(true, false);
    }

    public List<FeatureInfo> getUsableDeathFeatures() {
        return getUsableFeatures(false, true);
    }

    public List<FeatureInfo> getNormalUsableFeatures() {
        return getUsableFeatures(false, false);
    }
    
    public List<FeatureInfo> getAllUsableFeatures() {
        return getUsableFeatures(true, true);
    }

    private List<FeatureInfo> getUsableFeatures(boolean includeSummonFeature, boolean includeDeathFeature) {
        List<FeatureInfo> features = new ArrayList<FeatureInfo>(4);
        for (FeatureInfo feature : this.getAllFeatures()) {
            if (feature.getUnlockLevel() <= this.getCard().getLevel()
                    && (includeSummonFeature || !feature.isSummonFeature())
                    && (includeDeathFeature || !feature.isDeathFeature())) {
                features.add(feature);
            }
        }
        return features;
    }

    public void removeEffect(FeatureEffect effect) {
        List<FeatureEffect> result = this.effects.get(effect.getCause().getType());
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
            if (FeatureInfo.equals(effect.getCause(), feature)) {
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

    public int getLevel() {
        return this.card.getLevel();
    }

    public int getInitAT() {
        return this.card.getInitAT();
    }

    public String getEffectsDesc() {
        List<FeatureEffect> effects = this.getEffects();
        if (effects.isEmpty()) {
            return "-";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("¡¾");
        for (FeatureEffect effect : effects) {
            if (effect.getType() == FeatureEffectType.ATTACK_CHANGE) {
                sb.append("ATC");
            }
            else if (effect.getType() == FeatureEffectType.MAXHP_CHANGE) {
                sb.append("MHC");
            } else {
                throw new CardFantasyRuntimeException("Unknown feature effect type: " + effect.getType().name());
            }
            sb.append("(");
            sb.append(effect.getValue());
            sb.append("):");
            sb.append(effect.getCause().getType().name());
            sb.append(effect.getCause().getLevel());
            if (!effect.isEternal()) {
                sb.append(":");
                sb.append(effect.getSource().getShortDesc(false));
            }
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("¡¿");
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
        for (Feature feature : this.getAllUsableFeatures()) {
            if (feature.getType().containsTag(tag)) {
                return true;
            }
        }
        return false;
    }
}
