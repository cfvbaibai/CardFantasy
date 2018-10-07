package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public final class ReviveMultiple {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo reviver) throws HeroDieSignal {
        if (reviver == null) {
            throw new CardFantasyRuntimeException("reviver should not be null");
        }
        Grave grave = reviver.getOwner().getGrave();
        List<CardInfo> revivableCards = new ArrayList<CardInfo>();
        for (CardInfo deadCard : grave.toList()) {
            if (deadCard != null &&!deadCard.getIsDeathNow()&& !deadCard.containsAllUsableSkillsWithTag(SkillTag.复活)) {
                revivableCards.add(deadCard);
            }
        }
        if (revivableCards.isEmpty()) {
            return;
        }
        if (SoulSeal.soulSealed(resolver, reviver)) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        for(CardInfo cardToRevive:revivableCards) {
            if(!reviver.getOwner().getGrave().contains(cardToRevive))
            {
                continue;
            }
            resolver.getStage().getUI().useSkill(reviver, cardToRevive, skill, true);
            reviver.getOwner().getGrave().removeCard(cardToRevive);
            resolver.summonCard(reviver.getOwner(), cardToRevive, reviver, false, skill, 0);
            CardStatusItem item = CardStatusItem.weak(skillUseInfo);
            resolver.getStage().getUI().addCardStatus(reviver, cardToRevive, skill, item);
            cardToRevive.addStatus(item);
        }
    }
}
