package cfvbaibai.cardfantasy.game;

import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.engine.BattleEngine;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.Rule;

public class PvlEngine extends GameEngine {
    private int timeout;
    
    public PvlEngine(GameUI ui, Rule rule) {
        this(ui, rule, 50);
    }
    
    public PvlEngine(GameUI ui, Rule rule, int timeout) {
        super(ui, rule);
        this.timeout = timeout;
    }

    public PvlGameResult rushBoss(PlayerInfo lilith, int initialHP, PlayerInfo player) {
        int battleCount = 0;
        List<CardInfo> survivors = null;
        while (true) {
            BattleEngine engine = this.createBattleEngine();
            engine.registerPlayers(lilith, player);
            if (survivors != null) {
                engine.importSurvivers(0, survivors);
            }
            if (battleCount == 0) {
                Player lilithPlayer = engine.getStage().getPlayers().get(0);
                CardInfo lilithCard = lilithPlayer.getDeck().toList().get(0);
                lilithCard.setRemainingHP(initialHP);
            }
            GameResult result = engine.playGame();
            ++battleCount;
            if (battleCount > this.timeout) {
                throw new PvlGameTimeoutException();
            }
            if (result.getWinner().getId().equals(player.getId())) {
                return new PvlGameResult(battleCount, initialHP);
            }
            survivors = engine.exportSurvivers(0);
        }
    }
    
    public PvlGameResult clearGuards(PlayerInfo lilith, PlayerInfo player, int targetLilithAliveCardCount) {
        int battleCount = 0;
        List<CardInfo> survivors = null;
        while (true) {
            BattleEngine engine = this.createBattleEngine();
            engine.registerPlayers(lilith, player);
            if (survivors != null) {
                engine.importSurvivers(0, survivors);
            }
            GameResult result = engine.playGame();
            ++battleCount;
            if (battleCount > this.timeout) {
                //throw new PvlGameTimeoutException();
            }
            if (result.getWinner().getId().equals(player.getId())) {
                return getClearGuardsResult(battleCount, result.getLoser());
            }
            Player lilithPlayer = result.getWinner();
            if (isLilithKilled(lilithPlayer)) {
                return getClearGuardsResult(battleCount, lilithPlayer);
            }
            int lilithAliveCardCount = 0;
            lilithAliveCardCount += lilithPlayer.getDeck().size();
            lilithAliveCardCount += lilithPlayer.getHand().size();
            lilithAliveCardCount += lilithPlayer.getField().getAliveCards().size();
            if (lilithAliveCardCount <= targetLilithAliveCardCount) {
                return getClearGuardsResult(battleCount, lilithPlayer);
            }
            survivors = engine.exportSurvivers(0);
        }
    }

    private PvlGameResult getClearGuardsResult(int battleCount, Player lilith) {
        for (CardInfo card : lilith.getField().getAliveCards()) {
            if (card.getRace() == Race.BOSS) {
                return new PvlGameResult(battleCount, card.getMaxHP() - card.getHP());
            }
        }
        for (CardInfo card : lilith.getGrave().toList()) {
            if (card.getRace() == Race.BOSS) {
                return new PvlGameResult(battleCount, card.getRawMaxHP());
            }
        }
        for (CardInfo card : lilith.getOutField().toList()) {
            if (card.getRace() == Race.BOSS) {
                return new PvlGameResult(battleCount, card.getRawMaxHP());
            }
        }
        for (CardInfo card : lilith.getDeck().toList()) {
            if (card.getRace() == Race.BOSS) {
                return new PvlGameResult(battleCount, 0);
            }
        }
        for (CardInfo card : lilith.getHand().toList()) {
            if (card.getRace() == Race.BOSS) {
                return new PvlGameResult(battleCount, 0);
            }
        }
        throw new CardFantasyRuntimeException("Should not reach here.");
    }
    
    private boolean isLilithKilled(Player lilith) {
        for (CardInfo card : lilith.getGrave().toList()) {
            if (card.getRace() == Race.BOSS) {
                return true;
            }
        }
        for (CardInfo card : lilith.getOutField().toList()) {
            if (card.getRace() == Race.BOSS) {
                return true;
            }
        }
        return false;
    }
}
