package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class CounterBite {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo card) throws HeroDieSignal {
        if (card == null || card.isDead()) {
            throw new CardFantasyRuntimeException("card is null or dead!");
        }
        if (card.hasUsed(skillUseInfo)) {
            return;
        }
        Skill cardSkill = skillUseInfo.getSkill();
        int damage = cardSkill.getImpact();
        resolver.attackHero(card, card.getOwner(), cardSkill, damage);
        card.setUsed(skillUseInfo);
    }
}