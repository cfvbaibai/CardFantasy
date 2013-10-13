package cfvbaibai.cardfantasy.engine;

import cfvbaibai.cardfantasy.data.Legion;

public class LegionInfo extends EntityInfo {
    private Legion legion;
    private Player player;
    
    public LegionInfo(Legion legion, Player player) {
        this.legion = legion;
        this.player = player;
    }
    
    public Legion getLegion() {
        return this.legion;
    }
    
    public Player getPlayer() {
        return this.player;
    }

    @Override
    public String getShortDesc() {
        return player.getId() + "的军团";
    }

    @Override
    public CardStatus getStatus() {
        return new CardStatus();
    }

    @Override
    public Player getOwner() {
        return player;
    }
}
