package cfvbaibai.cardfantasy.game;

public class MapInfo {
    private MapEnemyHero enemyHero;
    private VictoryCondition condition;
    private String deckInfo;
    public MapInfo(MapEnemyHero enemyHero, VictoryCondition condition, String deckInfo) {
        this.enemyHero = enemyHero;
        this.condition = condition;
        this.deckInfo = deckInfo;
    }
    public MapEnemyHero getEnemyHero() {
        return this.enemyHero;
    }
    public VictoryCondition getCondition() {
        return this.condition;
    }
    public String getDeckInfo() {
        return this.deckInfo;
    }
}
