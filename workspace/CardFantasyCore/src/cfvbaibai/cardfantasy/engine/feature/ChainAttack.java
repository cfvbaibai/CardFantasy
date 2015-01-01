package cfvbaibai.cardfantasy.engine.feature;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnDamagedResult;

public final class ChainAttack {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, CardInfo defender, Skill attackFeature)
            throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        if (attackFeature != null) {
            // Only normal attack can trigger ChainAttack.
            // Sweep & ChainAttack itself cannot trigger ChainAttack
            return;
        }
        Skill skill = skillUseInfo.getFeature();
        // Prevent stack overflow...
        if (!attacker.getEffectsCausedBy(skillUseInfo).isEmpty()) {
            return;
        }
        
        List<CardInfo> victims = new ArrayList<CardInfo>();
        for (CardInfo victim : defender.getOwner().getField().getAliveCards()) {
            if (victim == defender || !CardInfo.isSameType(victim, defender)) {
                continue;
            }
            victims.add(victim);
        }
        if (victims.isEmpty()) {
            return;
        }

        GameUI ui = resolver.getStage().getUI();

        ui.useSkill(attacker, victims, skill, true);

        int chainAT = skill.getImpact() * attacker.getLevel1AT() / 100;
        int adjAT = chainAT - attacker.getCurrentAT();
        SkillEffect effect = new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false);
        ui.adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(effect);
        for (CardInfo victim : victims) {
            OnDamagedResult damagedResult = resolver.attackCard(attacker, victim, skillUseInfo);
            if (damagedResult == null) {
                // 闪避导致连锁中断
                break;
            }
        }
        ui.loseAdjustATEffect(attacker, effect);
        attacker.removeEffect(effect);
    }
}
