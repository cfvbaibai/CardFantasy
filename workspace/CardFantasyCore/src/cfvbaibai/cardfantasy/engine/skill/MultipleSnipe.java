package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.HashMap;
import java.util.List;

public final class MultipleSnipe {
    public static void apply(SkillUseInfo skillUseInfo,Skill cardSkill, SkillResolver resolver, EntityInfo attacker, Player defenderPlayer,
            int targetCount) throws HeroDieSignal {
        int number = cardSkill.getImpact();
        int damage = cardSkill.getImpact3();
        if (damage == 0) {
            
        }
        for(int count=0;count<number;count++) {
            List<CardInfo> victims = defenderPlayer.getField().getCardsWithLowestHP(targetCount);
            resolver.getStage().getUI().useSkill(attacker, victims, cardSkill, true);
            for (CardInfo victim : victims) {
                resolver.getStage().getUI().attackCard(attacker, victim, cardSkill, damage);
                OnDamagedResult damageResult = resolver.applyDamage(attacker, victim, cardSkill, damage);
                resolver.resolveDeathSkills(attacker, victim, cardSkill, damageResult);
            }
        }
    }
}
