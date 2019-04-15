package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

public class GiantEarthquakesLandslides {
    public static void apply(SkillResolver resolver, Skill cardSkill, EntityInfo attacker, Player defenderHero, int count) throws HeroDieSignal {
        if (defenderHero == null) {
            return;
        }
        if (attacker == null) {
            return;
        }
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        List<CardInfo> victims = random.pickRandom(defenderHero.getField().toList(), -1, true, null);
        List<CardInfo> myVictims = random.pickRandom(attacker.getOwner().getField().toList(), -1, true, null);
        List<CardInfo> candidates = new ArrayList<CardInfo>();
        List<CardInfo> myCandidates = new ArrayList<CardInfo>();
        RuneInfo defenerrune = defenderHero.getOwner().getActiveRuneOf(RuneData.磐石);
        RuneInfo attackrune = attacker.getOwner().getOwner().getActiveRuneOf(RuneData.磐石);
        if (defenerrune != null) {
            candidates = victims;
        } else {
            for (CardInfo victim : victims) {
                if (victim.containsUsableSkillsWithTag(SkillTag.不动)) {
                    candidates.add(victim);
                }
            }
        }
        if (attackrune != null) {
            myCandidates = myVictims;
        } else {
            for (CardInfo victim : myVictims) {
                if (victim.containsUsableSkillsWithTag(SkillTag.不动)) {
                    myCandidates.add(victim);
                }
            }
        }
        if (candidates.size() + myCandidates.size() < count) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        for (CardInfo effectCard : candidates) {
            if (resolver.resolveIsImmune(effectCard,1)) {
                continue;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, effectCard, cardSkill);
            if (magicEchoSkillResult == 1 || magicEchoSkillResult == 2) {

                if(attacker instanceof  CardInfo) {
                    CardInfo attackCard = (CardInfo) attacker;
                    if (attackCard.isDead()) {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    } else {
                        if (resolver.resolveIsImmune(attackCard, 1)) {
                            if (magicEchoSkillResult == 1) {
                                continue;
                            }
                        } else if (!attackCard.containsUsableSkillsWithTag(SkillTag.不动)) {
                            if (magicEchoSkillResult == 1) {
                                continue;
                            }
                        } else {
                            ui.useSkill(effectCard, attacker, cardSkill, true);
                            Return.returnCard2(resolver, cardSkill, effectCard, attackCard, true);
                        }
                    }
                    if (magicEchoSkillResult == 1) {
                        continue;
                    }
                }
            }
            ui.useSkill(attacker, effectCard, cardSkill, true);
            Return.returnCard2(resolver, cardSkill, attacker, effectCard,true);
        }
    }
}
