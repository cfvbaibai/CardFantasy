package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public final class ReturnCardAndDelay {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero,int delay) throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        List<CardInfo> defenderList =defenderHero.getField().getAliveCards();
        GameUI ui = resolver.getStage().getUI();
        for(CardInfo defender :defenderList) {
            if (defender == null || defender.isBoss()) {
                continue;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, defender, cardSkill);
            if(magicEchoSkillResult==1||magicEchoSkillResult==2) {
                if(attacker.isDead())
                {
                    if (magicEchoSkillResult == 1) {
                        continue;
                    }
                }
                else if (attacker == null || attacker.isBoss()) {
                    if (magicEchoSkillResult == 1) {
                        continue;
                    }
                }
                else {
                    Player attackerPlayer = attacker.getOwner();
                    if (attackerPlayer.getHand().isFull()) {
                        ui.useSkill(defender, attacker, cardSkill, true);
                        Return.returnCard2(resolver, cardSkill, defender, attacker, true);
                        if (!attacker.getStatus().containsStatus(CardStatusType.召唤)) {
                            resolver.getStage().getUI().increaseSummonDelay(attacker, delay);
                            attacker.setAddDelay(2);
                        }
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                }
                ui.useSkill(defender, attacker, cardSkill, true);
                Return.returnHand(resolver, cardSkill, defender, attacker);
                int summonDelay = attacker.getSummonDelay();
                if (!attacker.getStatus().containsStatus(CardStatusType.召唤)) {
                    resolver.getStage().getUI().increaseSummonDelay(attacker, delay);
                    attacker.setSummonDelay(summonDelay + delay);
                }
                if(magicEchoSkillResult==1)
                {
                    continue;
                }
            }
            if (defenderHero.getHand().isFull()) {
                ui.useSkill(attacker, defender, cardSkill, true);
                Return.returnCard2(resolver, cardSkill, attacker, defender, true);
                if (!defender.getStatus().containsStatus(CardStatusType.召唤)) {
                    resolver.getStage().getUI().increaseSummonDelay(defender, delay);
                    defender.setAddDelay(2);
                }
                continue;
            }
            ui.useSkill(attacker, defender, cardSkill, true);
            Return.returnHand(resolver, cardSkill, attacker, defender);
            int summonDelay = defender.getSummonDelay();
            if (!defender.getStatus().containsStatus(CardStatusType.召唤)) {
                resolver.getStage().getUI().increaseSummonDelay(defender, delay);
                defender.setSummonDelay(summonDelay + delay);
            }
        }
    }
}
