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
    private static int[] hps = new int[] {
        0,
        1000,  1070,  1140,  1210,  1280,  1350,  1420,  1490,  1560,  1630,
        1800,  1880,  1960,  2040,  2120,  2200,  2280,  2360,  2440,  2520,
        2800,  2890,  2980,  3070,  3160,  3250,  3340,  3430,  3520,  3610,
        4000,  4100,  4200,  4300,  4400,  4500,  4600,  4700,  4800,  4900, 
        5400,  5510,  5620,  5730,  5840,  5950,  6060,  6170,  6280,  6390,
        7000,  7120,  7240,  7360,  7480,  7600,  7720,  7840,  7960,  8080,
        8800,  8930,  9060,  9190,  9320,  9450,  9580,  9710,  9840,  9970,
        10800, 10940, 11080, 11220, 11360, 11500, 11640, 11780, 11920, 12060,
        13000, 13150, 13300, 13450, 13600, 13750, 13900, 14050, 14200, 14350,
        15400, 15560, 15720, 15880, 16040, 16200, 16360, 16520, 16680, 16840,
    };
    
    private static int[] costs = new int[] {
        0,
        13,  16,  19,  22,  25,  28,  31,  34,  37,  40,
        43,  46,  49,  52,  55,  58,  61,  64,  67,  70,
        72,  74,  76,  78,  80,  82,  84,  86,  88,  90,
        92,  94,  96,  98,  100, 102, 104, 106, 108, 110,
        112, 114, 116, 118, 120, 122, 124, 126, 128, 130,
        131, 132, 133, 134, 135, 136, 137, 138, 139, 140,
        141, 142, 143, 144, 145, 146, 147, 148, 149, 150,
        151, 152, 153, 154, 155, 156, 157, 158, 159, 160,
        161, 162, 163, 164, 165, 166, 167, 168, 169, 170,
        171, 172, 173, 174, 175, 176, 177, 178, 179, 180,
    };
    
    private static int[] cardSlots = new int[] {
        0,
        3, 3, 4, 4, 5, 5, 5, 5, 5, 6,
        6, 6, 6, 6, 6, 6, 6, 6, 6, 7,
        7, 7, 7, 7, 7, 7, 7, 7, 7, 8,
        8, 8, 8, 8, 9, 9, 9, 9, 9, 10,
    };
    
    private static int[] runeSlots = new int[] {
        0,
        0, 0, 0, 0, 0, 1, 1, 1, 1, 1,
        1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
        2, 2, 2, 2, 2, 2, 2, 2, 2, 3,
        3, 3, 3, 3, 3, 3, 3, 3, 3, 4,
    };

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

    public int getMaxCost() {
        if (this.level < 0) {
            return 0;
        }
        if (this.level < costs.length) {
            return costs[this.level];
        }
        return costs[costs.length - 1] + this.level - (costs.length - 1);
    }

    public int getCardSlot() {
        if (this.level < 0) {
            return 0;
        }
        if (this.level < cardSlots.length) {
            return cardSlots[this.level];
        }
        return 10;
    }
    
    public int getRuneSlot() {
        if (this.level < 0) {
            return 0;
        }
        if (this.level < runeSlots.length) {
            return runeSlots[this.level];
        }
        return 4;
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
