package cfvbaibai.cardfantasy.engine.skill;

import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;

public final class Spike {
    public static void apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, CardInfo defender, Skill attackSkill, int attackDamage)
            throws HeroDieSignal {
        if (attackDamage <= 0) {
            return;
        }
        if (attacker == null) {
            return;
        }
        CardInfo centerVictim = attacker;
        if (attackSkill != null && attackSkill.getType() == SkillType.连锁攻击) {
            // 连锁攻击触发雷盾的方式比较特殊，是以被连锁卡的正对面为中心的
            centerVictim = attacker.getOwner().getField().getCard(defender.getPosition());
        }
        if (centerVictim == null) {
            return;
        }
        int damage = cardSkill.getImpact();
        GameUI ui = resolver.getStage().getUI();
        List<CardInfo> victims = resolver.getAdjacentCards(centerVictim.getOwner().getField(), centerVictim.getPosition());
        ui.useSkill(defender, victims, cardSkill, true);
        for (CardInfo victim : victims) {
            if (victim == null) {
                continue;
            }
            if (!resolver.resolverCounterAttackBlockSkill(cardSkill, victim, defender)) {
                ui.attackCard(defender, victim, cardSkill, damage);
                resolver.resolveDeathSkills(defender, victim, cardSkill, resolver.applyDamage(victim, cardSkill, damage));
            }
        }
    }
}
