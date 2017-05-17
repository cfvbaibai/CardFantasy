package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.*;
import cfvbaibai.cardfantasy.Randomizer;

import java.util.ArrayList;
import java.util.List;

public class GiantEarthquakesLandslides {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero,int count) throws HeroDieSignal {
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        if (attacker == null) {
            return;
        }
        List<CardInfo> victims = random.pickRandom(defenderHero.getField().toList(), -1, true, null);
        if (defenderHero == null) {
            return;
        }
        List<CardInfo> candidates = new ArrayList<CardInfo>();
        for (CardInfo victim : victims){
            if(victim.containsUsableSkillsWithTag(SkillTag.不动) ){
                candidates.add(victim);
            }
        }
        if (candidates.size() < count) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        for(CardInfo effectCard : candidates){
            ui.useSkill(attacker, effectCard, cardSkill, true);
            Return.returnCard(resolver, cardSkill, effectCard, attacker);
        }
    }
}
