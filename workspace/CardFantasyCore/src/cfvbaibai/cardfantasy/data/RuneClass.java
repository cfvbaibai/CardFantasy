package cfvbaibai.cardfantasy.data;

public enum RuneClass {
    WATER("水"),
    GROUND("地"),
    FIRE("火"),
    WIND("风");
    
    private String displayName;
    RuneClass(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }
}
