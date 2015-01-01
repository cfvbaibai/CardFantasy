package cfvbaibai.cardfantasy.data;

public class LegionFeature extends Skill {

    public static LegionFeature create(Legion legion, Race race) {
        if (legion == null) {
            throw new IllegalArgumentException("legion cannot be null!");
        }
        int level = legion.getBuffLevel(race);
        switch (race) {
        case KINGDOM: return new LegionFeature(FeatureType.军团王国之力, level);
        case FOREST: return new LegionFeature(FeatureType.军团森林之力, level);
        case SAVAGE: return new LegionFeature(FeatureType.军团蛮荒之力, level);
        case HELL: return new LegionFeature(FeatureType.军团地狱之力, level);
        case BOSS: return new LegionFeature(FeatureType.军团魔神之力, 0);
        case MOE: return new LegionFeature(FeatureType.军团萌货之力, 0);
        default: throw new IllegalArgumentException("Unknown race: " + race); 
        }
    }

    private LegionFeature(FeatureType type, int level) {
        super(type, level);
    }

}
