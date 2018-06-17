package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class Blind {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo caster, Player enemyHero, int victimCount) throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        Randomizer random = resolver.getStage().getRandomizer();
        List<CardInfo> victims = random.pickRandom(enemyHero.getField().toList(), victimCount, true, null);
        ui.useSkill(caster, victims, skill, true);
        for (CardInfo victim : victims) {
            CardStatusItem statusItem = CardStatusItem.blind(skillUseInfo);
            if (!resolver.resolveAttackBlockingSkills(caster, victim, skill, 1).isAttackable()) {
                return;
            }
            int magicEchoSkillResult = resolver.resolveMagicEchoSkill(caster, victim, skill);
            if (magicEchoSkillResult == 1 || magicEchoSkillResult == 2) {
                if (caster.isDead()) {
                    if (magicEchoSkillResult == 1) {
                        continue;
                    }
                } else {
                    if (!resolver.resolveAttackBlockingSkills(victim, caster, skill, 1).isAttackable()) {
                        if (magicEchoSkillResult == 1) {
                            continue;
                        }
                    } else {
                        ui.addCardStatus(victim, caster, skill, statusItem);
                        caster.addStatus(statusItem);
                    }
                }
                if (magicEchoSkillResult == 1) {
                    continue;
                }
            }
            ui.addCardStatus(caster, victim, skill, statusItem);
            victim.addStatus(statusItem);
        }
    }

    public static Skill getDodgeSkill(List<CardStatusItem> statusItems) {
        int dodgeLevel = -1;
        for (CardStatusItem statusItem : statusItems) {
            Skill blindSkill = statusItem.getCause().getSkill();
            dodgeLevel = Math.max(dodgeLevel, blindSkill.getImpact());
        }
        return new CardSkill(SkillType.闪避, dodgeLevel, 0, false, false, false, false);
    }
}
