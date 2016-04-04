package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class Dodge {
    public static boolean apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, CardInfo defender, int originalDamage) {
        int dodgeRate = cardSkill.getImpact();
        GameUI ui = resolver.getStage().getUI();
        boolean bingo = true;
        List<CardStatusItem> blindStatusItems = attacker.getStatus().getStatusOf(CardStatusType.致盲);
        // Two types of dodge: triggered by native dodge skill or blind status
        SkillUseInfo nativeDodgeSkillUseInfo = null;
        for (SkillUseInfo blockSkillUseInfo : defender.getUsableNormalSkills()) {
            if (blockSkillUseInfo.getType() == SkillType.闪避) {
                nativeDodgeSkillUseInfo = blockSkillUseInfo;
                break;
            }
        }
        if (!blindStatusItems.isEmpty()) {
            // If attacker is blinded and defender has native dodge skill, 100% dodge success.
            ui.useSkill(attacker, blindStatusItems.get(0).getCause().getSkill(), true);
        }
        if (!blindStatusItems.isEmpty() && nativeDodgeSkillUseInfo != null) {
            bingo = true;
        } else {
            bingo = resolver.getStage().getRandomizer().roll100(dodgeRate);
        }
        ui.useSkill(defender, attacker, cardSkill, bingo);
        if (bingo) {
            if (resolver.resolveCounterBlockSkill(cardSkill, attacker, defender)) {
                return false;
            }
            ui.blockDamage(defender, attacker, defender, cardSkill, originalDamage, 0);
            return true;
        } else {
            return false;
        }
    }
}
