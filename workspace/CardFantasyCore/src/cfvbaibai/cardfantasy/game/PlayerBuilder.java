package cfvbaibai.cardfantasy.game;

import java.util.List;

import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Skill;

public class PlayerBuilder {
    public static PlayerInfo build(boolean isNormalPlayer, String id, int level, String... descs) {
        return build(isNormalPlayer, id, level, null, descs);
    }

    public static PlayerInfo build(boolean isNormalPlayer, String id, int level, List<Skill> cardBuffs, String... descs) {
        DeckStartupInfo deck = DeckBuilder.build(descs);
        return new PlayerInfo(isNormalPlayer, id, level, cardBuffs, deck.getRunes(), deck.getCards());
    }
    
    public static PlayerInfo build(boolean isNormalPlayer, String id, String descText, int level) {
        return build(isNormalPlayer, id, descText, level, null);
    }
    
    public static PlayerInfo build(boolean isNormalPlayer, String id, String descText, int level, List<Skill> cardBuffs) {
        DeckStartupInfo deck =  DeckBuilder.multiBuild(descText);
        return new PlayerInfo(isNormalPlayer, id, level, cardBuffs, deck.getRunes(), deck.getCards());
    }
}