package cfvbaibai.cardfantasy.engine.feature;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class RainfallFeature {
    private static class Heal {
        public CardInfo healee;
        public int healHP;
        public Heal(CardInfo healee, int healHP) {
            this.healee = healee;
            this.healHP = healHP;
        }
    }
    public static void apply(Feature cardFeature, FeatureResolver resolver, EntityInfo healer) {
        if (healer == null) {
            return;
        }

        GameUI ui = resolver.getStage().getUI();
        Field field = healer.getOwner().getField();
        List<Heal> heals = new ArrayList<Heal>();
        for (CardInfo healee : field.getAliveCards()) {
            int healHP = cardFeature.getImpact();
            if (healHP + healee.getHP() > healee.getMaxHP()) {
                healHP = healee.getMaxHP() - healee.getHP();
            }
            if (healHP == 0) {
                continue;
            }
            heals.add(new Heal(healee, healHP));
        }
        List<CardInfo> healees = new ArrayList<CardInfo>();
        for (Heal heal : heals) {
            healees.add(heal.healee);
        }
        ui.useSkill(healer, healees, cardFeature, true);
        for (Heal heal : heals) {
            OnAttackBlockingResult result = resolver.resolveHealBlockingFeature(healer, heal.healee, cardFeature);
            if (!result.isAttackable()) {
                continue;
            }
            ui.healCard(healer, heal.healee, cardFeature, heal.healHP);
            resolver.applyDamage(heal.healee, -heal.healHP);
        }
    }
}
