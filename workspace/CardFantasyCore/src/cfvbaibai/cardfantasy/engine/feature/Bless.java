package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public class Bless {
    public static void apply(Skill cardSkill, SkillResolver resolver, EntityInfo healer) {
        if (healer == null) {
            return;
        }
        Player healee = healer.getOwner();
        int healHP = healee.getMaxHP() * cardSkill.getImpact() / 100;
        Pray.healHero(cardSkill, resolver, healer, healHP);
    }
}
