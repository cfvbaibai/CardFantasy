package cfvbaibai.cardfantasy.data;

import cfvbaibai.cardfantasy.engine.CardInfo;

public abstract class BuffSkill extends Skill {

    public BuffSkill(SkillType type, int level) {
        super(type, level);
    }

    public abstract boolean canApplyTo(CardInfo card);
}
