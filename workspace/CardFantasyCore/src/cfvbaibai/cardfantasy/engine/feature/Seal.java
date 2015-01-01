package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class Seal {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker, Player defender)
            throws HeroDieSignal {
        if (attacker.hasUsed(skillUseInfo)) {
            return;
        }
        Skill skill = skillUseInfo.getFeature();
        List<CardInfo> victims = defender.getField().getAliveCards();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingFeature(attacker, victim, skill, 1).isAttackable()) {
                continue;
            }
            CardStatusItem status = CardStatusItem.trapped(skillUseInfo);
            ui.addCardStatus(attacker, victim, skill, status);
            victim.addStatus(status);
        }
        attacker.setUsed(skillUseInfo);
    }
}
