package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class ManaErode {
    public static void apply(Skill skill, SkillResolver resolver, EntityInfo attacker, Player defender,
            int victimCount) throws HeroDieSignal {
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defender.getField().toList(), victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, skill, true);
        int damage = skill.getImpact();
        int magnifier = skill.getImpact2();
        for (CardInfo victim : victims) {
			if (!resolver.resolveAttackBlockingSkills(attacker, victim, skill, damage).isAttackable()) {
				continue;
			}
            int actualDamage = damage;
            if (victim.containsUsableSkill(SkillType.免疫) || CounterMagic.getBlockSkill(victim) != null) {
            	actualDamage *= magnifier;
            }
            ui.attackCard(attacker, victim, skill, actualDamage);
            resolver.resolveDeathSkills(attacker, victim, skill, resolver.applyDamage(attacker, victim, skill, actualDamage));
        }
    }
}
