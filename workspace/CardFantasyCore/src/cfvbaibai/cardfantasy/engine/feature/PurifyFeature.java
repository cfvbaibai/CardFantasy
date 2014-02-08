package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

//import cfvbaibai.cardfantasy.GameUI;
//import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
//import cfvbaibai.cardfantasy.engine.CardStatus;
//import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class PurifyFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, CardInfo attacker, Player attacker1)
            throws HeroDieSignal {
        if (attacker.hasUsed(featureInfo)) {
            return;
        }
        Field myField = attacker1.getField();
        List<CardInfo> allHandCards = attacker1.getHand().toList();
        resolver.getStage().getUI().useSkill(attacker, allHandCards, featureInfo.getFeature(), true);
        for (int i = 0; i < myField.size(); ++i) {
            if (myField.getCard(i) == null) {
                continue;
            }
            CardInfo card = myField.getCard(i);
            card.removeStatus(CardStatusType.迷惑);
            card.removeStatus(CardStatusType.冰冻);
            card.removeStatus(CardStatusType.锁定);
            card.removeStatus(CardStatusType.麻痹);
            card.removeStatus(CardStatusType.中毒);
            card.removeStatus(CardStatusType.燃烧);
        }
        attacker.setUsed(featureInfo);
    }
}
