package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

public final class Flee {
    public static void apply(Skill cardSkill, SkillResolver resolver, CardInfo attacker, CardInfo defender,
            int actualDamage) throws HeroDieSignal {
        if (actualDamage <= 0) {
            return;
        }
        if (attacker == null) {
            return;
        }
        if (defender.isDead())
        {
            return;
        }
        if (defender.getOwner().getHand().contains(defender) || defender.getOwner().getDeck().contains(defender)) {
            // 如果已经转生了，那么就不再发动逃跑了
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(defender, attacker, cardSkill, true);
        // 如果没被物理攻击直接秒杀，需要从场上把卡牌去除
        // defender.getOwner().getField().removeCard(defender); BUG! 不能用removeCard，那样会使场上的卡重排，战斗中不该重排
        defender.getOwner().getField().expelCard(defender.getPosition());

        //逃跑卡牌会移除4技能的buff和铁壁效果。
        resolver.resolveLeaveSkills(defender);
        if(defender.containsAllSkill(SkillType.铁壁)||defender.containsAllSkill(SkillType.驱虎吞狼))
        {
            for(SkillUseInfo defenderskill:defender.getAllUsableSkills())
            {
                if (defenderskill.getType() == SkillType.铁壁)
                {
                    ImpregnableDefenseHeroBuff.remove(resolver, defenderskill, defender);
                }
                if (defenderskill.getType() == SkillType.驱虎吞狼)
                {
                    ImpregnableDefenseHeroBuff.remove(resolver, defenderskill.getAttachedUseInfo2(), defender);
                }
            }
        }
        
        // 如果是被召唤的卡牌，发动逃跑技能后应该直接消失
        if (defender.isSummonedMinion()) {
            return;
        }

        Hand hand = defender.getOwner().getHand();
        if (hand.isFull()) {
            ui.cardToDeck(defender.getOwner(), defender);
            defender.getOwner().getDeck().addCard(defender);
        } else {
            ui.returnCard(attacker, defender, cardSkill);
            ui.cardToHand(defender.getOwner(), defender);
            hand.addCard(defender);
        }
    }
}
