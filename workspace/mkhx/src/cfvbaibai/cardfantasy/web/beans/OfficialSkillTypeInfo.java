package cfvbaibai.cardfantasy.web.beans;

public class OfficialSkillTypeInfo {
    private String name;
    private String firstSkillId;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getFirstSkillId() {
        return firstSkillId;
    }
    public void setFirstSkillId(String firstSkillId) {
        this.firstSkillId = firstSkillId;
    }
    public OfficialSkillTypeInfo(String name, String firstSkillId) {
        this.name = name;
        this.firstSkillId = firstSkillId;
    }
}
