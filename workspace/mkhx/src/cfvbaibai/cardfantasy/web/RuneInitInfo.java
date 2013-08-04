package cfvbaibai.cardfantasy.web;

import cfvbaibai.cardfantasy.engine.RuneInfo;

public class RuneInitInfo {
    private String name;
    private String type;
    public RuneInitInfo(RuneInfo rune) {
        this.name = rune.getName();
        this.type = rune.getRuneClass();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
