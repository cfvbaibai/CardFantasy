package cfvbaibai.cardfantasy.officialdata;

public class OfficialReadyRune {
    private OfficialRune rune;
    private int level;
    public OfficialReadyRune(OfficialRune rune, int level) {
        this.rune = rune;
        this.level = level;
    }
    public OfficialReadyRune(OfficialRune rune) {
        this(rune, 4);
    }
    public OfficialRune getRune() {
        return rune;
    }
    public void setRune(OfficialRune rune) {
        this.rune = rune;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public String getRuneName() {
        return this.rune.RuneName;
    }
}
