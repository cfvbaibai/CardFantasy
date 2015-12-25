package cfvbaibai.cardfantasy.officialdata;

public class OfficialProperty {
    private int id;
    private String name;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public OfficialProperty(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
