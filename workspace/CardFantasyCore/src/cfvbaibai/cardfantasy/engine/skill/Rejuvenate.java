package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class Rejuvenate {
    public static void apply(Skill cardSkill, SkillResolver resolver, CardInfo card) throws HeroDieSignal {
        if (card.isDead()) {
            // Card has already dead due to CounterAttacker, MagicReflection or Overdraw.
            return;
        }
        int healHP = cardSkill.getImpact();
        if (cardSkill.getType() == SkillType.真理导言) {
            healHP = card.getMaxHP() * cardSkill.getImpact() / 100;
        }
        if (healHP + card.getHP() > card.getMaxHP()) {
            healHP = card.getMaxHP() - card.getHP();
        }
        if (healHP == 0) {
            return;
        }
        OnAttackBlockingResult result = resolver.resolveHealBlockingSkills(card, card, cardSkill);
        if (!result.isAttackable()) {
            return;
        }
        resolver.getStage().getUI().useSkill(card, cardSkill, true);
        resolver.getStage().getUI().healCard(card, card, cardSkill, healHP);
        resolver.applyDamage(card, card, cardSkill, -healHP);
    }
}
