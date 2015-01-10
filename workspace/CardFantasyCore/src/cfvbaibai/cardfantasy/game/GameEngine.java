package cfvbaibai.cardfantasy.game;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.engine.BattleEngine;
import cfvbaibai.cardfantasy.engine.Rule;

public abstract class GameEngine {

    private GameUI ui;
    private Rule rule;

    protected GameEngine(GameUI ui, Rule rule) {
        this.ui = ui;
        this.rule = rule;
    }

    public GameUI getUI() {
        return this.ui;
    }
    
    public Rule getRule() {
        return this.rule;
    }
    
    public BattleEngine createBattleEngine() {
        return new BattleEngine(this.ui, this.rule);
    }
}