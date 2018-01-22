package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

/**
 * Created by york on 2017/4/25.
 */
public final class DefeatArmy {
    public static boolean isDefenSkillDisabled(SkillResolver resolver, Skill attackSkill,
                                               Skill blockSkill, CardInfo attacker, CardInfo defender) {
        int rate = attackSkill.getImpact();
        boolean bingo = resolver.getStage().getRandomizer().roll100(rate);
        if (attacker == null) {
            throw new CardFantasyRuntimeException("attacker is null");
        }
        if (defender == null) {
            throw new CardFantasyRuntimeException("defender is null");
        }
        if (blockSkill.getType().containsTag(SkillTag.物理护甲) || blockSkill.getType() == SkillType.水流护甲
                || blockSkill.getType() == SkillType.格挡 || blockSkill.getType() == SkillType.钢铁之肤
                || blockSkill.getType() == SkillType.水流护甲 || blockSkill.getType() == SkillType.圣盾 || blockSkill.getType() == SkillType.无刀取 || blockSkill.getType() == SkillType.骑士守护) {
            resolver.getStage().getUI().useSkill(attacker, defender, attackSkill, true);
            resolver.getStage().getUI().disableBlock(attacker, defender, attackSkill, blockSkill);

        }
        if(bingo){
            return true;
        }
        else {
            return false;
        }
    }
}
