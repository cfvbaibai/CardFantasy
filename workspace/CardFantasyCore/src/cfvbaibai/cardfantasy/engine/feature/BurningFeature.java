package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class BurningFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, CardInfo attacker, CardInfo defender)
            throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        if (attacker.getStatus().containsStatusCausedBy(featureInfo, CardStatusType.燃烧)) {
            return;
        }
        Skill skill = featureInfo.getFeature();
        int damage = skill.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, skill, true);
        OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(defender, attacker, skill, damage);
        if (!result.isAttackable()) {
            return;
        }
        CardStatusItem status = CardStatusItem.burning(damage, featureInfo);
        ui.addCardStatus(defender, attacker, skill, status);
        attacker.addStatus(status);
    }
}
