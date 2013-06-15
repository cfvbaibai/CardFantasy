package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class ReturnFeature {
    public static void apply(FeatureResolver resolver, Feature cardFeature, CardInfo attacker, CardInfo defender) throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        if (defender == null) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, defender, cardFeature);
        OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, defender, cardFeature, 1);
        if (!result.isAttackable()) {
            return;
        }
        CardInfo expelledCard = defender.getOwner().getField().expelCard(defender.getPosition());
        if (expelledCard != defender) {
            throw new CardFantasyRuntimeException("expelledCard != defender");
        }
        
        ui.returnCard(attacker, defender, cardFeature);
        defender.getOwner().getDeck().addCard(defender);
        resolver.resolveLeaveFeature(defender, cardFeature);
    }
}
