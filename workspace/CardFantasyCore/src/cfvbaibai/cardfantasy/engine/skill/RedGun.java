package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

/**
 * Created by hasee on 2017/3/30.
 */
public class RedGun {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, Player defender,
                             int victimCount) throws HeroDieSignal {
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(defender.getField().toList(),
                victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(attacker, victims, skill, true);
        int damage = skill.getImpact();
        int magnifier = skill.getImpact2();
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingSkills(attacker, victim, skillUseInfo.getSkill(), damage).isAttackable()) {
                continue;
            }
            int actualDamage = damage;
            if (victim.containsUsableSkill(SkillType.免疫) || CounterMagic.getBlockSkill(victim) != null) {
                actualDamage *= magnifier;
            }
            ui.attackCard(attacker, victim, skill, actualDamage);
            resolver.resolveDeathSkills(attacker, victim, skill,
                    resolver.applyDamage(attacker, victim, skill, actualDamage));
        }
    }
}
