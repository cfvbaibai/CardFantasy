package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;
import cfvbaibai.cardfantasy.game.DeckBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SummonMultiple {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo summoner, SummonType summonType, int summonPicks, String... summonedCardsDescs) throws HeroDieSignal {
        if (summoner == null) {
            throw new CardFantasyRuntimeException("summoner should not be null");
        }
        List<CardInfo> cardsToSummon = new ArrayList<CardInfo>();
        List<CardInfo> summonCardCandidates = null;
        int summonNumber=0;
        Player attacker = summoner.getOwner();
        Skill skill = skillUseInfo.getSkill();
        List<CardInfo> livingCards = attacker.getField().getAliveCards();
        summonCardCandidates = DeckBuilder.build(summonedCardsDescs).getCardInfos(attacker);
        for (CardInfo fieldCard : livingCards) {
            if (fieldCard.getStatus().containsStatusCausedBy(skillUseInfo, CardStatusType.召唤)) {
                if(fieldCard.getRelationCardInfo()==summoner)
                {
                    summonNumber++;
                }
            }
        }
        if(summonNumber>=summonPicks)
        {
            return;
        }
        cardsToSummon = Randomizer.getRandomizer().pickRandom(summonCardCandidates, 1, true, null);
        for (int i = 0; i < cardsToSummon.size(); ++i) {
            CardInfo summonedCard = cardsToSummon.get(i);
            CardStatusItem summonedStatusItem = CardStatusItem.summoned(skillUseInfo);
            resolver.getStage().getUI().addCardStatus(summoner, summonedCard, skill, summonedStatusItem);
            summonedCard.addStatus(summonedStatusItem);
            CardStatusItem weakStatusItem = CardStatusItem.weak(skillUseInfo);
            resolver.getStage().getUI().addCardStatus(summoner, summonedCard, skill, weakStatusItem);
            summonedCard.addStatus(weakStatusItem);
            summonedCard.setRelationCardInfo(summoner);
            resolver.summonCard(summonedCard.getOwner(), summonedCard, summoner, true, skill,1);
        }
    }
}
