package cfvbaibai.cardfantasy.engine.skill;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;
import java.util.List;
public class EvenCut {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker, Player defender,
                             int victimCount, int multiple) throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        GameUI ui = stage.getUI();

        List<CardInfo> victims = random.pickRandom(defender.getField().toList(), victimCount, true, null);
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            ui.useSkill( attacker,victim, null, true);
            if(multiple ==0)
            {
                multiple = skill.getImpact();
            }
            int damage = attacker.getLevel1AT()*multiple/100;
                ui.attackCard(attacker, victim, null, damage);
                resolver.resolveDeathSkills(attacker, victim, skill, resolver.applyDamage(attacker,victim, null, damage));
        }
    }
}
