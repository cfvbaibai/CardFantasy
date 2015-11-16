package cfvbaibai.cardfantasy.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.engine.BattleEngine;
import cfvbaibai.cardfantasy.engine.GameEndCause;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Rule;
import cfvbaibai.cardfantasy.game.launcher.GameLauncher;

public class PveEngine extends GameEngine {
    protected MapStages maps;
    public PveEngine(GameUI ui, Rule rule, MapStages maps) {
        super(ui, rule);
        this.maps = maps;
    }

    public PveGameResult play(PlayerInfo player, String mapId) {
        GameUI ui = this.getUI();
        ui.showMessage("加载地图" + mapId + "...");
        MapInfo map = maps.getMap(mapId);
        if (map == null) {
            throw new CardFantasyRuntimeException("无法找到地图: " + mapId);
        }
        ui.showMessage("启动战斗引擎...");
        BattleEngine engine = new BattleEngine(ui, this.getRule());
        engine.registerPlayers(map.getEnemyHero(), player);
        ui.showMessage("战斗开始...");
        GameResult result = engine.playGame();
        PveGameResult gameResult = null;
        try {
            if (result.getCause() == GameEndCause.战斗超时) {
                gameResult = PveGameResult.TIMEOUT;
            } else if (result.getWinner().getId().equals(player.getId())) {
                if (map.getCondition().meetCriteria(result)) {
                    gameResult = PveGameResult.ADVANCED_WIN;
                } else {
                    gameResult = PveGameResult.BASIC_WIN;
                }
            } else {
                gameResult = PveGameResult.LOSE;
            }
            return gameResult;
        } finally {
            ui.mapStageResult(gameResult);
        }
    }

    public PveGameResultStat massivePlay(PlayerInfo player, String mapId, int count) {
        this.getUI().showMessage("Play " + count + " on " + mapId);
        
        class PveGameBattle implements Callable<PveGameResult> {
            @Override
            public PveGameResult call() {
                return play(player, mapId);
            }
        }
        ExecutorService execSvc = GameLauncher.getExecutorService();
        PveGameResultStat stat = new PveGameResultStat();
        List<PveGameBattle> battles = new ArrayList<>();
        for (int i = 0; i < count; ++i) {
            battles.add(new PveGameBattle());
        }
        List<Future<PveGameResult>> results;
        try {
            results = execSvc.invokeAll(battles);
            for (Future<PveGameResult> futureResult : results) {
                PveGameResult result = futureResult.get();
                stat.addResult(result);
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new CardFantasyRuntimeException("计算被打断或发生错误!", e);
        } finally {
            execSvc.shutdown();
        }

        return stat;
    }

    public List<DeckEvaluation> optimizeDeck(int runeCount, int cardCount, String mapId, int heroLevel,
            int resultCount, String... descs) {
        this.getUI().showMessage("Optimize deck for map: " + mapId);
        DeckStartupInfo deck = DeckBuilder.build(descs);
        List<DeckStartupInfo> decks = deck.generateCombinations(runeCount, cardCount);
        System.out.println(decks.size() + " combinations found!");
        List<DeckEvaluation> evals = new ArrayList<DeckEvaluation>();
        for (int i = 0; i < decks.size(); ++i) {
            System.out.println(String.format("Processing deck: %d / %d", i, decks.size()));
            DeckStartupInfo currentDeck = decks.get(i);
            PlayerInfo player = new PlayerInfo(true, "ME", heroLevel, null, 100, currentDeck.getRunes(), currentDeck.getCards());
            PveGameResultStat stat = massivePlay(player, mapId, 100);
            evals.add(new DeckEvaluation(stat, currentDeck));
        }
        System.out.println();
        Collections.sort(evals);

        System.out.println("Size of evals: " + evals.size());
        if (resultCount >= evals.size() || resultCount < 0) {
            return new ArrayList<DeckEvaluation>(evals);
        }

        List<DeckEvaluation> result = new ArrayList<DeckEvaluation>(resultCount);
        for (DeckEvaluation eval : evals) {
            if (result.size() == resultCount) {
                break;
            }
            result.add(eval);
        }
        return result;
    }
}
