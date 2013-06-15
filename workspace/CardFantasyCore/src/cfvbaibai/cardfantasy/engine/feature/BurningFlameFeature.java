package cfvbaibai.cardfantasy.engine.feature;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class BurningFlameFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, EntityInfo attacker, Player defender)
            throws HeroDieSignal {
        Feature feature = featureInfo.getFeature();
        int damage = feature.getImpact();
        List<CardInfo> candidates = defender.getField().pickRandom(-1, true);
        List<CardInfo> victims = new ArrayList<CardInfo>();
        for (CardInfo candidate : candidates) {
            if (!candidate.getStatus().containsStatusCausedBy(featureInfo, CardStatusType.»º…’)) {
                victims.add(candidate);
            }
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature);
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, feature, damage);
            if (!result.isAttackable()) {
                continue;
            }
            CardStatusItem status = CardStatusItem.burning(damage, featureInfo);
            ui.addCardStatus(attacker, victim, feature, status);
            victim.addStatus(status);
        }
    }
}
