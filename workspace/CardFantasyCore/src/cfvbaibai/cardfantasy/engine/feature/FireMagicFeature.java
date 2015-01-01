package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.StageInfo;

public final class FireMagicFeature {
    public static void apply(Skill cardFeature, SkillResolver resolver, EntityInfo attacker, Player defender,
            int victimCount) throws HeroDieSignal {
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        GameUI ui = stage.getUI();

        List<CardInfo> victims = random.pickRandom(defender.getField().toList(), victimCount, true, null);
        ui.useSkill(attacker, victims, cardFeature, true);
        for (CardInfo victim : victims) {
            int damage = random.next(cardFeature.getImpact(), cardFeature.getImpact() * 2 + 1);
            OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(attacker, victim, cardFeature, damage);
            if (!result.isAttackable()) {
                continue;
            }
            damage = result.getDamage();
            ui.attackCard(attacker, victim, cardFeature, damage);
            boolean cardDead = resolver.applyDamage(victim, damage).cardDead;
            if (attacker instanceof CardInfo) {
                resolver.resolveCounterAttackFeature((CardInfo)attacker, victim, cardFeature, result, null);
            }
            if (cardDead) {
                resolver.resolveDeathFeature(attacker, victim, cardFeature);
            }
        }
    }
}
