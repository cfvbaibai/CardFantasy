package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.StageInfo;

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
            if(victim.isDead()) {
                continue;
            }
            List<CardInfo> cardsAttackedByVictim = resolver.getCardsOnSides(
                victim.getOwner().getField(), victim.getPosition());

            if (cardsAttackedByVictim.size() == 0) {
                // Only victim itself is counted. No other cards around it. No effect.
                continue;
            }
            ui.useSkill(victim, cardsAttackedByVictim, null, true);
            int damage = victim.getLevel1AT()*multiple/100;
            for (CardInfo cardAttackedByVictim : cardsAttackedByVictim) {
                ui.attackCard(victim, cardAttackedByVictim, null, damage);
                resolver.resolveDeathSkills(attacker, cardAttackedByVictim, skill, 
                    resolver.applyDamage(victim, cardAttackedByVictim, null, damage));
            }
        }
    }
}
