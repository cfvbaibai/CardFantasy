package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.Player;

public final class Resurrection {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo resurrector) {
        if (resurrector == null) {
            throw new CardFantasyRuntimeException("resurrector should not be null");
        }
        Skill skill = skillUseInfo.getSkill();
        // Grave is a stack, find the last-in card and revive it.
        int resurrectionCount = skill.getImpact();
        Player player = resurrector.getOwner();
        CardInfo exclusion = skill.isDeathSkill() ? resurrector : null;
        List<CardInfo> deadCards = player.getGrave().toList();
        List<CardInfo> cardsToResurrect = Randomizer.getRandomizer().pickRandom(deadCards, resurrectionCount, true, exclusion);
        if (cardsToResurrect.size() > resurrectionCount) {
            throw new CardFantasyRuntimeException("cardsToResurrect.size() = " + cardsToResurrect.size() + ", resurrectionCount = " + resurrectionCount);
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(resurrector, cardsToResurrect, skill, true);
        for (CardInfo card : cardsToResurrect) {
            ui.cardToDeck(player, card);
            player.getGrave().removeCard(card);
            if (player.getDeck().size() > 0) {
            int position = Randomizer.getRandomizer().next(0, player.getDeck().size());
                player.getDeck().insertCardToPosition(card, position);
            } else {
                player.getDeck().addCard(card);
            }
        }
    }
}
