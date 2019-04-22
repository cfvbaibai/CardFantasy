package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public final class VampiricTouch {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, int normalAttackDamage) {
        if (attacker == null || normalAttackDamage <= 0) {
            return;
        }
        List<SkillEffect> effects = attacker.getEffectsCausedBy(skillUseInfo);
        for (SkillEffect effect : effects) {
            if (effect.getCause().getType() == skillUseInfo.getType()) {
                resolver.getStage().getUI().loseAdjustATEffect(attacker, effect);
                attacker.removeEffect(effect);
            }
        }
        Skill skill = skillUseInfo.getSkill();
        Player player = attacker.getOwner();
        int impact = skill.getImpact();
        int number = normalAttackDamage*impact/100;
        int healHP = number;
        if (healHP + player.getHP() > player.getMaxHP()) {
            healHP = player.getMaxHP() - player.getHP();
        }
        resolver.getStage().getUI().useSkill(attacker, skill, true);
        if (healHP != 0) {
            try {
                resolver.attackHero(attacker, player, skill, -healHP);
            } catch (HeroDieSignal e) {
                throw new CardFantasyRuntimeException("Cannot kill hero by Pray!");
            }
            number = number -healHP;
        }
        if(number>0){
            resolver.getStage().getUI().adjustAT(attacker, attacker, number, skill);
            attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, number, false));
        }
    }
}
