package cfvbaibai.cardfantasy.engine.skill;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusType;
import cfvbaibai.cardfantasy.engine.HeroDieSignal;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.SkillResolver;
import cfvbaibai.cardfantasy.engine.SkillUseInfo;

public class TimeBack {
    public static void apply(SkillUseInfo skillUseInfo, SkillResolver resolver, Player myHero, Player opHero) throws HeroDieSignal {
        if (resolver.getStage().hasUsed(skillUseInfo)) {
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
        resolver.getStage().setUsed(skillUseInfo, true);
    }
    
    private static void applyToPlayer(Player player, SkillUseInfo skillUseInfo, SkillResolver resolver) throws HeroDieSignal {
        GameUI ui = resolver.getStage().getUI();
        CardInfo caster = (CardInfo)skillUseInfo.getOwner();
        for (CardInfo card : player.getField().getAliveCards()) {
            if (card != caster) {
                if (resolver.resolveAttackBlockingSkills(caster, card, skillUseInfo.getSkill(), 0).isAttackable()) {
                    ui.returnCard(caster, card, skillUseInfo.getSkill());
                    if (!card.getStatus().containsStatus(CardStatusType.召唤)) {
                        player.getDeck().addCard(card);
                    }
                    player.getField().removeCard(card);
                }
            }
        }
        for (CardInfo card : player.getHand().toList()) {
            ui.cardToGrave(player, card);
            player.getDeck().addCard(card);
            player.getHand().removeCard(card);
        }
        player.getDeck().shuffle();
    }
}
