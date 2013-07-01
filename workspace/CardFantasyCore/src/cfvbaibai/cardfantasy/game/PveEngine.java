package cfvbaibai.cardfantasy.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.engine.GameEngine;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Rule;

public class PveEngine {
    private static MapStages maps;
    private GameUI ui;
    private Rule rule;

    public PveEngine(GameUI ui, Rule rule) {
        this.ui = ui;
        this.rule = rule;
    }

    public PveGameResult play(PlayerInfo player, String mapId) {
        if (maps == null) {
            maps = new MapStages();
        }
        ui.showMessage("Loading map " + mapId + "...");
        MapInfo map = maps.getMap(mapId);
        if (map == null) {
            throw new CardFantasyRuntimeException("Cannot find map: " + mapId);
        }
        ui.showMessage("Setting up game...");
        GameEngine engine = new GameEngine(ui, rule);
        engine.RegisterPlayers(player, map.getEnemyHero());
        ui.showMessage("Playing game...");
        GameResult result = engine.playGame();
        PveGameResult gameResult = null;
        try {
            if (result.getWinner().getId().equals(player.getId())) {
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
            ui.showMessage("Game result: " + gameResult);
        }
    }

    public PveGameResultStat massivePlay(PlayerInfo player, String mapId, int count) {
        ui.showMessage("Play " + count + " on " + mapId);
        PveGameResultStat stat = new PveGameResultStat();
        for (int i = 0; i < count; ++i) {
            stat.addResult(play(player, mapId));
        }
        return stat;
    }

    public List<DeckEvaluation> optimizeDeck(int runeCount, int cardCount, String mapId, int heroLevel,
            int resultCount, String... descs) {
        ui.showMessage("Optimize deck for map: " + mapId);
        DeckStartupInfo deck = DeckBuilder.build(descs);
        List<DeckStartupInfo> decks = deck.generateCombinations(runeCount, cardCount);
        System.out.println(decks.size() + " combinations found!");
        List<DeckEvaluation> evals = new ArrayList<DeckEvaluation>();
        for (int i = 0; i < decks.size(); ++i) {
            System.out.println(String.format("Processing deck: %d / %d", i, decks.size()));
            DeckStartupInfo currentDeck = decks.get(i);
            PlayerInfo player = new PlayerInfo("ME", heroLevel, currentDeck.getRunes(), currentDeck.getCards());
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
