package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Player;

public class WinningPursuitFeature extends PreAttackCardFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo attacker, Player defender) {
        int enemyDeadCount = defender.getGrave().size();
        if (enemyDeadCount == 0) {
            return;
        }
        Skill skill = featureInfo.getFeature();
        int adjAT = skill.getImpact() * enemyDeadCount;
        resolver.getStage().getUI().useSkill(attacker, skill, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, featureInfo, adjAT, false));
    }
}
