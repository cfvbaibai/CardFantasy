package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

/**
 * Snipe - ¾Ñ»÷, DoubleSnipe - ¶þÖØ¾Ñ»÷
 * Activated after normal attack, apply extra unavoidable, unblockable damage
 * to opponent's cards with least HP on field. 
 * 
 * Snipe cannot be blocked by any defense features like Immue or Dodge.
 * Dying features could be activated by death caused by Snipe.
 * @author °×°×
 *
 */
public final class SnipeFeature {
    public static void apply(Feature cardFeature, FeatureResolver resolver, CardInfo attacker, Player defenderPlayer, int targetCount) throws HeroDieSignal {
        int damage = cardFeature.getImpact();
        List<CardInfo> victims = defenderPlayer.getField().getCardsWithLowestHP(targetCount);
        resolver.getStage().getUI().useSkill(attacker, victims, cardFeature);
        for (CardInfo victim : victims) {
            resolver.getStage().getUI().attackCard(attacker, victim, cardFeature, damage);
            if (resolver.applyDamage(victim, damage).cardDead) {
                resolver.resolveDeathFeature(attacker, victim, cardFeature);
            }
        }
    }
}
