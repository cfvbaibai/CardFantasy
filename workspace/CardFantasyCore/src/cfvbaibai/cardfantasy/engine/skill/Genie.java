package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public final class Genie {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker) throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();
        int adjHP = skill.getImpact();
        int adjAT = skill.getImpact2();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, skill, true);
        ui.adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));
        ui.attackCard(attacker, attacker, skill, adjHP);
        resolver.resolveDeathSkills(attacker, attacker, skill, resolver.applyDamage(attacker, attacker, skill, adjHP));
    }
}
