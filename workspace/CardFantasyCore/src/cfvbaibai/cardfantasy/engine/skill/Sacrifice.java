package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public final class Sacrifice {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, CardInfo reviver) throws HeroDieSignal {
        if (card.hasUsed(skillUseInfo)) {
            return;
        }
        // 魔卡新改动，被复活的回合无法发动献祭
        if (reviver != null) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        GameUI ui = resolver.getStage().getUI();
        Randomizer random = resolver.getStage().getRandomizer();

        Field field = card.getOwner().getField();
        List<CardInfo> candidates = random.pickRandom(field.toList(), 1, true, card);

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
        card.setUsed(skillUseInfo);

        ui.killCard(card, oblation, skill);
        resolver.killCard(card, oblation, skill);
    }
}
