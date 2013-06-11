package cfvbaibai.cardfantasy.test;

import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.engine.GameEngine;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Rule;

public class GameBuilder {
    public static GameEngine build(PlayerInfo player0, PlayerInfo player1) {
        GameEngine engine = new GameEngine(new TestGameUI(), new Rule(6, 50));
        engine.RegisterPlayers(player0, player1);
        return engine;
    }

    public static GameResult play(PlayerInfo player0, PlayerInfo player1) {
        return build(player0, player1).playGame();
    }

    public static GameResult play5v5(String card1, String card2) {
        return GameBuilder.play(PlayerBuilder.build("гЂал" + card1, 50, "C" + card1 + "-10*5"),
                PlayerBuilder.build("гЂал" + card2, 50, "C" + card2 + "-10*5"));
    }

    public static GameResult play5v5withRunes(String card1, RuneData r11, String card2, RuneData r21) {
        return GameBuilder.play(PlayerBuilder.build("гЂал" + card1, 50, "C" + card1 + "-10*5", "R" + r11.name() + "-4"),
                PlayerBuilder.build("гЂал" + card2, 50, "C" + card2 + "-10*5", "R" + r21.name() + "-4"));
    }
}
