package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class Sacrifice {
    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card, CardInfo reviver) throws HeroDieSignal {
        if (card.hasUsed(skillUseInfo)) {
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        GameUI ui = resolver.getStage().getUI();
        Randomizer random = resolver.getStage().getRandomizer();

        Field field = card.getOwner().getField();
        List<CardInfo> candidates = random.pickRandom(field.toList(), 1, true, card);
        if (reviver != null) {
            // 兔子由于已经死亡无法被复活上来的九头献祭，但实际是先复活再死亡
            // 暂时先不动死亡逻辑，特殊处理一下
            candidates.add(reviver);
        }
        
        ui.useSkill(card, candidates, skill, true);
        if (candidates.isEmpty()) {
            return;
        }
        CardInfo oblation = candidates.get(0);
        OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(card, oblation, skill, 1);
        if (!result.isAttackable()) {
            return;
        }
        ui.killCard(card, oblation, skill);
        // Sacrifice does not trigger death skills.
        resolver.cardDead(oblation);
        
        int adjHP = skill.getImpact() * card.getMaxHP() / 100;
        int adjAT = skill.getImpact() * card.getInitAT() / 100;
        ui.adjustHP(card, card, adjHP, skill);
        ui.adjustAT(card, card, adjAT, skill);
        card.addEffect(new SkillEffect(SkillEffectType.MAXHP_CHANGE, skillUseInfo, adjHP, true));
        card.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adjAT, true));
        card.setUsed(skillUseInfo);
        ui.compactField(card.getOwner().getField());
    }
}
