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
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardStatusType;

public class Clever {
    public static void applyBySoulControl(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, Player defenderHero) throws HeroDieSignal {
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
                    !deadCard.containsUsableSkillsWithTag(SkillTag.守护) &&
                    deadCard.getRace() != Race.BOSS) {
                candidates.add(deadCard);
            }
        }
        int victimCount = skill.getImpact2();
        List<CardInfo> victims = resolver.getStage().getRandomizer().pickRandom(candidates, victimCount, false, null);
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
    public static void applyByErode(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Player defender, Skill summonSkill) throws HeroDieSignal {
        if (card.hasUsed(skillUseInfo)) {
            return;
        }
        // 魔卡新改动，被复活的回合无法发动献祭或侵蚀
        if (card.getStatus().containsStatus(CardStatusType.复活) ||
                summonSkill != null && summonSkill.getType() == SkillType.复活) {
            return;
        }
        card.setUsed(skillUseInfo);
        Skill skill = skillUseInfo.getSkill();
        GameUI ui = resolver.getStage().getUI();
        Randomizer random = resolver.getStage().getRandomizer();

        List<CardInfo> candidates = random.pickRandom(defender.getField().toList(), 1, true, null);

        ui.useSkill(card, candidates, skill, true);
        if (candidates.isEmpty()) {
            return;
        }
        CardInfo oblation = candidates.get(0);
        OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(card, oblation, skill, 1);
        if (!result.isAttackable()) {
            return;
        }

        int adjHP = skill.getImpact() * card.getMaxHP() / 100;
        int adjAT = skill.getImpact() * card.getLevel1AT() / 100;
        ui.adjustHP(card, card, adjHP, skill);
        ui.adjustAT(card, card, adjAT, skill);
        card.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, adjHP, true));
        card.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));

        ui.killCard(card, oblation, skill);
        resolver.killCard(card, oblation, skill);
    }
}
