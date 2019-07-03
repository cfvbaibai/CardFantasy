package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public class HalfSilence {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, EntityInfo caster, Player defenderHero) throws HeroDieSignal {
        List<CardInfo> liveCards = defenderHero.getField().getAliveCards();
        if(liveCards.size()<=0)
        {
            return;
        }
        int impact = liveCards.size()/2;
        if(impact==0)
        {
            impact=1;
        }
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> victims = random.pickRandom(liveCards, impact, true, null);
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(caster, victims, skill, true);
        for (CardInfo victim : victims) {
            CardStatusItem statusItem = CardStatusItem.slience(skillUseInfo);
            if (!resolver.resolveAttackBlockingSkills(caster, victim, skill, 1).isAttackable()) {
                continue;
            }
            ui.addCardStatus(caster, victim, skill, statusItem);
            victim.addStatus(statusItem);
        }
    }
}
