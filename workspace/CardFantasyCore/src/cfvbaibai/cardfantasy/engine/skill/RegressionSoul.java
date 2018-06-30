package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Hand;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class RegressionSoul {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo resurrector,Player opponent) {
        if (resurrector == null) {
            throw new CardFantasyRuntimeException("resurrector should not be null");
        }
        Skill skill = skillUseInfo.getSkill();
        // Grave is a stack, find the last-in card and revive it.
        int resurrectionCount = skill.getImpact();
        Player player = resurrector.getOwner();

        List<CardInfo> deadCards = player.getGrave().toList();
        if(!skillUseInfo.getSkill().isPostcastSkill())
        {
            deadCards.remove(resurrector);
        }
        List<CardInfo> cardsToResurrect = Randomizer.getRandomizer().pickRandom(
                deadCards, resurrectionCount, true, null);
        if (cardsToResurrect.size() > resurrectionCount) {
            throw new CardFantasyRuntimeException("cardsToResurrect.size() = " + cardsToResurrect.size() + ", resurrectionCount = " + resurrectionCount);
        }
        if(cardsToResurrect.size()==0)
        {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(resurrector, cardsToResurrect, skill, true);
        for (CardInfo card : cardsToResurrect) {
            Hand hand = player.getHand();
            if (hand.isFull()||card.getIsDeathNow()) {

            } else {
                ui.cardToHand(player, card);
                player.getGrave().removeCard(card);
                hand.addCard(card);
            }
        }
        HellPrison.apply(resolver,opponent,player);
        HellPrison.applyCoordination(resolver,opponent);
    }
}
