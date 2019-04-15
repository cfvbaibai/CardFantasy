package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;
import cfvbaibai.cardfantasy.game.DeckBuilder;

public final class ResonantElements {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo resonantCard, String summonedCardsDescs) throws HeroDieSignal{
        if(resonantCard ==null || resonantCard.isDead())
        {
            return;
        }
        Player player =  resonantCard.getOwner();
        List<CardInfo> summonCardCandidates = null;
        summonedCardsDescs = summonedCardsDescs+'-' + resonantCard.getLevel();
        summonCardCandidates = DeckBuilder.build(summonedCardsDescs).getCardInfos(player);
        CardInfo addCard = summonCardCandidates.get(0);
        SkillUseInfo thisSkillUserInfo= null;
        if(resonantCard.getLevel()>=15){
            Skill additionalSkill = resonantCard.getExtraSkill();
            Boolean summonSkill = false;
            Boolean preSkill = false;
            Boolean deathSkill = false;
            Boolean postSkill = false;
            if(additionalSkill !=null) {
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
        List<CardInfo> livingCards = null;
        livingCards = player.getField().getAliveCards();
        for (CardInfo fieldCard : livingCards) {
            if(fieldCard.containsAllSkill(SkillType.原素共鸣)&&!fieldCard.getName().equals(resonantCard.getName())) {
//                player.getField().removeCard(resonantCard);
                player.getField().expelCard(resonantCard.getPosition());
                player.getOutField().addCard(resonantCard);
//                player.getField().removeCard(fieldCard);
                player.getField().expelCard(fieldCard.getPosition());
                player.getOutField().addCard(fieldCard);
//                player.getField().addCard(addCard);

                resolver.resolveLeaveSkills(resonantCard);
                resolver.resolveLeaveSkills(fieldCard);

                resolver.summonCard(addCard.getOwner(), addCard, null, false, skillUseInfo.getSkill(),0);
                resolver.getStage().getUI().useSkill(resonantCard, skillUseInfo.getSkill(), true);
              //  resolver.getStage().getUI().summonCard(player, addCard);
                resolver.getStage().getUI().cardDead(resonantCard);
                resolver.getStage().getUI().cardDead(fieldCard);
                break;
            }
        }

    }
}
