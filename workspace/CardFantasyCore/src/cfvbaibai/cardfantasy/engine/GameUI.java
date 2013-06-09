package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.Feature;
import cfvbaibai.cardfantasy.data.FeatureType;

public abstract class GameUI {

    private Board board;
    private Rule rule;

    protected GameUI() {
    }

    public void gameStarted(Board board, Rule rule) {
        this.board = board;
        this.rule = rule;
        this.gameStarted();
    }

    protected Board getBoard() {
        return this.board;
    }

    protected Rule getRule() {
        return this.rule;
    }

    protected abstract void gameStarted();

    public abstract void playerAdded(Player player);

    public abstract void errorHappened(CardFantasyRuntimeException e);

    public abstract void phaseChanged(Player player, Phase previous, Phase current);

    public abstract void playerChanged(Player previousPlayer, Player nextPlayer);

    public abstract void gameEnded(GameResult result);

    public abstract void cardDrawed(Player drawer, CardInfo card);

    public abstract void cantDrawDeckEmpty(Player drawer);

    public abstract void cantDrawHandFull(Player drawer);

    public abstract void summonCard(Player player, CardInfo card);

    public abstract void roundStarted(Player player, int round);

    public abstract void roundEnded(Player player, int round);

    public abstract void attackCard(CardInfo attacker, CardInfo defender, Feature feature, int damage);

    public abstract void cardDead(CardInfo deadCard);

    public abstract void attackHero(CardInfo attacker, Player hero, Feature feature, int damage);

    public abstract void useSkill(CardInfo attacker, List<CardInfo> victims, Feature feature);

    public void useSkill(CardInfo attacker, CardInfo victim, Feature feature) {
        List<CardInfo> victims = new ArrayList<CardInfo>();
        victims.add(victim);
        useSkill(attacker, victims, feature);
    }

    public abstract void useSkillToHero(CardInfo attacker, Player victimHero, Feature feature);

    public abstract void addCardStatus(CardInfo attacker, CardInfo victim, Feature feature, CardStatusItem item);

    public abstract void battleBegins();

    public abstract void attackBlocked(CardInfo attacker, CardInfo defender, Feature atFeature, Feature dfFeature);

    public abstract void adjustAT(CardInfo source, CardInfo target, int adjAT, Feature feature);

    public abstract void adjustHP(CardInfo source, CardInfo target, int adjHP, Feature feature);

    public abstract void blockDamage(CardInfo attacker, CardInfo defender, Feature feature, int originalDamage,
            int actualDamage);

    public abstract void healBlocked(CardInfo healer, CardInfo healee, Feature feature, Feature blockerFeature);

    public abstract void debuffDamage(CardInfo card, CardStatusItem item, int effect);

    public abstract void cannotAction(CardInfo card);

    public abstract void recoverAT(CardInfo card, FeatureType cause, int recoveredAT);

    public abstract void healCard(CardInfo healer, CardInfo healee, Feature feature, int healHP);

    public abstract void healHero(CardInfo healer, Player healee, Feature feature, int healHP);

    public abstract void loseAdjustATEffect(CardInfo ally, FeatureEffect effect);

    public abstract void loseAdjustHPEffect(CardInfo ally, FeatureEffect effect);

    public abstract void cardToDeck(Player player, CardInfo card);

    public abstract void cardToHand(Player player, CardInfo card);

    public abstract void blockStatus(CardInfo attacker, CardInfo defender, Feature feature, CardStatusItem item);

    public abstract void blockFeature(CardInfo attacker, CardInfo defender, Feature feature, Feature attackFeature);

    public abstract void returnCard(CardInfo attacker, CardInfo defender, Feature feature);
}
