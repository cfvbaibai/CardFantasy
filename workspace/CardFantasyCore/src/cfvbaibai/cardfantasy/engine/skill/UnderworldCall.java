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
        int damage=cardSkill.getImpact2();
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
                defenderHero.getField().toList(), victimCount, true, null);
        ui.useSkill(attacker, victims, cardSkill, true);
        for (CardInfo victim : victims) {
            if(victim.containsUsableSkill(SkillType.免疫)||victim.containsUsableSkill(SkillType.结界立场)||victim.isBoss()||victim.isDeman())
            {
                continue;
            }
            else{
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
