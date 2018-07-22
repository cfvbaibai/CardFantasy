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
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, victim, skill);
            if (magicEchoSkillResult==1||magicEchoSkillResult==2) {
                if (attacker instanceof CardInfo) {
                    CardInfo attackCard = (CardInfo) attacker;
                    if (attackCard.isDead()) {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else {
                        int damage2 = damage;
                        if (!resolver.resolveAttackBlockingSkills(victim, attackCard, skillUseInfo.getSkill(), damage2).isAttackable()) {
                            if (magicEchoSkillResult == 1) {
                                continue;
                            }
                        }
                        else{
                            if (resolver.resolveIsImmune(attackCard,0)) {
                                damage2 *= magnifier;
                            }
                            ui.attackCard(victim, attackCard, skill, damage2);
                            resolver.resolveDeathSkills(victim, attackCard, skill,
                                    resolver.applyDamage(victim, attackCard, skill, damage2));
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
            resolver.resolveDeathSkills(attacker, victim, skill,
                    resolver.applyDamage(attacker, victim, skill, actualDamage));
        }
    }
}
