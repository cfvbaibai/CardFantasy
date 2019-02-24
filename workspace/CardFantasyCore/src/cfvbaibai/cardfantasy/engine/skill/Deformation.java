package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;
import cfvbaibai.cardfantasy.game.DeckBuilder;

import java.util.ArrayList;
import java.util.List;

public class Deformation {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo summoner,  String... summonedCardsDescs) throws HeroDieSignal {
        if (summoner == null) {
            throw new CardFantasyRuntimeException("summoner should not be null");
        }
        int threshold = skillUseInfo.getSkill().getImpact();
        if (summoner.getHP() >= summoner.getMaxHP() * threshold / 100) {
            return;
        }
        Player player =  summoner.getOwner();
        List<CardInfo> summonCardCandidates = DeckBuilder.build(summonedCardsDescs).getCardInfos(summoner.getOwner());
        CardInfo addCard = summonCardCandidates.get(0);
        resolver.getStage().getUI().useSkill(summoner, skillUseInfo.getSkill(), true);
        if (summoner.getRelationCardInfo() != null) {
            addCard = summoner.getRelationCardInfo();
            addCard.reset();
            for(CardStatusItem cardStatusItem:summoner.getStatus().getStatusOf(CardStatusType.召唤)){
                addCard.addStatus(cardStatusItem);
            }
            player.getOutField().removeCard(addCard);
            //player.getField().removeCard(summoner);
            player.getField().expelCard(summoner.getPosition());
            if(!player.getOutField().contains(summoner)){
                player.getOutField().addCard(summoner);
            }
            resolver.getStage().getUI().cardDead(summoner);
            resolver.resolveLeaveSkills(summoner);
            resolver.summonCard(summoner.getOwner(), addCard, summoner, false, skillUseInfo.getSkill(), 1);
        } else {
            if (summoner.getLevel() >= 15) {
                SkillUseInfo thisSkillUserInfo= null;
                Skill additionalSkill = summoner.getExtraSkill();
                if(additionalSkill!=null) {
                    Boolean summonSkill = false;
                    Boolean preSkill = false;
                    Boolean deathSkill = false;
                    Boolean postSkill = false;
                    if (additionalSkill.isPostcastSkill()) {
                        postSkill = true;
                    } else if (additionalSkill.isDeathSkill()) {
                        deathSkill = true;
                    } else if (additionalSkill.isPrecastSkill()) {
                        preSkill = true;
                    } else if (additionalSkill.isSummonSkill()) {
                        summonSkill = true;
                    }
                    CardSkill cardSkill = new CardSkill(additionalSkill.getType(), additionalSkill.getLevel(), 0, summonSkill, deathSkill, preSkill, postSkill);
                    thisSkillUserInfo = new SkillUseInfo(addCard, cardSkill);
                    addCard.setExtraSkill(cardSkill);
                    addCard.addSkill(thisSkillUserInfo);
                }
            }
            for(CardStatusItem cardStatusItem:summoner.getStatus().getStatusOf(CardStatusType.召唤)){
                addCard.addStatus(cardStatusItem);
            }
            addCard.setRelationCardInfo(summoner);
            summoner.setRelationCardInfo(addCard);
//            player.getField().removeCard(summoner);
            player.getField().expelCard(summoner.getPosition());
            player.getOutField().addCard(summoner);
            resolver.getStage().getUI().cardDead(summoner);
            resolver.resolveLeaveSkills(summoner);
            resolver.summonCard(summoner.getOwner(), addCard, summoner, false, skillUseInfo.getSkill(), 1);
        }
    }
}
