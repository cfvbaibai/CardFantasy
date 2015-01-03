package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.OnDamagedResult;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class Guard {
    public static int apply(Skill guardSkill, Skill attackSkill, SkillResolver resolver, EntityInfo attacker, CardInfo guardian,
            int damage) throws HeroDieSignal {
        if (attacker == null) {
            throw new CardFantasyRuntimeException("Attacker cannot be null");
        }
        if (attackSkill != null && attackSkill.getType().containsTag(SkillTag.抗守护)) {
            return damage;
        }
        resolver.getStage().getUI().useSkill(guardian, attacker, guardSkill, true);
        int remainingDamage = 0;
        if (damage > guardian.getHP()) {
            remainingDamage = damage - guardian.getHP();
            damage = guardian.getHP();
        }
        resolver.getStage().getUI().attackCard(attacker, guardian, guardSkill, damage);
        OnDamagedResult result = resolver.applyDamage(guardian, damage);
        resolver.resolveDeathSkills(attacker, guardian, guardSkill, result);
        return remainingDamage;
    }
}
