package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public final class HellPrison {
    public static void apply(SkillResolver resolver, Player player, Player defender)
    {
        List<CardInfo> allHandCards = defender.getHand().toList();
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> fileCard = random.pickRandom(defender.getField().toList(), -1, true, null);
        for(CardInfo attackerCard :fileCard) {
            if(attackerCard==null)
            {
                continue;
            }
            if(!attackerCard.containsUsableSkill(SkillType.冥域牢囚))
            {
                continue;
            }
            for(SkillUseInfo skillUseInfo :attackerCard.getAllUsableSkills()) {
                if(skillUseInfo.getSkill().getType()==SkillType.冥域牢囚) {
                    resolver.getStage().getUI().useSkill(attackerCard, allHandCards, skillUseInfo.getSkill(), true);
                    for (CardInfo card : allHandCards) {
                        resolver.getStage().getUI().useSkill(attackerCard, allHandCards, skillUseInfo.getSkill(), true);
                        int summonDelay = card.getSummonDelay();
                        resolver.getStage().getUI().increaseSummonDelay(card, skillUseInfo.getSkill().getImpact());
                        card.setSummonDelay(summonDelay + skillUseInfo.getSkill().getImpact());
                    }
                }
            }
        }
    }
}
