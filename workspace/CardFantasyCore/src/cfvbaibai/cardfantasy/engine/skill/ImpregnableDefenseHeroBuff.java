package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public final class ImpregnableDefenseHeroBuff {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, EntityInfo attacker) {

        Player player = attacker.getOwner();
        resolver.getStage().getUI().useSkill(attacker, skillUseInfo.getSkill(), true);
        player.addImpregnableDefenseHero(skillUseInfo);

    }

    public static int explode(SkillResolver resolver, EntityInfo attacker, Player defender,int damage) throws HeroDieSignal {

        int remainingDamage = damage;

        List<SkillUseInfo> skillUseInfoList = defender.getImpregnableDefenseHero();

        List<SkillUseInfo> impregnableDefenseHeroList = new ArrayList<>();
        for(SkillUseInfo skillUseInfo:skillUseInfoList)
        {
            impregnableDefenseHeroList.add(skillUseInfo);
        }

        for(SkillUseInfo skillUseInfo:impregnableDefenseHeroList)
        {
            if(skillUseInfo.getOwner().getStatus().containsStatus(CardStatusType.不屈))
            {
                continue;
            }
            if(!FailureSkillUseInfoList.explode(resolver,skillUseInfo.getOwner(),attacker.getOwner())) {
                Skill skill = skillUseInfo.getSkill();
                int impact = skill.getImpact();
                resolver.getStage().getUI().useSkill(skillUseInfo.getOwner(), skill, true);
                remainingDamage = remainingDamage *impact/100;
            }
        }

        return remainingDamage;
    }

    public static void remove(EntityInfo attacker, SkillResolver resolver) {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        List<SkillUseInfo> skillUseInfoList = attacker.getOwner().getImpregnableDefenseHero();

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
            attacker.getOwner().removeImpregnableDefenseHero(skillUseInfo);
        }
    }
}
