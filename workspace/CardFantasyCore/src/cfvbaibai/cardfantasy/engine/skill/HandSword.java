package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public final class HandSword {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, Player defender, int number)
            throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();

        List<CardInfo> fieldCard = defender.getField().getAliveCards();
        List<CardInfo> attackVictims = attacker.getOwner().getField().getAliveCards();
        List<CardInfo> victims = Randomizer.getRandomizer().pickRandom(fieldCard, number, true, null);
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            int damage = skill.getImpact() + skill.getImpact2() * fieldCard.size();
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, skill, damage);
            if (!result.isAttackable()) {
                continue;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, victim, skill);
            if (magicEchoSkillResult == 1 || magicEchoSkillResult == 2) {
                if (attacker.isDead()) {
                    if (magicEchoSkillResult == 1) {
                        continue;
                    }
                } else {
                    int damage2 = skill.getImpact() + skill.getImpact2() * attackVictims.size();
                    OnAttackBlockingResult result2 = resolver.resolveAttackBlockingSkills(victim, attacker, skill, damage2);
                    if (!result2.isAttackable()) {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    } else {
                        damage2 = result2.getDamage();
                        PosionBlade.apply(resolver, skillUseInfo, victim, attacker, damage2);
                        ui.attackCard(victim, attacker, skill, damage2);
                        resolver.resolveDeathSkills(victim, attacker, skill, resolver.applyDamage(victim, attacker, skill, damage2));
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            damage = result.getDamage();
            PosionBlade.apply(resolver, skillUseInfo, attacker, victim, damage);
            ui.attackCard(attacker, victim, skill, damage);
            resolver.resolveDeathSkills(attacker, victim, skill, resolver.applyDamage(attacker, victim, skill, damage));
        }
    }
}
