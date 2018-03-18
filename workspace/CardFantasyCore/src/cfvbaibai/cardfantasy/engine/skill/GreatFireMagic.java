package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public final class GreatFireMagic {
    public static void apply(Skill cardSkill, SkillResolver resolver, EntityInfo attacker, Player defender,
            int victimCount) throws HeroDieSignal {
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        GameUI ui = stage.getUI();

        List<CardInfo> victims = random.pickRandom(defender.getField().toList(), victimCount, true, null);
        ui.useSkill(attacker, victims, cardSkill, true);
        for (CardInfo victim : victims) {
            int damage = random.next(cardSkill.getImpact(), cardSkill.getImpact2()  + 1);
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, cardSkill, damage);
            if (!result.isAttackable()) {
                continue;
            }
            damage = result.getDamage();
            if (attacker instanceof CardInfo) {
                resolver.resolveCounterAttackSkills((CardInfo)attacker, victim, cardSkill, result, null);
            }
            ui.attackCard(attacker, victim, cardSkill, damage);
            resolver.resolveDeathSkills(attacker, victim, cardSkill, resolver.applyDamage(attacker, victim, cardSkill, damage));
        }
    }
}
