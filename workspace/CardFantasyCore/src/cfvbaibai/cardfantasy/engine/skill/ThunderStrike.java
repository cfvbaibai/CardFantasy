package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

/**
 * Created by hasee on 2017/3/30.
 */
public final  class ThunderStrike {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, Player defender,
                             int victimCount) throws HeroDieSignal {
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(defender.getField().toList(),
                victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(attacker, victims, skill, true);
        int damage = skill.getImpact();
        int magnifier = skill.getImpact2();
        int rate = skill.getImpact3();
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, skillUseInfo.getSkill(), damage);
            if (!result.isAttackable()) {
                continue;
            }
            int actualDamage = result.getDamage();
            if (victim.containsAllSkill(SkillType.免疫)|| victim.containsAllSkill(SkillType.结界立场)|| victim.containsAllSkill(SkillType.影青龙) || victim.containsAllSkill(SkillType.魔力抗性)|| CounterMagic.getBlockSkill(victim) != null) {
                if(actualDamage>=damage)
                {
                    actualDamage *= magnifier;
                }
            } else {
                if (resolver.getStage().getRandomizer().roll100(rate)) {
                    CardStatusItem status = CardStatusItem.paralyzed(skillUseInfo);
                    if (!resolver.resolveBlockStatusSkills(attacker, victim, skillUseInfo, status).isBlocked()) {
                        ui.addCardStatus(attacker, victim, skill, status);
                        victim.addStatus(status);
                    }
                }
            }
            ui.attackCard(attacker, victim, skill, actualDamage);
            resolver.resolveDeathSkills(attacker, victim, skill,
                    resolver.applyDamage(attacker, victim, skill, actualDamage));
        }
    }
}
