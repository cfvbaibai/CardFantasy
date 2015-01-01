package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.game.DeckBuilder;

public class SummonFeature {
    public static void apply(FeatureResolver resolver, SkillUseInfo skillUseInfo, CardInfo summoner, String ... summonedCardsDescs) throws HeroDieSignal {
        if (summoner == null) {
            throw new CardFantasyRuntimeException("summoner should not be null");
        }
        Skill skill = skillUseInfo.getFeature();
        if (summoner.hasUsed(skillUseInfo)) {
            return;
        }
        Field field = summoner.getOwner().getField();
        for (CardInfo fieldCard : field.toList()) {
            if (fieldCard.getStatus().containsStatusCausedBy(skillUseInfo, CardStatusType.召唤)) {
                return;
            }
        }
        resolver.getStage().getUI().useSkill(summoner, skill, true);
        List<CardInfo> summonedCards = DeckBuilder.build(summonedCardsDescs).getCardInfos(summoner.getOwner());
        for (int i = 0; i < summonedCards.size(); ++i) {
            CardInfo summonedCard = summonedCards.get(i);
            resolver.summonCard(summoner.getOwner(), summonedCard, summoner);
            CardStatusItem weakStatusItem = CardStatusItem.weak(skillUseInfo);
            resolver.getStage().getUI().addCardStatus(summoner, summonedCard, skill, weakStatusItem);
            summonedCard.addStatus(weakStatusItem);
            CardStatusItem summonedStatusItem = CardStatusItem.summoned(skillUseInfo);
            resolver.getStage().getUI().addCardStatus(summoner, summonedCard, skill, summonedStatusItem);
            summonedCard.addStatus(summonedStatusItem);
        }
        summoner.setUsed(skillUseInfo);
    }
}
