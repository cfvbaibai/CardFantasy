package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public class NoEffect {
    private static Skill NO_EFFECT_SKILL = new CardSkill(SkillType.无效, 0, 0, false, false, false, false);
    public static boolean isSkillBlocked(SkillResolver resolver, Skill attackSkill, EntityInfo attacker, CardInfo defender) {
        if (attackSkill.getType() == SkillType.虚弱 ||
            attackSkill.getType() == SkillType.战争怒吼 ||
            attackSkill.getType() == SkillType.死亡印记 ||
            attackSkill.getType() == SkillType.魔力法阵 ||
            attackSkill.getType() == SkillType.沉默 ||
            attackSkill.getType() == SkillType.全体沉默 ||
            attackSkill.getType() == SkillType.觉醒沉默 ||
            attackSkill.getType() == SkillType.大地之盾 ||
            attackSkill.getType() == SkillType.一闪 ||
            attackSkill.getType() == SkillType.裂伤 ||
            attackSkill.getType() == SkillType.时光倒流 ||
            attackSkill.getType() == SkillType.圣光洗礼 ||
            attackSkill.getType() == SkillType.森林沐浴 ||
            attackSkill.getType() == SkillType.蛮荒威压 ||
            attackSkill.getType() == SkillType.地狱同化 ||
            attackSkill.getType() == SkillType.恶灵汲取 ||
            attackSkill.getType() == SkillType.反射装甲 ||
            attackSkill.getType() == SkillType.迷魂 ||
            attackSkill.getType() == SkillType.混乱领域 ||
            attackSkill.getType() == SkillType.无我境界) {
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, NO_EFFECT_SKILL, true);
            ui.blockSkill(attacker, defender, NO_EFFECT_SKILL, attackSkill);
            return true;
        } else {
            return false;
        }
    }
}
