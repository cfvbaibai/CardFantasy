package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class Resurrection {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo resurrector) {
        if (resurrector == null) {
            throw new CardFantasyRuntimeException("resurrector should not be null");
        }
        Skill skill = skillUseInfo.getSkill();
        // Grave is a stack, find the last-in card and revive it.
        int resurrectionCount = skill.getImpact();
        Player player = resurrector.getOwner();
        List<CardInfo> exclusions = null;
        if (skill.isDeathSkill()) {
            exclusions = new ArrayList<CardInfo>();
            exclusions.add(resurrector);
        }
        List<CardInfo> deadCards = player.getGrave().toList();
        List<CardInfo> cardsToResurrect = Randomizer.getRandomizer().pickRandom(
            deadCards, resurrectionCount, true, exclusions);
        if (cardsToResurrect.size() > resurrectionCount) {
            throw new CardFantasyRuntimeException("cardsToResurrect.size() = " + cardsToResurrect.size() + ", resurrectionCount = " + resurrectionCount);
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(resurrector, cardsToResurrect, skill, true);
        if (SoulSeal.soulSealed(resolver, resurrector)) {
            return;
        }
        for (CardInfo card : cardsToResurrect) {
            ui.cardToDeck(player, card);
            player.getGrave().removeCard(card);
            if (player.getDeck().size() > 0) {
       //     int position = Randomizer.getRandomizer().next(0, player.getDeck().size());
         // 回魂是有顺序的。
                int position = 0;
                player.getDeck().insertCardToPosition(card, position);
            } else {
                player.getDeck().addCard(card);
            }
        }
    }
}
