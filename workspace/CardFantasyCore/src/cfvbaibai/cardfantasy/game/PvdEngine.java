package cfvbaibai.cardfantasy.game;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.PlayerCardBuffSkill;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.BattleEngine;
import cfvbaibai.cardfantasy.engine.GameEndCause;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Rule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PvdEngine extends GameEngine {
    protected DungeonsStages dungeons;
    public PvdEngine(GameUI ui, DungeonsStages dungeons) {
        super(ui, Rule.getMapBattle());
        this.dungeons = dungeons;
    }

    public PveGameResult play(PlayerInfo player, String mapId,Rule rule,int p1HeroHpBuff,int p1CardAtBuff,int p1CardHpBuff) {
        GameUI ui = this.getUI();
        ui.showMessage("加载地图" + mapId + "...");
        MapInfo map = dungeons.getDungeons(mapId);
        if (map == null) {
            throw new CardFantasyRuntimeException("无法找到地图: " + mapId);
        }
        rule.setCondition(map.getCondition());
        ui.showMessage("启动战斗引擎...");
        List<Skill> p1CardBuffs = new ArrayList<Skill>();
        if (p1CardAtBuff != 100) {
            p1CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始攻击调整, p1CardAtBuff - 100));
        }
        if (p1CardHpBuff != 100) {
            p1CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始体力调整, p1CardHpBuff - 100));
        }
        PlayerInfo player2 = PlayerBuilder.build(true, "地下城", map.getDeckInfo(), 135,p1CardBuffs,p1HeroHpBuff);
        BattleEngine engine = new BattleEngine(ui, rule);
        engine.registerPlayers(player2, player);
        ui.showMessage("战斗开始...");
        GameResult result = engine.playGame();
        PveGameResult gameResult = null;
        try {
            if (result.getCause() == GameEndCause.战斗超时) {
                gameResult = PveGameResult.TIMEOUT;
            } else if (result.getWinner().getId().equals(player.getId())) {
                if (rule.getCondition().meetCriteria(result)) {
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

    public PveGameResultStat massivePlay(PlayerInfo player, String mapId, int count,Rule rule,int p1HeroHpBuff,int p1CardAtBuff,int p1CardHpBuff) {
        this.getUI().showMessage("Play " + count + " on " + mapId);
        PveGameResultStat stat = new PveGameResultStat();
        for (int i = 0; i < count; ++i) {
            stat.addResult(play(player, mapId,rule,p1HeroHpBuff,p1CardAtBuff,p1CardHpBuff));
        }
        return stat;
    }

    public List<DeckEvaluation> optimizeDeck(int p1HeroHpBuff,int p1CardAtBuff,int p1CardHpBuff,int runeCount, int cardCount, String mapId, int heroLevel,Rule rule,
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
            PveGameResultStat stat = massivePlay(player, mapId, 100,rule,p1HeroHpBuff,p1CardAtBuff,p1CardHpBuff);
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
