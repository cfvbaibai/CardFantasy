package cfvbaibai.cardfantasy.data;


public enum Race {
    KINGDOM("王国"),
    FOREST("森林"),
    SAVAGE("蛮荒"),
    HELL("地狱"),
    BOSS("魔神"),
    MOE("萌货");
    
    private String displayName;
    Race(String displayName) {
        this.displayName = displayName;
    }

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
        if ("萌货".equals(displayName)) {
            return MOE;
        }
        throw new IllegalArgumentException("Invalid Race: " + displayName);
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public static Race parse(int raceIndex) {
        Race[] races = Race.values();
        if (raceIndex < 0 || raceIndex > races.length) {
            throw new IllegalArgumentException("Invalid race index: " + raceIndex);
        }
        return races[raceIndex];
    }
}
