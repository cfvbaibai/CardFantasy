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
    private Collection <Rune> runes;
    
    public PlayerInfo(String id, int level, Collection <Rune> runes, Card ... cards) {
        this.id = id;
        this.level = level;
        this.runes = new ArrayList<Rune>(runes);
        this.cards = new ArrayList<Card>();
        for (Card card : cards) {
            this.cards.add(card);
        }
    }

    public int getMaxHP() {
        // TODO: Apply adjustments

        // TODO: Apply real life algorithm
        return level * 200;
    }
    
    public String getId() {
        return this.id;
    }

    public Collection<Card> getCards() {
        return new ArrayList<Card>(this.cards);
    }
    
    public Collection<Rune> getRunes() {
        return new ArrayList<Rune>(this.runes);
    }
}
