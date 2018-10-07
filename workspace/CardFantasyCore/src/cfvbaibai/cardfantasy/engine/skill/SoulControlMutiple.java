package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.*;

import java.util.ArrayList;
import java.util.List;

public class SoulControlMutiple {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, Player defenderHero) throws HeroDieSignal {
        int impact = skillUseInfo.getSkill().getImpact();
        if(skillUseInfo.getSkillNumber()==0)
        {
            return;
        }
        if(skillUseInfo.getSkillNumber()==-1)
        {
            skillUseInfo.setSkillNumber(impact);
        }
        skillUseInfo.setSkillNumber(skillUseInfo.getSkillNumber()-1);
        if (SoulSeal.soulSealed(resolver, attacker)) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        List<CardInfo> candidates = new ArrayList<CardInfo>();
        for (CardInfo deadCard : defenderHero.getGrave().toList()) {
            if (!deadCard.getIsDeathNow() && !deadCard.containsUsableSkillsWithTag(SkillTag.召唤) &&
                !deadCard.containsUsableSkillsWithTag(SkillTag.复活) &&
                !deadCard.containsUsableSkillsWithTag(SkillTag.守护) &&
                !deadCard.containsUsableSkillsWithTag(SkillTag.抗夺魂) &&
                deadCard.getRace() != Race.BOSS &&
                deadCard.getRace() != Race.DEMON) {
                candidates.add(deadCard);
            }
        }
        int victimCount = skill.getImpact();
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(candidates, victimCount, false, null);
        if (victims.size() == 0) {
            return;
        }
        CardInfo victim = victims.get(0);
        resolver.getStage().getUI().useSkill(attacker, victim, skill, true);
        victim.switchOwner(attacker.getOwner());
        resolver.summonCard(victim.getOwner(), victim, attacker, false, skill,0);
        CardStatusItem weakStatusItem = CardStatusItem.weak(skillUseInfo);
        resolver.getStage().getUI().addCardStatus(attacker, victim, skill, weakStatusItem);
        victim.addStatus(weakStatusItem);
    }

    public static void applySetNumber(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker){
        int impact = skillUseInfo.getSkill().getImpact();
        skillUseInfo.setSkillNumber(impact);
    }
}
