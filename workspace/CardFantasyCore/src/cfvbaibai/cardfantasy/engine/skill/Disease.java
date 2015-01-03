package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class Disease {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker, CardInfo defender, int normalAttackDamage) throws HeroDieSignal {
        if (normalAttackDamage <= 0 || defender == null || defender.isDead()) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int damage = skill.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, defender, skill, true);

        OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, defender, skill, damage);
        if (!result.isAttackable()) {
            return;
        }
        
        ui.attackCard(attacker, defender, skill, damage);
        resolver.applyDamage(defender, damage);
        ui.adjustAT(attacker, defender, -damage, skill);
        defender.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, -damage, true));
    }
}
