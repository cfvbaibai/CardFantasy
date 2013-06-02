package cfvbaibai.cardfantasy.test;

import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.engine.GameEngine;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Rule;

public class GameBuilder {
    public static GameEngine build(PlayerInfo player0, PlayerInfo player1) {
        GameEngine engine = new GameEngine(new TestGameUI(), new Rule(3, 50));
        engine.RegisterPlayers(player0, player1);
        return engine;
    }
    
    public static GameResult play(PlayerInfo player0, PlayerInfo player1) {
        return build(player0, player1).playGame();
    }
}
