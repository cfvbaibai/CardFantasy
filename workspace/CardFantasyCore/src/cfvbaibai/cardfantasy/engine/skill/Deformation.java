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
            player.getOutField().removeCard(addCard);
            player.getField().removeCard(summoner);
            player.getOutField().addCard(summoner);
            resolver.getStage().getUI().cardDead(summoner);
            resolver.summonCard(summoner.getOwner(), addCard, summoner, false, skillUseInfo.getSkill(), 0);
        } else {
            if (summoner.getLevel() >= 15) {
                int size = summoner.getAllUsableSkills().size();
                SkillUseInfo thisSkillUserInfo= null;
                Skill additionalSkill = summoner.getAllUsableSkills().get(size - 1).getSkill();
                CardSkill cardSkill = new CardSkill(additionalSkill.getType(), additionalSkill.getLevel(), 0, false, false, false, false);
                thisSkillUserInfo = new SkillUseInfo(addCard, cardSkill);
                addCard.addSkill(thisSkillUserInfo);
            }
            addCard.setRelationCardInfo(summoner);
            summoner.setRelationCardInfo(addCard);
            player.getField().removeCard(summoner);
            player.getOutField().addCard(summoner);
            resolver.getStage().getUI().cardDead(summoner);
            resolver.summonCard(summoner.getOwner(), addCard, summoner, false, skillUseInfo.getSkill(), 1);
        }
    }
}
