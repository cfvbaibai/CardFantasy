package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;

public class NewBorn {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card,int victimCount) throws HeroDieSignal{
        if (card == null || card.isDead())  {
            throw new CardFantasyRuntimeException("card should not be null or dead!");
        }
        Skill skill = skillUseInfo.getSkill();
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        resolver.getStage().getUI().useSkill(card, skill, true);
        Field field = card.getOwner().getField();
        List<CardInfo> frontCards = resolver.getFrontCards(field, card.getPosition());
        List<CardInfo> victims = random.pickRandom(frontCards , victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();

        for (CardInfo victim : victims) {
            if (!victim.containsAllUsableSkillsWithTag(SkillTag.新生)) {
                for (SkillUseInfo victimSkillUseInfo : victim.getAllUsableSkillsIgnoreSilence()) {
                    resolver.getStage().removeUsed(victimSkillUseInfo);
                }
                ui.useSkill(card, victim, skillUseInfo.getSkill(), true);
                Return.returnCard(resolver, skill, card, victim);
                resolver.getStage().getUI().useSkill(card, victim, skillUseInfo.getSkill(), true);
                if (victim.getStatus().containsStatus(CardStatusType.召唤)) {
                    Summon.apply(resolver, skillUseInfo, card, SummonType.Normal, 1, victim.getName());
                    return;
                }
                resolver.summonCard(card.getOwner(), victim, null, false, skillUseInfo.getSkill());
            }
        }

    }
}
