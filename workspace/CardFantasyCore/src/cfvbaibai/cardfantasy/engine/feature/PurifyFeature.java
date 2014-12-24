package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class PurifyFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, CardInfo attacker)
            throws HeroDieSignal {
        List<CardInfo> cards = attacker.getOwner().getField().getAliveCards();
        resolver.getStage().getUI().useSkill(attacker, cards, featureInfo.getFeature(), true);
        for (CardInfo card : cards) {
            resolver.removeStatus(card, CardStatusType.迷惑);
            resolver.removeStatus(card, CardStatusType.冰冻);
            resolver.removeStatus(card, CardStatusType.锁定);
            resolver.removeStatus(card, CardStatusType.麻痹);
            resolver.removeStatus(card, CardStatusType.中毒);
            resolver.removeStatus(card, CardStatusType.燃烧);
            resolver.removeStatus(card, CardStatusType.弱化);
        }
    }
}
