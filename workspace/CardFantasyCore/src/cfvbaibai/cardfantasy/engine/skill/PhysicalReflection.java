package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class PhysicalReflection {
    public static void apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, CardInfo defender,
            int actualDamage) throws HeroDieSignal {
        if (actualDamage <= 0) {
            return;
        }
        if (attacker == null) {
            return;
        }
        int damage = actualDamage / 2;
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardSkill, true);
        if (!resolver.resolverCounterAttackBlockSkill(cardSkill, attacker, defender)) {
            ui.attackCard(defender, attacker, cardSkill, damage);
            resolver.resolveDeathSkills(defender, attacker, cardSkill, resolver.applyDamage(attacker, cardSkill, damage));
        }
    }
}
