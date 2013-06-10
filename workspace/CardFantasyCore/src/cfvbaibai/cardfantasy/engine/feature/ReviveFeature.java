package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class ReviveFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo feature, CardInfo reviver) throws HeroDieSignal {
        if (reviver == null) {
            throw new CardFantasyRuntimeException("reviver should not be null");
        }
        if (reviver.getOwner() != resolver.getStage().getActivePlayer()) {
            throw new CardFantasyRuntimeException("Reviver is not the current active player!");
        }
        // Grave is a stack, find the last-in card and revive it.
        CardInfo cardToRevive = null;
        for (CardInfo deadCard : reviver.getOwner().getGrave().toList()) {
            if (!deadCard.containsUsableFeaturesWithTag(FeatureTag.¸´»î)) {
                cardToRevive = deadCard;
                break;
            }
        }
        if (cardToRevive == null) {
            return;
        }
        resolver.getStage().getUI().useSkill(reviver, cardToRevive, feature);
        reviver.getOwner().getGrave().removeCard(cardToRevive);
        resolver.summonCard(reviver.getOwner(), cardToRevive);
        CardStatusItem item = CardStatusItem.weak(feature);
        resolver.getStage().getUI().addCardStatus(reviver, cardToRevive, feature, item);
        cardToRevive.addStatus(item);
    }
}
