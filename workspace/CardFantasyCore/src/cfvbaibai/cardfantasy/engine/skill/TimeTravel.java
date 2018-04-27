package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class TimeTravel {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, Player myHero, Player opHero) throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        CardInfo caster = (CardInfo) skillUseInfo.getOwner();
        List<CardInfo> victims = new ArrayList<CardInfo>();
        victims.addAll(myHero.getField().getAliveCards());
        victims.addAll(opHero.getField().getAliveCards());
        victims.addAll(myHero.getHand().toList());
        victims.addAll(opHero.getHand().toList());
        victims.remove(caster);
        ui.useSkill(caster, victims, skillUseInfo.getSkill(), true);

        applyToPlayer(myHero, skillUseInfo, resolver);
        applyToPlayer(opHero, skillUseInfo, resolver);
    }

    private static void applyToPlayer(Player player, SkillUseInfo skillUseInfo, SkillResolver resolver) throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        boolean flag = false;
        CardInfo caster = (CardInfo) skillUseInfo.getOwner();
        if (skillUseInfo.getSkill().getType() == SkillType.决胜时刻) {
            flag = true;
        }
        for (CardInfo card : player.getField().getAliveCards()) {
            if (flag && card == caster) {
                continue;
            }
            if (card != caster) {
                if (resolver.resolveAttackBlockingSkills(caster, card, skillUseInfo.getSkill(), 0).isAttackable()) {
                    //移除附加的第四技能
                    card.setSummonNumber(0);
                    card.setRuneActive(false);
                    resolver.resolveLeaveSkills(card);
                    ImpregnableDefenseHeroBuff.removeSkill(card,resolver);//移除铁壁的buff

                    ui.returnCard(caster, card, skillUseInfo.getSkill());
                    if (!card.getStatus().containsStatus(CardStatusType.召唤)) {
                        card.restoreOwner();
                        card.getOwner().getDeck().addCard(card);
                    }
                    player.getField().removeCard(card);
                    card.setSummonNumber(0);
                    card.setAddDelay(0);
                    card.setRuneActive(false);
                }
            }
        }
        for (CardInfo card : player.getHand().toList()) {
            if (resolver.resolveAttackBlockingSkills(caster, card, skillUseInfo.getSkill(), 0).isAttackable()) {
                //移除附加的第四技能
                resolver.resolveLeaveSkills(card);
                ui.cardToGrave(player, card);
                player.getDeck().addCard(card);
                player.getHand().removeCard(card);
                card.setSummonNumber(0);
                card.setAddDelay(0);
                card.setRuneActive(false);
            }
        }
        player.getDeck().shuffle();
    }
}
