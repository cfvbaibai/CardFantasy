package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class PrayFeature {
    public static void apply(Skill cardFeature, SkillResolver resolver, EntityInfo healer) {
        if (healer == null) {
            return;
        }
        int healHP = cardFeature.getImpact();
        Player healee = healer.getOwner();
        if (healHP + healee.getHP() > healee.getMaxHP()) {
            healHP = healee.getMaxHP() - healee.getHP();
        }
        if (healHP == 0) {
            return;
        }
        if (healHP < 0) {
            throw new CardFantasyRuntimeException("Invalid hero heal HP: " + healHP);
        }
        try {
            resolver.attackHero(healer, healee, cardFeature, -healHP);
        } catch (HeroDieSignal e) {
            throw new CardFantasyRuntimeException("Cannot kill hero by Pray!");
        }
    }
}
