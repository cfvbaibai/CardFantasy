package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class LegionBuff {
    public static void apply(SkillResolver resolver, CardInfo card, SkillUseInfo skillUseInfo, Race race) {
        if (card.getRace() != race) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        if (skill.getLevel() == 0) {
            return;
        }
        int adjAT = skill.getImpact() * card.getInitAT() / 100;
        resolver.getStage().getUI().useSkill(card, skill, true);
        resolver.getStage().getUI().adjustAT(skillUseInfo.getOwner(), card, adjAT, skill);
        card.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
        
        int adjHP = skill.getImpact() * card.getRawMaxHP() / 100;
        resolver.getStage().getUI().useSkill(card, skill, true);
        resolver.getStage().getUI().adjustHP(skillUseInfo.getOwner(), card, adjHP, skill);
        card.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, adjHP, false));
    }

    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        List<SkillEffect> effects = card.getEffectsCausedBy(skillUseInfo);
        for (SkillEffect effect : effects) {
            if (effect.getType() == SkillEffectType.ATTACK_CHANGE) {
                resolver.getStage().getUI().loseAdjustATEffect(card, effect);
                card.removeEffect(effect);
            }
        }
    }
}
