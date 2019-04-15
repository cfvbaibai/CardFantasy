package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public class CounterAttackHero {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker) throws HeroDieSignal {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Player player = attacker.getOwner();
        resolver.getStage().getUI().useSkill(attacker, skillUseInfo.getSkill(), true);
        player.addCounterAttackHero(skillUseInfo);
    }

    public static void explode(SkillResolver resolver, EntityInfo attacker, Player defender,int damage) throws HeroDieSignal {

        List<SkillUseInfo> skillUseInfoList = defender.getCounterAttackHero();
        List<SkillUseInfo> counterSkillUserInfoList = new ArrayList<>();
        for(SkillUseInfo skillUseInfo:skillUseInfoList)
        {
            counterSkillUserInfoList.add(skillUseInfo);
        }
        for(SkillUseInfo skillUseInfo:counterSkillUserInfoList)
        {
            Skill skill = skillUseInfo.getSkill();
            resolver.getStage().getUI().useSkill(skillUseInfo.getOwner(), skill, true);
            resolver.attackHero(skillUseInfo.getOwner(),attacker.getOwner(),skill,damage*skill.getImpact()/100);
        }
    }

    public static void remove(EntityInfo attacker,SkillResolver resolver) {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        List<SkillUseInfo> skillUseInfoList = attacker.getOwner().getCounterAttackHero();

        if(skillUseInfoList.size() == 0)
        {
            return;
        }

        List<SkillUseInfo> removeSkillUserInfoList = new ArrayList<>();

        for(SkillUseInfo skillUseInfo:skillUseInfoList)
        {
            if(skillUseInfo.getOwner() == attacker)
            {
                removeSkillUserInfoList.add(skillUseInfo);
            }
        }
        for(SkillUseInfo skillUseInfo:removeSkillUserInfoList)
        {
            attacker.getOwner().removeCounterAttackHero(skillUseInfo);
        }
    }
}
