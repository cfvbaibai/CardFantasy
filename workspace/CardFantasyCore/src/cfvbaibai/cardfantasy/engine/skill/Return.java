package cfvbaibai.cardfantasy.engine.skill;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.*;

public final class Return {
    public static void apply(SkillResolver resolver, Skill cardSkill, CardInfo attacker, Player defenderHero) throws HeroDieSignal {
        if (attacker == null) {
            return;
        }
        CardInfo defender = defenderHero.getField().getCard(attacker.getPosition());
        if (defender == null) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        ui.useSkill(attacker, defender, cardSkill, true);
        OnAttackBlockingResult result = resolver.resolveAttackBlockingSkills(attacker, defender, cardSkill, 1);
        if (!result.isAttackable()) {
            return;
        }
        returnCard(resolver, cardSkill, attacker, defender);
    }

    public static void returnCard(SkillResolver resolver, Skill cardSkill, CardInfo attacker, CardInfo defender) {
        defender.getOwner().getField().expelCard(defender.getPosition());
        // 这段验证不再有效，因为反射装甲可能将横扫的攻击者送还
        //if (expelledCard != defender) {
        //throw new CardFantasyRuntimeException("expelledCard != defender");
        //}
        GameUI ui = resolver.getStage().getUI();
        ui.returnCard(attacker, defender, cardSkill);
        if (!defender.getStatus().containsStatus(CardStatusType.召唤)) {
            // 被召唤的卡牌不回到卡组，而是直接消失
            // 送还的卡是随机插入卡组而非加在末尾
            int deckSize = defender.getOwner().getDeck().size();
            int index = 0;
            if (deckSize > 0)
            {
                index = Randomizer.getRandomizer().next(0, deckSize);
            }
            defender.getOwner().getDeck().insertCardToPosition(defender, index);
            defender.reset();
        }
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
    }

    //地裂等牌返回牌库是有顺序的。
    public static void returnCard2(SkillResolver resolver, Skill cardSkill, CardInfo attacker, CardInfo defender) {
        defender.getOwner().getField().expelCard(defender.getPosition());
        // 这段验证不再有效，因为反射装甲可能将横扫的攻击者送还
        //if (expelledCard != defender) {
        //throw new CardFantasyRuntimeException("expelledCard != defender");
        //}
        GameUI ui = resolver.getStage().getUI();
        ui.returnCard(attacker, defender, cardSkill);
        if (!defender.getStatus().containsStatus(CardStatusType.召唤)) {
            // 被召唤的卡牌不回到卡组，而是直接消失
            // 送还的卡是随机插入卡组而非加在末尾
            //int deckSize = defender.getOwner().getDeck().size();
            int index = 0;
            //地裂的抽卡是有顺序的。
//            if (deckSize > 0)
//            {
//                index = Randomizer.getRandomizer().next(0, deckSize);
//            }
            defender.getOwner().getDeck().insertCardToPosition(defender, index);
            defender.reset();
        }
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
    }
}
