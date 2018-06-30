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
        int magicEchoSkillResult =resolver.resolveMagicEchoSkill(attacker,defender,cardSkill);
        if(magicEchoSkillResult==1||magicEchoSkillResult==2)
        {
            if(attacker.isDead())
            {
                if (magicEchoSkillResult == 1) {
                    return;
                }
            }
            else {
                OnAttackBlockingResult result2 = resolver.resolveAttackBlockingSkills(defender, attacker, cardSkill, 1);
                if (!result2.isAttackable()) {
                    if (magicEchoSkillResult == 1) {
                        return;
                    }
                }
               else returnCard(resolver, cardSkill, defender, attacker);
            }
        }
        if(magicEchoSkillResult==0||magicEchoSkillResult==2) {returnCard(resolver, cardSkill, attacker, defender);}
    }

    public static void returnCard(SkillResolver resolver, Skill cardSkill, CardInfo attacker, CardInfo defender) {
        defender.getOwner().getField().expelCard(defender.getPosition());
        // 这段验证不再有效，因为反射装甲可能将横扫的攻击者送还
        //if (expelledCard != defender) {
        //throw new CardFantasyRuntimeException("expelledCard != defender");
        //}
        GameUI ui = resolver.getStage().getUI();
        ui.returnCard(attacker, defender, cardSkill);
        resolver.resolveLeaveSkills(defender);
        ImpregnableDefenseHeroBuff.removeSkill(defender,resolver);//移除铁壁的buff
        if (!defender.getStatus().containsStatus(CardStatusType.召唤)) {
            // 被召唤的卡牌不回到卡组，而是直接消失
            // 送还的卡是随机插入卡组而非加在末尾
            int deckSize = defender.getOwner().getDeck().size();
            int index = 0;
            if (deckSize > 0) {
                index = Randomizer.getRandomizer().next(0, deckSize);
            }
            defender.restoreOwner();
            defender.getOwner().getDeck().insertCardToPosition(defender, index);
            defender.reset();

        }
        defender.setSummonNumber(0);
        defender.setAddDelay(0);
        defender.setRuneActive(false);
    }

    //地裂等牌返回牌库是有顺序的。
    public static void returnCard2(SkillResolver resolver, Skill cardSkill, CardInfo attacker, CardInfo defender, boolean flag) {
        defender.getOwner().getField().expelCard(defender.getPosition());
        // 这段验证不再有效，因为反射装甲可能将横扫的攻击者送还
        //if (expelledCard != defender) {
        //throw new CardFantasyRuntimeException("expelledCard != defender");
        //}
        GameUI ui = resolver.getStage().getUI();
        ui.returnCard(attacker, defender, cardSkill);
        //flag判断是否是从手牌回到牌库
        if (flag) {
            ImpregnableDefenseHeroBuff.removeSkill(defender,resolver);//移除铁壁的buff
        }
        resolver.resolveLeaveSkills(defender);
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
            defender.restoreOwner();
            defender.getOwner().getDeck().insertCardToPosition(defender, index);
            defender.reset();
        }
        defender.setSummonNumber(0);
        defender.setAddDelay(0);
        defender.setRuneActive(false);
    }

    //卡牌返回到手牌
    public static void returnHand(SkillResolver resolver, Skill cardSkill, CardInfo attacker, CardInfo defender) {
        if(defender.getOriginalOwner()!=null&&defender.getOriginalOwner().getHand().isFull())
        {
            return;
        }
        defender.getOwner().getField().expelCard(defender.getPosition());
        GameUI ui = resolver.getStage().getUI();
        ui.returnCard(attacker, defender, cardSkill);
        resolver.resolveLeaveSkills(defender);
        ImpregnableDefenseHeroBuff.removeSkill(defender,resolver);//移除铁壁的buff
        if (!defender.getStatus().containsStatus(CardStatusType.召唤)) {
            defender.restoreOwner();
            defender.getOwner().getHand().addCard(defender);
            defender.reset();
            ui.cardToHand(defender.getOwner(), defender);
        }
        defender.setSummonNumber(0);
        defender.setAddDelay(0);
        defender.setRuneActive(false);
    }
}
