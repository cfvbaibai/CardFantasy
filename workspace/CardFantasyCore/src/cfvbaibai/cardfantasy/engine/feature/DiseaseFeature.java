package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class DiseaseFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, CardInfo attacker, CardInfo defender, int normalAttackDamage) throws HeroDieSignal {
        if (normalAttackDamage <= 0 || defender == null || defender.isDead()) {
            return;
        }
        Skill skill = featureInfo.getFeature();
        int damage = skill.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, defender, skill, true);

        OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, defender, skill, damage);
        if (!result.isAttackable()) {
            return;
        }
        
        ui.attackCard(attacker, defender, skill, damage);
        resolver.applyDamage(defender, damage);
        ui.adjustAT(attacker, defender, -damage, skill);
        defender.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, featureInfo, -damage, true));
    }
}
