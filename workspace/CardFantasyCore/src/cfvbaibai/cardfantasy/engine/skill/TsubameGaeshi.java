package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

/**
 * 当受到致命攻击时，对正对面卡牌造成200%基础攻击力的伤害，不受冰甲、格挡、闪避影响，该技能死亡前仅可使用一次
 * @author tonynie
 *
 */
public class TsubameGaeshi {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, Player attackerHero, CardInfo defender) throws HeroDieSignal {
        if (defender.hasUsed(skillUseInfo)) {
            return;
        }

        Skill cardSkill = skillUseInfo.getSkill();
        int damage = cardSkill.getImpact() * defender.getLevel1AT() / 100;
        CardInfo victim = attackerHero.getField().getCard(defender.getPosition());
        if (victim == null) {
            return;
        }

        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, victim, cardSkill, true);
        if (!resolver.resolverCounterAttackBlockSkill(cardSkill, victim, defender)) {
            ui.attackCard(defender, victim, cardSkill, damage);
            resolver.resolveDeathSkills(defender, victim, cardSkill, resolver.applyDamage(victim, cardSkill, damage));
        }
        defender.setUsed(skillUseInfo);
    }
}
