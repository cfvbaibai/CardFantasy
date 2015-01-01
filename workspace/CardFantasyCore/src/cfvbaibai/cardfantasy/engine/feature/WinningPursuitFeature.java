package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.Player;

public class WinningPursuitFeature extends PreAttackCardFeature {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, Player defender) {
        int enemyDeadCount = defender.getGrave().size();
        if (enemyDeadCount == 0) {
            return;
        }
        Skill skill = skillUseInfo.getFeature();
        int adjAT = skill.getImpact() * enemyDeadCount;
        resolver.getStage().getUI().useSkill(attacker, skill, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
    }
}
