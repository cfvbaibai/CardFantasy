package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class LunaTouch {
    public static void apply(Skill cardSkill, SkillResolver resolver, EntityInfo healer) {
        if (healer == null) {
            return;
        }

        Field field = healer.getOwner().getField();
        List<CardInfo> healees = field.getCardsWithLowestHP(1);
        Rainfall.healCards(resolver, healer, cardSkill, HealType.Percentage, healees);
    }
}
