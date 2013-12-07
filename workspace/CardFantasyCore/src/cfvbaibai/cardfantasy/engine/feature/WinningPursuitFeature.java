package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Player;

public class WinningPursuitFeature extends PreAttackCardFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo attacker, Player defender) {
        int enemyDeadCount = defender.getGrave().size();
        if (enemyDeadCount == 0) {
            return;
        }
        Feature feature = featureInfo.getFeature();
        int adjAT = feature.getImpact() * enemyDeadCount;
        resolver.getStage().getUI().useSkill(attacker, feature, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, feature);
        attacker.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, featureInfo, adjAT, false));
    }
}
