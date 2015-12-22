package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.NonSerializable;

public class CardStatusItem {
    private CardStatusType type;
    private int effect;
    @NonSerializable
    private SkillUseInfo cause;

    private CardStatusItem(CardStatusType type, int effect, SkillUseInfo cause) {
        this.type = type;
        this.effect = effect;
        this.cause = cause;
    }

    public SkillUseInfo getCause() {
        return this.cause;
    }

    public CardStatusType getType() {
        return type;
    }

    public void setType(CardStatusType type) {
        this.type = type;
    }

    public int getEffect() {
        return effect;
    }

    public void setEffect(int effect) {
        this.effect = effect;
    }

    public static CardStatusItem paralyzed(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.麻痹, 0, cause);
    }

    public static CardStatusItem frozen(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.冰冻, 0, cause);
    }

    public static CardStatusItem poisoned(int effect, SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.中毒, effect, cause);
    }

    public static CardStatusItem trapped(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.锁定, 0, cause);
    }

    public static CardStatusItem burning(int effect, SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.燃烧, effect, cause);
    }

    public static CardStatusItem wound(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.裂伤, 0, cause);
    }

    public static CardStatusItem weak(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.复活, 0, cause);
    }

    public static CardStatusItem confused(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.迷惑, 0, cause);
    }

    public static CardStatusItem softened(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.弱化, 0, cause);
    }

    public static CardStatusItem summoned(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.召唤, 0, cause);
    }

    public static CardStatusItem faint(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.晕眩, 0, cause);
    }

    public static CardStatusItem unbending(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.不屈, 0, cause);
    }

    public static CardStatusItem deathMark(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.死印, 0, cause);
    }

    public static CardStatusItem magicMark(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.魔印, 0, cause);
    }
    
    public static CardStatusItem blind(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.致盲, 0, cause);
    }

    public static CardStatusItem raceChange(SkillUseInfo cause) {
        CardStatusType type = null;
        switch (cause.getType().getImpact(0)) {
            case 0: type = CardStatusType.王国; break;
            case 1: type = CardStatusType.森林; break;
            case 2: type = CardStatusType.蛮荒; break;
            case 3: type = CardStatusType.地狱; break;
        }
        return new CardStatusItem(type, 0, cause);
    }
    
    public static CardStatusItem slience(SkillUseInfo cause) {
        return new CardStatusItem(CardStatusType.沉默, 0, cause);
    }

    public String getShortDesc() {
        StringBuffer sb = new StringBuffer();
        sb.append(getType().name());
        if (getType().isQuantitive()) {
            sb.append("(");
            sb.append(getEffect());
            sb.append(")");
        }
        sb.append(":");
        sb.append(cause.getSkill().getType().name());
        sb.append(cause.getSkill().getLevel());
        sb.append(":");
        sb.append(cause.getOwner().getShortDesc());
        return sb.toString();
    }
}