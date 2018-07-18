package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public final class DevourMultiple {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Player defender, int number) throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();
        GameUI ui = resolver.getStage().getUI();
        Randomizer random = resolver.getStage().getRandomizer();

        List<CardInfo> candidates = random.pickRandom(defender.getField().toList(), number, true, null);

        ui.useSkill(card, candidates, skill, true);
        if (candidates.isEmpty()) {
            return;
        }
        for(CardInfo effectCard:candidates) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(card, effectCard, skill, 1);
            if (!result.isAttackable()) {
                return;
            }
            ui.killCard(card, effectCard, skill);
            resolver.killCard(card, effectCard, skill);
        }
    }
}
