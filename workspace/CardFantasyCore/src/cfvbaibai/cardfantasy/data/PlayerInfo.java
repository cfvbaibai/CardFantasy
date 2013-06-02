package cfvbaibai.cardfantasy.data;

import java.util.ArrayList;
import java.util.Collection;

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

    public int getMaxHP() {
        // TODO: Apply adjustments

        // TODO: Apply real life algorithm
        return level * 1000;
    }
    
    public String getId() {
        return this.id;
    }

    public Collection<Card> getCards() {
        return this.cards;
    }
}
