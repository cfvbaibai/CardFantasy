package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class RacialAttackFeature {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, CardInfo defender, Race targetRace) {
        Skill skill = skillUseInfo.getSkill();
        if (defender.getRace() == targetRace) {
            int adjAT = (int) (attacker.getLevel1AT() * skill.getImpact() / 100);
            resolver.getStage().getUI().useSkill(attacker, skill, true);
            resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
            attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
        }
    }

    public static void remove(SkillResolver resolver, SkillUseInfo feature, CardInfo card) {
        List<SkillEffect> effects = card.getEffectsCausedBy(feature);
        for (SkillEffect effect : effects) {
            resolver.getStage().getUI().loseAdjustATEffect(card, effect);
            card.removeEffect(effect);
        }
    }
}
