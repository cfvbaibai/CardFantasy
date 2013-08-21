package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class TransportFeature {
    public static void apply(FeatureResolver resolver, Feature cardFeature, CardInfo attacker, Player defenderHero) throws HeroDieSignal {
        if (attacker == null || attacker.isDead()) {
            return;
        }
        CardInfo cardToTransport = null;
        for (CardInfo card : defenderHero.getHand().toList()) {
            if (cardToTransport == null || card.getSummonDelay() > cardToTransport.getSummonDelay()) {
                cardToTransport = card;
            }
        }
        if (cardToTransport == null) {
            return;
        }
        resolver.getStage().getUI().useSkill(attacker, cardToTransport, cardFeature, true);
        OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, cardToTransport, cardFeature, 1);
        if (!result.isAttackable()) {
            return;
        }
        resolver.getStage().getUI().cardToGrave(defenderHero, cardToTransport);
        defenderHero.getHand().removeCard(cardToTransport);
        defenderHero.getGrave().addCard(cardToTransport);
    }
}
