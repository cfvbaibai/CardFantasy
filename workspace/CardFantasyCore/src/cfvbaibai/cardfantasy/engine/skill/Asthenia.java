package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public class Asthenia {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attackCard, Player defenderHero,int victimCount,int effectNumber) throws HeroDieSignal {

        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> victims = random.pickRandom(defenderHero.getField().toList(), victimCount, true, null);
        if (victims.size() == 0) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        int impact = skill.getImpact2();
        ui.useSkill(attackCard, victims, skill, true);
        CardStatusItem status = CardStatusItem.paralyzed(skillUseInfo);
        status.setEffect(impact);
        status.setEffectNumber(effectNumber);
        CardStatusItem statusItem = CardStatusItem.asthenia(skillUseInfo);
        statusItem.setEffectNumber(effectNumber);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingSkills(attackCard, victim, skill, 1).isAttackable()) {
                continue;
            }
            if(effectNumber>0)
            {
                if(!victim.getStatus().getStatusOf(CardStatusType.虚化).isEmpty()){
                    victim.removeForce(CardStatusType.虚化);
                }
                if(!victim.getStatus().getStatusOf(CardStatusType.麻痹).isEmpty())
                {
                    victim.removeForce(CardStatusType.麻痹);
                }
            }
            ui.addCardStatus(attackCard, victim, skill, status);
            ui.addCardStatus(attackCard, victim, skill, statusItem);
            victim.addStatus(status);
            victim.addStatus(statusItem);
        }
    }

    public static boolean explode(SkillResolver resolver, CardInfo defender,int dodgeRate) {
        if(defender.getStatus().getStatusOf(CardStatusType.虚化).isEmpty()){
            return false;
        }
        boolean bingo = true;
        GameUI ui = resolver.getStage().getUI();
        bingo = resolver.getStage().getRandomizer().roll100(dodgeRate);
        if (bingo) {
            List<CardStatusItem> statusItems = defender.getStatus().getStatusOf(CardStatusType.虚化);
            for (CardStatusItem statusItem : statusItems) {
                SkillUseInfo skillUseInfo = statusItem.getCause();
                EntityInfo attacker = skillUseInfo.getOwner();
                if(attacker instanceof CardInfo) {
                    ui.useSkill(attacker, defender, skillUseInfo.getSkill(), true);
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
