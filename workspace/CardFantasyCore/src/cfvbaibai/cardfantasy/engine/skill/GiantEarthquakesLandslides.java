package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.StageInfo;

public class GiantEarthquakesLandslides {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero, int count) throws HeroDieSignal {
        if (defenderHero == null) {
            return;
        }
        if (attacker == null) {
            return;
        }
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> victims = random.pickRandom(defenderHero.getField().toList(), -1, true, null);

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
