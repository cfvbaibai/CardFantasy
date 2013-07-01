package cfvbaibai.cardfantasy.game;


public class MapInfo {
    private MapEnemyHero enemyHero;
    private VictoryCondition condition;
    public MapInfo(MapEnemyHero enemyHero, VictoryCondition condition) {
        super();
        this.enemyHero = enemyHero;
        this.condition = condition;
    }
    public MapEnemyHero getEnemyHero() {
        return enemyHero;
    }
    public VictoryCondition getCondition() {
        return condition;
    }

}
