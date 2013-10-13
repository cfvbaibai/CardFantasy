package cfvbaibai.cardfantasy.data;

public class LegionFeature extends Feature {

    public static LegionFeature create(Legion legion, Race race) {
        if (legion == null) {
            throw new IllegalArgumentException("legion cannot be null!");
        }
        int level = legion.getBuffLevel(race);
        switch (race) {
        case 王国: return new LegionFeature(FeatureType.军团王国之力, level);
        case 森林: return new LegionFeature(FeatureType.军团森林之力, level);
        case 蛮荒: return new LegionFeature(FeatureType.军团蛮荒之力, level);
        case 地狱: return new LegionFeature(FeatureType.军团地狱之力, level);
        case 魔神: return new LegionFeature(FeatureType.军团魔神之力, 0);
        default: throw new IllegalArgumentException("Unknown race: " + race); 
        }
    }

    private LegionFeature(FeatureType type, int level) {
        super(type, level);
    }

}
