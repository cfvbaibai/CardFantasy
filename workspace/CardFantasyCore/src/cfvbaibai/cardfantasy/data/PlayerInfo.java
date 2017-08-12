package cfvbaibai.cardfantasy.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Immutable
 */
public class PlayerInfo {
    private boolean isNormalPlayer;
    private String id;
    private int level;
    private List<Skill> cardBuffs;
    private int heroHpAdj;
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
        13000, 13180, 13360, 13540, 13720, 13900, 14080, 14260, 14440, 14620,
        15700, 15920, 16140, 16360, 16580, 16800, 17020, 17240, 17460, 17680,
        18900, 19200, 19500, 19800, 20100, 20400, 20700, 21000, 21300, 21600,
        23000, 23380, 23760, 24140, 24520, 24900, 25280, 25660, 26040, 26420,
        26800, 27100, 27500, 27800, 28200, 28500, 28900, 29500, 29800, 30500,
        30800, 31100, 31400, 31700, 32000
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
        181, 182, 183, 184, 185, 186, 187, 188, 189, 190,
        191, 192, 193, 194, 195, 196, 197, 198, 199, 200,
        201, 202, 203, 204, 205, 206, 207, 208, 209, 210,
        211, 212, 213, 214, 215
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

    public PlayerInfo(boolean isNormalPlayer, String id, int level, List<Skill> cardBuffs, int heroHpAdj, Collection<Rune> runes, Card ... cards) {
        List<Card> cardList = new ArrayList<Card>();
        for (Card card : cards) {
            cardList.add(card);
        }
        init(isNormalPlayer, id, level, cardBuffs, heroHpAdj, runes, cardList);
    }
    
    public PlayerInfo(boolean isNormalPlayer, String id, int level, List<Skill> cardBuffs, int heroHpAdj, Collection <Rune> runes, Collection <Card> cards) {
        init(isNormalPlayer, id, level, cardBuffs, heroHpAdj, runes, cards);
    }
    
    private final void init(boolean isNormalPlayer, String id, int level, List<Skill> cardBuffs, int heroHpAdj, Collection <Rune> runes, Collection <Card> cards) {
        this.isNormalPlayer = isNormalPlayer;
        this.id = id;
        this.level = level;
        if (cardBuffs == null) {
            this.cardBuffs = new ArrayList<Skill>();
        } else {
            this.cardBuffs = new ArrayList<Skill>(cardBuffs);
        }
        this.heroHpAdj = heroHpAdj;
        this.runes = new ArrayList<Rune>(runes);
        this.cards = new ArrayList<Card>(cards);
    }
    
    public List<Skill> getCardBuffs() {
        return new ArrayList<Skill>(this.cardBuffs);
    }

    public int getMaxHP() {
        if (this.level >= 0 && this.level < hps.length) {
            return hps[this.level] * this.heroHpAdj / 100;
        } else if (this.heroHpAdj != 100) {
            return this.level * 200 * this.heroHpAdj / 100;
        } else {
            return this.level * 200;
        }
    }

    public int getMaxCost() {
        return getMaxCost(this.level);
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

    public List<Card> getCards() {
        return new ArrayList<Card>(this.cards);
    }
    
    public void addCard(Card card) {
        this.cards.add(card);
    }

    public Collection<Rune> getRunes() {
        return new ArrayList<Rune>(this.runes);
    }

    public int getLevel() {
        return this.level;
    }
    
    public boolean isNormalPlayer() {
        return this.isNormalPlayer;
    }
    
    public static int getMaxCost(int level) {
        if (level < 0) {
            return 0;
        }
        if (level < costs.length) {
            return costs[level];
        }
        return costs[costs.length - 1] + level - (costs.length - 1);
    }
    
    public String getDeckParsableDesc() {
        StringBuffer sb = new StringBuffer();
        for (Card card : this.cards) {
            sb.append(card.getParsableDesc());
            sb.append(",");
        }
        for (Rune rune : this.runes) {
            sb.append(rune.getParsableDesc());
            sb.append(",");
        }
        return sb.toString();
    }

    public void removeCard(Card card) {
        this.cards.remove(card);
    }
    
    public static int getHpAtLevel(int level) {
        return hps[level];
    }
}
