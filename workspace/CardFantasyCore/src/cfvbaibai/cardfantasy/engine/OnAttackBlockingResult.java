package cfvbaibai.cardfantasy.engine;

public class OnAttackBlockingResult {

    private boolean attackable;
    private int damage;

    public OnAttackBlockingResult(boolean attackable, int damage) {
        this.attackable = attackable;
        this.damage = damage;
    }
    
    public void setAttackable(boolean attackable) {
        this.attackable = attackable;
        if (!this.attackable) { this.damage = 0; }
    }
    
    public boolean isAttackable() {
        return this.attackable;
    }
    
    public void setDamage(int damage) {
        this.damage = damage;
    }
    
    public int getDamage() {
        return this.damage;
    }
}
