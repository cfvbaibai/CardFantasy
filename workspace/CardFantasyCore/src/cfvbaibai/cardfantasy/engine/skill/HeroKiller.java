package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.SkillEffect;
import cfvbaibai.cardfantasy.engine.SkillEffectType;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.Player;

public final class HeroKiller {

    private HeroKiller() {
    }

    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo attacker, Player victim) {
        if (attacker.getStatus().containsStatus(CardStatusType.迷惑)) {
            // 迷魂状态下不发动英雄杀手
            return;
        }
        Skill skill = skillUseInfo.getSkill();
        int adj = attacker.getLevel1AT() * skill.getImpact() / 100;
        resolver.getStage().getUI().useSkill(attacker, skill, true);
        resolver.getStage().getUI().adjustAT(attacker, attacker, adj, skill);
        attacker.addEffect(new SkillEffect(SkillEffectType.ATTACK_CHANGE, skillUseInfo, adj, false));
    }
    
    public static void remove(SkillResolver resolver, SkillUseInfo skillUseInfo, CardInfo card) {
        List<SkillEffect> effects = card.getEffectsCausedBy(skillUseInfo);
        for (SkillEffect effect : effects) {
            resolver.getStage().getUI().loseAdjustATEffect(card, effect);
            card.removeEffect(effect);
        }
    }
}
