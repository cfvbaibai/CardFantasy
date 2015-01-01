package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public class LegionBuffFeature {
    public static void apply(FeatureResolver resolver, CardInfo card) {
        SkillUseInfo skillUseInfo = card.getOwner().getLegionBuffFeature(card.getRace());
        if (skillUseInfo == null) {
            return;
        }
        Skill skill = skillUseInfo.getFeature();
        if (skill.getLevel() == 0) {
            return;
        }
        int adjAT = skill.getImpact() * card.getInitAT() / 100;
        resolver.getStage().getUI().useSkill(card, skill, true);
        resolver.getStage().getUI().adjustAT(skillUseInfo.getOwner(), card, adjAT, skill);
        card.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
        
        int adjHP = skill.getImpact() * card.getOriginalMaxHP() / 100;
        resolver.getStage().getUI().useSkill(card, skill, true);
        resolver.getStage().getUI().adjustHP(skillUseInfo.getOwner(), card, adjHP, skill);
        card.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, adjHP, false));
    }

    public static void remove(FeatureResolver resolver, SkillUseInfo feature, CardInfo card) {
        List<SkillEffect> effects = card.getEffectsCausedBy(feature);
        for (SkillEffect effect : effects) {
            resolver.getStage().getUI().loseAdjustATEffect(card, effect);
            card.removeEffect(effect);
        }
    }
}
