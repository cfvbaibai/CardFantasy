package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class GiveSideSkill {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Skill addSkill) {
        if (card == null || card.isDead()) {
            throw new CardFantasyRuntimeException("card should not be null or dead!");
        }
        Skill skill = skillUseInfo.getSkill();
        CardSkill cardSkill = new CardSkill(addSkill.getType(), addSkill.getLevel(), 0, false, false, false, false);
        resolver.getStage().getUI().useSkill(card, skill, true);
        Field field = card.getOwner().getField();
        List<CardInfo> allies = resolver.getAdjacentCards(field, card.getPosition());
        SkillUseInfo thisSkillUserInfo=null;
        for (CardInfo ally : allies) {
            if(ally.containsUsableSkill(cardSkill.getType())){
                continue;
            }
            thisSkillUserInfo = new SkillUseInfo(ally,cardSkill);
            thisSkillUserInfo.setGiveSkill(1);
            ally.addSkill(thisSkillUserInfo);
        }
    }

    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card,Skill addSkill) {
        CardSkill cardSkill = new CardSkill(addSkill.getType(), addSkill.getLevel(), 0, false, false, false, false);
        SkillUseInfo thisSkillUserInfo = null;
        for (CardInfo ally : card.getOwner().getField().getAliveCards()) {
            if (ally == null) { continue; }
            thisSkillUserInfo = new SkillUseInfo(ally,cardSkill);
            thisSkillUserInfo.setGiveSkill(1);
            ally.removeSkill(thisSkillUserInfo);
        }
    }

    public static void removeAll(SkillResolver resolver,SkillUseInfo skillUseInfo,CardInfo card) {
            card.removeAllGiveSkill();
    }
}
