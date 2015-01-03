package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.Field;

public class HealingMist {
    public static void apply(Skill cardSkill, SkillResolver resolver, CardInfo healer) {
        if (healer == null) {
            return;
        }

        Field field = healer.getOwner().getField();
        List<CardInfo> healeeCandidates = resolver.getAdjacentCards(field, healer.getPosition());
        Rainfall.healCards(resolver, healer, cardSkill, healeeCandidates);
    }
}
