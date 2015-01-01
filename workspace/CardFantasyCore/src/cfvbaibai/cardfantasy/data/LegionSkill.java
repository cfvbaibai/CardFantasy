package cfvbaibai.cardfantasy.data;

public class LegionSkill extends Skill {

    public static LegionSkill create(Legion legion, Race race) {
        if (legion == null) {
            throw new IllegalArgumentException("legion cannot be null!");
        }
        int level = legion.getBuffLevel(race);
        switch (race) {
        case KINGDOM: return new LegionSkill(FeatureType.军团王国之力, level);
        case FOREST: return new LegionSkill(FeatureType.军团森林之力, level);
        case SAVAGE: return new LegionSkill(FeatureType.军团蛮荒之力, level);
        case HELL: return new LegionSkill(FeatureType.军团地狱之力, level);
        case BOSS: return new LegionSkill(FeatureType.军团魔神之力, 0);
        case MOE: return new LegionSkill(FeatureType.军团萌货之力, 0);
        default: throw new IllegalArgumentException("Unknown race: " + race); 
        }
    }

    private LegionSkill(FeatureType type, int level) {
        super(type, level);
    }

}
