package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class Destroy {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero,
            int victimCount) throws HeroDieSignal {
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defenderHero.getField().toList(), victimCount, true, null);
        apply(resolver, cardSkill, attacker, victims,true);
    }
    
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, CardInfo defender) throws HeroDieSignal {
        List<CardInfo> victims = new ArrayList<CardInfo>();
        victims.add(defender);
        apply(resolver, cardSkill, attacker, victims,false);
    }
    
    private static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, List<CardInfo> victims,boolean activeSkillFlag) throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, cardSkill, true);
        for (CardInfo victim : victims) {
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, victim, cardSkill, 1);
            if (!result.isAttackable()) {
               continue;
            }
            if(activeSkillFlag)
            {
                int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, victim, cardSkill);
                if (magicEchoSkillResult==1||magicEchoSkillResult==2) {
                    if(attacker.isDead())
                    {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else{
                        OnAttackBlockingResult result2 = resolver.resolveAttackBlockingSkills(victim, attacker, cardSkill, 1);
                        if (!result2.isAttackable()) {
                            if (magicEchoSkillResult == 1) {
                                continue;
                            }
                        }
                        else{
                            ui.killCard(victim, attacker, cardSkill);
                            attacker.removeStatus(CardStatusType.不屈);
                            resolver.killCard(victim, attacker, cardSkill);
                        }
                    }
                    if (magicEchoSkillResult == 1) {
                        continue;
                    }
                }
            }
            ui.killCard(attacker, victim, cardSkill);
            victim.removeStatus(CardStatusType.不屈);
            resolver.killCard(attacker, victim, cardSkill);
        }
    }
}
