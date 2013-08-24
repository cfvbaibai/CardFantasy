package cfvbaibai.cardfantasy.web;

import cfvbaibai.cardfantasy.engine.Player;

public class PlayerRuntimeInfo {
    private String id;
    private int level;
    private int maxHP;
    private int number;
    private int hp;

    public PlayerRuntimeInfo(Player player, int playerNumber) {
        this.id = player.getId();
        this.level = player.getLevel();
        this.maxHP = player.getMaxHP();
        this.hp = player.getHP();
        this.number = playerNumber;
    }

    public String getId() {
        return id;
    }

    public int getLevel() {
        return level;
    }

    public int getMaxHP() {
        return maxHP;
    }

    public int getNumber() {
        return number;
    }

    public int getHp() {
        return hp;
    }

    public int getHP() {
        return this.hp;
    }
}
