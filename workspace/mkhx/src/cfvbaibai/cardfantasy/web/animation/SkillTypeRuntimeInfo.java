package cfvbaibai.cardfantasy.web.animation;

import cfvbaibai.cardfantasy.data.SkillType;

public class SkillTypeRuntimeInfo {

    public SkillTypeRuntimeInfo(SkillType skillType) {
        this.name = skillType.name();
        this.growable = skillType.isGrowable();
        this.wikiId = skillType.getWikiId();
    }

    private String name;
    private boolean growable;
    private String wikiId;
    
    public String getWikiId() {
        return wikiId;
    }
    
    public String getName() {
        return name;
    }

    public boolean isGrowable() {
        return growable;
    }
}
