package cfvbaibai.cardfantasy.game;

import cfvbaibai.cardfantasy.data.PlayerInfo;

public class PlayerBuilder {

    public static PlayerInfo build(String id, int level, String... descs) {
        DeckStartupInfo deck = DeckBuilder.build(descs);
        return new PlayerInfo(id, level, deck.getRunes(), deck.getCards());
    }
    
    public static PlayerInfo build(String id, String descText, int level) {
        DeckStartupInfo deck =  DeckBuilder.multiBuild(descText);
        return new PlayerInfo(id, level, deck.getRunes(), deck.getCards());
    }
}