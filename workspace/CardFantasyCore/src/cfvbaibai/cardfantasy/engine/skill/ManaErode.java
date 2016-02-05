package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class ManaErode {
    public static void apply(Skill cardSkill, SkillResolver resolver, EntityInfo attacker, Player defender,
            int victimCount) throws HeroDieSignal {
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defender.getField().toList(), victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, cardSkill, true);
        int damage = cardSkill.getImpact();
        for (CardInfo victim : victims) {
            int actualDamage = damage;
            if (victim.containsUsableSkill(SkillType.免疫) ||
                victim.containsUsableSkill(SkillType.法力反射) ||
                defender.getActiveRuneOf(RuneData.石林) != null) {
                actualDamage *= 3;
            }
            ui.attackCard(attacker, victim, cardSkill, actualDamage);
            resolver.resolveDeathSkills(attacker, victim, cardSkill, resolver.applyDamage(victim, cardSkill, actualDamage));
        }
    }
}
