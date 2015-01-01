package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class EscapeFeature {
    public static boolean isFeatureEscaped(SkillResolver resolver, Skill cardFeature, Skill attackFeature,
            EntityInfo attacker, CardInfo defender) {
        if (attackFeature.getType().containsTag(SkillTag.控制)) {
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, cardFeature, true);
            ui.blockFeature(attacker, defender, cardFeature, attackFeature);
            return true;
        } else {
            return false;
        }
    }

    public static boolean isStatusEscaped(Skill cardFeature, SkillResolver resolver, CardStatusItem item,
            CardInfo defender) {
        if (item.getType() == CardStatusType.冰冻 || item.getType() == CardStatusType.麻痹) {
            EntityInfo attacker = item.getCause().getOwner();
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, cardFeature, true);
            ui.blockStatus(attacker, defender, cardFeature, item);
            return true;
        } else {
            return false;
        }
    }
}
