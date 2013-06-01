package cfvbaibai.cardfantasy.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.Deck;

/**
 * Immutable
 * @author °×°×
 *
 */
public class PlayerInfo {
    private String id;
    private int level;
    private Collection <Card> cards;
    
    public PlayerInfo(String id, int level, Card ... cards) {
        this.id = id;
        this.level = level;
        this.cards = new ArrayList<Card>();
        for (Card card : cards) {
            this.cards.add(card);
        }
    }

    private Adjustments adjustments;

    public int getMaxLife() {
        // TODO: Apply adjustments

        // TODO: Apply real life algorithm
        return level * 100;
    }
    
    public String getId() {
        return this.id;
    }

    public Deck prepareDeck() {
        List<CardInfo> cardInfos = new ArrayList<CardInfo>(cards.size());
        for (Card card : cards) {
            cardInfos.add(new CardInfo(card));
        }
        return new Deck(cardInfos);
    }
}
