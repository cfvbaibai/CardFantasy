package cfvbaibai.cardfantasy;

import java.util.List;

import cfvbaibai.cardfantasy.game.DummyGameUI;

public abstract class Randomizer {
    private static Randomizer registeredRandomizer;
    public static void registerRandomizer(Randomizer randomizer) {
        registeredRandomizer = randomizer;
    }

    public static Randomizer getRandomizer() {
        if (registeredRandomizer == null) {
            registeredRandomizer = new RealRandomizer();
        }
        return registeredRandomizer;
    }

    public abstract int next(int min, int max);
    public abstract void shuffle(List<?> list);
    public abstract <T> List<T> pickRandom(final List<T> list, int max, boolean excludeNull, List<T> extraExclusion);

    private GameUI ui;
    
    protected Randomizer() {
        this.ui = new DummyGameUI();
    }
    
    public Randomizer setUI(GameUI ui) {
        this.ui = ui;
        return this;
    }
    
    protected GameUI getUI() {
        return this.ui;
    }
    
    /**
     * Return a random number N for [0, 100).
     * 
     * @return
     */
    public boolean roll100(int rate) {
        int dice = next(0, 100);
        if (ui != null) {
            ui.roll100(dice, rate);
        }
        return dice < rate;
    }
}
