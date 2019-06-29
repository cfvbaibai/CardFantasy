package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public final class FailureSkillUseInfoList {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, EntityInfo attacker) {

        Player player = attacker.getOwner();
        resolver.getStage().getUI().useSkill(attacker, skillUseInfo.getSkill(), true);
        player.addFailureSkillUseInfoList(skillUseInfo);

    }

    public static boolean explode(SkillResolver resolver, EntityInfo attacker, Player defender) throws HeroDieSignal {

        boolean flag = false;

        if (!(attacker instanceof CardInfo)) {
            return flag;
        }
        CardInfo attackCardinfo = (CardInfo) attacker;

        if (attackCardinfo.isBoss() || attackCardinfo.isDeman()) {
            return flag;
        }

        if (attackCardinfo.isDead()) {
            return flag;
        }

        List<SkillUseInfo> skillUseInfoList = defender.getFailureSkillUseInfoList();
        StageInfo stage = resolver.getStage();
        GameUI ui = stage.getUI();
        for(SkillUseInfo skillUseInfo:skillUseInfoList){
            ui.useSkill(skillUseInfo.getOwner(), attacker, skillUseInfo.getSkill(), true);
            flag = true;
            break;
        }
        return flag;
    }

    public static boolean exploded(SkillResolver resolver, EntityInfo attacker, Player defender){

        boolean flag = false;

        if (!(attacker instanceof CardInfo)) {
            return flag;
        }
        CardInfo attackCardinfo = (CardInfo) attacker;

        if (attackCardinfo.isBoss() || attackCardinfo.isDeman()) {
            return flag;
        }

        if (attackCardinfo.isDead()) {
            return flag;
        }

        List<SkillUseInfo> skillUseInfoList = defender.getFailureSkillUseInfoList();
        StageInfo stage = resolver.getStage();
        GameUI ui = stage.getUI();
        for(SkillUseInfo skillUseInfo:skillUseInfoList){
            ui.useSkill(skillUseInfo.getOwner(), attacker, skillUseInfo.getSkill(), true);
            flag = true;
            break;
        }
        return flag;
    }

    public static void remove(EntityInfo attacker, SkillResolver resolver) {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        List<SkillUseInfo> skillUseInfoList = attacker.getOwner().getFailureSkillUseInfoList();

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
            attacker.getOwner().removeFailureSkillUseInfoList(skillUseInfo);
        }
    }
}
