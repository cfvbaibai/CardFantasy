package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class EarthShield {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker, CardInfo victim) throws HeroDieSignal {
        if (attacker == null || attacker.isDead()) {
            return;
        }
        if (attacker.getStatus().containsStatus(CardStatusType.晕眩)) {
            // 例如：横扫两个带大地之盾的卡
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(victim, attacker, skill, true);
        if (!resolver.resolveAttackBlockingSkills(victim, attacker, skill, 1).isAttackable()) {
            return;
        }

        CardStatusItem status = CardStatusItem.faint(skillUseInfo);
        ui.addCardStatus(victim, attacker, skill, status);
        attacker.addStatus(status);
    }
}
