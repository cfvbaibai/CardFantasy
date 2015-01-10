package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class BasicAtBuff {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        if (card.getRace() == Race.BOSS) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int adjAT = skill.getImpact() * card.getInitAT() / 100;
        resolver.getStage().getUI().useSkill(card, skill, true);
        resolver.getStage().getUI().adjustAT(skillUseInfo.getOwner(), card, adjAT, skill);
        card.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
    }
}
