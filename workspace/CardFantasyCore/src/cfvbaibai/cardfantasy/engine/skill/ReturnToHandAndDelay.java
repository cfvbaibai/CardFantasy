package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public final class ReturnToHandAndDelay {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero,int delay,int victimCount) throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> defenderList = random.pickRandom(defenderHero.getField().getAliveCards(), victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        for(CardInfo defender :defenderList) {
            if(defenderHero.getHand().isFull())
            {
                return;
            }
            if (defender == null || defender.isBoss()) {
                continue;
            }
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, defender, cardSkill, 1);
            if (!result.isAttackable()) {
                continue;
            }
            ui.useSkill(attacker, defender, cardSkill, true);
            Return.returnHand(resolver, cardSkill, attacker, defender);
            int summonDelay = defender.getSummonDelay();
            if(!defender.getStatus().containsStatus(CardStatusType.召唤)) {
                resolver.getStage().getUI().increaseSummonDelay(defender, delay);
                defender.setSummonDelay(summonDelay + delay);
            }
        }
    }

    public static void applySelf(SkillResolver resolver, Skill cardSkill, CardInfo attacker, int delay,int victimCount) throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        Player player = attacker.getOwner();
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> extraCard =new ArrayList<>();
        extraCard.add(attacker);
        List<CardInfo> defenderList = random.pickRandom(player.getField().getAliveCards(), victimCount, true, extraCard);
        GameUI ui = resolver.getStage().getUI();
        for(CardInfo defender :defenderList) {
            if (defender == null || defender.isBoss()) {
                continue;
            }
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, defender, cardSkill, 1);
            if (!result.isAttackable()) {
                continue;
            }
            if (player.getHand().isFull()) {
                ui.useSkill(attacker, defender, cardSkill, true);
                Return.returnCard2(resolver, cardSkill, attacker, defender, true);
                continue;
            }
            ui.useSkill(attacker, defender, cardSkill, true);
            Return.returnHand(resolver, cardSkill, attacker, defender);
            int summonDelay = defender.getSummonDelay();
            if(!defender.getStatus().containsStatus(CardStatusType.召唤)) {
                resolver.getStage().getUI().increaseSummonDelay(defender, delay);
                defender.setSummonDelay(summonDelay + delay);
            }
        }
    }
}
