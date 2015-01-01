package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Player;

public final class ResurrectionFeature {
    public static void apply(FeatureResolver resolver, SkillUseInfo skillUseInfo, CardInfo resurrector) {
        if (resurrector == null) {
            throw new CardFantasyRuntimeException("resurrector should not be null");
        }
        if (resurrector.getOwner() != resolver.getStage().getActivePlayer()) {
            throw new CardFantasyRuntimeException("resurrector is not the current active player!");
        }
        Skill skill = skillUseInfo.getFeature();
        // Grave is a stack, find the last-in card and revive it.
        int resurrectionCount = skill.getImpact();
        Player player = resurrector.getOwner();
        List<CardInfo> cardsToResurrect = player.getGrave().pop(resurrectionCount);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(resurrector, cardsToResurrect, skill, true);
        for (CardInfo card : cardsToResurrect) {
            ui.cardToDeck(player, card);
            player.getDeck().addCard(card);
        }
    }
}
