package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public class LegionBuffFeature {
    public static void apply(FeatureResolver resolver, CardInfo card) {
        FeatureInfo featureInfo = card.getOwner().getLegionBuffFeature(card.getRace());
        if (featureInfo == null) {
            return;
        }
        Feature feature = featureInfo.getFeature();
        if (feature.getLevel() == 0) {
            return;
        }
        int adjAT = feature.getImpact() * card.getOriginalAT() / 100;
        resolver.getStage().getUI().useSkill(card, feature);
        resolver.getStage().getUI().adjustAT(featureInfo.getOwner(), card, adjAT, feature);
        card.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, featureInfo, adjAT, false));
        
        int adjHP = feature.getImpact() * card.getOriginalMaxHP() / 100;
        resolver.getStage().getUI().useSkill(card, feature);
        resolver.getStage().getUI().adjustHP(featureInfo.getOwner(), card, adjHP, feature);
        card.addEffect(new FeatureEffect(FeatureEffectType.MAXHP_CHANGE, featureInfo, adjHP, false));
    }

    public static void remove(FeatureResolver resolver, FeatureInfo feature, CardInfo card) {
        List<FeatureEffect> effects = card.getEffectsCausedBy(feature);
        for (FeatureEffect effect : effects) {
            resolver.getStage().getUI().loseAdjustATEffect(card, effect);
            card.removeEffect(effect);
        }
    }
}
