package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.FeatureEffectType;
import cfvbaibai.cardfantasy.engine.FeatureInfo;
import cfvbaibai.cardfantasy.engine.FeatureResolver;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnAttackBlockingResult;

public final class SacrificeFeature {
    public static void apply(FeatureResolver resolver, FeatureInfo featureInfo, CardInfo card, CardInfo reviver) throws HeroDieSignal {
        if (card.hasUsed(featureInfo)) {
            return;
        }
        Skill skill = featureInfo.getFeature();
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
        OnAttackBlockingResult result = resolver.resolveAttackBlockingFeature(card, oblation, skill, 1);
        if (!result.isAttackable()) {
            return;
        }
        ui.killCard(card, oblation, skill);
        // Sacrifice does not trigger death features.
        resolver.cardDead(oblation);
        
        int adjHP = skill.getImpact() * card.getMaxHP() / 100;
        int adjAT = skill.getImpact() * card.getInitAT() / 100;
        ui.adjustHP(card, card, adjHP, skill);
        ui.adjustAT(card, card, adjAT, skill);
        card.addEffect(new FeatureEffect(FeatureEffectType.MAXHP_CHANGE, featureInfo, adjHP, true));
        card.addEffect(new FeatureEffect(FeatureEffectType.ATTACK_CHANGE, featureInfo, adjAT, true));
        card.setUsed(featureInfo);
        ui.compactField(card.getOwner().getField());
    }
}
