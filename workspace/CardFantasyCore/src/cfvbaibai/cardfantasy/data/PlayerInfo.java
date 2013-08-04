package cfvbaibai.cardfantasy.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Immutable
 * 
 * @author °×°×
 * 
 */
public class PlayerInfo {
    private String id;
    private int level;
    private Legion legion;
    private Collection<Card> cards;
    private Collection<Rune> runes;
    private static int[] hps = new int[] { 0000, 1000, 1070, 1140, 1210, 1280, 1350, 1420, 1490, 1560, 1630, 1800,
            1880, 1960, 2040, 2120, 2200, 2280, 2360, 2440, 2520, 2800, 2890, 2980, 3070, 3160, 3250, 3340, 3430, 3520,
            3610, 4000, 4100, 4200, 4300, 4400, 4500, 4600, 4700, 4800, 4900, 5400, 5510, 5620, 5730, 5840, 5950, 6060,
            6170, 6280, 6390, 7000, 7120, 7240, 7360, 7480, 7600, 7720, 7840, 7960, 8080, 8800, 8930, 9060, 9190, 9320,
            9450, 9580, 9710, 9840, 9970, 10800, 10940, 11080, 11220, 11360, 11500, 11640, 11780, 11920, 12060, 13000,
            13150, 13300, 13450, 13600, 13750, 13900, 14050, 14200, 14350, 15400, 15560, 15720, 15880, 16040, 16200,
            16360, 16520, 16680, 16840 };

    public PlayerInfo(String id, int level, Legion legion, Collection<Rune> runes, Card ... cards) {
        List<Card> cardList = new ArrayList<Card>();
        for (Card card : cards) {
            cardList.add(card);
        }
        init(id, level, legion, runes, cardList);
    }
    
    public PlayerInfo(String id, int level, Legion legion, Collection <Rune> runes, Collection <Card> cards) {
        init(id, level, legion, runes, cards);
    }
    
    private final void init(String id, int level, Legion legion, Collection <Rune> runes, Collection <Card> cards) {
        this.id = id;
        this.level = level;
        this.legion = legion;
        this.runes = new ArrayList<Rune>(runes);
        this.cards = new ArrayList<Card>(cards);
    }
    
    public Legion getLegion() {
        return this.legion;
    }

    public int getMaxHP() {
        if (this.level >= 0 && this.level < hps.length) {
            return hps[this.level];
        } else {
            return this.level * 200;
        }
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

    public int getLevel() {
        return this.level;
    }
}
