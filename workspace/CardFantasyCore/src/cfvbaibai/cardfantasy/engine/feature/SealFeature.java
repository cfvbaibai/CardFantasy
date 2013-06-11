package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class SealFeature {
    public static void apply(FeatureInfo feature, FeatureResolver resolver, CardInfo attacker, Player defender) throws HeroDieSignal {
        if (!attacker.isFirstRound()) {
            return;
        }
        List <CardInfo> victims = defender.getField().getAliveCards();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingFeature(attacker, victim, feature).isAttackable()) {
                continue;
            }
            CardStatusItem status = CardStatusItem.trapped(feature);
            ui.addCardStatus(attacker, victim, feature, status);
            victim.addStatus(status);
        }
    }
}
