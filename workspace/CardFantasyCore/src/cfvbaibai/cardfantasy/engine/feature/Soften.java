package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class Soften {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, Player defender)
        throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();

        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defender.getField().toList(), 1, true, null);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, skill, true);

        if (victims.isEmpty()) {
            return;
        }

        CardInfo victim = victims.get(0);
        List<CardStatusItem> items = victim.getStatus().getStatusOf(CardStatusType.弱化);
        for (int i = 0; i < items.size(); ++i) {
            if (items.get(i).getCause() == skillUseInfo) {
                return;
            }
        }

        if (!resolver.resolveAttackBlockingFeature(attacker, victim, skill, 1).isAttackable()) {
            return;
        }

        CardStatusItem status = CardStatusItem.softened(skillUseInfo);
        ui.addCardStatus(attacker, victim, skill, status);
        victim.addStatus(status);
    }
}
