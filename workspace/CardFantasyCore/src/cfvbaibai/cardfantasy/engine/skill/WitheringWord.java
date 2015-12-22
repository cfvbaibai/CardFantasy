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
        double Percent = skill.getImpact() /100.0;
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = defenderHero.getField().getAliveCards();
        ui.useSkill(attacker, victims, skill, true);

        for (CardInfo victim : victims) {
            int LifeDamage = (int)(victim.getBasicMaxHP() * Percent);
            int AttackReduce = (int)(victim.getATToReduce()*Percent);
            
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, skill, LifeDamage);
            if (!result.isAttackable()) {
                continue;
            }           


            ui.attackCard(attacker, victim, skill, LifeDamage);
            resolver.applyDamage(victim, skill, LifeDamage);
            ui.adjustAT(attacker, victim, -AttackReduce, skill);
            victim.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, -AttackReduce, true));
        }
    }
}
