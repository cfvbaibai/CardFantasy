package cfvbaibai.cardfantasy.engine;

public enum CardStatusType {
    PARALYZED(false),
    FROZEN(false),
    POISONED(true),
    TRAPPED(false);
    
    private boolean quantitive;
    public boolean isQuantitive() {
        return this.quantitive;
    }
    
    CardStatusType(boolean quantitive) {
        this.quantitive = quantitive;
    }
}
