package cfvbaibai.cardfantasy.engine;

public class AllCardsDieSignal extends Throwable {
    private Player player;
    public AllCardsDieSignal(Player player) {
        this.player = player;
    }
    public Player getDeadPlayer() {
        return this.player;
    }
}
