package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.game.DeckBuilder;

public class Summon {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo summoner, boolean isSummoningSummon, int summonPicks, String ... summonedCardsDescs) throws HeroDieSignal {
        if (summoner == null) {
            throw new CardFantasyRuntimeException("summoner should not be null");
        }
        Skill skill = skillUseInfo.getSkill();
        List<CardInfo> livingCards = summoner.getOwner().getField().getAliveCards();
        List<String> cardDescsToSummon = new LinkedList<String>();
        for (String summonedCardDesc : summonedCardsDescs) {
            cardDescsToSummon.add(summonedCardDesc);
        }

        resolver.getStage().getUI().useSkill(summoner, skill, true);
        List<CardInfo> summonedCards = DeckBuilder.build(summonedCardsDescs).getCardInfos(summoner.getOwner());
        List<CardInfo> aliveSummonedCards = new ArrayList<CardInfo>();
        for (int i = 0; i < summonedCards.size(); ++i) {
            CardInfo summonedCard = summonedCards.get(i);
            boolean cardStillAlive = false;
            for (CardInfo fieldCard : livingCards) {
                if (fieldCard.getStatus().containsStatusCausedBy(skillUseInfo, CardStatusType.召唤)) {
                    if (fieldCard.getName().equals(summonedCard.getName())) {
                        cardStillAlive = true;
                        continue;
                    }
                }
            }
            if (cardStillAlive) {
                aliveSummonedCards.add(summonedCard);
            }
        }

        List<CardInfo> cardsToSummon = Randomizer.getRandomizer().pickRandom(summonedCards, summonPicks, true, aliveSummonedCards);
        for (int i = 0; i < cardsToSummon.size(); ++i) {
            CardInfo summonedCard = cardsToSummon.get(i);
            resolver.summonCard(summoner.getOwner(), summonedCard, summoner, true, skill);
            if (!isSummoningSummon) {
                CardStatusItem weakStatusItem = CardStatusItem.weak(skillUseInfo);
                resolver.getStage().getUI().addCardStatus(summoner, summonedCard, skill, weakStatusItem);
                summonedCard.addStatus(weakStatusItem);
            }
            CardStatusItem summonedStatusItem = CardStatusItem.summoned(skillUseInfo);
            resolver.getStage().getUI().addCardStatus(summoner, summonedCard, skill, summonedStatusItem);
            summonedCard.addStatus(summonedStatusItem);
        }
    }
}
