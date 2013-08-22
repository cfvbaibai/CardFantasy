package cfvbaibai.cardfantasy.engine;

public enum CardStatusType {
    È¼ÉÕ(true, "È¼"),
    Âé±Ô(false, "Âé"),
    ±ù¶³(false, "¶³"),
    ÖĞ¶¾(true, "¶¾"),
    Ëø¶¨(false, "Ëø"),
    ÁÑÉË(false, "ÁÑ"),
    ĞéÈõ(false, "Ğé"),
    ÃÔ»ó(false, "»ó");

    private boolean quantitive;
    public boolean isQuantitive() {
        return this.quantitive;
    }
    
    private String abbrev;
    public String getAbbrev() {
        return this.abbrev;
    }
    
    CardStatusType(boolean quantitive, String abbrev) {
        this.quantitive = quantitive;
        this.abbrev = abbrev;
    }
}
