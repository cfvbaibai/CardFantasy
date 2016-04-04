package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class PosionBlade {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, CardInfo defender,
            int normalAttackDamage) throws HeroDieSignal {
        if (normalAttackDamage <= 0 || defender == null) {
            return;
        }
        if (attacker.getPosition() != defender.getPosition()) {
            // 横扫或者连锁攻击的溅射部分不触发毒刃
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        resolver.getStage().getUI().useSkill(attacker, defender, skill, true);
        List<CardInfo> defenders = new ArrayList<CardInfo>();
        defenders.add(defender);

        weakenCardLife(resolver, skillUseInfo, normalAttackDamage, attacker, defenders);
    }

    public static int weakenCardLife(SkillResolver resolver, SkillUseInfo skillUseInfo, int lifeToWeaken, EntityInfo attacker,
            List<CardInfo> defenders) throws HeroDieSignal {
        int totalAttackWeakened = 0;
        for (CardInfo defender : defenders) {
            if (defender == null) {
                continue;
            }
            Skill skill = skillUseInfo.getSkill();
            if (!resolver.resolveAttackBlockingSkills(attacker, defender, skill, 1).isAttackable()) {
                continue;
            }
            //先恢复物理伤害的HP，因为降生命上限这个动作是连同当前血量一起降
            // TODO: 检查是否需要触发死契技能
            resolver.applyDamage(attacker, defender, skill, -lifeToWeaken);

            int lifeWeakened = lifeToWeaken;
            if (lifeWeakened > defender.getBasicMaxHP()) {
                lifeWeakened = defender.getBasicMaxHP();
            }

            resolver.getStage().getUI().adjustHP(attacker, defender, -lifeWeakened, skill);
            List<SkillEffect> effects = defender.getEffects();
            for (SkillEffect effect : effects) {
                if (effect.getType() == SkillEffectType.MAXHP_CHANGE && effect.getValue() > 0 &&
                        !effect.getCause().getSkill().getType().containsTag(SkillTag.抗毒刃)) {
                    if (lifeWeakened > effect.getValue()) {
                        lifeWeakened -= effect.getValue();
                        effect.setValue(0);
                    } else {
                        lifeWeakened = 0;
                        effect.setValue(effect.getValue() - lifeWeakened);
                    }
                }
                if (lifeWeakened == 0) {
                    break;
                }
            }

            defender.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, -lifeWeakened, true));
            totalAttackWeakened += lifeWeakened;
        }
        return totalAttackWeakened;
    }
}
