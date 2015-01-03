package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class Overdraw {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker) throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();
        int adjAT = skill.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, skill, true);
        ui.adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));
        ui.attackCard(attacker, attacker, skill, adjAT);
        resolver.resolveDeathSkills(attacker, attacker, skill, resolver.applyDamage(attacker, adjAT));
    }
}
