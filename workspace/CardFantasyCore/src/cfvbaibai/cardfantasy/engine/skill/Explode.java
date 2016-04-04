package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class Explode {
    /**
     * 
     * @param resolver
     * @param cardSkill
     * @param attacker The card which attacks the skill owner.
     * @param exploder The card which is attacked and tries to activate Explode skill.
     * @throws HeroDieSignal 
     */
    public static void apply(SkillResolver resolver, Skill cardSkill, EntityInfo attacker, CardInfo exploder) throws HeroDieSignal {

        int damage = cardSkill.getImpact();
        GameUI ui = resolver.getStage().getUI();
        Player attackerOwner = resolver.getStage().getOpponent(exploder.getOwner());
        List<CardInfo> victims = resolver.getAdjacentCards(attackerOwner.getField(), exploder.getPosition());
        for (CardInfo victim : victims) {
            if (victim == null) {
                continue;
            }
            ui.useSkill(exploder, victim, cardSkill, true);
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, cardSkill, damage);
            if (!result.isAttackable()) {
                continue;
            }
            damage = result.getDamage();
            ui.attackCard(exploder, victim, cardSkill, damage);
            resolver.resolveDeathSkills(exploder, victim, cardSkill, resolver.applyDamage(attacker, victim, cardSkill, damage));
        }
    }
}
