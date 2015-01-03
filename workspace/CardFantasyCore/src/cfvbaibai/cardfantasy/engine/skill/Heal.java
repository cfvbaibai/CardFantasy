package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class Heal {
    public static void apply(Skill cardFeature, SkillResolver resolver, EntityInfo healer) {
        if (healer == null) {
            return;
        }
        int healHP = cardFeature.getImpact();
        CardInfo healee = resolver.pickHealee(healer);
        if (healee == null) {
            return;
        }
        if (healHP + healee.getHP() > healee.getMaxHP()) {
            healHP = healee.getMaxHP() - healee.getHP();
        }
        if (healHP == 0) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(healer, healee, cardFeature, true);
        OnAttackBlockingResult result = resolver.resolveHealBlockingSkills(healer, healee, cardFeature);
        if (!result.isAttackable()) {
            return;
        }
        ui.healCard(healer, healee, cardFeature, healHP);
        resolver.applyDamage(healee, -healHP);
    }
}
