package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public class LifeDrain {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker, CardInfo defender,
            OnAttackBlockingResult result, OnDamagedResult damagedResult) throws HeroDieSignal {
        if (result.getDamage() == 0 || defender == null) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int damage = attacker.getHP() * skill.getImpact() / 100;

        if (!attacker.isDead()) {
            // 反射装甲+恶灵汲取的场合，攻击卡牌可能先被送还了，然后再发动恶灵汲取
            OnAttackBlockingResult onAttackBlockingResult = resolver.resolveAttackBlockingSkills(defender, attacker, skill, damage);
            if (!onAttackBlockingResult.isAttackable()) {
                return;
            }
            damage = onAttackBlockingResult.getDamage();
            resolver.getStage().getUI().attackCard(defender, attacker, skill, damage);
            resolver.resolveDeathSkills(defender, attacker, skill, resolver.applyDamage(defender, attacker, skill, damage));
        }

        if (!defender.isDead()) {
            int healHP = damage;
//            if (healHP + defender.getHP() > defender.getMaxHP()) {
//                healHP = defender.getMaxHP() - defender.getHP();
//            }
            resolver.getStage().getUI().adjustHP(defender, defender, healHP, skill);
         //   resolver.applyDamage(defender, defender, skill, -healHP);
            defender.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, healHP, false));
        }
    }
}
