package cfvbaibai.cardfantasy.officialdata;

public class OfficialSkillCategory {
    public int id;
    public String name;
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public OfficialSkillCategory() {
        
    }
    public OfficialSkillCategory(int id) {
        this.id = id;
        this.name = getCategoryNameFromId(id);
    }
    public static String getCategoryNameFromId(int categoryId) {
        switch (categoryId) {
        case 1: return "攻击";
        case 2: return "魔法";
        case 3: return "防御";
        case 4: return "治疗";
        case 5: return "辅助";
        default: return "未知";
        }
    }
}
