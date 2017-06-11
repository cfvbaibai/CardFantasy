package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.RuneInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class CounterMagic {

    public static boolean apply(SkillResolver resolver, Skill attackSkill, EntityInfo attacker, CardInfo defender)
            throws HeroDieSignal {
        if (attacker == null) {
            return false;
        }
        if (!resolver.isMagicalSkill(attackSkill) || attackSkill.getType()==SkillType.雷霆一击 || attackSkill.getType()==SkillType.雷霆之怒) {
            return false;
        }
        Skill cardSkill = getBlockSkill(defender);
        if (cardSkill == null) {
            return false;
        }
        int damage = cardSkill.getImpact();
        if (cardSkill.getType() == SkillType.花族秘术 || cardSkill.getType() == SkillType.武形秘术) {
            damage = cardSkill.getImpact2();
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardSkill, true);
        if (attacker instanceof CardInfo) {
            CardInfo cardAttacker = (CardInfo) attacker;
            if (!cardAttacker.isDead()) {
                ui.attackCard(defender, cardAttacker, cardSkill, damage);
                resolver.resolveDeathSkills(defender, cardAttacker, cardSkill,
                        resolver.applyDamage(defender, cardAttacker, cardSkill, damage));
            }
        }
        return true;
    }

    public static Skill getBlockSkill(CardInfo defender) {
        for (SkillUseInfo blockSkillUseInfo : defender.getUsableNormalSkills()) {
            if (blockSkillUseInfo.getType() == SkillType.法力反射 ||
                blockSkillUseInfo.getType() == SkillType.镜面装甲 ||
                blockSkillUseInfo.getType() == SkillType.花族秘术 ||
                blockSkillUseInfo.getType() == SkillType.武形秘术 ) {
                return blockSkillUseInfo.getSkill();
            }
        }
        if (!defender.isSilent()) {
            RuneInfo rune = defender.getOwner().getRuneBox().getRuneOf(RuneData.石林);
            if (rune != null && rune.isActivated() && !defender.justRevived()) {
                return rune.getSkill();
            }
        }
        return null;
    }
}
