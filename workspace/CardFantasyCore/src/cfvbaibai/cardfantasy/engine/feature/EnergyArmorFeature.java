package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Field;

public final class EnergyArmorFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, EntityInfo caster, int targetCount) {
        if (caster == null) {
            throw new CardFantasyRuntimeException("caster cannot be null");
        }
        Feature feature = featureInfo.getFeature();
        int impact = feature.getImpact();
        Field field = caster.getOwner().getField();
        List<CardInfo> targets = resolver.getStage().getRandomizer().pickRandom(
            field.toList(), targetCount, true, null);
        resolver.getStage().getUI().useSkill(caster, targets, feature, true);
        for (CardInfo target : targets) {
            resolver.getStage().getUI().adjustHP(caster, target, impact, feature);
            target.addEffect(new FeatureEffect(FeatureEffectType.MAXHP_CHANGE, featureInfo, impact, false));
        }
    }

    public static void remove(FeatureResolver resolver, FeatureInfo feature, EntityInfo caster) {
        if (caster == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        if (feature == null) {
            throw new CardFantasyRuntimeException("feature cannot be null");
        }
        Field field = caster.getOwner().getField();
        for (CardInfo target : field.getAliveCards()) {
            for (FeatureEffect effect : target.getEffectsCausedBy(feature)) {
                resolver.getStage().getUI().loseAdjustHPEffect(target, effect);
                target.removeEffect(effect);
            }
        }
    }
}
