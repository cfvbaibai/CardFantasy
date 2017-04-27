package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class AlchemyFailure {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo card) throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(card, card, cardSkill, true);
        ui.killCard(card, card, cardSkill);
        resolver.killCard(null, card, cardSkill);
    }
}
