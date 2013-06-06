package cfvbaibai.cardfantasy.engine.feature;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.GameUI;

public final class RainfallFeature {
    private static class Heal {
        public CardInfo healee;
        public int healHP;
        public Heal(CardInfo healee, int healHP) {
            this.healee = healee;
            this.healHP = healHP;
        }
    }
    public static void apply(Feature feature, FeatureResolver resolver, CardInfo healer) {
        if (healer == null) {
            return;
        }

        GameUI ui = resolver.getStage().getUI();
        Field field = healer.getOwner().getField();
        List<Heal> heals = new ArrayList<Heal>();
        for (CardInfo healee : field) {
            if (healee == null) {
                continue;
            }
            int healHP = feature.getImpact();
            if (healHP + healee.getHP() > healee.getCard().getMaxHP()) {
                healHP = healee.getCard().getMaxHP() - healee.getHP();
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
        ui.useSkill(healer, healees, feature);
        for (Heal heal : heals) {
            // TODO: аяик
            ui.healCard(healer, heal.healee, feature, heal.healHP);
            resolver.applyDamage(heal.healee, -heal.healHP);
        }
    }
}
