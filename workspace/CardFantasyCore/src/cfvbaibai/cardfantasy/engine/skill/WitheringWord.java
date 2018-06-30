package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class WitheringWord {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, Player defenderHero)
            throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();
        double Percent = skill.getImpact() / 100.0;
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = defenderHero.getField().getAliveCards();
        ui.useSkill(attacker, victims, skill, true);

        for (CardInfo victim : victims) {
            int lifeDamage = (int)(victim.getBasicMaxHP() * Percent);
            int attackReduce = (int)(victim.getATToReduce() * Percent);

            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, skill, lifeDamage);
            if (!result.isAttackable()) {
                continue;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, victim, skill);
            if (magicEchoSkillResult == 1 || magicEchoSkillResult == 2) {
                if (attacker instanceof CardInfo) {
                    CardInfo attackCard =  (CardInfo)attacker;
                    if(attackCard.isDead())
                    {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else{
                        int lifeDamage2 = (int)(attackCard.getBasicMaxHP() * Percent);
                        int attackReduce2 = (int)(attackCard.getATToReduce() * Percent);

                        OnAttackBlockingResult result2 = resolver.resolveAttackBlockingSkills(victim, attackCard, skill, lifeDamage2);
                        if (!result2.isAttackable()) {
                            continue;
                        }
                        else {
                            ui.adjustAT(victim, attackCard, -attackReduce2, skill);
                            attackCard.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, -attackReduce2, true));
                            ui.attackCard(victim, attackCard, skill, lifeDamage2);
                            resolver.resolveDeathSkills(victim, attackCard, skill, resolver.applyDamage(victim, attackCard, skill, lifeDamage2));
                        }
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            ui.adjustAT(attacker, victim, -attackReduce, skill);
            victim.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, -attackReduce, true));
            ui.attackCard(attacker, victim, skill, lifeDamage);
            resolver.resolveDeathSkills(attacker, victim, skill, resolver.applyDamage(attacker, victim, skill, lifeDamage));
        }
    }
}
