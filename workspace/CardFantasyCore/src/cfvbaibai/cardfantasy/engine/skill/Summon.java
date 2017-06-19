package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;
import cfvbaibai.cardfantasy.game.DeckBuilder;

public class Summon {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo summoner, SummonType summonType, int summonPicks, String... summonedCardsDescs) throws HeroDieSignal {
        if (summoner == null) {
            throw new CardFantasyRuntimeException("summoner should not be null");
        }
        // 镜像不能再次发动镜像
        if (summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.镜像
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.镜魔) {
            for(CardInfo primaryCardss : summoner.getOwner().getAllPrimaryCards())
            {
                if(summoner.getName().equals(primaryCardss.getName()))
                {
                    return;
                }
            }
        }
        boolean opSklill = false;
        if(skillUseInfo.getType() == SkillType.召唤炮灰)
        {
            opSklill = true;
        }
        Skill skill = skillUseInfo.getSkill();
        List<CardInfo> livingCards = null;
        Player enemy = null;
        if(opSklill)
        {
            enemy = resolver.getStage().getOpponent(summoner.getOwner());
            livingCards = enemy.getField().getAliveCards();
        }
        else {
            livingCards = summoner.getOwner().getField().getAliveCards();
        }
        List<String> cardDescsToSummon = new LinkedList<String>();
        for (String summonedCardDesc : summonedCardsDescs) {
            cardDescsToSummon.add(summonedCardDesc);
        }

        List<CardInfo> cardsToSummon = new ArrayList<CardInfo>();
        List<CardInfo> summonCardCandidates = null;
        if(opSklill)
        {
            summonCardCandidates = DeckBuilder.build(summonedCardsDescs).getCardInfos(enemy);
        }
        else {summonCardCandidates = DeckBuilder.build(summonedCardsDescs).getCardInfos(summoner.getOwner());}
        if (summonType == SummonType.Normal || summonType == SummonType.Summoning) {
            boolean anySummonedCardStillAlive = false;
            for (CardInfo fieldCard : livingCards) {
                if (fieldCard.getStatus().containsStatusCausedBy(skillUseInfo, CardStatusType.召唤)) {
                    anySummonedCardStillAlive = true;
                }
            }
            if (!anySummonedCardStillAlive) {
                cardsToSummon.addAll(summonCardCandidates);
            }
        } else if (summonType == SummonType.Random || summonType == SummonType.RandomSummoning) {
            List<CardInfo> aliveSummonedCards = new ArrayList<CardInfo>();
            for (int i = 0; i < summonCardCandidates.size(); ++i) {
                CardInfo summonCardCandidate = summonCardCandidates.get(i);
                boolean cardStillAlive = false;
                for (CardInfo fieldCard : livingCards) {
                    if (fieldCard.getStatus().containsStatusCausedBy(skillUseInfo, CardStatusType.召唤)) {
                        if (fieldCard.getName().equals(summonCardCandidate.getName())) {
                            cardStillAlive = true;
                            continue;
                        }
                    }
                }
                if (cardStillAlive) {
                    aliveSummonedCards.add(summonCardCandidate);
                }
            }

            cardsToSummon = Randomizer.getRandomizer().pickRandom(summonCardCandidates, summonPicks, true, aliveSummonedCards);
        }

        if (cardsToSummon.size() > 0) {
            resolver.getStage().getUI().useSkill(summoner, skill, true);
        }
        for (int i = 0; i < cardsToSummon.size(); ++i) {
            CardInfo summonedCard = cardsToSummon.get(i);
            resolver.summonCard(summoner.getOwner(), summonedCard, summoner, true, skill);
            if (summonType != SummonType.Summoning && summonType != SummonType.RandomSummoning) {
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
