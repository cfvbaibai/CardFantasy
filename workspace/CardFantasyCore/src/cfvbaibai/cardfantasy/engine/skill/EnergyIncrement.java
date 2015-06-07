package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.RuneInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class EnergyIncrement {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, CardInfo caster) {
        if (caster == null) {
            return;
        }
        if (resolver.getStage().hasUsed(skillUseInfo)) {
            return;
        }
        resolver.getStage().getUI().useSkill(caster, skillUseInfo.getSkill(), true);
        for (RuneInfo runeInfo : caster.getOwner().getRuneBox().getRunes()) {
            runeInfo.setEnergy(runeInfo.getEnergy() + 1);
            resolver.getStage().getUI().updateRuneEnergy(runeInfo);
        }
        resolver.getStage().setUsed(skillUseInfo, true);
    }
}
