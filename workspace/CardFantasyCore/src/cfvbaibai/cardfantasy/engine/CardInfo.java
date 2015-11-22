package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.NonSerializable;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.PlayerCardBuffSkill;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;

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
    private List<SkillUseInfo> skillUseInfos;
    // Used to record the previous position after card dies.
    private int cachedPosition;
    private boolean deadOnce;
    
    private int eternalWound = 0;

    @NonSerializable
    private Map<SkillType, List<SkillEffect>> effects;
    //private boolean firstRound;

    public CardInfo(Card card, Player owner) {
        this.card = card;
        this.hp = card.getMaxHP();
        this.summonDelay = card.getSummonSpeed();
        this.status = new CardStatus();
        this.owner = owner;
        this.effects = new HashMap<SkillType, List<SkillEffect>>();
        this.skillUseInfos = new ArrayList<SkillUseInfo>();
        for (CardSkill cardSkill : card.getAllSkills()) {
            this.skillUseInfos.add(new SkillUseInfo(this, cardSkill));
        }
        this.cachedPosition = -1;
        this.deadOnce = false;
    }
    
    public CardSkill getExtraSkill() {
        return card.getExtraSkill();
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

    public void addEffect(SkillEffect effect) {
        SkillType type = effect.getCause().getSkill().getType();
        if (!effects.containsKey(type)) {
            effects.put(type, new LinkedList<SkillEffect>());
        }
        this.effects.get(type).add(effect);
        if (effect.getType() == SkillEffectType.MAXHP_CHANGE) {
            this.setBasicHP(this.getHP() + effect.getValue());
        }
    }

    public List<SkillEffect> getEffectsCausedBy(SkillType cause) {
        List<SkillEffect> result = effects.get(cause);
        if (result == null) {
            return new ArrayList<SkillEffect>();
        } else {
            return new ArrayList<SkillEffect>(result);
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

    public void setOwner(Player owner) {
        this.owner = owner;
    }

    private Card getCard() {
        return this.card;
    }

    public int getInitAT() {
        return this.card.getInitAT();
    }
    
    private int getSpecificLevelEffectAT(SkillTag tag) {
        int at = 0;
        for (List<SkillEffect> effects : this.effects.values()) {
            for (SkillEffect effect : effects) {
                if (effect.getType() == SkillEffectType.ATTACK_CHANGE &&
                    effect.getCause().getType().containsTag(tag)) {
                    at += effect.getValue();
                }
            }
        }
        return at;
    }
    
    public int getLevel0AT() {
        return this.getInitAT() + this.getSpecificLevelEffectAT(SkillTag.原始攻击加成);
    }

    public int getLevel1AT() {
        return this.getLevel0AT() + this.getSpecificLevelEffectAT(SkillTag.基础攻击加成);
    }
    
    public int getLevel2AT() {
        return this.getLevel1AT() + this.getSpecificLevelEffectAT(SkillTag.额外攻击加成);
    }

    public int getLevel3AT() {
        return this.getLevel2AT() + this.getSpecificLevelEffectAT(SkillTag.独立攻击加成);
    }
    
    public int getCurrentAT() {
        return this.getLevel3AT();
    }

    public int getHP() {
        return this.hp;
    }
    
    public CardInfo setBasicHP(int hp) {
        this.hp = hp;
        return this;
    }

    public int getSummonDelay() {
        return this.summonDelay;
    }

    public CardInfo setSummonDelay(int summonDelay) {
        this.summonDelay = summonDelay;
        return this;
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

    private List<SkillUseInfo> getAllSkills() {
        return this.skillUseInfos;
    }

    public Race getRace() {
        Race race = this.getOriginalRace();
        List<CardStatusItem> items = this.getStatus().getAllItems();
        for (CardStatusItem item : items) {
            if (item.getType() == CardStatusType.王国) {
                race = Race.KINGDOM;
            } else if (item.getType() == CardStatusType.森林) {
                race = Race.FOREST;
            } else if (item.getType() == CardStatusType.蛮荒) {
                race = Race.SAVAGE;
            } else if (item.getType() == CardStatusType.地狱) {
                race = Race.HELL;
            }
        }
        return race;
    }
    
    public Race getOriginalRace() {
        return this.getCard().getRace();
    }
    
    public boolean isSummonedMinion() {
        return this.getStatus().containsStatus(CardStatusType.召唤);
    }

    public List<SkillUseInfo> getUsableSummonSkills() {
        List<SkillUseInfo> skillUseInfos = new ArrayList<SkillUseInfo>();
        for (SkillUseInfo skillUseInfo : this.getAllUsableSkills()) {
            CardSkill cardSkill = (CardSkill)skillUseInfo.getSkill();
            if (cardSkill.isSummonSkill()) {
                skillUseInfos.add(skillUseInfo);
            }
        }
        return skillUseInfos;
    }

    public List<SkillUseInfo> getUsableDeathSkills() {
        List<SkillUseInfo> skillUseInfos = new ArrayList<SkillUseInfo>();
        for (SkillUseInfo skillUseInfo : this.getAllUsableSkills()) {
            CardSkill cardSkill = (CardSkill)skillUseInfo.getSkill();
            if (cardSkill.isDeathSkill()) {
                skillUseInfos.add(skillUseInfo);
            }
        }
        return skillUseInfos;
    }

    public List<SkillUseInfo> getNormalUsableSkills() {
        List<SkillUseInfo> skillUseInfos = new ArrayList<SkillUseInfo>();
        for (SkillUseInfo skillUseInfo : this.getAllUsableSkills()) {
            CardSkill cardSkill = (CardSkill)skillUseInfo.getSkill();
            if (!cardSkill.isDeathSkill() && !cardSkill.isSummonSkill()) {
                skillUseInfos.add(skillUseInfo);
            }
        }
        return skillUseInfos;
    }
    
    public List<SkillUseInfo> getAllUsableSkills() {
        return getUsableSkills();
    }

    private List<SkillUseInfo> getUsableSkills() {
        List<SkillUseInfo> skillUseInfos = new ArrayList<SkillUseInfo>(6);
        for (SkillUseInfo skillUseInfo : this.getAllSkills()) {
            CardSkill cardSkill = (CardSkill)skillUseInfo.getSkill();
            if (cardSkill.getUnlockLevel() <= this.getCard().getLevel()) {
                skillUseInfos.add(skillUseInfo);
            }
        }
        return skillUseInfos;
    }

    public void removeEffect(SkillEffect effect) {
        List<SkillEffect> result = this.effects.get(effect.getCause().getSkill().getType());
        if (result == null) {
            return;
        }
        result.remove(effect);
        if (effect.getType() == SkillEffectType.MAXHP_CHANGE && this.getHP() > this.getMaxHP()) {
            this.setBasicHP(this.getMaxHP());
        }
    }

    public List<SkillEffect> getEffects() {
        List<SkillEffect> result = new ArrayList<SkillEffect>();
        for (List<SkillEffect> effects : this.effects.values()) {
            result.addAll(effects);
        }
        return result;
    }

    public List<SkillEffect> getEffectsCausedBy(SkillUseInfo skillUseInfo) {
        if (skillUseInfo.getOwner() == null) {
            throw new CardFantasyRuntimeException("skillUseInfo.getOwner() is null");
        }
        List<SkillEffect> result = new ArrayList<SkillEffect>();
        for (SkillEffect effect : this.getEffects()) {
            if (effect.getCause().equals(skillUseInfo)) {
                result.add(effect);
            }
        }
        return result;
    }

    public int getMaxHP() {
        int actualMaxHP = this.card.getMaxHP();
        for (SkillEffect effect : this.getEffects()) {
            if (effect.getType() == SkillEffectType.MAXHP_CHANGE) {
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
        List<SkillEffect> effects = this.getEffects();
        if (effects.isEmpty()) {
            return "-";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("【");
        for (SkillEffect effect : effects) {
            if (effect.getType() == SkillEffectType.ATTACK_CHANGE) {
                sb.append("攻击变化");
            }
            else if (effect.getType() == SkillEffectType.MAXHP_CHANGE) {
                sb.append("HP变化");
            } else if (effect.getType() == SkillEffectType.SKILL_USED) {
                sb.append("技能已使用");
            } else if (effect.getType() == SkillEffectType.SKILL_AWAKEN) {
                sb.append("技能觉醒");
            } else {
                throw new CardFantasyRuntimeException("Unknown skill effect type: " + effect.getType().name());
            }
            sb.append("(");
            sb.append(effect.getValue());
            sb.append("):");
            sb.append(effect.getCause().getSkill().getType().name());
            sb.append(effect.getCause().getSkill().getLevel());
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

    public boolean justRevived() {
        return this.getStatus().containsStatus(CardStatusType.复活);
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
                this.setBasicHP(0);
                return originalHP;
            } else {
                this.setBasicHP(originalHP - damage);
                return damage;
            }
        } else if (damage == 0) {
            return 0;
        } else {
            int lostHP = this.getMaxHP() - this.getHP();
            int healHP = -damage;
            if (lostHP < healHP) {
                this.setBasicHP(this.getMaxHP());
                return -lostHP;
            } else {
                this.setBasicHP(this.getHP() + healHP);
                return -healHP;
            }
        }
    }

    public int getRawMaxHP() {
        return this.card.getMaxHP();
    }
        

    public int getBasicMaxHP() {
        int actualMaxHP = this.card.getMaxHP();
        for (SkillEffect effect : this.getEffects()) {
            if (effect.getType() == SkillEffectType.MAXHP_CHANGE &&
                effect.getCause().getType().containsTag(SkillTag.原始体力加成)) {
                actualMaxHP += effect.getValue();
            }
        }
        return actualMaxHP;
    }
    
    public boolean containsUsableSkillsWithTag(SkillTag tag) {
        for (SkillUseInfo skillUseInfo : this.getAllUsableSkills()) {
            CardSkill cardSkill = (CardSkill) skillUseInfo.getSkill();
            if (cardSkill.getType().containsTag(tag)) {
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
    
    public boolean containsUsableSkill(SkillType type) {
        List<SkillUseInfo> skillUseInfos = this.getAllUsableSkills();
        for (SkillUseInfo skillUseInfo : skillUseInfos) {
            if (skillUseInfo.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public int getStar() {
        return this.card.getStar();
    }

    public boolean hasUsed(SkillUseInfo skillUseInfo) {
        for (SkillEffect effect : this.getEffects()) {
            if (effect.getCause() == skillUseInfo && effect.getType() == SkillEffectType.SKILL_USED) {
                return true;
            }
        }
        return false;
    }
    
    public void setUsed(SkillUseInfo skillUseInfo) {
        this.addEffect(new SkillEffect(SkillEffectType.SKILL_USED, skillUseInfo, 0, true));
    }

    public boolean isFullyControlled() {
        CardStatus status = this.getStatus();
        return
            status.containsStatus(CardStatusType.冰冻) ||
            status.containsStatus(CardStatusType.晕眩) ||
            status.containsStatus(CardStatusType.迷惑) ||
            status.containsStatus(CardStatusType.锁定);
    }

    public void applySurvivalStatus() {
        Skill skill = new PlayerCardBuffSkill(SkillType.原始体力调整, -eternalWound);
        SkillUseInfo skillUseInfo = new SkillUseInfo(this.getOwner(), skill);
        SkillEffect effect = new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, -eternalWound, true);
        this.addEffect(effect);
    }

    public void carveEternalWound() {
        this.eternalWound += this.getMaxHP() - this.getHP();
    }
    
    public int getEternalWound() {
        return this.eternalWound;
    }

    public void setRemainingHP(int remainingHP) {
        this.eternalWound += this.getMaxHP() - remainingHP;
    }

    public boolean isAwaken(SkillUseInfo skillUseInfo, Race race) {
        if (this.isDead()) {
            return false;
        }
        List<SkillEffect> skillEffects = this.getEffectsCausedBy(skillUseInfo);
        for (SkillEffect effect : skillEffects) {
            if (effect.getType() == SkillEffectType.SKILL_AWAKEN) {
                return true;
            }
        }
        List<CardInfo> aliveCards = this.getOwner().getField().getAliveCards();
        for (CardInfo aliveCard : aliveCards) {
            if (aliveCard.getRace() == race) {
                this.addEffect(new SkillEffect(SkillEffectType.SKILL_AWAKEN, skillUseInfo, 0, true));
                return true;
            }
        }
        return false;
    }
}