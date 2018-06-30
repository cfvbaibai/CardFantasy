package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class ChemicalRage {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, Player defender)
            throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();

        List<CardInfo> allVictims = defender.getField().getAliveCards();
        List<CardInfo> attackVictims = attacker.getOwner().getField().getAliveCards();
        List<CardInfo> victims = resolver.getAdjacentCards(defender.getField(), attacker.getPosition());
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            int damage = skill.getImpact() + skill.getImpact2() * allVictims.size();
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, skill, damage);
            if (!result.isAttackable()) {
                continue;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, victim, skill);
            if (magicEchoSkillResult==1||magicEchoSkillResult==2) {
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
                        ui.attackCard(victim, attacker, skill, damage2);
                        resolver.resolveDeathSkills(victim, attacker, skill, resolver.applyDamage(victim, attacker, skill, damage2));
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            damage = result.getDamage();
            ui.attackCard(attacker, victim, skill, damage);
            resolver.resolveDeathSkills(attacker, victim, skill, resolver.applyDamage(attacker, victim, skill, damage));
        }
    }
}
