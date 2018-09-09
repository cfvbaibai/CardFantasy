package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.game.DeckBuilder;

import java.util.ArrayList;
import java.util.List;

public class AddSelfCard {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo summoner, SummonType summonType, int summonPicks, String... summonedCardsDescs) throws HeroDieSignal {
        if (summoner == null) {
            throw new CardFantasyRuntimeException("summoner should not be null");
        }
        int impact = skillUseInfo.getSkill().getImpact();
        SkillUseInfo useSkllInfo =null;
        SkillUseInfo productSkillUserInfo = summoner.getProductSkillUserInfo();
        if(productSkillUserInfo !=null)
        {
            useSkllInfo = productSkillUserInfo;
        }
        else{
            useSkllInfo = skillUseInfo;
        }
        if(useSkllInfo.getSkillNumber()==0)
        {
            return;
        }
        if(useSkllInfo.getSkillNumber()==-1)
        {
            useSkllInfo.setSkillNumber(impact);
        }
        useSkllInfo.setSkillNumber(useSkllInfo.getSkillNumber()-1);
        List<CardInfo> cardsToSummon = new ArrayList<CardInfo>();
        List<CardInfo> summonCardCandidates = null;
        summonCardCandidates = DeckBuilder.build(summonedCardsDescs).getCardInfos(summoner.getOwner());
        cardsToSummon = Randomizer.getRandomizer().pickRandom(summonCardCandidates, summonPicks, true, null);

        if (cardsToSummon.size() > 0) {
            resolver.getStage().getUI().useSkill(summoner, useSkllInfo.getSkill(), true);
        }
        for (int i = 0; i < cardsToSummon.size(); ++i) {
            CardInfo summonedCard = cardsToSummon.get(i);
            summonedCard.setProductSkillUserInfo(useSkllInfo);
            resolver.summonCard(summoner.getOwner(), summonedCard, summoner, false, useSkllInfo.getSkill(),1);
        }
    }
}
