package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class Plague {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, Player defenderHero)
            throws HeroDieSignal {
        Skill skill = skillUseInfo.getFeature();
        int damage = skill.getImpact();
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = defenderHero.getField().getAliveCards();
        ui.useSkill(attacker, victims, skill, true);

        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, skill, damage);
            if (!result.isAttackable()) {
                continue;
            }

            ui.attackCard(attacker, victim, skill, damage);
            resolver.applyDamage(victim, damage);
            ui.adjustAT(attacker, victim, -damage, skill);
            victim.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, -damage, true));
        }
    }
}
