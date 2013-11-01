package cfvbaibai.cardfantasy.web.animation;

import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.RuneData;

public class EntityDataRuntimeInfo {

    private static final String RUNE_TYPE = "rune";
    private static final String CARD_TYPE = "card";
    
    private String id;
    private String name;
    private String race;
    private int star;
    // rune or card
    private String type;

    public EntityDataRuntimeInfo(CardData data) {
        this.id = data.getId();
        this.name = data.getName();
        this.race = data.getRace().name();
        this.star = data.getStar();
        this.type = CARD_TYPE;
    }

    public EntityDataRuntimeInfo(RuneData data) {
        this.id = null;
        this.name = data.name();
        this.race = data.getRuneClass().name();
        this.star = data.getStar();
        this.type = RUNE_TYPE;
    }

    public String getId() {
        return id;
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

    public String getType() {
        return type;
    }
}
