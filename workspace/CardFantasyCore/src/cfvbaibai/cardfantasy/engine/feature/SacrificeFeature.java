package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.GameUI;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class SacrificeFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo feature, CardInfo card) throws HeroDieSignal {
        if (!card.isFirstRound()) {
            return;
        }
        
        Field field = card.getOwner().getField();
        List<CardInfo> candidates = field.pickRandom(1, true, card);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(card, candidates, feature);
        if (candidates.isEmpty()) {
            return;
        }
        CardInfo oblation = candidates.get(0);
        OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(card, oblation, feature);
        if (!result.isAttackable()) {
            return;
        }
        ui.killCard(card, oblation, feature);
        // Sacrifice does not trigger death features.
        resolver.cardDead(oblation);
        
        int adjHP = feature.getImpact() * card.getMaxHP() / 100;
        int adjAT = feature.getImpact() * card.getAT() / 100;
        ui.adjustHP(card, card, adjHP, feature);
        ui.adjustAT(card, card, adjAT, feature);
        card.addEffect(new FeatureEffect(FeatureEffectType.MAXHP_CHANGE, feature, adjHP, true));
        card.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, feature, adjAT, true));
    }
}
