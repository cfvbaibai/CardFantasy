package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardPile;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public class CounterSummonFeature {
    public static void apply(FeatureResolver resolver, CardInfo defender, Feature feature, int star) throws HeroDieSignal {
        if (defender == null) {
            return;
        }
        if (!resolver.getStage().getRandomizer().roll100(30)) {
            return;
        }
        int summonCount = feature.getImpact();
        List<CardInfo> deckCards = defender.getOwner().getDeck().toList();
        CardPile candidates = new CardPile();
        for (CardInfo card : deckCards) {
            if (card.getStar() == star) {
                candidates.addCard(card);
            }
        }
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> summonCards = candidates.pickRandom(summonCount, true);
        ui.useSkill(defender, summonCards, feature, true);
        for (CardInfo summonCard : summonCards) {
            summonCard.getOwner().getDeck().removeCard(summonCard);
            resolver.summonCard(summonCard.getOwner(), summonCard, defender);
        }
    }
}
