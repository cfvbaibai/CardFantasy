package cfvbaibai.cardfantasy.engine.feature;

//import java.util.Collection;
import java.util.List;


//import cfvbaibai.cardfantasy.GameUI;
//import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
//import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class OneDelayFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, CardInfo attacker, Player defender)
            throws HeroDieSignal {
        if (attacker.hasUsed(featureInfo)) {
            return;
        }
        List<CardInfo> allHandCards = defender.getHand().toList();
        resolver.getStage().getUI().useSkill(attacker, allHandCards, featureInfo.getFeature(), true);
        int a= 6;
        for (CardInfo card : allHandCards) {
            int summonDelay = card.getSummonDelay();
            if (summonDelay < a) {
            	a = summonDelay;
            }else {
            	allHandCards.remove(summonDelay);
            }
            }
        CardInfo delaycard = allHandCards.get(a);
        delaycard.setSummonDelay(a + 1);
        attacker.setUsed(featureInfo);
        }
 
}
