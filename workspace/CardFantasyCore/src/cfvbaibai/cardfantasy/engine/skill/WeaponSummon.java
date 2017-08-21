package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public final class WeaponSummon extends PreAttackCardSkill {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, EntityInfo defender, int min, int max) {
        if (attacker.getStatus().containsStatus(CardStatusType.迷惑)) {
            // 迷魂状态下不发动英雄杀手
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int adjAT = resolver.getStage().getRandomizer().next(min, max);
        //resolver.getStage().getUI().useSkill(attacker, skill, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
    }
}
