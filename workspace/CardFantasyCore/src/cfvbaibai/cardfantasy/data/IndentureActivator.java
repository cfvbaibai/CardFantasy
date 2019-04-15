package cfvbaibai.cardfantasy.data;

public class IndentureActivator {
    private RuneActivationType type;
    private int threshold;
    private Race race;
    private boolean checkEnemy;
    public IndentureActivator(RuneActivationType type, int threshold, Race race, boolean checkEnemy) {
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
    public static IndentureActivator enemyHeroHP(int threshold) {
        return new IndentureActivator(RuneActivationType.HeroHP, threshold, null, true);
    }
    public static IndentureActivator myHeroHPLess(int threshold) {
        return new IndentureActivator(RuneActivationType.HeroHPLess, threshold, null, false);
    }
    public static IndentureActivator myHeroHPMore(int threshold) {
        return new IndentureActivator(RuneActivationType.HeroHpMore, threshold, null, false);
    }
    public static IndentureActivator enemyField(int threshold, Race race) {
        return new IndentureActivator(RuneActivationType.Field, threshold, race, true);
    }
    public static IndentureActivator myField(int threshold, Race race) {
        return new IndentureActivator(RuneActivationType.Field, threshold, race, false);
    }
    public static IndentureActivator enemyGrave(int threshold, Race race) {
        return new IndentureActivator(RuneActivationType.Grave, threshold, race, true);
    }
    public static IndentureActivator myGrave(int threshold, Race race) {
        return new IndentureActivator(RuneActivationType.Grave, threshold, race, false);
    }
    public static IndentureActivator enemyHand(int threshold, Race race) {
        return new IndentureActivator(RuneActivationType.Hand, threshold, race, true);
    }
    public static IndentureActivator myHand(int threshold, Race race) {
        return new IndentureActivator(RuneActivationType.Hand, threshold, race, false);
    }
    public static IndentureActivator myHandLess(int threshold, Race race) {
        return new IndentureActivator(RuneActivationType.HandLess, threshold, race, false);
    }
    public static IndentureActivator roundLess(int threshold) {
        return new IndentureActivator(RuneActivationType.RoundLess, threshold, null, true);
    }
    public static IndentureActivator roundMore(int threshold) {
        return new IndentureActivator(RuneActivationType.RoundMore, threshold, null, true);
    }
    public static IndentureActivator myDeck(int threshold, Race race) {
        return new IndentureActivator(RuneActivationType.Deck, threshold, race, false);
    }
    public static IndentureActivator enemyDeck(int threshold, Race race) {
        return new IndentureActivator(RuneActivationType.Deck, threshold, race, true);
    }
    public static IndentureActivator fieldDiff(int threshold) {
        return new IndentureActivator(RuneActivationType.FieldDiff, threshold, null, true /* checkEnemy is actually ignored */);
    }
    public static IndentureActivator doubleOrRace(int threshold) {
        return new IndentureActivator(RuneActivationType.DobleOrRace, 0, null, false );
    }
    public static IndentureActivator doubleRace(int threshold) {
        return new IndentureActivator(RuneActivationType.DoubleRace, 0, null, false );
    }
    public static IndentureActivator fourRace(int threshold) {
        return new IndentureActivator(RuneActivationType.FourRace, 0, null, false );
    }
}
