package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public class SealMagic {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker, Player defender, int number) throws HeroDieSignal {
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        GameUI ui = stage.getUI();
        Skill skill = skillUseInfo.getSkill();
        List<CardInfo> victims = random.pickRandom(defender.getField().toList(), number, true, null);
        for (CardInfo card : victims) {
            ui.useSkill(attacker, card, skill, true);
            if (card.getCurrentAT() > attacker.getCurrentAT()) {
                int adjAT = skill.getImpact();
                resolver.getStage().getUI().adjustAT(attacker, card, adjAT, skill);
                card.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));
            } else if (card.getCurrentAT() < attacker.getCurrentAT()) {
                int adjAT = skill.getImpact();
                resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
                attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));
                ui.killCard(attacker, card, skill);
                card.removeStatus(CardStatusType.不屈);
                resolver.killCard(attacker, card, skill);
//                ui.cardToOutField(defender, card);
//                if (card.getRace() != Race.DEMON && card.getRace() != Race.BOSS) {
//                    if (resolver.getStage().getRandomizer().roll100(skill.getImpact2())) {
//                        defender.getOwner().getField().expelCard(card.getPosition());
////                      defender.getField().removeCard(card);
//                        defender.getOutField().addCard(card);
//                    }
//                }
            }
        }
    }
}
