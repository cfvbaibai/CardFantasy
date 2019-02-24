package cfvbaibai.cardfantasy.game;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Skill;

public class PlayerBuilder {
    public static PlayerInfo build(boolean isNormalPlayer, String id, int level, String... descs) {
        return build(isNormalPlayer, id, level, null, 100, descs);
    }

    public static PlayerInfo build(boolean isNormalPlayer, String id, int level, List<Skill> cardBuffs, int heroHpAdj, String... descs) {
        DeckStartupInfo deck = DeckBuilder.build(descs);
        return new PlayerInfo(isNormalPlayer, id, level, cardBuffs, heroHpAdj, deck.getRunes(), deck.getIndentures(), deck.getCards());
    }

    public static PlayerInfo build(boolean isNormalPlayer, String id, String descText, int level) {
        return build(isNormalPlayer, id, descText, level, null, 100);
    }

    public static PlayerInfo build(boolean isNormalPlayer, String id, String descText, int level, List<Skill> cardBuffs, int heroHpAdj) {
        DeckStartupInfo deck = DeckBuilder.multiBuild(descText);
        return new PlayerInfo(isNormalPlayer, id, level, cardBuffs, heroHpAdj, deck.getRunes(), deck.getIndentures(), deck.getCards());
    }

    public static PlayerInfo buildLilith(LilithDataStore lds, String bossId, boolean withGuards) {
        LilithStartupInfo lsi = lds.getStartupInfo(bossId);
        if (lsi == null) {
            throw new CardFantasyRuntimeException("Invalid Lilith ID: " + bossId);
        }
        List<Skill> buffs = PvlEngine.getCardBuffs(lsi.getCardAtBuff(), lsi.getCardHpBuff());
        PlayerInfo playerInfo = PlayerBuilder.build(false, bossId, 99999, buffs, 100, lsi.getDeckDescs(withGuards));
        return playerInfo;
    }
}