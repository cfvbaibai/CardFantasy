package cfvbaibai.cardfantasy.data;


public enum Race {
    KINGDOM,
    FOREST,
    SAVAGE,
    HELL,
    BOSS,;

    public static Race parse(String displayName) {
        if ("王国".equals(displayName)) {
            return KINGDOM;
        }
        if ("森林".equals(displayName)) {
            return FOREST;
        }
        if ("蛮荒".equals(displayName)) {
            return SAVAGE;
        }
        if ("地狱".equals(displayName)) {
            return HELL;
        }
        if ("魔神".equals(displayName)) {
            return BOSS;
        }
        throw new IllegalArgumentException("Invalid Race: " + displayName);
    }
}
