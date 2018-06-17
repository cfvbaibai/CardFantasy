package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.OnDamagedResult;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class PoisonMagic {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, EntityInfo attacker, Player defender,
            int victimCount) throws HeroDieSignal {
        Skill skill = skillUseInfo.getSkill();
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(
            defender.getField().toList(), victimCount, true, null);
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, victims, skill, true);
        for (CardInfo victim : victims) {
            int damage = skill.getImpact();
            OnAttackBlockingResult onAttackBlockingResult = resolver.resolveAttackBlockingSkills(attacker, victim, skill, damage);
            if (!onAttackBlockingResult.isAttackable()) {
                continue;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(attacker, victim, skill);
            if (magicEchoSkillResult==1||magicEchoSkillResult==2) {
                if (attacker instanceof CardInfo) {
                    CardInfo attackCard =  (CardInfo)attacker;
                    if(attackCard.isDead())
                    {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    }
                    else {
                        OnAttackBlockingResult result2 = resolver.resolveAttackBlockingSkills(victim, attackCard, skill, damage);
                        int damage2 = result2.getDamage();
                        if (!result2.isAttackable()) {
                            if (magicEchoSkillResult == 1) {
                                continue;
                            }
                        }
                        else {
                            ui.attackCard(victim, attackCard, skill, damage2);
                            OnDamagedResult onDamagedResult2 = resolver.applyDamage(victim, attackCard, skill, damage2);
                            resolver.resolveDeathSkills(victim, attackCard, skill, onDamagedResult2);
                            if (!onDamagedResult2.cardDead) {
                                CardStatusItem status = CardStatusItem.poisoned(damage, skillUseInfo);
                                ui.addCardStatus(victim, attackCard, skill, status);
                                attackCard.addStatus(status);
                            }
                        }
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            damage = onAttackBlockingResult.getDamage();
            ui.attackCard(attacker, victim, skill, damage);
            OnDamagedResult onDamagedResult = resolver.applyDamage(attacker, victim, skill, damage);
            resolver.resolveDeathSkills(attacker, victim, skill, onDamagedResult);
            if (!onDamagedResult.cardDead) {
                CardStatusItem status = CardStatusItem.poisoned(damage, skillUseInfo);
                ui.addCardStatus(attacker, victim, skill, status);
                victim.addStatus(status);
            }
        }
    }
}
