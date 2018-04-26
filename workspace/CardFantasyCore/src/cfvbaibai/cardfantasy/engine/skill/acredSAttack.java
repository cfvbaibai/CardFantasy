package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public final class acredSAttack {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, Player defender,
            int victimCount) throws HeroDieSignal {
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(defender.getField().toList(),
                victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(attacker, victims, skill, true);
        int damage = skill.getImpact();
        int rate = skill.getImpact2();
        for (CardInfo victim : victims) {
            int actualDamage = damage;
            if (resolver.getStage().getRandomizer().roll100(rate)) {
                CardStatusItem status = CardStatusItem.faint(skillUseInfo);
                if (!resolver.resolveBlockStatusSkills(attacker, victim, skillUseInfo, status).isBlocked()) {
                    ui.addCardStatus(attacker, victim, skill, status);
                    victim.addStatus(status);
                }
            }
            ui.attackCard(attacker, victim, skill, actualDamage);
            resolver.resolveDeathSkills(attacker, victim, skill,
                    resolver.applyDamage(attacker, victim, skill, actualDamage));
        }
    }
}
