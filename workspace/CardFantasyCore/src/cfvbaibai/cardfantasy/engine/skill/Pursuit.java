package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatus;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class Pursuit {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, CardInfo defender) {
        CardStatus status = defender.getStatus();
        Skill skill = skillUseInfo.getSkill();
        boolean skillActivated = false;
        for (CardStatusItem statusItem : status.getAllItems()) {
            if (statusItem.getType() == CardStatusType.冰冻 && statusItem.getCause().getType() != SkillType.寒霜冲击) {
                skillActivated = true;
                break;
            }
            if (statusItem.getType() == CardStatusType.中毒 ||
                statusItem.getType() == CardStatusType.麻痹 ||
                statusItem.getType() == CardStatusType.燃烧) {
                skillActivated = true;
                break;
            }
        }
        if (skillActivated) {
            int adjAT = (int) (attacker.getLevel1AT() * skill.getImpact() / 100);
            resolver.getStage().getUI().useSkill(attacker, skill, true);
            resolver.getStage().getUI().adjustAT(attacker, attacker, adjAT, skill);
            attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, false));
        }
    }

    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        List<SkillEffect> effects = card.getEffectsCausedBy(skillUseInfo);
        for (SkillEffect effect : effects) {
            resolver.getStage().getUI().loseAdjustATEffect(card, effect);
            card.removeEffect(effect);
        }
    }
}
