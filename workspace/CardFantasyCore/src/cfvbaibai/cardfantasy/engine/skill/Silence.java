package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class Silence {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo caster, Player defenderHero, boolean isTargetAll) throws HeroDieSignal {
        List<CardInfo> victims = new ArrayList<CardInfo>();
        if (isTargetAll) {
            victims.addAll(defenderHero.getField().getAliveCards());
        } else {
            CardInfo victim = defenderHero.getField().getCard(caster.getPosition());
            if (victim == null || victim.isDead()) {
                return;
            }
            victims.add(victim);
        }
        GameUI ui = resolver.getStage().getUI();
        Skill skill = skillUseInfo.getSkill();
        ui.useSkill(caster, victims, skill, true);
        for (CardInfo victim : victims) {
            CardStatusItem statusItem = CardStatusItem.slience(skillUseInfo);
            if (!resolver.resolveAttackBlockingSkills(caster, victim, skill, 1).isAttackable()) {
                return;
            }
            ui.addCardStatus(caster, victim, skill, statusItem);
            victim.addStatus(statusItem);
        }
    }
}
