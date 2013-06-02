package cfvbaibai.cardfantasy.engine;

public class OnAttackBlockingResult {

    public boolean attackable;
    public int damage;

    public OnAttackBlockingResult(boolean attackable, int damage) {
        this.attackable = attackable;
        this.damage = damage;
    }
}
