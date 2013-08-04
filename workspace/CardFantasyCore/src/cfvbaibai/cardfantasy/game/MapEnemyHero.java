package cfvbaibai.cardfantasy.game;

import java.util.Collection;

import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Rune;

public final class MapEnemyHero extends PlayerInfo {

    private int maxHP;
    public MapEnemyHero(String id, int maxHP, Collection<Rune> runes, Card[] cards) {
        super(id, 0, null, runes, cards);
        this.maxHP = maxHP;
    }
    
    @Override
    public int getMaxHP() {
        return this.maxHP;
    }
}
