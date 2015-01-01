package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

/**
 * Trap 1*level enemy card at 65% probability.
 * 
 * Can be blocked by Immue
 */
public final class TrapFeature {
    public static void apply(SkillUseInfo skillUseInfo, FeatureResolver resolver, CardInfo attacker, Player defender)
            throws HeroDieSignal {
        Skill skill = skillUseInfo.getFeature();
        int targetCount = skill.getImpact();
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defender.getField().toList(), targetCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingFeature(attacker, victim, skill, 1).isAttackable()) {
                continue;
            }
            if (resolver.getStage().getRandomizer().roll100(65)) {
                CardStatusItem status = CardStatusItem.trapped(skillUseInfo);
                ui.addCardStatus(attacker, victim, skill, status);
                victim.addStatus(status);
            }
        }
    }
}
