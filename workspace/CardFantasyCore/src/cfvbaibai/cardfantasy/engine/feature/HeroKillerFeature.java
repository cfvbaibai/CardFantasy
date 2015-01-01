package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Player;

public final class HeroKillerFeature {

    private HeroKillerFeature() {
    }

    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo attacker, Player victim) {
        Skill skill = featureInfo.getFeature();
        int adj = attacker.getLevel1AT() * skill.getImpact() / 100;
        resolver.getStage().getUI().useSkill(attacker, skill, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adj, skill);
        attacker.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, featureInfo, adj, false));
    }
    
    public static void remove(FeatureResolver resolver, FeatureInfo feature, CardInfo card) {
        List<FeatureEffect> effects = card.getEffectsCausedBy(feature);
        for (FeatureEffect effect : effects) {
            resolver.getStage().getUI().loseAdjustATEffect(card, effect);
            card.removeEffect(effect);
        }
    }
}
