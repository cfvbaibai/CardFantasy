package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class SoulControl {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, Player defenderHero) throws HeroDieSignal {
        if (attacker.hasUsed(skillUseInfo)) {
            return;
        }
        if (SoulSeal.soulSealed(resolver, attacker)) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        List<CardInfo> candidates = new ArrayList<CardInfo>();
        for (CardInfo deadCard : defenderHero.getGrave().toList()) {
            if (!deadCard.containsUsableSkillsWithTag(SkillTag.召唤) &&
                !deadCard.containsUsableSkillsWithTag(SkillTag.复活) &&
                deadCard.getRace() != Race.BOSS) {
                candidates.add(deadCard);
            }
        }
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(candidates, 1, false, null);
        if (victims.size() == 0) {
            return;
        }
        CardInfo victim = victims.get(0);
        resolver.getStage().getUI().useSkill(attacker, victim, skill, true);
        victim.switchOwner(attacker.getOwner());
        resolver.summonCard(victim.getOwner(), victim, attacker, true, skill);
        CardStatusItem weakStatusItem = CardStatusItem.weak(skillUseInfo);
        resolver.getStage().getUI().addCardStatus(attacker, victim, skill, weakStatusItem);
        victim.addStatus(weakStatusItem);
        attacker.setUsed(skillUseInfo);
    }
}
