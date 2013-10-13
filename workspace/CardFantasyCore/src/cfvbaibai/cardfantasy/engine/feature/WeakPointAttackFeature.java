package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public final class WeakPointAttackFeature {
    public static boolean isBlockFeatureDisabled(FeatureResolver resolver, Feature attackFeature,
            Feature blockFeature, CardInfo attacker, CardInfo defender) {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("attacker is null");
        }
        if (defender == null) {
            throw new CardFantasyRuntimeException("defender is null");
        }
        if (blockFeature.getType().containsTag(FeatureTag.物理护甲)) {
            resolver.getStage().getUI().useSkill(attacker, defender, attackFeature, true);
            resolver.getStage().getUI().disableBlock(attacker, defender, attackFeature, blockFeature);
            return true;
        } else {
            return false;
        }
    }
}
