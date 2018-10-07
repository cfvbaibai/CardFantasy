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

public class SummonWhenAttack {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo summoner, int summonPicks,boolean needSelf, String ...summonedCardsDescs) throws HeroDieSignal {
        if (summoner == null) {
            throw new CardFantasyRuntimeException("summoner should not be null");
        }
        Skill skill = skillUseInfo.getSkill();
        int maxNumber = skill.getImpact();
        int rate = skill.getImpact2();
        if(!resolver.getStage().getRandomizer().roll100(rate))
        {
            return ;
        }
        List<CardInfo> livingCards = null;
        livingCards = summoner.getOwner().getField().getAliveCards();

        List<CardInfo> cardsToSummon = new ArrayList<CardInfo>();
        List<CardInfo> summonCardCandidates = null;
        summonCardCandidates = DeckBuilder.build(summonedCardsDescs).getCardInfos(summoner.getOwner());
        List<CardInfo> liveSummonCard = new ArrayList<>();
        boolean needFlag = false;
        CardInfo initCardInfo = summoner;
        SkillUseInfo initSkillUserInfo = skillUseInfo;
        //暂时不考虑多重召唤者，是召唤物的情形。
        if(summoner.isSummonedMinion()) {
            for (CardStatusItem item : summoner.getStatus().getAllItems()) {
                if (item.getType() == CardStatusType.召唤) {
                    if(item.getCause().getType()==skillUseInfo.getType())
                    {
                        initSkillUserInfo =item.getCause();
                        initCardInfo = (CardInfo) item.getCause().getOwner();
                        break;
                    }
                }
            }
        }

        for (CardInfo fieldCard : livingCards) {
            if (!fieldCard.isSummonedMinion()) {
                if(needSelf&&!needFlag)
                {
                    if(fieldCard == initCardInfo)
                    {
                        needFlag = true;
                    }
                }
                continue;
            }
            for(CardInfo summon:summonCardCandidates){
                if(summon.getName().equals(fieldCard.getName()))
                {
                    liveSummonCard.add(fieldCard);
                }
            }
        }
        if(needSelf&&!needFlag)
        {
            return;
        }
        if(liveSummonCard.size()>=maxNumber)
        {
            return;
        }


        cardsToSummon = Randomizer.getRandomizer().pickRandom(summonCardCandidates, summonPicks, true, null);

        if (cardsToSummon.size() > 0) {
            resolver.getStage().getUI().useSkill(summoner, skill, true);
        }
        for (int i = 0; i < cardsToSummon.size(); ++i) {
            CardInfo summonedCard = cardsToSummon.get(i);
            CardStatusItem weakStatusItem = CardStatusItem.weak(skillUseInfo);
            resolver.getStage().getUI().addCardStatus(summoner, summonedCard, skill, weakStatusItem);
            summonedCard.addStatus(weakStatusItem);
            CardStatusItem summonedStatusItem = CardStatusItem.summoned(initSkillUserInfo);
            resolver.getStage().getUI().addCardStatus(summoner, summonedCard, skill, summonedStatusItem);
            summonedCard.addStatus(summonedStatusItem);
            resolver.summonCard(summonedCard.getOwner(), summonedCard, summoner, true, skill,1);
        }
    }
}
