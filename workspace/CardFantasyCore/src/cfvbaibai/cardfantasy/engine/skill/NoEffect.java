package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public class NoEffect {
    public static boolean isFeatureBlocked(SkillResolver resolver, Skill cardFeature, Skill attackFeature, EntityInfo attacker, CardInfo defender) {
        if (attackFeature.getType() == SkillType.虚弱 ||
            attackFeature.getType() == SkillType.战争怒吼 ||
            // attackFeature.getType() == FeatureTYpe.死亡印记 ||
            // attackFeature.getType() == FeatureType.沉默 ||
            attackFeature.getType() == SkillType.大地之盾 ||
            attackFeature.getType() == SkillType.裂伤) {
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, cardFeature, true);
            ui.blockSkill(attacker, defender, cardFeature, attackFeature);
            return true;
        } else {
            return false;
        }
    }
}
