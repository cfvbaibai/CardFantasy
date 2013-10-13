package cfvbaibai.cardfantasy.engine;

public enum CardStatusType {
    燃烧(true, "燃"),
    麻痹(false, "麻"),
    冰冻(false, "冻"),
    中毒(true, "毒"),
    锁定(false, "锁"),
    裂伤(false, "裂"),
    虚弱(false, "虚"),
    迷惑(false, "惑");

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
