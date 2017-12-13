package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class Wound {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, Skill attackSkill, CardInfo attacker, CardInfo defender,
            int normalAttackDamage) {
        if (normalAttackDamage <= 0) {
            return;
        }
        if (defender.isDead()) {
            return;
        }
        if (attackSkill != null && (attackSkill.getType() == SkillType.横扫 || attackSkill.getType() == SkillType.三千世界|| attackSkill.getType() == SkillType.一文字|| attackSkill.getType() == SkillType.灵击 ||
                attackSkill.getType() == SkillType.三千世界 ||
                attackSkill.getType() == SkillType.魔龙之怒 ||
                attackSkill.getType() == SkillType.鬼彻 ||
                attackSkill.getType() == SkillType.毒杀)) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        resolver.getStage().getUI().useSkill(attacker, defender, skill, true);
        CardStatusItem status = CardStatusItem.wound(skillUseInfo);
        resolver.getStage().getUI().addCardStatus(attacker, defender, skill, status);
        defender.addStatus(status);
    }

    public static void applyToAll(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, Player defenderHero) {
        Skill skill = skillUseInfo.getSkill();
        List<CardInfo> victims = defenderHero.getField().getAliveCards();
        resolver.getStage().getUI().useSkill(attacker, victims, skill, true);
        CardStatusItem status = CardStatusItem.wound(skillUseInfo);
        for (CardInfo victim : victims) {
            resolver.getStage().getUI().addCardStatus(attacker, victim, skill, status);
            victim.addStatus(status);
        }
    }
}
