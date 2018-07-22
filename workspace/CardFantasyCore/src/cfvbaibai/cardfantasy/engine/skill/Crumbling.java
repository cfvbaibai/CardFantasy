package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public class Crumbling {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero, int count,int delay) throws HeroDieSignal {
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
            ui.useSkill(attacker, effectCard, cardSkill, true);
            OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, effectCard, cardSkill, 1);
            if (!result.isAttackable()) {
                return;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, effectCard, cardSkill);
            if (magicEchoSkillResult == 1 || magicEchoSkillResult == 2) {
                if (attacker.isDead()) {
                    if (magicEchoSkillResult == 1) {
                        continue;
                    }
                } else {
                    ui.useSkill(effectCard, attacker, cardSkill, true);
                    OnAttackBlockingResult result2 = resolver.resolveAttackBlockingSkills(effectCard, attacker, cardSkill, 1);
                    if (!result2.isAttackable()) {
                        if (magicEchoSkillResult == 1) {
                            return;
                        }
                    }
                    else if(!attacker.containsUsableSkillsWithTag(SkillTag.不动))
                    {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else {
                        if (attacker == null || attacker.isBoss()) {
                            if (magicEchoSkillResult == 1) {
                                continue;
                            }
                        }
                        else if(defenderHero.getHand().isFull()){
                            ui.useSkill(effectCard, attacker, cardSkill, true);
                            Return.returnCard2(resolver, cardSkill, effectCard, attacker,true);
                            if(!attacker.getStatus().containsStatus(CardStatusType.召唤)) {
                                resolver.getStage().getUI().increaseSummonDelay(attacker, delay);
                                attacker.setAddDelay(delay);
                            }
                            if (magicEchoSkillResult == 1) {
                                continue;
                            }
                        }
                        else {
                            Return.returnHand(resolver, cardSkill, effectCard, attacker);
                            int summonDelay = attacker.getSummonDelay();
                            if(!attacker.getStatus().containsStatus(CardStatusType.召唤)) {
                                resolver.getStage().getUI().increaseSummonDelay(attacker, delay);
                                attacker.setSummonDelay(summonDelay + delay);
                            }
                        }
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            if (effectCard == null || effectCard.isBoss()) {
                continue;
            }
            if(defenderHero.getHand().isFull()){
                ui.useSkill(attacker, effectCard, cardSkill, true);
                Return.returnCard2(resolver, cardSkill, attacker, effectCard,true);
                if(!effectCard.getStatus().containsStatus(CardStatusType.召唤)) {
                    resolver.getStage().getUI().increaseSummonDelay(effectCard, delay);
                    effectCard.setAddDelay(delay);
                }
                continue;
            }
            Return.returnHand(resolver, cardSkill, attacker, effectCard);
            int summonDelay = effectCard.getSummonDelay();
            if(!effectCard.getStatus().containsStatus(CardStatusType.召唤)) {
                resolver.getStage().getUI().increaseSummonDelay(effectCard, delay);
                effectCard.setSummonDelay(summonDelay + delay);
            }
        }
    }
}
