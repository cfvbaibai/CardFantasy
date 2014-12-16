package cfvbaibai.cardfantasy.test;

import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.engine.GameEngine;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Rule;
import cfvbaibai.cardfantasy.game.PlayerBuilder;

public final class TestGameBuilder {
    public static GameEngine build(PlayerInfo player0, PlayerInfo player1) {
        GameEngine engine = new GameEngine(new TestGameUI(), Rule.getDefault());
        engine.registerPlayers(player0, player1);
        return engine;
    }
    
    public static GameEngine buildEmptyGameForTest(int levelA, int levelB) {
        return TestGameBuilder.build(
            PlayerBuilder.build(true, "PlayerA", levelA, ""),
            PlayerBuilder.build(true, "PlayerB", levelB, ""));
    }
    
    public static GameResult playBossBattle(PlayerInfo player, String bossName) {
        PlayerInfo boss = PlayerBuilder.build(false, "BOSS", 9999, bossName);
        GameEngine engine = new GameEngine(new TestGameUI(), Rule.getBossBattle());
        engine.registerPlayers(boss, player);
        return engine.playGame();
    }
    
    public static GameResult play(PlayerInfo player0, PlayerInfo player1) {
        return build(player0, player1).playGame();
    }
    
    public static GameResult play5v5(String card1, String card2) {
        String suffixA = "";
        String suffixB = "";
        if (card1.equals(card2)) {
            suffixA = "A";
            suffixB = "B";
        }
        return play(PlayerBuilder.build(true, "英雄" + card1 + suffixA, 50, "C" + card1 + "-10*5"),
                PlayerBuilder.build(true, "英雄" + card2 + suffixB, 50, "C" + card2 + "-10*5"));
    }
    
    public static GameResult play5v5withRunes(String card1, RuneData r11, String card2, RuneData r21) {
        return play(PlayerBuilder.build(true, "英雄" + card1, 50, "C" + card1 + "-10*5", "R" + r11.name() + "-4"),
                PlayerBuilder.build(true, "英雄" + card2, 50, "C" + card2 + "-10*5", "R" + r21.name() + "-4"));
    }
    
    public static GameResult play10v10(String card1, String card2) {
        return play10v10(card1, card2, 80);
    }
    
    public static GameResult play10v10(String card1, String card2, int level) {
        return play(PlayerBuilder.build(true, "英雄" + card1, level, "C" + card1 + "-10*10"),
                PlayerBuilder.build(true, "英雄" + card2, level, "C" + card2 + "-10*10"));
    }

    public static GameResult play1v1(String card1, String card2) {
        return play(PlayerBuilder.build(true, "英雄" + card1, 80, "C" + card1 + "-10"),
                PlayerBuilder.build(true, "英雄" + card2, 80, "C" + card2 + "-10"));
    }
    
    public static GameResult play2v2(String card1, String card2) {
        return play(PlayerBuilder.build(true, "A英雄" + card1, 80, "C" + card1 + "-10*2"),
                PlayerBuilder.build(true, "B英雄" + card2, 80, "C" + card2 + "-10*2"));
    }
}
