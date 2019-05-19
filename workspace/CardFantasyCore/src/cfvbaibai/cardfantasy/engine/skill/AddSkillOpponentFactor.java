package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public class AddSkillOpponentFactor {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Skill addSkill,Player defenderHero,int factorType)throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();
        CardSkill cardSkill = new CardSkill(addSkill.getType(), addSkill.getLevel(), 0, false, false, false, false);
        resolver.getStage().getUI().useSkill(card, skill, true);
        List<CardInfo> allHandCards = defenderHero.getHand().toList();
        List<CardInfo> addCard= new ArrayList<CardInfo>();
        List<CardInfo> revivableCards = new ArrayList<CardInfo>();
        SkillUseInfo thisSkillUserInfo=null;
        for (CardInfo handCard : allHandCards) {
            if (handCard != null && !handCard.containsAllSkill(addSkill.getType())) {
                revivableCards.add(handCard);
            }
        }
        if (revivableCards.isEmpty()) {
            return;
        }
//        addCard = Randomizer.getRandomizer().pickRandom(
//                revivableCards, number, true, null);
        if(factorType == 0){
            CardInfo opCard = null;
            for(CardInfo cardInfo:revivableCards){
                if(opCard == null){
                    opCard = cardInfo;
                }else if(opCard.getSummonDelay() > cardInfo.getSummonDelay()){
                    opCard = cardInfo;
                }
            }
            addCard.add(opCard);
        }


        for (CardInfo once : addCard) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(card, once, skill, 1);
            if(!result.isAttackable()) {
                continue;
            }
            if(once.containsAllSkill(addSkill.getType()))
            {
                continue;
            }
            thisSkillUserInfo = new SkillUseInfo(once,cardSkill);
            thisSkillUserInfo.setGiveSkill(2);
            once.addSkill(thisSkillUserInfo);
        }
    }

}
