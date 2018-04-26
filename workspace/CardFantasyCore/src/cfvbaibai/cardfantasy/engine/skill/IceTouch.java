package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class IceTouch {
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
            if (!resolver.resolveAttackBlockingSkills(attacker, victim, skillUseInfo.getSkill(), damage).isAttackable()) {
                continue;
            }
            int actualDamage = damage;
            if (victim.containsAllSkill(SkillType.免疫)|| victim.containsAllSkill(SkillType.结界立场)|| victim.containsAllSkill(SkillType.影青龙)|| victim.containsAllSkill(SkillType.恶龙血脉)|| victim.containsAllSkill(SkillType.魔力抗性) || CounterMagic.getBlockSkill(victim) != null) {
                actualDamage *= magnifier;
            } else {
                if (resolver.getStage().getRandomizer().roll100(rate)) {
                    CardStatusItem status = CardStatusItem.frozen(skillUseInfo);
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
