package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

/**
 * Chain Lightening give 25 * level damages to 3 enemy's cards and 40%
 * probability to cause paralyzed.
 * 
 * Can be blocked by Immue. Can be reflected by Magic Reflection. Can activate
 * dying feature.
 */
public final class LighteningMagicFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, EntityInfo attacker, Player defender,
            int victimCount, int paralyzeRate) throws HeroDieSignal {
        Feature feature = featureInfo.getFeature();
        List<CardInfo> victims = defender.getField().pickRandom(victimCount, true);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, feature, true);
        for (CardInfo victim : victims) {
            int damage = feature.getImpact();
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, feature, damage);
            if (!result.isAttackable()) {
                continue;
            }
            damage = result.getDamage();
            ui.attackCard(attacker, victim, feature, damage);
            boolean cardDead = resolver.applyDamage(victim, damage).cardDead;
            if (attacker instanceof CardInfo) {
                resolver.resolveCounterAttackFeature((CardInfo)attacker, victim, feature, result);
            }
            if (cardDead) {
                resolver.resolveDeathFeature(attacker, victim, feature);
            } else if (resolver.getStage().getRandomizer().roll100(paralyzeRate)) {
                CardStatusItem status = CardStatusItem.paralyzed(featureInfo);
                if (!resolver.resolveBlockStatusFeature(attacker, victim, featureInfo, status).isBlocked()) {
                    ui.addCardStatus(attacker, victim, feature, status);
                    victim.addStatus(status);
                }
            }
        }
    }
}