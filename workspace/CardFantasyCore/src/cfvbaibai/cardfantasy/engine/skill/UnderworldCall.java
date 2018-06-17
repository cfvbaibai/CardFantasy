package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;
import cfvbaibai.cardfantasy.data.SkillType;

import java.util.List;

public final class UnderworldCall {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero,
                              int victimCount) throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        int threshold=cardSkill.getImpact();
        int damageInit=cardSkill.getImpact2();
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
                defenderHero.getField().toList(), victimCount, true, null);
        ui.useSkill(attacker, victims, cardSkill, true);
        for (CardInfo victim : victims) {
            int damage = damageInit;
            if(victim.containsUsableSkill(SkillType.免疫)||victim.containsUsableSkill(SkillType.结界立场)|| victim.containsUsableSkill(SkillType.影青龙)|| victim.containsAllSkill(SkillType.恶龙血脉) || victim.containsUsableSkill(SkillType.魔力抗性) ||victim.isBoss()||victim.isDeman())
            {
                continue;
            }
            else{
                int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, victim, cardSkill);
                if (magicEchoSkillResult==1||magicEchoSkillResult==2) {
                    if(attacker.isDead())
                    {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else{
                        if (attacker.getHP() >= attacker.getMaxHP() * threshold / 100) {
                            OnAttackBlockingResult result2 = resolver.resolveAttackBlockingSkills(victim, attacker, cardSkill, damage);
                            if (!result2.isAttackable()) {
                                if (magicEchoSkillResult == 1) {
                                    continue;
                                }
                            }
                            else {
                                int damage2 = result2.getDamage();
                                ui.attackCard(victim, attacker, cardSkill, damage2);
                                resolver.resolveDeathSkills(victim, attacker, cardSkill, resolver.applyDamage(victim, attacker, cardSkill, damage2));
                            }
                        }
                        else{
                            ui.killCard(victim, attacker, cardSkill);
                            attacker.removeStatus(CardStatusType.不屈);
                            resolver.killCard(victim, attacker, cardSkill);
                        }
                    }
                    if (magicEchoSkillResult == 1) {
                        continue;
                    }
                }
                if (victim.getHP() >= victim.getMaxHP() * threshold / 100) {
                    OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, cardSkill, damage);
                    if (!result.isAttackable()) {
                        continue;
                    }
                    damage = result.getDamage();
                    ui.attackCard(attacker, victim, cardSkill, damage);
                    resolver.resolveDeathSkills(attacker, victim, cardSkill,  resolver.applyDamage(attacker, victim, cardSkill,damage));
                }
                else{
                    ui.killCard(attacker, victim, cardSkill);
                    victim.removeStatus(CardStatusType.不屈);
                    resolver.killCard(attacker, victim, cardSkill);
                }
            }

        }
    }
}
