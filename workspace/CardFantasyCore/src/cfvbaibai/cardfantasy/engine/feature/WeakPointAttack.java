package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class WeakPointAttack {
    public static boolean isBlockFeatureDisabled(SkillResolver resolver, Skill attackFeature,
            Skill blockFeature, CardInfo attacker, CardInfo defender) {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("attacker is null");
        }
        if (defender == null) {
            throw new CardFantasyRuntimeException("defender is null");
        }
        if (blockFeature.getType().containsTag(SkillTag.物理护甲)) {
            resolver.getStage().getUI().useSkill(attacker, defender, attackFeature, true);
            resolver.getStage().getUI().disableBlock(attacker, defender, attackFeature, blockFeature);
            return true;
        } else {
            return false;
        }
    }
}
