package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;
import cfvbaibai.cardfantasy.game.DeckBuilder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class HumanRefining {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attackCard, Player defenderHero,int victimCount,int effectNumber) throws HeroDieSignal {

        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> victims = random.pickRandom(defenderHero.getField().toList(), victimCount, true, null);
        if (victims.size() == 0) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(attackCard, victims, skill, true);
        CardStatusItem statusItem = CardStatusItem.humanRefining(skillUseInfo);
        statusItem.setEffectNumber(effectNumber);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingSkills(attackCard, victim, skill, 1).isAttackable()) {
                continue;
            }
            if(effectNumber>0)
            {
                if(!victim.getStatus().getStatusOf(CardStatusType.炼成).isEmpty()){
                    victim.removeForce(CardStatusType.炼成);
                }
            }
            ui.addCardStatus(attackCard, victim, skill, statusItem);
            victim.addStatus(statusItem);
        }
    }

    public static void explode(SkillResolver resolver, CardInfo deadCard, OnDamagedResult onDamagedResult, String... summonedCardsDescs) throws HeroDieSignal {
        if (!onDamagedResult.cardDead) {
            // Card could be unbending and not dead. In that case, death mark does not explode.
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        List<CardStatusItem> statusItems = deadCard.getStatus().getStatusOf(CardStatusType.炼成);
        for (CardStatusItem statusItem : statusItems) {
            SkillUseInfo skillUseInfo = statusItem.getCause();
            Skill skill = skillUseInfo.getSkill();
            EntityInfo attacker = skillUseInfo.getOwner();
            if(attacker instanceof CardInfo) {
                CardInfo attackCard = (CardInfo) attacker;
                ui.useSkill(deadCard, attacker, skill, true);
                Player enemy = resolver.getStage().getOpponent(deadCard.getOwner());
                List<CardInfo> livingCards = enemy.getField().getAliveCards();
                List<String> cardDescsToSummon = new LinkedList<String>();
                for (String summonedCardDesc : summonedCardsDescs) {
                    cardDescsToSummon.add(summonedCardDesc);
                }
                List<CardInfo> cardsToSummon = new ArrayList<CardInfo>();
                List<CardInfo> summonCardCandidates = null;
                int summonNumber=0;
                summonCardCandidates = DeckBuilder.build(summonedCardsDescs).getCardInfos(enemy);
                for (CardInfo fieldCard : livingCards) {
                    if (fieldCard.getStatus().containsStatusCausedBy(skillUseInfo, CardStatusType.召唤)) {
                        if(fieldCard.getRelationCardInfo()==attackCard)
                        {
                            summonNumber++;
                        }
                    }
                }
                if(summonNumber>=1)
                {
                    return;
                }
                cardsToSummon = Randomizer.getRandomizer().pickRandom(summonCardCandidates, 1, true, null);
                for (int i = 0; i < cardsToSummon.size(); ++i) {
                    CardInfo summonedCard = cardsToSummon.get(i);
                    CardStatusItem summonedStatusItem = CardStatusItem.summoned(skillUseInfo);
                    resolver.getStage().getUI().addCardStatus(deadCard, summonedCard, skill, summonedStatusItem);
                    summonedCard.addStatus(summonedStatusItem);
                    CardStatusItem weakStatusItem = CardStatusItem.weak(skillUseInfo);
                    resolver.getStage().getUI().addCardStatus(deadCard, summonedCard, skill, weakStatusItem);
                    summonedCard.addStatus(weakStatusItem);
                    summonedCard.setRelationCardInfo(attackCard);
                    resolver.summonCard(summonedCard.getOwner(), summonedCard, deadCard, true, skill,1);
                }
            }
        }
    }
}
