package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.RuneInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

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

        //现在反射装甲不完全防御破军了
//        //反射装甲可以免疫破军
//        if(defender.containsUsableSkill(SkillType.反射装甲)||defender.containsUsableSkill(SkillType.LETITGO)||defender.containsUsableSkill(SkillType.击溃)||defender.containsUsableSkill(SkillType.高位逼抢))
//        {
//                return false;
//        }
//        RuneInfo rune = defender.getOwner().getActiveRuneOf(RuneData.升阳);
//        if (rune != null && !defender.justRevived()) {
//                return false;
//        }
//        if (blockSkill.getType().containsTag(SkillTag.物理护甲) || blockSkill.getType() == SkillType.水流护甲
//                || blockSkill.getType() == SkillType.格挡
//                || blockSkill.getType() == SkillType.钢铁之肤
//                || blockSkill.getType() == SkillType.水流护甲
//                || blockSkill.getType() == SkillType.圣盾
//                || blockSkill.getType() == SkillType.无刀取
//                || blockSkill.getType() == SkillType.骑士守护
//                || blockSkill.getType() == SkillType.邪甲术
//                || blockSkill.getType() == SkillType.不朽原核
//                || blockSkill.getType() == SkillType.白袍银甲
//                || blockSkill.getType() == SkillType.魔族之血) {
//
//
//        }
        if(bingo){
            resolver.getStage().getUI().useSkill(attacker, defender, attackSkill, true);
            resolver.getStage().getUI().disableBlock(attacker, defender, attackSkill, blockSkill);
            return true;
        }
        else {
            return false;
        }
    }
}
