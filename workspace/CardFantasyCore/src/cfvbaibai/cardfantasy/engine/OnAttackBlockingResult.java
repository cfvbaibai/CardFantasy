package cfvbaibai.cardfantasy.engine;

public class OnAttackBlockingResult {

    public boolean isBlocked;
    public int damage;

    public OnAttackBlockingResult(boolean isBlocked, int damage) {
        this.isBlocked = isBlocked;
        this.damage = damage;
    }
}
