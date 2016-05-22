package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class SoulCrash {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker, Player defender) throws HeroDieSignal {
        List<CardInfo> victims = new ArrayList<CardInfo>();
        for (CardInfo card : defender.getField().getAliveCards()) {
            if (card.isSummonedMinion()) {
                victims.add(card);
            }
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            ui.killCard(attacker, victim, skill);
            victim.removeStatus(CardStatusType.不屈);
            resolver.killCard(attacker, victim, skill);
        }
    }
}
