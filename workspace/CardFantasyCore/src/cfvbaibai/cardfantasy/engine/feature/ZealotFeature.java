package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class ZealotFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, EntityInfo attacker, CardInfo defender,
            OnAttackBlockingResult result) {
        if (result.getDamage() == 0 || defender == null) {
            return;
        }
        Skill skill = featureInfo.getFeature();
        int adjAT = skill.getImpact();
        resolver.getStage().getUI().useSkill(defender, defender, skill, true);
        resolver.getStage().getUI().adjustAT(defender, defender, adjAT, skill);
        defender.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, featureInfo, adjAT, true));
    }
}
