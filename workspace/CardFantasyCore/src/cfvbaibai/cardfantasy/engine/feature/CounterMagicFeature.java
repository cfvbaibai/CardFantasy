package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class CounterMagicFeature {

    public static boolean isFeatureBlocked(SkillResolver resolver, Skill cardFeature, Skill attackFeature,
            EntityInfo attacker, CardInfo defender) throws HeroDieSignal {
        if (attacker == null) {
            return false;
        }
        if (attackFeature.getType().containsTag(SkillTag.魔法)) {
            int damage = cardFeature.getImpact();
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, cardFeature, true);
            if (attacker instanceof CardInfo) {
                CardInfo cardAttacker = (CardInfo) attacker;
                if (!cardAttacker.isDead()) {
                    ui.attackCard(defender, cardAttacker, cardFeature, damage);
                    if (resolver.applyDamage(cardAttacker, damage).cardDead) {
                        resolver.resolveDeathFeature(defender, cardAttacker, cardFeature);
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
