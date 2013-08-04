package cfvbaibai.cardfantasy.game;

import cfvbaibai.cardfantasy.data.Legion;
import cfvbaibai.cardfantasy.data.PlayerInfo;

public class PlayerBuilder {
    public static PlayerInfo build(String id, int level, String... descs) {
        return build(id, level, null, descs);
    }

    public static PlayerInfo build(String id, int level, Legion legion, String... descs) {
        DeckStartupInfo deck = DeckBuilder.build(descs);
        return new PlayerInfo(id, level, legion, deck.getRunes(), deck.getCards());
    }
    
    public static PlayerInfo build(String id, String descText, int level) {
        return build(id, descText, level, null);
    }
    
    public static PlayerInfo build(String id, String descText, int level, Legion legion) {
        DeckStartupInfo deck =  DeckBuilder.multiBuild(descText);
        return new PlayerInfo(id, level, legion, deck.getRunes(), deck.getCards());
    }
}