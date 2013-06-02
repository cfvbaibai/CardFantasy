package cfvbaibai.cardfantasy.engine;

public class AllCardsDieSignal extends Throwable {

    private static final long serialVersionUID = -5551478449576686324L;
    private Player player;
    public AllCardsDieSignal(Player player) {
        this.player = player;
    }
    public Player getDeadPlayer() {
        return this.player;
    }
}
