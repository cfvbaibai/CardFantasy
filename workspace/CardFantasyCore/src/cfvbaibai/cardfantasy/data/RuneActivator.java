package cfvbaibai.cardfantasy.data;

public class RuneActivator {
    private RuneActivationType type;
    private int threshold;
    private Race race;
    private boolean checkEnemy;
    public RuneActivator(RuneActivationType type, int threshold, Race race, boolean checkEnemy) {
        super();
        this.type = type;
        this.threshold = threshold;
        this.race = race;
        this.checkEnemy = checkEnemy;
    }
    public RuneActivationType getType() {
        return type;
    }
    public int getThreshold() {
        return threshold;
    }
    public Race getRace() {
        return race;
    }
    public boolean shouldCheckEnemy() {
        return this.checkEnemy;
    }
    public static RuneActivator enemyHeroHP(int threshold) {
        return new RuneActivator(RuneActivationType.HeroHP, threshold, null, true);
    }
    public static RuneActivator myHeroHP(int threshold) {
        return new RuneActivator(RuneActivationType.HeroHP, threshold, null, false);
    }
    public static RuneActivator enemyField(int threshold, Race race) {
        return new RuneActivator(RuneActivationType.Field, threshold, race, true);
    }
    public static RuneActivator myField(int threshold, Race race) {
        return new RuneActivator(RuneActivationType.Field, threshold, race, false);
    }
    public static RuneActivator enemyGrave(int threshold, Race race) {
        return new RuneActivator(RuneActivationType.Grave, threshold, race, true);
    }
    public static RuneActivator myGrave(int threshold, Race race) {
        return new RuneActivator(RuneActivationType.Grave, threshold, race, false);
    }
    public static RuneActivator enemyHand(int threshold, Race race) {
        return new RuneActivator(RuneActivationType.Hand, threshold, race, true);
    }
    public static RuneActivator myHand(int threshold, Race race) {
        return new RuneActivator(RuneActivationType.Hand, threshold, race, false);
    }
    public static RuneActivator round(int threshold) {
        return new RuneActivator(RuneActivationType.Round, threshold, null, true);
    }
}
