package cfvbaibai.cardfantasy.engine;

public class HeroDieSignal extends Throwable {

    private Player player;
    public Player getDeadPlayer() {
        return player;
    }
    public HeroDieSignal(Player player) {
        this.player = player;
    }

}
