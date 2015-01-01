package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public class LegionBuffFeature {
    public static void apply(FeatureResolver resolver, CardInfo card) {
        FeatureInfo featureInfo = card.getOwner().getLegionBuffFeature(card.getRace());
        if (featureInfo == null) {
            return;
        }
        Skill skill = featureInfo.getFeature();
        if (skill.getLevel() == 0) {
            return;
        }
        int adjAT = skill.getImpact() * card.getInitAT() / 100;
        resolver.getStage().getUI().useSkill(card, skill, true);
        resolver.getStage().getUI().adjustAT(featureInfo.getOwner(), card, adjAT, skill);
        card.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, featureInfo, adjAT, false));
        
        int adjHP = skill.getImpact() * card.getOriginalMaxHP() / 100;
        resolver.getStage().getUI().useSkill(card, skill, true);
        resolver.getStage().getUI().adjustHP(featureInfo.getOwner(), card, adjHP, skill);
        card.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, featureInfo, adjHP, false));
    }

    public static void remove(FeatureResolver resolver, FeatureInfo feature, CardInfo card) {
        List<SkillEffect> effects = card.getEffectsCausedBy(feature);
        for (SkillEffect effect : effects) {
            resolver.getStage().getUI().loseAdjustATEffect(card, effect);
            card.removeEffect(effect);
        }
    }
}
