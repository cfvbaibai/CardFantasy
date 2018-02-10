package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

import java.util.List;

public final class Devour {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, Player defender, Skill summonSkill) throws HeroDieSignal {
        // 魔卡新改动，被复活的回合无法发动献祭或侵蚀
        if (card.getStatus().containsStatus(CardStatusType.复活) ||
            summonSkill != null && summonSkill.getType() == SkillType.复活) {
            return;
        }
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
