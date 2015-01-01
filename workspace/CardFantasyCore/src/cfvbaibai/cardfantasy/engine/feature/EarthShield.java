package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class EarthShield {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, CardInfo victim) throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(victim, attacker, skill, true);
        if (!resolver.resolveAttackBlockingFeature(attacker, victim, skill, 1).isAttackable()) {
            return;
        }

        CardStatusItem status = CardStatusItem.faint(skillUseInfo);
        ui.addCardStatus(attacker, victim, skill, status);
        victim.addStatus(status);
    }
}
