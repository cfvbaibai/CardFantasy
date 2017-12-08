package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;
import cfvbaibai.cardfantasy.game.DeckBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class AddCard {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo summoner, SummonType summonType, int summonPicks, String... summonedCardsDescs) throws HeroDieSignal {
        if (summoner == null) {
            throw new CardFantasyRuntimeException("summoner should not be null");
        }
        int impact = skillUseInfo.getSkill().getImpact();
        if(skillUseInfo.getSkillNumber()==0)
        {
            return;
        }
        if(skillUseInfo.getSkillNumber()==-1)
        {
            skillUseInfo.setSkillNumber(impact);
        }
        skillUseInfo.setSkillNumber(skillUseInfo.getSkillNumber()-1);
        List<CardInfo> cardsToSummon = new ArrayList<CardInfo>();
        List<CardInfo> summonCardCandidates = null;
        summonCardCandidates = DeckBuilder.build(summonedCardsDescs).getCardInfos(summoner.getOwner());
        cardsToSummon = Randomizer.getRandomizer().pickRandom(summonCardCandidates, summonPicks, true, null);

        if (cardsToSummon.size() > 0) {
            resolver.getStage().getUI().useSkill(summoner, skillUseInfo.getSkill(), true);
        }
        for (int i = 0; i < cardsToSummon.size(); ++i) {
            CardInfo summonedCard = cardsToSummon.get(i);
            resolver.summonCard(summoner.getOwner(), summonedCard, summoner, true, skillUseInfo.getSkill(),1);
        }
    }
}
