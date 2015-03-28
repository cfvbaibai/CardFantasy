package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

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
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, List<CardInfo> summonedCards, Race race) {
        if (card == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        int adjAT = skill.getImpact() * card.getLevel0AT() / 100;
        int adjHP = skill.getImpact() * card.getLevel0AT() / 100;
        GameUI ui = resolver.getStage().getUI();
        for (CardInfo summonedCard : summonedCards) {
            if (summonedCard == card) {
                continue;
            }
            if (summonedCard.isSummonedMinion()) {
                continue;
            }
            if (summonedCard.getOriginalRace() != race) {
                continue;
            }
            ui.useSkill(summonedCard, card, skill, true);
            ui.adjustAT(summonedCard, card, adjAT, skill);
            card.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));
            ui.adjustHP(summonedCard, card, adjHP, skill);
            card.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, adjHP, true));
        }
    }
}
