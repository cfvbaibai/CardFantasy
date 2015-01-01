package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.Grave;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class Revive {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo reviver) throws HeroDieSignal {
        if (reviver == null) {
            throw new CardFantasyRuntimeException("reviver should not be null");
        }
        Grave grave = reviver.getOwner().getGrave();
        boolean hasRevivableCard = false;
        for (CardInfo deadCard : grave.toList()) {
            if (deadCard != null && !deadCard.containsUsableFeaturesWithTag(SkillTag.复活)) {
                hasRevivableCard = true;
                break;
            }
        }
        if (!hasRevivableCard) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        // Grave is a stack, find the last-in card and revive it.
        CardInfo cardToRevive = null;
        while (true) {
            CardInfo deadCard = resolver.getStage().getRandomizer().pickRandom(
                grave.toList(), 1, true, null).get(0);
            if (!deadCard.containsUsableFeaturesWithTag(SkillTag.复活)) {
                cardToRevive = deadCard;
                break;
            }
        }

        if (cardToRevive == null) {
            return;
        }
        resolver.getStage().getUI().useSkill(reviver, cardToRevive, skill, true);
        reviver.getOwner().getGrave().removeCard(cardToRevive);
        resolver.summonCard(reviver.getOwner(), cardToRevive, reviver);
        CardStatusItem item = CardStatusItem.weak(skillUseInfo);
        resolver.getStage().getUI().addCardStatus(reviver, cardToRevive, skill, item);
        cardToRevive.addStatus(item);
    }
}
