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

/**
 * Chain Lightening give 25 * level damages to 3 enemy's cards and 40%
 * probability to cause paralyzed.
 * 
 * Can be blocked by Immue. Can be reflected by Magic Reflection. Can activate
 * dying skill.
 */
public final class LighteningMagic {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, Player defender,
            int victimCount, int paralyzeRate) throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defender.getField().toList(), victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            int damage = skill.getImpact();
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