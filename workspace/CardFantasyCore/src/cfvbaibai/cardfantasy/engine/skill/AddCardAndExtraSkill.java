package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.game.DeckBuilder;

import java.util.ArrayList;
import java.util.List;

public class AddCardAndExtraSkill {
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
        List<CardInfo> productCards = summoner.getOwner().getProductCards();
        List<CardInfo> addCardList = new ArrayList<CardInfo>();
        for(CardInfo cardInfo:summonCardCandidates)
        {
            boolean addFlag = true;
            for(CardInfo productCard:productCards)
            {
                if(productCard.getProductSkillUserInfo() == skillUseInfo)
                {
                    if(productCard.getName().equals(cardInfo.getName()))
                    {
                        addFlag = false;
                        continue;
                    }
                }
            }
            if(addFlag)
            {
                addCardList.add(cardInfo);
            }
        }

        cardsToSummon = Randomizer.getRandomizer().pickRandom(addCardList, summonPicks, true, null);

        CardSkill cardSkill = null;
        if (cardsToSummon.size() > 0) {
            resolver.getStage().getUI().useSkill(summoner, skillUseInfo.getSkill(), true);
            if (summoner.getLevel() >= 15) {
                Skill additionalSkill = summoner.getExtraSkill();
                if(additionalSkill!=null) {
                    Boolean summonSkill = false;
                    Boolean preSkill = false;
                    Boolean deathSkill = false;
                    Boolean postSkill = false;
                    if (additionalSkill.isPostcastSkill()) {
                        postSkill = true;
                    } else if (additionalSkill.isDeathSkill()) {
                        deathSkill = true;
                    } else if (additionalSkill.isPrecastSkill()) {
                        preSkill = true;
                    } else if (additionalSkill.isSummonSkill()) {
                        summonSkill = true;
                    }
                    cardSkill = new CardSkill(additionalSkill.getType(), additionalSkill.getLevel(), 0, summonSkill, deathSkill, preSkill, postSkill);
                }
            }
        }


        for (int i = 0; i < cardsToSummon.size(); ++i) {
            CardInfo summonedCard = cardsToSummon.get(i);
            if(cardSkill !=null)
            {
                summonedCard.setExtraSkill(cardSkill);
                SkillUseInfo thisSkillUserInfo = new SkillUseInfo(summonedCard, cardSkill);
                summonedCard.addSkill(thisSkillUserInfo);
            }
            summoner.getOwner().addProductCards(summonedCard);
            summonedCard.setProductSkillUserInfo(skillUseInfo);
            resolver.summonCard(summoner.getOwner(), summonedCard, summoner, false, skillUseInfo.getSkill(),1);
        }
    }
}
