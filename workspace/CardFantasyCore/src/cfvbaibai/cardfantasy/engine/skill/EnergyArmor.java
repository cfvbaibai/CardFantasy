package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.Field;

public final class EnergyArmor {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, EntityInfo caster, int targetCount) {
        if (caster == null) {
            throw new CardFantasyRuntimeException("caster cannot be null");
        }
        Skill skill = skillUseInfo.getSkill();
        int impact = skill.getImpact();
        Field field = caster.getOwner().getField();
        List<CardInfo> targets = resolver.getStage().getRandomizer().pickRandom(
            field.toList(), targetCount, true, null);
        resolver.getStage().getUI().useSkill(caster, targets, skill, true);
        for (CardInfo target : targets) {
            resolver.getStage().getUI().adjustHP(caster, target, impact, skill);
            target.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, impact, false));
        }
    }

    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, EntityInfo caster) {
        if (caster == null) {
            throw new CardFantasyRuntimeException("card cannot be null");
        }
        if (skillUseInfo == null) {
            throw new CardFantasyRuntimeException("skillUseInfo cannot be null");
        }
        Field field = caster.getOwner().getField();
        for (CardInfo target : field.getAliveCards()) {
            for (SkillEffect effect : target.getEffectsCausedBy(skillUseInfo)) {
                resolver.getStage().getUI().loseAdjustHPEffect(target, effect);
                target.removeEffect(effect);
            }
        }
    }
}
