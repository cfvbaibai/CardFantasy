package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
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
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.虚梦
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.镜魔
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.九转禁术
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.影青龙
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.恶龙血脉
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.寒心恨雪
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.栗子军团
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.月影分身
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.樱蝶重生
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.幻化
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.幻影
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.北海报恩) {
            for(CardStatusItem item : summoner.getStatus().getAllItems())
            {
                if(item.getType()==CardStatusType.召唤){
                    if(item.getCause().getOwner() instanceof CardInfo){
                        if(((CardInfo)item.getCause().getOwner()).getName().equals(summoner.getName()))
                        {
                            return;
                        }
                    }
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
            String parentCardName="";
            for (CardInfo fieldCard : livingCards) {
                if (fieldCard.getStatus().containsStatusCausedBy(skillUseInfo, CardStatusType.召唤)) {
                    anySummonedCardStillAlive = true;
                }
                //else if判断的是卡牌的召唤物的召唤物不能重复召唤。
                else if(summoner.isSummonedMinion())
                {
                    for(CardStatusItem item : fieldCard.getStatus().getAllItems())
                    {
                        if(item.getType()==CardStatusType.召唤){
                            if(item.getCause().getOwner() instanceof CardInfo){
                                if(item.getCause().getSkill()!=skillUseInfo.getSkill())
                                {
                                    break;//修复召唤物多次召唤同一个时只发动一次。
                                }
                                if(((CardInfo)item.getCause().getOwner()).isSummonedMinion())
                                {
                                    parentCardName=((CardInfo) item.getCause().getOwner()).getName();
                                    break;
                                }
                            }
                            break;
                        }
                    }
                    if(!parentCardName.equals(""))
                    {
                        if(summoner.getName().equals(parentCardName))
                        {
                                anySummonedCardStillAlive = true;
                        }
                    }
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
                String parentCard="";
                for (CardInfo fieldCard : livingCards) {
                    if (fieldCard.getStatus().containsStatusCausedBy(skillUseInfo, CardStatusType.召唤)) {
                        if (fieldCard.getName().equals(summonCardCandidate.getName())) {
                            cardStillAlive = true;
                            continue;
                        }
                    }
                    //else if判断的是卡牌的召唤物的召唤物不能重复召唤。
                    else if(summoner.isSummonedMinion())
                    {
                        for(CardStatusItem item : fieldCard.getStatus().getAllItems())
                        {
                            if(item.getType()==CardStatusType.召唤){
                                if(item.getCause().getOwner() instanceof CardInfo){
                                    if(((CardInfo)item.getCause().getOwner()).isSummonedMinion())
                                    {
                                        parentCard=((CardInfo) item.getCause().getOwner()).getName();
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        if(!parentCard.equals(""))
                        {
                            if(summoner.getName().equals(parentCard))
                            {
                                if (fieldCard.getName().equals(summonCardCandidate.getName())) {
                                    cardStillAlive = true;
                                }
                            }
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
            //去掉降临召唤不添加复活，所有召唤都添加复活状态，回合开始移除。
            //   if (summonType != SummonType.Summoning && summonType != SummonType.RandomSummoning) {
            //新生召唤的可以直接攻击。
            if (!skillUseInfo.getType().containsTag(SkillTag.新生)){
                CardStatusItem weakStatusItem = CardStatusItem.weak(skillUseInfo);
                resolver.getStage().getUI().addCardStatus(summoner, summonedCard, skill, weakStatusItem);
                summonedCard.addStatus(weakStatusItem);
             }
         //   }
            CardStatusItem summonedStatusItem = CardStatusItem.summoned(skillUseInfo);
            resolver.getStage().getUI().addCardStatus(summoner, summonedCard, skill, summonedStatusItem);
            summonedCard.addStatus(summonedStatusItem);
            resolver.summonCard(summonedCard.getOwner(), summonedCard, summoner, true, skill,1);
        }
    }

    //新生的召唤
    public static void newBornApply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo summoner, SummonType summonType, int summonPicks,CardStatusItem summonedStatusItem, String... summonedCardsDescs) throws HeroDieSignal {
        if (summoner == null) {
            throw new CardFantasyRuntimeException("summoner should not be null");
        }
        Skill skill = skillUseInfo.getSkill();
        List<CardInfo> livingCards = null;
        Player enemy = null;
        List<String> cardDescsToSummon = new LinkedList<String>();
        for (String summonedCardDesc : summonedCardsDescs) {
            cardDescsToSummon.add(summonedCardDesc);
        }
        List<CardInfo> cardsToSummon = null;
        cardsToSummon = DeckBuilder.build(summonedCardsDescs).getCardInfos(summoner.getOwner());

        if (cardsToSummon.size() > 0) {
            resolver.getStage().getUI().useSkill(summoner, skill, true);
        }
        for (int i = 0; i < cardsToSummon.size(); ++i) {
            CardInfo summonedCard = cardsToSummon.get(i);
            resolver.getStage().getUI().addCardStatus(summoner, summonedCard, skill, summonedStatusItem);
            summonedCard.addStatus(summonedStatusItem);
            resolver.summonCard(summonedCard.getOwner(), summonedCard, summoner, true, skill,1);
        }
    }
}
