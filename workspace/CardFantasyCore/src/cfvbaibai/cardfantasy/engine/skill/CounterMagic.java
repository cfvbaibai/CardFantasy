package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.SkillResolver;

public final class CounterMagic {

    public static boolean isSkillBlocked(SkillResolver resolver, Skill cardSkill, Skill attackSkill,
            EntityInfo attacker, CardInfo defender) throws HeroDieSignal {
        if (attacker == null) {
            return false;
        }
        if (resolver.isMagicalSkill(attackSkill)) {
            int damage = cardSkill.getImpact();
            if (cardSkill.getType() == SkillType.花族秘术) {
                damage = cardSkill.getImpact2();
            }
            GameUI ui = resolver.getStage().getUI();
            ui.useSkill(defender, attacker, cardSkill, true);
            if (attacker instanceof CardInfo) {
                CardInfo cardAttacker = (CardInfo) attacker;
                if (!cardAttacker.isDead()) {
                    ui.attackCard(defender, cardAttacker, cardSkill, damage);
                    resolver.resolveDeathSkills(defender, cardAttacker, cardSkill, resolver.applyDamage(defender, cardAttacker, cardSkill, damage));
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
