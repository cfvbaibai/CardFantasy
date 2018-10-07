package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class WaterArmor {
    public static int apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, CardInfo defender, int originalDamage) throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardSkill, true);
        if(resolver.resolveStopBlockSkill(cardSkill, attacker, defender))
        {
            return originalDamage;
        }
        int maxDamage = cardSkill.getImpact();
        int maxHealHeroDamage = cardSkill.getImpact2();
        if(cardSkill.getType() == SkillType.铁骨衣)
        {
            maxDamage = 650 - cardSkill.getImpact()*50;
            maxHealHeroDamage = 50*cardSkill.getImpact();
        }
        int actualDamage = originalDamage;
        int healHero = 0;
        if (actualDamage > maxDamage) {
            healHero = actualDamage - maxDamage;
            actualDamage = maxDamage;
        }
        if (healHero > maxHealHeroDamage) {
            healHero = maxHealHeroDamage;
        }

        resolver.attackHero(defender, defender.getOwner(), cardSkill, -healHero);

        ui.blockDamage(defender, attacker, defender, cardSkill, originalDamage, actualDamage);
        return actualDamage;
    }
}
