package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public abstract class RemovableBuff {
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
