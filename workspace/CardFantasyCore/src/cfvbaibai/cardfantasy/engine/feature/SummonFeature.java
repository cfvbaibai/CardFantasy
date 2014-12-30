package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.game.DeckBuilder;

public class SummonFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo summoner, String ... summonedCardsDescs) throws HeroDieSignal {
        if (summoner == null) {
            throw new CardFantasyRuntimeException("summoner should not be null");
        }
        Feature feature = featureInfo.getFeature();
        if (summoner.hasUsed(featureInfo)) {
            return;
        }
        Field field = summoner.getOwner().getField();
        for (CardInfo fieldCard : field.toList()) {
            if (fieldCard.getStatus().containsStatusCausedBy(featureInfo, CardStatusType.召唤)) {
                return;
            }
        }
        resolver.getStage().getUI().useSkill(summoner, feature, true);
        List<CardInfo> summonedCards = DeckBuilder.build(summonedCardsDescs).getCardInfos(summoner.getOwner());
        for (int i = 0; i < summonedCards.size(); ++i) {
            CardInfo summonedCard = summonedCards.get(i);
            resolver.summonCard(summoner.getOwner(), summonedCard, summoner);
            CardStatusItem weakStatusItem = CardStatusItem.weak(featureInfo);
            resolver.getStage().getUI().addCardStatus(summoner, summonedCard, feature, weakStatusItem);
            summonedCard.addStatus(weakStatusItem);
            CardStatusItem summonedStatusItem = CardStatusItem.summoned(featureInfo);
            resolver.getStage().getUI().addCardStatus(summoner, summonedCard, feature, summonedStatusItem);
            summonedCard.addStatus(summonedStatusItem);
        }
        summoner.setUsed(featureInfo);
    }
}
