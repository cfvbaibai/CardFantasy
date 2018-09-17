package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public final class RaceChangeSelf {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker) throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();
        Player attackHero = attacker.getOwner();
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = attackHero.getField().getAliveCards();
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingSkills(attacker, victim, skill, 1).isAttackable()) {
                continue;
            }
            resolver.removeStatus(victim, CardStatusType.王国);
            resolver.removeStatus(victim, CardStatusType.森林);
            resolver.removeStatus(victim, CardStatusType.蛮荒);
            resolver.removeStatus(victim, CardStatusType.地狱);
            CardStatusItem item = CardStatusItem.raceChange(skillUseInfo);
            ui.addCardStatus(attacker, victim, skill, item);
            victim.addStatus(item);
        }
    }
}
