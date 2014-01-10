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

public final class PoisonMagicFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, EntityInfo attacker, Player defender,
            int victimCount) throws HeroDieSignal {
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
                resolver.resolveCounterAttackFeature((CardInfo)attacker, victim, feature, result, null);
            }
            if (cardDead) {
                resolver.resolveDeathFeature(attacker, victim, feature);
            } else {
                CardStatusItem status = CardStatusItem.poisoned(damage, featureInfo);
                ui.addCardStatus(attacker, victim, feature, status);
                victim.addStatus(status);
            }
        }
    }
}
