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
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, victim, skill);
            if (magicEchoSkillResult==1||magicEchoSkillResult==2) {
                if (attacker instanceof CardInfo) {
                    CardInfo attackCard =  (CardInfo)attacker;
                    if (attackCard.isDead()) {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else{
                        if (!resolver.resolveAttackBlockingSkills(victim, attackCard, skill, damage).isAttackable()) {
                            if (magicEchoSkillResult == 1) {
                                continue;
                            }
                        }
                        else{
                            int actualDamage2 = damage;
                            if (resolver.resolveIsImmune(attackCard,0)) {
                                actualDamage2 *= magnifier;
                            }
                            ui.attackCard(victim, attackCard, skill, actualDamage2);
                            resolver.resolveDeathSkills(victim, attackCard, skill, resolver.applyDamage(victim, attackCard, skill, actualDamage2));
                        }
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            int actualDamage = damage;
            if (resolver.resolveIsImmune(victim,0)) {
                actualDamage *= magnifier;
            }
            ui.attackCard(attacker, victim, skill, actualDamage);
            resolver.resolveDeathSkills(attacker, victim, skill, resolver.applyDamage(attacker, victim, skill, actualDamage));
        }
    }
}
