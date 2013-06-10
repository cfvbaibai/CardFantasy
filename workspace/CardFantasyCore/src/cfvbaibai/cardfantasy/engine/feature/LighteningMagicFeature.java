package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

/**
 * Chain Lightening give 25 * level damages to 3 enemy's cards and 40%
 * probability to cause paralyzed.
 * 
 * Can be blocked by Immue. Can be reflected by Magic Reflection. Can activate
 * dying feature.
 * 
 * @author °×°×
 * 
 */
public final class LighteningMagicFeature {
    public static void apply(FeatureInfo feature, FeatureResolver resolver, CardInfo attacker, Player defender,
            int victimCount, int paralyzeRate) throws HeroDieSignal {
        int damage = feature.getImpact();
        List<CardInfo> victims = defender.getField().pickRandom(victimCount, true);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature);
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, feature);
            if (!result.isAttackable()) {
                continue;
            }
            damage = result.getDamage();
            ui.attackCard(attacker, victim, feature, damage);
            boolean cardDead = resolver.applyDamage(victim, damage).cardDead;
            resolver.resolveCounterAttackFeature(attacker, victim, feature);
            if (cardDead) {
                resolver.resolveDeathFeature(attacker, victim, feature);
            } else if (resolver.getStage().getRandomizer().roll100(paralyzeRate)) {
                CardStatusItem status = CardStatusItem.paralyzed(feature);
                if (!resolver.resolveBlockStatusFeature(attacker, victim, feature, status).isBlocked()) {
                    ui.addCardStatus(attacker, victim, feature, status);
                    victim.addStatus(status);
                }
            }
        }
    }
}