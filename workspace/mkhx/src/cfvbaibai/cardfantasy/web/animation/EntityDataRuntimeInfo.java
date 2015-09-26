package cfvbaibai.cardfantasy.web.animation;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardSkill;
import cfvbaibai.cardfantasy.data.RuneData;

public class EntityDataRuntimeInfo {

    private static final String RUNE_TYPE = "rune";
    private static final String CARD_TYPE = "card";

    private String id;
    private String wikiId;
    private String name;
    private String race;
    private int star;
    private List<String> skillNames;
    // rune or card
    private String type;

    public EntityDataRuntimeInfo(CardData data) {
        this.id = data.getId();
        this.wikiId = data.getWikiId();
        this.name = data.getName();
        this.race = data.getRace().name();
        this.star = data.getStar();
        this.type = CARD_TYPE;
        this.skillNames = new ArrayList<String>();
        for (CardSkill skill : data.getSkills()) {
            this.skillNames.add(skill.getShortDesc());
        }
    }

    public EntityDataRuntimeInfo(RuneData data) {
        this.id = null;
        this.wikiId = data.getWikiId();
        this.name = data.name();
        this.race = data.getRuneClass().name();
        this.star = data.getStar();
        this.type = RUNE_TYPE;
    }

    public String getId() {
        return id;
    }
    
    public String getWikiId() {
        return wikiId;
    }

    public String getName() {
        return name;
    }

    public String getRace() {
        return race;
    }

    public int getStar() {
        return star;
    }

    public List<String> getSkillNames() {
        return new ArrayList<String>(this.skillNames);
    }

    public String getType() {
        return type;
    }
}
