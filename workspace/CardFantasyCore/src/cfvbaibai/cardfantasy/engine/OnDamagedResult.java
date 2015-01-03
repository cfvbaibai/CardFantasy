package cfvbaibai.cardfantasy.engine;

public class OnDamagedResult {
    // Actual damage could be less than originalDamage
    // because of card death and attack overflow
    public int actualDamage;
    public int originalDamage;
    public boolean cardDead;
    public boolean unbending;
    public OnDamagedResult() {
        
    }
}
