package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Field;

public class HolyGuardFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo card) {
        if (card == null || card.isDead()) {
            throw new CardFantasyRuntimeException("card should not be null or dead!");
        }
        Skill skill = featureInfo.getFeature();
        int impact = skill.getImpact();
        resolver.getStage().getUI().useSkill(card, skill, true);
        Field field = card.getOwner().getField();
        List<CardInfo> allies = resolver.getAdjacentCards(field, card.getPosition());
        for (CardInfo ally : allies) {
            if (ally.getEffectsCausedBy(featureInfo).isEmpty()) {
                resolver.getStage().getUI().adjustHP(card, ally, impact, skill);
                ally.addEffect(new FeatureEffect(FeatureEffectType.MAXHP_CHANGE, featureInfo, impact, false));
            }
        }
    }
    
    public static void remove(FeatureResolver resolver, FeatureInfo feature, CardInfo card) {
        for (CardInfo ally : card.getOwner().getField().toList()) {
            if (ally == null) { continue; }
            List<FeatureEffect> effects = ally.getEffectsCausedBy(feature);
            for (FeatureEffect effect : effects) {
                resolver.getStage().getUI().loseAdjustHPEffect(ally, effect);
                ally.removeEffect(effect);
            }
        }
    }
}
