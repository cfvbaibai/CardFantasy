package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

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
            int magicEchoSkillResult =resolver.resolveMagicEchoSkill(attacker,defender,cardSkill);
            if(magicEchoSkillResult==1||magicEchoSkillResult==2)
            {
                if(attacker.isDead())
                {
                    if (magicEchoSkillResult == 1) {
                        continue;
                    }
                }
                else if(attacker.getOwner().getHand().isFull())
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
                    ui.useSkill(defender, attacker, cardSkill, true);
                    Return.returnHand(resolver, cardSkill, defender, attacker);
                    int summonDelay = attacker.getSummonDelay();
                    if (!attacker.getStatus().containsStatus(CardStatusType.召唤)) {
                        resolver.getStage().getUI().increaseSummonDelay(attacker, delay);
                        attacker.setSummonDelay(summonDelay + delay);
                    }
                    if (magicEchoSkillResult == 1) {
                        continue;
                    }
                }
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
