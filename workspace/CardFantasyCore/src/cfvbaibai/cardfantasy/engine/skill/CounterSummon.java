package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardPile;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public class CounterSummon {
    public static void apply(SkillResolver resolver, CardInfo defender, Skill skill, int star) throws HeroDieSignal {
        if (defender == null) {
            return;
        }
        if (!resolver.getStage().getRandomizer().roll100(30)) {
            return;
        }
        int summonCount = skill.getImpact();
        List<CardInfo> deckCards = defender.getOwner().getDeck().toList();
        CardPile candidates = new CardPile();
        for (CardInfo card : deckCards) {
            if (card.getStar() == star) {
                candidates.addCard(card);
            }
        }
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> summonCards = resolver.getStage().getRandomizer().pickRandom(
            candidates.toList(), summonCount, true, null);
        ui.useSkill(defender, summonCards, skill, true);
        for (CardInfo summonCard : summonCards) {
            if (!summonCard.getOwner().getDeck().removeCard(summonCard)) {
                throw new CardFantasyRuntimeException(
                    "[CounterSummon] Cannot find card " + summonCard.getShortDesc() + " in deck of " + defender.getId());
            }
            resolver.summonCard(summonCard.getOwner(), summonCard, defender, false);
        }
    }
}
