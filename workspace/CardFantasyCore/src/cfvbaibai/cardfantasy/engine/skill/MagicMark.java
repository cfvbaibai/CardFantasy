package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class MagicMark {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, EntityInfo caster, Player enemyPlayer, int victimCount) throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        Randomizer random = Randomizer.getRandomizer();
        List<CardInfo> victims = random.pickRandom(enemyPlayer.getField().toList(), victimCount, true, null);
        ui.useSkill(caster, victims, skill, true);
        for (CardInfo victim : victims) {
            CardStatusItem statusItem = CardStatusItem.magicMark(skillUseInfo);
            if (!resolver.resolveAttackBlockingSkills(caster, victim, skill, 1).isAttackable()) {
                continue;
            }
            ui.addCardStatus(caster, victim, skill, statusItem);
            victim.addStatus(statusItem);
        }
    }
}
