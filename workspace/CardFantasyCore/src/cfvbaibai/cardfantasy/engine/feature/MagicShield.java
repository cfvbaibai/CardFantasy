package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class MagicShield {
    public static int apply(SkillResolver resolver, Skill skill, EntityInfo attacker, CardInfo defender,
            Skill attackFeature, int originalDamage) {
        if (!attackFeature.getType().containsTag(SkillTag.魔法)) {
            return originalDamage;
        }
        int maxDamage = skill.getImpact();
        int actualDamage = Math.min(maxDamage, originalDamage);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, skill, true);
        ui.blockDamage(defender, attacker, defender, skill, originalDamage, actualDamage);
        return actualDamage;
    }
}
