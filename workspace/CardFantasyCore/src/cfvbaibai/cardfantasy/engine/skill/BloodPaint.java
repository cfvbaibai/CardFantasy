package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.OnDamagedResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class BloodPaint {
    public static void apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, Player defender, int victimCount) throws HeroDieSignal {
        int damage = cardSkill.getImpact();
        List <CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defender.getField().toList(), victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, cardSkill, true);
        for (CardInfo victim : victims) {
            OnAttackBlockingResult onAttackBlockingResult = resolver.resolveAttackBlockingSkills(attacker, victim, cardSkill, damage);
            if (!onAttackBlockingResult.isAttackable()) {
                if (attacker.isDead()) {
                    break;
                }
                continue;
            }
            int actualDamage = onAttackBlockingResult.getDamage(); 
            ui.attackCard(attacker, victim, cardSkill, actualDamage);
            OnDamagedResult onDamagedResult = resolver.applyDamage(victim, cardSkill, actualDamage);
            ui.healCard(attacker, attacker, cardSkill, actualDamage);
            resolver.applyDamage(attacker, cardSkill, -actualDamage);
            resolver.resolveDeathSkills(attacker, victim, cardSkill, onDamagedResult);
        }
    }
}
