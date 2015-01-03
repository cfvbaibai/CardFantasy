package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class Pray {
    public static void apply(Skill cardSkill, SkillResolver resolver, EntityInfo healer) {
        if (healer == null) {
            return;
        }
        int healHP = cardSkill.getImpact();
        healHero(cardSkill, resolver, healer, healHP);
    }
    
    public static void healHero(Skill cardSkill, SkillResolver resolver, EntityInfo healer, int healHP) {
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
            resolver.attackHero(healer, healee, cardSkill, -healHP);
        } catch (HeroDieSignal e) {
            throw new CardFantasyRuntimeException("Cannot kill hero by Pray!");
        }
    }
}
