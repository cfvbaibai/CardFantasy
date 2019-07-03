package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public final class ReturnNumber {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero,int victimCount) throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> defenderList = random.pickRandom(defenderHero.getField().getAliveCards(), victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        for(CardInfo defender :defenderList) {
            if (defender == null || defender.isBoss()) {
                continue;
            }
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, defender, cardSkill, 1);
            if (!result.isAttackable()) {
                continue;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, defender, cardSkill);
            if (magicEchoSkillResult==1||magicEchoSkillResult==2) {
                if(attacker.isDead())
                {
                    if (magicEchoSkillResult == 1) {
                        continue;
                    }
                }
                else {
                    OnAttackBlockingResult result2 = resolver.resolveAttackBlockingSkills(defender, attacker, cardSkill, 1);
                    if (!result2.isAttackable()) {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else{
                        Return.returnCard(resolver, cardSkill, defender, attacker);
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            ui.useSkill(attacker, defender, cardSkill, true);
            ui.returnCard(attacker, defender, cardSkill);
            Return.returnCard(resolver, cardSkill, attacker, defender);
        }
    }
}
