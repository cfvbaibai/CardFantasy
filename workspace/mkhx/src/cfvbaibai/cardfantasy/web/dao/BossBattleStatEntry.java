package cfvbaibai.cardfantasy.web.dao;

public class BossBattleStatEntry {
    private String sortedDeck;
    private long battleCount;
    private int heroLv;
    private String bossName;
    private long minDamage;
    private long avgDamage;
    private long maxDamage;

    public String getSortedDeck() {
        return sortedDeck;
    }
    public void setSortedDeck(String sortedDeck) {
        this.sortedDeck = sortedDeck;
    }
    public long getBattleCount() {
        return battleCount;
    }
    public void setBattleCount(long battleCount) {
        this.battleCount = battleCount;
    }
    public int getHeroLv() {
        return heroLv;
    }
    public void setHeroLv(int heroLv) {
        this.heroLv = heroLv;
    }
    public String getBossName() {
        return bossName;
    }
    public void setBossName(String bossName) {
        this.bossName = bossName;
    }
    public long getMinDamage() {
        return minDamage;
    }
    public void setMinDamage(long minDamage) {
        this.minDamage = minDamage;
    }
    public long getAvgDamage() {
        return avgDamage;
    }
    public void setAvgDamage(long avgDamage) {
        this.avgDamage = avgDamage;
    }
    public long getMaxDamage() {
        return maxDamage;
    }
    public void setMaxDamage(long maxDamage) {
        this.maxDamage = maxDamage;
    }
}