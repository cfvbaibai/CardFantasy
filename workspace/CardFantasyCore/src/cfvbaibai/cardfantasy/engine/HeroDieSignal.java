package cfvbaibai.cardfantasy.engine;

public class HeroDieSignal extends Throwable {

    private static final long serialVersionUID = -2702115142660771838L;
    private Player player;
    public Player getDeadPlayer() {
        return player;
    }
    public HeroDieSignal(Player player) {
        this.player = player;
    }

}
