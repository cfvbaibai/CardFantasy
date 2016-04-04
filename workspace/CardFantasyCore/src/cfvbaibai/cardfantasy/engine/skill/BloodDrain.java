package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class BloodDrain {
    public static void apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, CardInfo defender, int damage) throws HeroDieSignal {
        if (attacker == null || attacker.isDead()) {
            throw new CardFantasyRuntimeException("attacker is null or dead!");
        }
        if (attacker.getHP() == attacker.getMaxHP()) {
            return;
        }
        int drainedHP = cardSkill.getImpact() * damage / 100;
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, defender, cardSkill, true);
        ui.healCard(attacker, attacker, cardSkill, drainedHP);
        resolver.applyDamage(attacker, attacker, cardSkill, -drainedHP);
    }
}
