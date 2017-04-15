package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

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
        List<CardInfo> revivableCards = new ArrayList<CardInfo>();
        for (CardInfo deadCard : grave.toList()) {
            if (deadCard != null && !deadCard.containsUsableSkillsWithTag(SkillTag.复活) && deadCard.getStar() != 1) {
                revivableCards.add(deadCard);
            }
        }
        if (revivableCards.isEmpty()) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        CardInfo cardToRevive = resolver.getStage().getRandomizer().pickRandom(
                revivableCards, 1, true, null).get(0);
        resolver.getStage().getUI().useSkill(reviver, cardToRevive, skill, true);
        if (SoulSeal.soulSealed(resolver, reviver)) {
            return;
        }
        reviver.getOwner().getGrave().removeCard(cardToRevive);
        resolver.summonCard(reviver.getOwner(), cardToRevive, reviver, false, skill);
        CardStatusItem item = CardStatusItem.weak(skillUseInfo);
        resolver.getStage().getUI().addCardStatus(reviver, cardToRevive, skill, item);
        cardToRevive.addStatus(item);
    }
}
