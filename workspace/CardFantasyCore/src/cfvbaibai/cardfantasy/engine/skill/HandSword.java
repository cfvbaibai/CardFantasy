package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public final class HandSword {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, Player defender,int number)
        throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
    
        List<CardInfo> fieldCard = defender.getField().getAliveCards();
        List<CardInfo> victims = Randomizer.getRandomizer().pickRandom(fieldCard, number, true, null);
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            int damage = skill.getImpact() + skill.getImpact2() * victims.size(); 
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, skill, damage);
            if (!result.isAttackable()) {
                continue;
            }
            damage = result.getDamage();
            PosionBlade.apply(resolver, skillUseInfo, attacker, victim, damage);
            ui.attackCard(attacker, victim, skill, damage);
            resolver.resolveDeathSkills(attacker, victim, skill, resolver.applyDamage(attacker, victim, skill, damage));
        }
    }
}
