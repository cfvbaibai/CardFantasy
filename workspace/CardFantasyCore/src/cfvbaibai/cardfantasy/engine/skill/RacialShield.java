package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public class RacialShield {
    public static int apply(Skill skill, SkillResolver resolver, CardInfo attacker, EntityInfo victim,
            EntityInfo blocker, int originalDamage, Race targetRace) {
        if (attacker.getRace() != targetRace) {
            return originalDamage;
        }
        if(blocker instanceof  CardInfo)
        {
            CardInfo defender = (CardInfo) blocker;
            if(resolver.resolveStopBlockSkill(skill, attacker, defender))
            {
                return originalDamage;
            }
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(blocker, skill, true);
        int reduction = skill.getImpact();
        int actualDamage = originalDamage - originalDamage * reduction / 100;
        if (actualDamage < 0) {
            actualDamage = 0;
        }
        ui.protect(blocker, attacker, victim, null, skill);
        ui.blockDamage(blocker, attacker, victim, skill, originalDamage, actualDamage);
        return actualDamage;
    }
}
