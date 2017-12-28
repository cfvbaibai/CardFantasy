package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.Player;

public final class Transport {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero) throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        CardInfo cardToTransport = null;
        for (CardInfo card : defenderHero.getHand().toList()) {
            if (cardToTransport == null || card.getSummonDelay() > cardToTransport.getSummonDelay()) {
                cardToTransport = card;
            }
        }
        if (cardToTransport == null) {
            return;
        }
        resolver.getStage().getUI().useSkill(attacker, cardToTransport, cardSkill, true);
        OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, cardToTransport, cardSkill, 1);
        if (!result.isAttackable()) {
            return;
        }
        resolver.getStage().getUI().cardToGrave(defenderHero, cardToTransport);
        defenderHero.getHand().removeCard(cardToTransport);
        defenderHero.getGrave().addCard(cardToTransport);
        //传送移除附加技能
        resolver.resolveLeaveSkills(cardToTransport);
    }
}
