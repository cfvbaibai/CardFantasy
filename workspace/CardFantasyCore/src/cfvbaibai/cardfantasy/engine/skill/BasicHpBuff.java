package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class BasicHpBuff extends RemovableBuff {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        Skill skill = skillUseInfo.getSkill();
        int adjHP = skill.getImpact() * card.getOriginalMaxHP() / 1000;
        resolver.getStage().getUI().useSkill(card, skill, true);
        resolver.getStage().getUI().adjustHP(skillUseInfo.getOwner(), card, adjHP, skill);
        card.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, adjHP, false));
    }
}
