package cfvbaibai.cardfantasy.game;

public class PvlGameResult {
    private int battleCount;
    private int damageToLilith;

    public int resultMessage() {
        return battleCount;
    }
    public int getBattleCount() {
        return this.battleCount;
    }
    public void setBattleCount(int battleCount) {
        this.battleCount = battleCount;
    }
    public int getDamageToLilith() {
        return damageToLilith;
    }
    public void setDamageToLilith(int damageToLilith) {
        this.damageToLilith = damageToLilith;
    }
    public PvlGameResult(int battleCount, int damageToLilith) {
        this.battleCount = battleCount;
        this.damageToLilith = damageToLilith;
    }
    public int getAvgDamageToLilith() {
        return this.getDamageToLilith() / this.getBattleCount();
    }
}
