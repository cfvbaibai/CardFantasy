package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class ManaErode {
    public static void apply(Skill cardFeature, SkillResolver resolver, CardInfo attacker, Player defender,
            int victimCount) throws HeroDieSignal {
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defender.getField().toList(), victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, cardFeature, true);
        int damage = cardFeature.getImpact();
        for (CardInfo victim : victims) {
            if (victim.containsUsableFeature(SkillType.免疫) ||
                victim.containsUsableFeature(SkillType.法力反射)) {
                // TODO: ui.damageUp
                damage *= 3;
            }
            ui.attackCard(attacker, victim, cardFeature, damage);
            boolean cardDead = resolver.applyDamage(victim, damage).cardDead;
            if (cardDead) {
                resolver.resolveDeathSkills(attacker, victim, cardFeature);
            }
        }
    }
}
