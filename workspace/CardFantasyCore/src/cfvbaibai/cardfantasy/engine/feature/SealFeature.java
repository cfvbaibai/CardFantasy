package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class SealFeature {
    public static void apply(FeatureInfo featureInfo, FeatureResolver resolver, CardInfo attacker, Player defender)
            throws HeroDieSignal {
        if (attacker.hasUsed(featureInfo)) {
            return;
        }
        Skill skill = featureInfo.getFeature();
        List<CardInfo> victims = defender.getField().getAliveCards();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingFeature(attacker, victim, skill, 1).isAttackable()) {
                continue;
            }
            CardStatusItem status = CardStatusItem.trapped(featureInfo);
            ui.addCardStatus(attacker, victim, skill, status);
            victim.addStatus(status);
        }
        attacker.setUsed(featureInfo);
    }
}
