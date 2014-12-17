package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public class SoftenFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, EntityInfo attacker, Player defender)
        throws HeroDieSignal {
        Feature feature = featureInfo.getFeature();

        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defender.getField().toList(), 1, true, null);
        if (victims.isEmpty()) {
            return;
        }

        CardInfo victim = victims.get(0);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature, true);

        if (!resolver.resolveAttackBlockingFeature(attacker, victim, feature, 1).isAttackable()) {
            return;
        }

        CardStatusItem status = CardStatusItem.softened(featureInfo);
        ui.addCardStatus(attacker, victim, feature, status);
        victim.addStatus(status);
    }
}
