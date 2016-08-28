package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class Silence {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, EntityInfo caster, Player defenderHero, boolean isTargetAll, boolean onlyCastOnce) throws HeroDieSignal {
        if (onlyCastOnce) {
            if (resolver.getStage().hasUsed(skillUseInfo)) {
                return;
            }
        }
        List<CardInfo> victims = new ArrayList<CardInfo>();
        if (isTargetAll) {
            victims.addAll(defenderHero.getField().getAliveCards());
        } else {
            CardInfo casterCard = (CardInfo) caster;
            CardInfo victim = defenderHero.getField().getCard(casterCard.getPosition());
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
                continue;
            }
            ui.addCardStatus(caster, victim, skill, statusItem);
            victim.addStatus(statusItem);
        }
        if (onlyCastOnce) {
            resolver.getStage().setUsed(skillUseInfo, true);
        }
    }
}
