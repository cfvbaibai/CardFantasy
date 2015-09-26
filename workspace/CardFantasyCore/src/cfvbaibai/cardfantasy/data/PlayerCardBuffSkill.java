package cfvbaibai.cardfantasy.data;

import cfvbaibai.cardfantasy.engine.CardInfo;

public class PlayerCardBuffSkill extends BuffSkill {
    public PlayerCardBuffSkill(SkillType type, int level) {
        super(type, level);
    }

    @Override
    public boolean canApplyTo(CardInfo card) {
        return true;
    }
}