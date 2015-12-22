package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.OnDamagedResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class ThorArrival {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, EntityInfo attacker, Player defender
    		,int paralyzeRate)
        throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
    
        List<CardInfo> victims = defender.getField().getAliveCards();
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            int damage = skill.getImpact() + skill.getImpact2() * victims.size(); 
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, skill, damage);
            if (!result.isAttackable()) {
                continue;
            }
            if (resolver.getStage().getRandomizer().roll100(paralyzeRate)) {
                CardStatusItem status = CardStatusItem.paralyzed(skillUseInfo);
                if (!resolver.resolveBlockStatusSkills(attacker, victim, skillUseInfo, status).isBlocked()) {
                    ui.addCardStatus(attacker, victim, skill, status);
                    victim.addStatus(status);
                }
            }
            damage = result.getDamage();
            ui.attackCard(attacker, victim, skill, damage);
            OnDamagedResult onDamagedResult = resolver.applyDamage(victim, skill, damage);
            if (attacker instanceof CardInfo) {
                resolver.resolveCounterAttackSkills((CardInfo)attacker, victim, skill, result, null);
            }
            resolver.resolveDeathSkills(attacker, victim, skill, onDamagedResult);       
         }
    }
}
