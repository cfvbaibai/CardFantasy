package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Player;

public final class ResurrectionFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo resurrector) {
        if (resurrector == null) {
            throw new CardFantasyRuntimeException("resurrector should not be null");
        }
        if (resurrector.getOwner() != resolver.getStage().getActivePlayer()) {
            throw new CardFantasyRuntimeException("resurrector is not the current active player!");
        }
        Feature feature = featureInfo.getFeature();
        // Grave is a stack, find the last-in card and revive it.
        int resurrectionCount = feature.getImpact();
        Player player = resurrector.getOwner();
        List<CardInfo> cardsToResurrect = player.getGrave().pop(resurrectionCount);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(resurrector, cardsToResurrect, feature);
        for (CardInfo card : cardsToResurrect) {
            ui.cardToDeck(player, card);
            player.getDeck().addCard(card);
        }
    }
}
