package cfvbaibai.cardfantasy.engine.feature;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;

public final class WeakenAllFeature {

    public static void apply(SkillResolver resolver, SkillUseInfo skillUseInfo, EntityInfo attacker, Player defenderPlayer) throws HeroDieSignal {
        if (defenderPlayer == null) {
            throw new CardFantasyRuntimeException("defenderPlayer is null");
        }
        if (attacker == null) {
            return;
        }
        Skill skill = skillUseInfo.getFeature();
        List<CardInfo> defenders = defenderPlayer.getField().getAliveCards();
        resolver.getStage().getUI().useSkill(attacker, defenders, skill, true);
        WeakenFeature.weakenCard(resolver, skillUseInfo, skillUseInfo.getFeature().getImpact(), attacker, defenders);
    }
}
