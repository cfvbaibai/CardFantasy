package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.Field;

public class HolyGuardFeature {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        if (card == null || card.isDead()) {
            throw new CardFantasyRuntimeException("card should not be null or dead!");
        }
        Skill skill = skillUseInfo.getFeature();
        int impact = skill.getImpact();
        resolver.getStage().getUI().useSkill(card, skill, true);
        Field field = card.getOwner().getField();
        List<CardInfo> allies = resolver.getAdjacentCards(field, card.getPosition());
        for (CardInfo ally : allies) {
            if (ally.getEffectsCausedBy(skillUseInfo).isEmpty()) {
                resolver.getStage().getUI().adjustHP(card, ally, impact, skill);
                ally.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, impact, false));
            }
        }
    }
    
    public static void remove(SkillResolver resolver, SkillUseInfo feature, CardInfo card) {
        for (CardInfo ally : card.getOwner().getField().toList()) {
            if (ally == null) { continue; }
            List<SkillEffect> effects = ally.getEffectsCausedBy(feature);
            for (SkillEffect effect : effects) {
                resolver.getStage().getUI().loseAdjustHPEffect(ally, effect);
                ally.removeEffect(effect);
            }
        }
    }
}
