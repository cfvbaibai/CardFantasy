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

public class SummonOpponent {
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
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.日光浴
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.护主
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.星座能量掌握
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.龙城之志
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.北海报恩
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.熔岩分身
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.萦梦
                || summoner.isSummonedMinion() && skillUseInfo.getType() == SkillType.复仇之影) {
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
        opSklill = true;
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
}
