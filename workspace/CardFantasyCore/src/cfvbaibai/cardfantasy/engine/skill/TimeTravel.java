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
                    resolver.resolveLeaveSkills(card);
                    if (card.containsAllSkill(SkillType.铁壁) || card.containsAllSkill(SkillType.驱虎吞狼) || card.containsAllSkill(SkillType.金汤)) {
                        for (SkillUseInfo defenderskill : card.getAllUsableSkills()) {
                            if (defenderskill.getType() == SkillType.铁壁 || defenderskill.getType() == SkillType.金汤) {
                                ImpregnableDefenseHeroBuff.remove(resolver, defenderskill, card);
                            } else if (defenderskill.getType() == SkillType.驱虎吞狼) {
                                ImpregnableDefenseHeroBuff.remove(resolver, defenderskill.getAttachedUseInfo2(), card);
                            }
                        }
                    }

                    ui.returnCard(caster, card, skillUseInfo.getSkill());
                    if (!card.getStatus().containsStatus(CardStatusType.召唤)) {
                        card.restoreOwner();
                        card.getOwner().getDeck().addCard(card);

                    }
                    player.getField().removeCard(card);
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
            }
        }
        player.getDeck().shuffle();
    }
}
