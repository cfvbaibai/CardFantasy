package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class CaeserAttack extends PreAttackCardSkill {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, EntityInfo defender) {
        Skill skill = skillUseInfo.getSkill();
        List<CardInfo> adjacentCards = resolver.getAdjacentCards(attacker.getOwner().getField(), attacker.getPosition());
        int adjAT = 0;
        for (CardInfo adjacentCard : adjacentCards) {
            if (adjacentCard != attacker) {
                adjAT += adjacentCard.getLevel1AT() * skill.getImpact() / 100;
            }
        }
        resolver.getStage().getUI().useSkill(attacker, skill, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
        attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
    }
}
