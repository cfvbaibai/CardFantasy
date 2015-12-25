package cfvbaibai.cardfantasy.officialdata;

public class OfficialRace {
    private String name;
    private int id;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public OfficialRace(int id, String name) {
        this.name = name;
        this.id = id;
    }
}
