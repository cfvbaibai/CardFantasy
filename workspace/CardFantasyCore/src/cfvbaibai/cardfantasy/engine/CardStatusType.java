package cfvbaibai.cardfantasy.engine;

public enum CardStatusType {
    Âé±Ô(false),
    ±ù¶³(false),
    ÖÐ¶¾(true),
    Ëø¶¨(false);

    private boolean quantitive;
    public boolean isQuantitive() {
        return this.quantitive;
    }
    
    CardStatusType(boolean quantitive) {
        this.quantitive = quantitive;
    }
}
