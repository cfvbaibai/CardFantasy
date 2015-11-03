package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.OnDamagedResult;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class Snipe {
    public static void apply(Skill cardSkill, SkillResolver resolver, EntityInfo attacker, Player defenderPlayer,
            int targetCount) throws HeroDieSignal {
        int damage = cardSkill.getImpact();
        List<CardInfo> victims = defenderPlayer.getField().getCardsWithLowestHP(targetCount);
        resolver.getStage().getUI().useSkill(attacker, victims, cardSkill, true);
        for (CardInfo victim : victims) {
            resolver.getStage().getUI().attackCard(attacker, victim, cardSkill, damage);
            OnDamagedResult damageResult = resolver.applyDamage(victim, cardSkill, damage);
            if (attacker instanceof CardInfo) {
                OnAttackBlockingResult blockResult = new OnAttackBlockingResult(true, damage);
                resolver.resolveCounterAttackSkills((CardInfo)attacker, victim, cardSkill, blockResult, damageResult);
            }
            resolver.resolveDeathSkills(attacker, victim, cardSkill, damageResult);
        }
    }
}
