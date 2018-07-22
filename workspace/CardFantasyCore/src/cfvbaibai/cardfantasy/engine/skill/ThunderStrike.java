package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

/**
 * Created by hasee on 2017/3/30.
 */
public final  class ThunderStrike {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, Player defender,
                             int victimCount) throws HeroDieSignal {
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(defender.getField().toList(),
                victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(attacker, victims, skill, true);
        int damage = skill.getImpact();
        int magnifier = skill.getImpact2();
        int rate = skill.getImpact3();
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, skillUseInfo.getSkill(), damage);
            if (!result.isAttackable()) {
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
                        OnAttackBlockingResult result2 = resolver.resolveAttackBlockingSkills(victim, attackCard, skillUseInfo.getSkill(), damage2);
                        if (!result2.isAttackable()){
                            if (magicEchoSkillResult == 1) {
                                continue;
                            }
                        }
                        else{
                            int actualDamage2 = result2.getDamage();
                            if (resolver.resolveIsImmune(attackCard,0)) {
                                if(actualDamage2>=damage2)
                                {
                                    actualDamage2 *= magnifier;
                                }
                            } else {
                                if (resolver.getStage().getRandomizer().roll100(rate)) {
                                    CardStatusItem status = CardStatusItem.paralyzed(skillUseInfo);
                                    if (!resolver.resolveBlockStatusSkills(victim, attackCard, skillUseInfo, status).isBlocked()) {
                                        ui.addCardStatus(victim, attackCard, skill, status);
                                        attackCard.addStatus(status);
                                    }
                                }
                            }
                            ui.attackCard(victim, attackCard, skill, damage2);
                            resolver.resolveDeathSkills(victim, attackCard, skill,
                                    resolver.applyDamage(victim, attackCard, skill, actualDamage2));
                        }
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            int actualDamage = result.getDamage();
            if (resolver.resolveIsImmune(victim,0)) {
                if(actualDamage>=damage)
                {
                    actualDamage *= magnifier;
                }
            } else {
                if (resolver.getStage().getRandomizer().roll100(rate)) {
                    CardStatusItem status = CardStatusItem.paralyzed(skillUseInfo);
                    if (!resolver.resolveBlockStatusSkills(attacker, victim, skillUseInfo, status).isBlocked()) {
                        ui.addCardStatus(attacker, victim, skill, status);
                        victim.addStatus(status);
                    }
                }
            }
            ui.attackCard(attacker, victim, skill, actualDamage);
            resolver.resolveDeathSkills(attacker, victim, skill,
                    resolver.applyDamage(attacker, victim, skill, actualDamage));
        }
    }
}
