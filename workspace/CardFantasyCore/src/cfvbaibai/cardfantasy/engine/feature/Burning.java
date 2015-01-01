package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class Burning {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo attacker, CardInfo defender)
            throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        if (attacker.getStatus().containsStatusCausedBy(skillUseInfo, CardStatusType.燃烧)) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int damage = skill.getImpact();
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, skill, true);
        OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(defender, attacker, skill, damage);
        if (!result.isAttackable()) {
            return;
        }
        CardStatusItem status = CardStatusItem.burning(damage, skillUseInfo);
        ui.addCardStatus(defender, attacker, skill, status);
        attacker.addStatus(status);
    }
}
