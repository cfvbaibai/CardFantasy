package cfvbaibai.cardfantasy.web;

import cfvbaibai.cardfantasy.engine.Player;

public class PlayerRuntimeInfo extends PlayerInitInfo {

    private int hp;
    
    public PlayerRuntimeInfo(Player player, int playerNumber) {
        super(player, playerNumber);
        this.hp = player.getHP();
    }

    public int getHP() {
        return this.hp;
    }
}
