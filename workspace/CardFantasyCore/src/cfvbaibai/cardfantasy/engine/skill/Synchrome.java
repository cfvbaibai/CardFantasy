package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class Synchrome {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo fieldCard, CardInfo summonedCard, Race race) {
        if (fieldCard == null) {
            throw new CardFantasyRuntimeException("fieldCard cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        int adjAT = skill.getImpact() * fieldCard.getLevel0AT() / 100;
        int adjHP = skill.getImpact() * fieldCard.getMaxHP() / 100;
        GameUI ui = resolver.getStage().getUI();
        if (summonedCard == fieldCard) {
            return;
        }
        if (summonedCard.isSummonedMinion()) {
            return;
        }
        if (summonedCard.getOriginalRace() != race) {
            return;
        }
        ui.useSkill(summonedCard, fieldCard, skill, true);
        ui.adjustAT(summonedCard, fieldCard, adjAT, skill);
        fieldCard.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));
        ui.adjustHP(summonedCard, fieldCard, adjHP, skill);
        fieldCard.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, adjHP, true));
    }
}
