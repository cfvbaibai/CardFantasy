package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;

public class NoEffectFeature {
    public static boolean isFeatureBlocked(FeatureResolver resolver, Skill cardFeature, Skill attackFeature, EntityInfo attacker, CardInfo defender) {
        if (attackFeature.getType() == SkillType.虚弱 ||
            // attackFeature.getType() == FeatureType.战斗怒吼 ||
            // attackFeature.getType() == FeatureTYpe.死亡印记 ||
            // attackFeature.getType() == FeatureType.沉默 ||
            // attackFeature.getType() == FeatureType.大地之盾 ||
            attackFeature.getType() == SkillType.裂伤) {
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, cardFeature, true);
            ui.blockFeature(attacker, defender, cardFeature, attackFeature);
            return true;
        } else {
            return false;
        }
    }
}
