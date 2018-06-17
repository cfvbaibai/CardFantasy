package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public final class Insane {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker, Player defender,
        int victimCount, int multiple) throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        GameUI ui = stage.getUI();

        List<CardInfo> victims = random.pickRandom(defender.getField().toList(), victimCount, true, null);
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            if (!resolver.resolveAttackBlockingSkills(attacker, victim, skill, 1).isAttackable()) {
                continue;
            }
            if(victim.isDead()||victim.getStatus().containsStatus(CardStatusType.不屈)) {
                continue;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, victim, skill);
            if (magicEchoSkillResult==1||magicEchoSkillResult==2) {
                if(attacker.isDead())
                {
                    if (magicEchoSkillResult == 1) {
                        continue;
                    }
                }
                else{
                    if (!resolver.resolveAttackBlockingSkills(victim, attacker, skill, 1).isAttackable()) {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else if(attacker.isDead()||attacker.getStatus().containsStatus(CardStatusType.不屈)) {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else {
                        List<CardInfo> cardsAttackedByAttacker = resolver.getCardsOnSides(
                                attacker.getOwner().getField(), attacker.getPosition());

                        if (cardsAttackedByAttacker.size() == 0) {
                            // Only victim itself is counted. No other cards around it. No effect.
                            if (magicEchoSkillResult == 1) {
                                continue;
                            }
                        }
                        else {
                            ui.useSkill(attacker, cardsAttackedByAttacker, null, true);
                            if (multiple == 0) {
                                multiple = skill.getImpact();
                            }
                            int damage2 = attacker.getLevel1AT() * multiple / 100;
                            for (CardInfo cardAttackedByAttacker : cardsAttackedByAttacker) {
                                ui.attackCard(attacker, cardAttackedByAttacker, null, damage2);
                                resolver.resolveDeathSkills(attacker, cardAttackedByAttacker, skill,
                                        resolver.applyDamage(attacker, cardAttackedByAttacker, null, damage2));
                            }
                        }
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            List<CardInfo> cardsAttackedByVictim = resolver.getCardsOnSides(
                victim.getOwner().getField(), victim.getPosition());

            if (cardsAttackedByVictim.size() == 0) {
                // Only victim itself is counted. No other cards around it. No effect.
                continue;
            }
            ui.useSkill(victim, cardsAttackedByVictim, null, true);
            if(multiple ==0)
            {
                multiple = skill.getImpact();
            }
            int damage = victim.getLevel1AT()*multiple/100;
            for (CardInfo cardAttackedByVictim : cardsAttackedByVictim) {
                ui.attackCard(victim, cardAttackedByVictim, null, damage);
                resolver.resolveDeathSkills(attacker, cardAttackedByVictim, skill, 
                    resolver.applyDamage(victim, cardAttackedByVictim, null, damage));
            }
        }
    }
}
