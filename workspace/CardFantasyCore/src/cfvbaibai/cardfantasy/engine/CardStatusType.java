package cfvbaibai.cardfantasy.engine;

public enum CardStatusType {
    È¼ÉÕ(true),
    Âé±Ô(false),
    ±ù¶³(false),
    ÖĞ¶¾(true),
    Ëø¶¨(false),
    ÁÑÉË(false);

    private boolean quantitive;
    public boolean isQuantitive() {
        return this.quantitive;
    }
    
    CardStatusType(boolean quantitive) {
        this.quantitive = quantitive;
    }
}
