package cfvbaibai.cardfantasy.officialdata;

public class OfficialRuneProperty {
    private int id;
    private String name;
    public static String getNameFromId(int id) {
        switch (id) {
        case 1: return "地";
        case 2: return "水";
        case 3: return "风";
        case 4: return "火";
        default: return "未知";
        }
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public OfficialRuneProperty(int id) {
        this.id = id;
        this.name = getNameFromId(id);
    }
}
