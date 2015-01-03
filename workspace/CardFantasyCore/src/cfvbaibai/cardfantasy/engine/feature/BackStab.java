package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class BackStab {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker) {
        Skill skill = skillUseInfo.getSkill();
        int adjAT = skill.getImpact();
        if (attacker.hasUsed(skillUseInfo)) {
            return;
        }

        resolver.getStage().getUI().useSkill(attacker, skill, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
        attacker.setUsed(skillUseInfo);
    }

    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        List<SkillEffect> effects = card.getEffectsCausedBy(skillUseInfo);
        for (SkillEffect effect : effects) {
            if (effect.getType() == SkillEffectType.ATTACK_CHANGE) {
                resolver.getStage().getUI().loseAdjustATEffect(card, effect);
                card.removeEffect(effect);
            }
        }
    }
}
