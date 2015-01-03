package cfvbaibai.cardfantasy.engine.feature;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class Guard {
    public static int apply(Skill guardFeature, Skill attackFeature, SkillResolver resolver, EntityInfo attacker, CardInfo guardian,
            int damage) throws HeroDieSignal {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("Attacker cannot be null");
        }
        if (attackFeature != null && attackFeature.getType().containsTag(SkillTag.抗守护)) {
            return damage;
        }
        resolver.getStage().getUI().useSkill(guardian, attacker, guardFeature, true);
        int remainingDamage = 0;
        if (damage > guardian.getHP()) {
            remainingDamage = damage - guardian.getHP();
            damage = guardian.getHP();
        }
        resolver.getStage().getUI().attackCard(attacker, guardian, guardFeature, damage);
        if (resolver.applyDamage(guardian, damage).cardDead) {
            resolver.resolveDeathSkills(attacker, guardian, guardFeature);
        }
        return remainingDamage;
    }
}
