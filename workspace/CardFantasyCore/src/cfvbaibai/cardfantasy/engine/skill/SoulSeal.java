package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class SoulSeal {
    public static boolean soulSealed(SkillResolver resolver, EntityInfo graveOperator) {
        for (Player player : resolver.getStage().getPlayers()) {
            for (CardInfo card : player.getField().getAliveCards()) {
                for (SkillUseInfo skillUseInfo : card.getAllUsableSkills()) {
                    if (skillUseInfo.getType() == SkillType.灵魂禁锢) {
                        resolver.getStage().getUI().useSkill(card, graveOperator, skillUseInfo.getSkill(), true);
                        return true;
                    }
                }
            }
        }
        return false;
    }
}