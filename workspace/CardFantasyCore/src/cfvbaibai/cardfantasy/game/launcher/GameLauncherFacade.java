package cfvbaibai.cardfantasy.game.launcher;

import cfvbaibai.cardfantasy.game.DummyGameUI;
import cfvbaibai.cardfantasy.game.VictoryCondition;

import com.google.gson.Gson;


public final class GameLauncherFacade {
    private static Gson gson = new Gson();
    public static String playArenaGame(
            String deck1, String deck2, int heroLv1, int heroLv2,
            int p1CardAtBuff, int p1CardHpBuff, int p1HeroHpBuff,
            int p2CardAtBuff, int p2CardHpBuff, int p2HeroHpBuff,
            int firstAttack, int deckOrder, String vc1Text, int gameCount) {
        VictoryCondition vc1 = VictoryCondition.parse(vc1Text);
        GameSetup setup = GameSetup.setupArenaGame(
                deck1, deck2, heroLv1, heroLv2,
                p1CardAtBuff, p1CardHpBuff, p1HeroHpBuff, p2CardAtBuff, p2CardHpBuff, p2HeroHpBuff,
                firstAttack, deckOrder, vc1, gameCount, new DummyGameUI());
        ArenaGameResult result = GameLauncher.playArenaGame(setup);
        TrivialArenaGameResult serializableResult = new TrivialArenaGameResult(result);
        String textResult = gson.toJson(serializableResult);
        return textResult;
    }
    
    public static String playBossGame(
        String playerDeck, String bossName, int heroLv,
        int kingdomBuff, int forestBuff, int savageBuff, int hellBuff,
        int guardType, int gameCount) {
        GameSetup setup = GameSetup.setupBossGame(
                playerDeck, bossName, heroLv, kingdomBuff, forestBuff, savageBuff, hellBuff,
                guardType, gameCount, new DummyGameUI());
        BossGameResult result = GameLauncher.playBossGame(setup);
        TrivialBossGameResult serializableResult = new TrivialBossGameResult(result);
        String textResult = gson.toJson(serializableResult);
        return textResult;
    }
    
    public static String playMapGame(String playerDeck, String mapName, int heroLv, int gameCount) {
        MapGameResult result = GameLauncher.playMapGame(playerDeck, mapName, heroLv, gameCount, new DummyGameUI());
        String textResult = gson.toJson(result);
        return textResult;
    }
    
    public static String playLilithGame(String playerDeck, String lilithName, int heroLv,
            int gameType, int remainingGuard, int remainingHp, String eventCardNames, int gameCount) {
        LilithGameResult result = GameLauncher.playLilithGame(
            playerDeck, lilithName, heroLv, gameType, remainingGuard,
            remainingHp, eventCardNames, gameCount, new DummyGameUI());
        String textResult = gson.toJson(result);
        return textResult;
    }
    
    public static String playCustomLilithGame(
            String playerDeck, String lilithDeck, int heroLv, int lilithCardAtBuff, int lilithCardHpBuff,
            int gameType, int remainingGuard, int remainingHp, String eventCardNames, int gameCount) {
        LilithGameResult result = GameLauncher.playCustomLilithGame(
                playerDeck, lilithDeck, heroLv, lilithCardAtBuff, lilithCardHpBuff, gameType, remainingGuard,
                remainingHp, eventCardNames, gameCount, new DummyGameUI());
            String textResult = gson.toJson(result);
            return textResult;
    }
}

class TrivialArenaGameResult {
    public String validationResult;
    public int p1Win;
    public int p2Win;
    public int timeoutCount;
    public TrivialArenaGameResult(ArenaGameResult result) {
        this.validationResult = result.getDeckValidationResult();
        this.p1Win = result.getStat().getP1Win();
        this.p2Win = result.getStat().getP2Win();
        this.timeoutCount = result.getStat().getTimeoutCount();
    }
}