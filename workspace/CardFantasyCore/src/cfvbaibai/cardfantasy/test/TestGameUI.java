package cfvbaibai.cardfantasy.test;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.engine.Board;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.CardStatusItem;
import cfvbaibai.cardfantasy.engine.Deck;
import cfvbaibai.cardfantasy.engine.EntityInfo;
import cfvbaibai.cardfantasy.engine.FeatureEffect;
import cfvbaibai.cardfantasy.engine.Field;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Grave;
import cfvbaibai.cardfantasy.engine.Hand;
import cfvbaibai.cardfantasy.engine.Phase;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.RuneBox;
import cfvbaibai.cardfantasy.engine.RuneInfo;

public class TestGameUI extends GameUI {

    public TestGameUI() {
    }

    @Override
    public void playerAdded(Player player) {
        // TODO Auto-generated method stub

    }

    @Override
    public void roundStarted(Player player, int round) {
        say("================================================================================================================================");
        sayF("================================ Round %d started for player <%s> ================================",
                round, player.getId());
        say("================================================================================================================================");
    }

    @Override
    public void roundEnded(Player player, int round) {
        sayF("Round %d ended for player <%s>", round, player.getId());
    }

    @Override
    public void errorHappened(CardFantasyRuntimeException e) {
        e.printStackTrace();
    }

    @Override
    public void phaseChanged(Player player, Phase previous, Phase current) {
        sayF("Phase Changed: %s => %s", previous.name().toUpperCase(), current.name().toUpperCase());
    }

    @Override
    public void playerChanged(Player previousPlayer, Player nextPlayer) {
        sayF("Player Changed: <%s> => <%s>", previousPlayer.getId(), nextPlayer.getId());
    }

    @Override
    public void cardDrawed(Player drawer, CardInfo card) {
        sayF("<%s> draws a card: <%s (LV: %d)>", drawer.getId(), card.getId(), card.getLevel());
        showBoard();
    }

    @Override
    public void cantDrawDeckEmpty(Player drawer) {
        sayF("Player <%s>'s deck is empty. Cannot draw card.", drawer.getId());
        this.showBoard();
    }

    @Override
    public void cantDrawHandFull(Player drawer) {
        sayF("Player <%s>'s hand is full. Cannot draw card.", drawer.getId());
        this.showBoard();
    }

    @Override
    public void summonCard(Player player, CardInfo card) {
        sayF("<%s> summons card: <%s (LV: %d)>", player.getId(), card.getId(), card.getLevel());
    }

    @Override
    public void attackCard(EntityInfo attacker, CardInfo defender, Feature cardFeature, int damage) {
        String featureClause = cardFeature == null ? "" : (" by " + cardFeature.getShortDesc() + "");
        int logicalRemainingHP = defender.getHP() - damage;
        if (logicalRemainingHP < 0) {
            sayF("%s attacks %s%s. Damage: %d (%d overflow). HP: %d -> 0.", attacker.getShortDesc(),
                    defender.getShortDesc(), featureClause, damage, -logicalRemainingHP, defender.getHP());
        } else {
            sayF("%s attacks %s%s. Damage: %d. HP: %d -> %d", attacker.getShortDesc(), defender.getShortDesc(),
                    featureClause, damage, defender.getHP(), logicalRemainingHP);
        }
    }

    @Override
    public void cardDead(CardInfo deadCard) {
        sayF("%s dies!", deadCard.getShortDesc());
    }

    @Override
    public void attackHero(CardInfo attacker, Player hero, Feature cardFeature, int damage) {
        String featureClause = cardFeature == null ? "" : (" by " + cardFeature.getShortDesc() + "");
        int logicalRemainingHP = hero.getHP() - damage;
        if (logicalRemainingHP < 0) {
            sayF("%s attacks <%s> directly%s! Damage: %d (%d overflow). HP: %d -> %d", attacker.getShortDesc(),
                    hero.getId(), featureClause, damage, -logicalRemainingHP, hero.getHP(), 0);
        } else {
            sayF("%s attacks <%s> directly%s! Damage: %d. HP: %d -> %d", attacker.getShortDesc(), hero.getId(),
                    featureClause, damage, hero.getHP(), hero.getHP() - damage);
        }
    }

    @Override
    public void useSkill(EntityInfo attacker, List<? extends EntityInfo> victims, Feature cardFeature) {
        if (victims.isEmpty()) {
            sayF("%s cannot find target to use %s.", attacker.getShortDesc(), cardFeature.getShortDesc());
        } else {
            List<String> victimTexts = new LinkedList<String>();
            for (EntityInfo victim : victims) {
                victimTexts.add(victim.getShortDesc());
            }
            String victimsText = StringUtils.join(victimTexts, ", ");
            String featureDesc = cardFeature == null ? "¡¾ÆÕÍ¨¹¥»÷¡¿" : cardFeature.getShortDesc();
            sayF("%s uses %s to { %s }!", attacker.getShortDesc(), featureDesc, victimsText);
        }
    }

    @Override
    public void useSkillToHero(EntityInfo attacker, Player victimHero, Feature cardFeature) {
        String featureDesc = cardFeature == null ? "¡¾ÆÕÍ¨¹¥»÷¡¿" : cardFeature.getShortDesc();
        sayF("%s uses %s to hero <%s>!", attacker.getShortDesc(), featureDesc, victimHero.getId());
    }

    @Override
    public void addCardStatus(EntityInfo attacker, CardInfo victim, Feature cardFeature, CardStatusItem item) {
        sayF("%s.%s adds a new status to %s: ¡¾%s¡¿", attacker.getShortDesc(), cardFeature.getShortDesc(),
                victim.getShortDesc(), item.getShortDesc());
    }

    @Override
    public void gameEnded(GameResult result) {
        sayF("Game ended. Winner: <%s>, GameEndCause: %s", result.getWinner().getId(), result.getCause().toString());
    }

    @Override
    protected void gameStarted() {
        say("Game started!");
        sayF("Rule: MaxHandCard=%d, MaxRound=%d", getRule().getMaxHandCards(), getRule().getMaxRound());
        this.showBoard();
    }

    private void showBoard() {
        Board board = this.getBoard();
        say("-----------------------------------------------------------------------------");
        Player player = board.getPlayer(0);
        sayF("Player %d: %s - Life: %d", 0, player.getId(), player.getHP());
        showGrave(player.getGrave());
        showDeck(player.getDeck());
        showHand(player.getHand());
        say("");
        showRune(player.getRuneBox());
        showField(player.getField());
        say("");

        player = board.getPlayer(1);
        say("");
        showField(player.getField());
        showRune(player.getRuneBox());
        say("");
        showHand(player.getHand());
        showDeck(player.getDeck());
        showGrave(player.getGrave());
        sayF("Player %d: %s - Life: %d", 1, player.getId(), player.getHP());
        say("-----------------------------------------------------------------------------");
    }

    private void showRune(RuneBox runeBox) {
        StringBuffer sb = new StringBuffer();
        sb.append("Rune : ");
        for (RuneInfo rune : runeBox.getRunes()) {
            sb.append(rune.getShortDesc());
            sb.append(", ");
        }
        say(sb.toString());
    }

    private void showGrave(Grave grave) {
        StringBuffer sb = new StringBuffer();
        sb.append("Grave: ");
        for (CardInfo card : grave.toList()) {
            sb.append(String.format("%s (LV=%d, IAT=%d, MHP=%d), ", card.getId(), card.getLevel(), card.getInitAT(),
                    card.getMaxHP()));
        }
        say(sb.toString());
    }

    private void showField(Field field) {
        StringBuffer sb = new StringBuffer();
        //sb.append("Field: ");
        int i = 0;
        List<CardInfo> cards = field.getAliveCards();
        for (CardInfo card : cards) {
            sb.append(String.format("[%d] %s (LV=%d, AT=%d/%d, HP=%d/%d/%d, ST=%s, EF=%s)\r\n", i, card.getId(),
                    card.getLevel(), card.getAT(), card.getInitAT(), card.getHP(), card.getMaxHP(),
                    card.getOriginalMaxHP(), card.getStatus().getShortDesc(), card.getEffectsDesc()));
            ++i;
        }
        if (cards.size() > 0) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }
        say(sb.toString());
    }

    private void showHand(Hand hand) {
        StringBuffer sb = new StringBuffer();
        sb.append("Hand : ");
        for (CardInfo card : hand.toList()) {
            sb.append(String.format("%s (LV=%d, IAT=%d, MHP=%d, SD=%d), ", card.getId(), card.getLevel(),
                    card.getInitAT(), card.getMaxHP(), card.getSummonDelay()));
        }
        say(sb.toString());
    }

    private void showDeck(Deck deck) {
        StringBuffer sb = new StringBuffer();
        sb.append("Deck : ");
        for (CardInfo card : deck.toList()) {
            sb.append(String.format("%s (LV=%d, IAT=%d, MHP=%d), ", card.getId(), card.getLevel(), card.getInitAT(),
                    card.getMaxHP()));
        }
        say(sb.toString());
    }

    private static void sayF(String format, Object... args) {
        say(String.format(format, args));
    }

    private static void say(Object obj) {
        System.out.println(obj.toString());
    }

    @Override
    public void battleBegins() {
        this.showBoard();
    }

    @Override
    public void attackBlocked(EntityInfo attacker, CardInfo defender, Feature atFeature, Feature dfFeature) {
        String attackerDesc = attacker.getShortDesc();
        if (atFeature == null && dfFeature == null) {
            sayF("%s is in status %s so cannot attack!", attackerDesc, attacker.getStatus().getShortDesc());
        } else if (atFeature == null && dfFeature != null) {
            sayF("%s's attack is blocked by %s due to %s!", attackerDesc, defender.getShortDesc(),
                    dfFeature.getShortDesc());
        } else if (atFeature != null && dfFeature == null) {
            sayF("%s is in status %s so cannot activate %s!", attackerDesc, attacker.getStatus().getShortDesc(),
                    atFeature.getShortDesc());
        } else if (atFeature != null && dfFeature != null) {
            sayF("%s's feature %s is blocked by %s due to %s!", attackerDesc, atFeature.getShortDesc(),
                    defender.getShortDesc(), dfFeature.getShortDesc());
        }
    }

    @Override
    public void adjustAT(CardInfo source, CardInfo target, int adjAT, Feature cardFeature) {
        String verb = adjAT > 0 ? "increases" : "decreases";
        sayF("%s %s %s's AT by %s ! %d -> %d.", source.getShortDesc(), verb, target.getShortDesc(),
                cardFeature.getShortDesc(), target.getAT(), target.getAT() + adjAT);
    }

    @Override
    public void adjustHP(CardInfo source, CardInfo target, int adjHP, Feature cardFeature) {
        String verb = adjHP > 0 ? "increases" : "decreases";
        sayF("%s %s %s's HP by %s ! %d -> %d.", source.getShortDesc(), verb, target.getShortDesc(),
                cardFeature.getShortDesc(), target.getHP(), target.getHP() + adjHP);
    }

    @Override
    public void blockDamage(EntityInfo attacker, EntityInfo defender, Feature cardFeature, int originalDamage,
            int actualDamage) {
        sayF("%s blocks the attack from %s by %s. Damage: %d -> %d", defender.getShortDesc(),
                attacker.getShortDesc(), cardFeature.getShortDesc(), originalDamage, actualDamage);
    }

    @Override
    public void debuffDamage(CardInfo card, CardStatusItem item, int damage) {
        sayF("%s gets damage in status %s. Damage: %d. HP: %d -> %d", card.getShortDesc(), item.getShortDesc(),
                damage, card.getHP(), Math.max(0, card.getHP() - damage));
    }

    @Override
    public void cannotAction(CardInfo card) {
        sayF("%s is in status %s and cannot action!", card.getShortDesc(), card.getStatus().getShortDesc());
    }

    @Override
    public void recoverAT(CardInfo card, FeatureType cause, int recoveredAT) {
        sayF("%s's AT recovered from ¡¾%s¡¿. AT: %d -> %d", card.getShortDesc(), cause.name(), card.getAT(),
                card.getAT() - recoveredAT);
    }

    @Override
    public void healCard(CardInfo healer, CardInfo healee, Feature cardFeature, int healHP) {
        int postHealHP = healee.getHP() + healHP;
        String healText = String.valueOf(healHP);
        if (postHealHP > healee.getMaxHP()) {
            healText += " (" + (postHealHP - healee.getMaxHP()) + " overflow)";
            postHealHP = healee.getMaxHP();
        }
        sayF("%s heals %s by %s for %s points. HP: %d -> %d", healer.getShortDesc(), healee.getShortDesc(),
                cardFeature.getShortDesc(), healText, healee.getHP(), postHealHP);
    }

    @Override
    public void healHero(CardInfo healer, Player healee, Feature cardFeature, int healHP) {
        int postHealHP = healee.getHP() + healHP;
        String healText = String.valueOf(healHP);
        if (postHealHP > healee.getMaxHP()) {
            healText += " (" + (postHealHP - healee.getMaxHP()) + " overflow)";
            postHealHP = healee.getMaxHP();
        }
        sayF("%s heals %s by %s for %s points. HP: %d -> %d", healer.getShortDesc(), healee.getShortDesc(),
                cardFeature.getShortDesc(), healText, healee.getHP(), postHealHP);
    }

    @Override
    public void loseAdjustATEffect(CardInfo ally, FeatureEffect effect) {
        sayF("%s loses effect caused by %s from %s. AT: %d -> %d.", ally.getShortDesc(), effect.getCause().getFeature()
                .getShortDesc(), effect.getSource().getShortDesc(), ally.getAT(), ally.getAT() - effect.getValue());
    }

    @Override
    public void loseAdjustHPEffect(CardInfo ally, FeatureEffect effect) {
        int currentHP = ally.getHP() > ally.getMaxHP() - effect.getValue() ? ally.getMaxHP() - effect.getValue() : ally
                .getHP();
        sayF("%s loses effect caused by %s from %s. HP: %d -> %d.", ally.getShortDesc(), effect.getCause().getFeature()
                .getShortDesc(), effect.getSource().getShortDesc(), ally.getHP(), currentHP);
    }

    @Override
    public void cardToDeck(Player player, CardInfo card) {
        sayF("%s is put into %s's deck.", card.getShortDesc(), player.getShortDesc());
    }

    @Override
    public void cardToHand(Player player, CardInfo card) {
        sayF("%s is put into %s's hand.", card.getShortDesc(), player.getShortDesc());
    }

    @Override
    public void healBlocked(CardInfo healer, CardInfo healee, Feature cardFeature, Feature blockerFeature) {
        if (blockerFeature == null) {
            sayF("%s is in status %s so cannot be healed by %s from %s!", healee.getShortDesc(), healee.getStatus()
                    .getShortDesc(), cardFeature.getShortDesc(), healer.getShortDesc());
        } else {
            throw new CardFantasyRuntimeException("blockerFeature is not null. To be implemented.");
        }
    }

    @Override
    public void blockStatus(EntityInfo attacker, EntityInfo defender, Feature cardFeature, CardStatusItem item) {
        sayF("%s blocked status ¡¾%s¡¿ by %s", defender.getShortDesc(), item.getShortDesc(),
                cardFeature.getShortDesc());
    }

    @Override
    public void blockFeature(EntityInfo attacker, EntityInfo defender, Feature cardFeature, Feature attackFeature) {
        sayF("%s blocked %s by %s", defender.getShortDesc(), attackFeature.getShortDesc(),
                cardFeature.getShortDesc());
    }

    @Override
    public void returnCard(CardInfo attacker, CardInfo defender, Feature cardFeature) {
        sayF("%s returns %s back to deck by %s.", attacker.getShortDesc(), defender.getShortDesc(),
                cardFeature.getShortDesc());
    }

    @Override
    public void cardToGrave(Player player, CardInfo card) {
        sayF("%s is put into %s's grave.", card.getShortDesc(), player.getShortDesc());
    }

    @Override
    public void disableBlock(CardInfo attacker, CardInfo defender, Feature attackFeature, Feature blockFeature) {
        sayF("%s's %s is disabled by %s's %s.", defender.getShortDesc(), blockFeature.getShortDesc(),
                attacker.getShortDesc(), attackFeature.getShortDesc());
    }

    @Override
    public void confused(CardInfo card) {
        sayF("%s is in status %s and attacks own hero!", card.getShortDesc(), card.getStatus().getShortDesc());
        this.attackHero(card, card.getOwner(), null, card.getAT());
    }

    @Override
    public void roll100(int dice, int rate) {
        sayF("Roll dice for %d%%: %d. %s!", rate, dice, dice < rate ? "Bingo" : "Miss");
    }

    @Override
    public void useSkill(EntityInfo attacker, Feature cardFeature) {
        sayF("%s uses %s", attacker.getShortDesc(), cardFeature.getShortDesc());
    }

    @Override
    public void killCard(EntityInfo attacker, CardInfo victim, Feature cardFeature) {
        sayF("%s kills %s by %s directly!", attacker.getShortDesc(), victim.getShortDesc(),
                cardFeature.getShortDesc());
    }

    @Override
    public void activateRune(RuneInfo rune) {
        sayF("%s is activated!", rune.getShortDesc());
    }

    @Override
    public void deactivateRune(RuneInfo rune) {
        sayF("%s is deactivated!", rune.getShortDesc());
    }
}
