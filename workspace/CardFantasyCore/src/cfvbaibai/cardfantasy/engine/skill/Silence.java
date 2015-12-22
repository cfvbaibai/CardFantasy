package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class Silence {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo caster, CardInfo victim) throws HeroDieSignal {
        if (victim == null || victim.isDead()) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(caster, victim, skill, true);
        CardStatusItem statusItem = CardStatusItem.slience(skillUseInfo);
        if (!resolver.resolveAttackBlockingSkills(caster, victim, skill, 1).isAttackable()) {
            return;
        }
        ui.addCardStatus(caster, victim, skill, statusItem);
        victim.addStatus(statusItem);
    }
}
