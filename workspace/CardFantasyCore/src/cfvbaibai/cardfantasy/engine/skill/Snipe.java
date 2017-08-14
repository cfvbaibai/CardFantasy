package cfvbaibai.cardfantasy.engine.skill;

import java.util.HashMap;
import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

public final class Snipe {
    public static void apply(SkillUseInfo skillUseInfo,Skill cardSkill, SkillResolver resolver, EntityInfo attacker, Player defenderPlayer,
            int targetCount) throws HeroDieSignal {
        int rate = cardSkill.getImpact();
        int damage = cardSkill.getImpact3();
        if (damage == 0) {
            
        }
        if (cardSkill.getType() == SkillType.神箭三重奏) {//Tripling Snipe needs custom damage setting
            HashMap<Integer, Integer> hLevelToDamage = new HashMap<Integer, Integer>();
            hLevelToDamage.put(1, 250);
            hLevelToDamage.put(2, 270);
            hLevelToDamage.put(3, 300);
            hLevelToDamage.put(4, 320);
            hLevelToDamage.put(5, 350);
            hLevelToDamage.put(6, 370);
            hLevelToDamage.put(7, 400);
            hLevelToDamage.put(8, 420);
            hLevelToDamage.put(9, 450);
            hLevelToDamage.put(10, 500);
            Integer level = cardSkill.getLevel();
            if (hLevelToDamage.containsKey(level))
            {
                damage = hLevelToDamage.get(level);
            }
            else
            {
                damage = 400;
            }
        }

        List<CardInfo> victims = defenderPlayer.getField().getCardsWithLowestHP(targetCount);
        resolver.getStage().getUI().useSkill(attacker, victims, cardSkill, true);
        for (CardInfo victim : victims) {
            resolver.getStage().getUI().attackCard(attacker, victim, cardSkill, damage);
            OnDamagedResult damageResult = resolver.applyDamage(attacker, victim, cardSkill, damage);
            if (attacker instanceof CardInfo) {
                if(rate>0)
                {
                    if (resolver.getStage().getRandomizer().roll100(rate)) {
                        CardStatusItem status = CardStatusItem.frozen(skillUseInfo);
                        if (!resolver.resolveBlockStatusSkills(attacker, victim, skillUseInfo, status).isBlocked()) {
                            resolver.getStage().getUI().addCardStatus(attacker, victim, cardSkill, status);
                            victim.addStatus(status);
                        }
                    }
                }
                OnAttackBlockingResult blockResult = new OnAttackBlockingResult(true, damage);
                resolver.resolveCounterAttackSkills((CardInfo)attacker, victim, cardSkill, blockResult, damageResult);
            }
            resolver.resolveDeathSkills(attacker, victim, cardSkill, damageResult);
        }
    }
}
