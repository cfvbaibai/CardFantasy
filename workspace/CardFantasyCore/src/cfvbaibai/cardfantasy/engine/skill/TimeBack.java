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

public class TimeBack {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, Player myHero, Player opHero) throws HeroDieSignal {
        if (resolver.getStage().hasUsed(skillUseInfo)&&resolver.getStage().hasPlayerUsed(skillUseInfo.getOwner().getOwner())) {
            return;
        }
        GameUI ui = resolver.getStage().getUI();
        CardInfo caster = (CardInfo)skillUseInfo.getOwner();
        List<CardInfo> victims = new ArrayList<CardInfo>();
        victims.addAll(myHero.getField().getAliveCards());
        victims.addAll(opHero.getField().getAliveCards());
        victims.addAll(myHero.getHand().toList());
        victims.addAll(opHero.getHand().toList());
        victims.remove(caster);
        ui.useSkill(caster, victims, skillUseInfo.getSkill(), true);

        applyToPlayer(myHero, skillUseInfo, resolver);
        applyToPlayer(opHero, skillUseInfo, resolver);
        if(!resolver.getStage().hasUsed(skillUseInfo)) {
            resolver.getStage().setUsed(skillUseInfo, true);
        }
        resolver.getStage().setPlayerUsed(skillUseInfo.getOwner().getOwner(), true);
    }
    
    private static void applyToPlayer(Player player, SkillUseInfo skillUseInfo, SkillResolver resolver) throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        CardInfo caster = (CardInfo)skillUseInfo.getOwner();
        for (CardInfo card : player.getField().getAliveCards()) {
            if (card != caster) {
                if (resolver.resolveAttackBlockingSkills(caster, card, skillUseInfo.getSkill(), 0).isAttackable()) {
                    ui.returnCard(caster, card, skillUseInfo.getSkill());
                    resolver.resolveLeaveSkills(card);
                    if (card.containsAllSkill(SkillType.铁壁)||card.containsAllSkill(SkillType.驱虎吞狼)||card.containsAllSkill(SkillType.金汤)) {
                        for (SkillUseInfo defenderskill : card.getAllUsableSkills()) {
                            if (defenderskill.getType() == SkillType.铁壁||defenderskill.getType() == SkillType.金汤) {
                                ImpregnableDefenseHeroBuff.remove(resolver, defenderskill, card);
                            }
                            else if (defenderskill.getType() == SkillType.驱虎吞狼)
                            {
                                ImpregnableDefenseHeroBuff.remove(resolver, defenderskill.getAttachedUseInfo2(), card);
                            }
                        }
                    }
                    if (!card.getStatus().containsStatus(CardStatusType.召唤)) {
                        if (card.getOriginalOwner() != null) {
                            card.restoreOwner();
                            card.getOriginalOwner().getDeck().addCard(card);
                        } else {
                            player.getDeck().addCard(card);
                        }
                    }
                    player.getField().removeCard(card);
                }
            }
        }
        for (CardInfo card : player.getHand().toList()) {
            if (resolver.resolveAttackBlockingSkills(caster, card, skillUseInfo.getSkill(), 0).isAttackable()) {
                ui.returnCard(caster, card, skillUseInfo.getSkill());
                ui.cardToGrave(player, card);
                resolver.resolveLeaveSkills(card);
                player.getDeck().addCard(card);
                player.getHand().removeCard(card);
            }
        }
        player.getDeck().shuffle();
    }
}
