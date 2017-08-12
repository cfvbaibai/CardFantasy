package cfvbaibai.cardfantasy.engine.skill;


import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.*;

public final class ContinuousMagic {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker,Player defender) throws HeroDieSignal {
        if (attacker == null ) {
            throw new CardFantasyRuntimeException("attacker should not be null");
        }
        if(attacker.isDead())
        {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        StageInfo stage = resolver.getStage();
        Randomizer random = stage.getRandomizer();
        int rate = skill.getImpact();
        int number = 0;
        if (random.roll100(rate))
        {
            number=1;
            GameUI ui = resolver.getStage().getUI();
            for(;number<=5;number++)
            {
                ui.useSkill(attacker, attacker, skill, true);
                //用1表示触发连续魔法
                resolver.resolvePreAttackSkills(attacker,defender,1);
                if (!random.roll100(rate)||attacker.isDead()){
                    break;
                }
            }
        }
    }
}
