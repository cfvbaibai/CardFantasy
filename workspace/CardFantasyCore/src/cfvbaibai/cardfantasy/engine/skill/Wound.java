package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class Wound {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, Skill attackSkill, CardInfo attacker, CardInfo defender,
            int normalAttackDamage) {
        if (normalAttackDamage <= 0) {
            return;
        }
        if (defender.isDead()) {
            return;
        }
        if (attackSkill != null && attackSkill.getType() == SkillType.横扫) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        resolver.getStage().getUI().useSkill(attacker, defender, skill, true);
        CardStatusItem status = CardStatusItem.wound(skillUseInfo);
        resolver.getStage().getUI().addCardStatus(attacker, defender, skill, status);
        defender.addStatus(status);
    }
}
