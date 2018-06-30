package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.OnDamagedResult;

public final class EnergyDrain {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker, CardInfo defender,
            OnAttackBlockingResult result, OnDamagedResult damagedResult) throws HeroDieSignal {
        if (result.getDamage() == 0 || defender == null) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int adjAT = attacker.getLevel1AT() * skill.getImpact() / 100;

        List<CardInfo> victims = new ArrayList<CardInfo>();
        victims.add(attacker);
        int totalAttackWeakened = Weaken.weakenCard(resolver, skillUseInfo, adjAT, defender, victims,false);

        if (!defender.isDead()) {
            resolver.getStage().getUI().adjustAT(defender, defender, adjAT, skill);
            defender.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));
        }
        
        if (damagedResult != null) {
            // Null on magical attack. Only sweep is affected by damaged result.
            damagedResult.originalDamage -= totalAttackWeakened;
        }
    }
}
