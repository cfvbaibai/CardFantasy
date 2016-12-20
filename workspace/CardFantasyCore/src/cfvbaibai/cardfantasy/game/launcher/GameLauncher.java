package cfvbaibai.cardfantasy.game.launcher;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.CardFantasyUserRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.OneDimensionDataStat;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.LilithCardBuffSkill;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.BattleEngine;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.GameEndCause;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.Rule;
import cfvbaibai.cardfantasy.game.DeckBuilder;
import cfvbaibai.cardfantasy.game.GameResultStat;
import cfvbaibai.cardfantasy.game.LilithDataStore;
import cfvbaibai.cardfantasy.game.LilithStartupInfo;
import cfvbaibai.cardfantasy.game.MapStages;
import cfvbaibai.cardfantasy.game.PlayerBuilder;
import cfvbaibai.cardfantasy.game.PveEngine;
import cfvbaibai.cardfantasy.game.PveGameResult;
import cfvbaibai.cardfantasy.game.PveGameResultStat;
import cfvbaibai.cardfantasy.game.PvlEngine;
import cfvbaibai.cardfantasy.game.PvlGameResult;
import cfvbaibai.cardfantasy.game.VictoryCondition;

public final class GameLauncher {
    private GameLauncher() {
    }

    private static String getDeckValidationResult(PlayerInfo player1, PlayerInfo player2) {
        String result = "";
        if (player1 != null) {
            try {
                BattleEngine.validateDeck(player1);
            } catch (CardFantasyUserRuntimeException e) {
                result += "[" + e.getMessage() + "]";
            }
        }
        if (player2 != null) {
            try {
                BattleEngine.validateDeck(player2);
            } catch (CardFantasyUserRuntimeException e) {
                result += "[" + e.getMessage() + "]";
            }
        }
        return result;
    }

    private static GameStartupInfo initGame(GameSetup setup) {
        GameStartupInfo gsi = new GameStartupInfo();
        gsi.setUi(setup.getUi());
        VictoryCondition vc1 = setup.getVictoryCondition1();
        PlayerInfo player1 = PlayerBuilder.build(
                setup.getHeroLv1() != 0, setup.getName1(), setup.getDeck1(), setup.getHeroLv1(), setup.getBuffs1(), setup.getHeroHpBuff1());
        gsi.setP1(player1);
        PlayerInfo player2 = PlayerBuilder.build(
                setup.getHeroLv2() != 0, setup.getName2(), setup.getDeck2(), setup.getHeroLv2(), setup.getBuffs2(), setup.getHeroHpBuff2());
        gsi.setP2(player2);
        Rule rule = new Rule(5, 999, setup.getFirstAttack(), setup.getDeckOrder(), setup.isBossBattle(), vc1);
        gsi.setRule(rule);
        gsi.setValidationResult(getDeckValidationResult(setup.isBossBattle() ? null : player1, player2));
        return gsi;
    }

    public static ArenaGameResult playArenaGame(GameSetup setup) {
        try {
            GameStartupInfo gsi = initGame(setup);
            GameResultStat stat = new GameResultStat(gsi.getP1(), gsi.getP2(), gsi.getRule());
            for (int i = 0; i < setup.getGameCount(); ++i) {
                stat.addResult(BattleEngine.play1v1(setup.getUi(), gsi.getRule(), gsi.getP1(), gsi.getP2()));
            }
    
            ArenaGameResult result = new ArenaGameResult();
            result.setPlayer1(gsi.getP1());
            result.setPlayer2(gsi.getP2());
            result.setGameCount(setup.getGameCount());
            result.setStat(stat);
            result.setDeckValidationResult(gsi.getValidationResult());
            return result;
        } catch (Exception e) {
            throw new CardFantasyRuntimeException(e.getMessage() + "卡组1: " + setup.getDeck1() + "\r\n卡组2: " + setup.getDeck2(), e);
        }
    }
    
    private static List<Skill> buildBuffsForLilithEvents(String eventCardNames) {
        List<Skill> player2Buffs = new ArrayList<Skill>();
        if (eventCardNames != null && !eventCardNames.isEmpty()) {
            String[] eventCardNameArray = DeckBuilder.splitDescsText(eventCardNames);
            for (String eventCardName : eventCardNameArray) {
                CardData cardData = CardDataStore.loadDefault().getCard(eventCardName);
                if (cardData == null) {
                    throw new CardFantasyRuntimeException("无效的活动卡牌：" + eventCardName);
                }
                int level = 0;
                switch (cardData.getStar()) {
                    case 3: level = 50; break;
                    case 4: level = 100; break;
                    case 5: level = 200; break;
                    default: throw new CardFantasyRuntimeException("无效的活动卡牌：" + eventCardName);
                }
                player2Buffs.add(new LilithCardBuffSkill(SkillType.原始体力调整, level, eventCardName));
                player2Buffs.add(new LilithCardBuffSkill(SkillType.原始攻击调整, level, eventCardName));
            }
        }
        return player2Buffs;
    }

    public static LilithGameResult playCustomLilithGame(
            String playerDeck, String lilithDeck, int playerHeroLv, int lilithCardAtBuff, int lilithCardHpBuff,
            int gameType, int remainingGuard, int remainingHp, String eventCardNames, int gameCount, GameUI ui) {
        List<Skill> player1Buffs = PvlEngine.getCardBuffs(lilithCardAtBuff, lilithCardHpBuff);
        PlayerInfo player1 = PlayerBuilder.build(false, "莉莉丝", lilithDeck, 9999999, player1Buffs, 100); 
        List<Skill> player2Buffs = buildBuffsForLilithEvents(eventCardNames);
        PlayerInfo player2 = PlayerBuilder.build(true, "玩家", playerDeck, playerHeroLv, player2Buffs, 100);
        String validationResult = getDeckValidationResult(null, player2);
        OneDimensionDataStat statBattleCount = new OneDimensionDataStat();
        OneDimensionDataStat statDamageToLilith = new OneDimensionDataStat();
        if (gameCount == -1) {
            BattleEngine battleEngine = new BattleEngine(ui, Rule.getDefault());
            battleEngine.registerPlayers(player1, player2);
            if (gameType == 1) {
                Player lilithPlayer = battleEngine.getStage().getPlayers().get(0);
                CardInfo lilithCard = lilithPlayer.getDeck().toList().get(0);
                lilithCard.setRemainingHP(remainingHp);
            }
            battleEngine.playGame();
            return null;
        }
        for (int i = 0; i < gameCount; ++i) {
            PvlEngine engine = new PvlEngine(ui, Rule.getDefault());
            PvlGameResult pvlGameResult = null;
            if (gameType == 0) {
                pvlGameResult = engine.clearGuards(player1, player2, remainingGuard);
            } else {
                pvlGameResult = engine.rushBoss(player1, remainingHp, player2);
            }
            statBattleCount.addData(pvlGameResult.getBattleCount());
            statDamageToLilith.addData(pvlGameResult.getAvgDamageToLilith());
        }
        LilithGameResult result = new LilithGameResult();
        result.setAvgBattleCount(statBattleCount.getAverage());
        result.setAvgDamageToLilith(statDamageToLilith.getAverage());
        result.setCvBattleCount(statBattleCount.getCoefficientOfVariation());
        result.setCvDamageToLilith(statDamageToLilith.getCoefficientOfVariation());
        result.setValidationResult(validationResult);
        return result;
    }

    public static LilithGameResult playLilithGame(String playerDeck, String lilithName, int heroLv,
            int gameType, int remainingGuard, int remainingHp, String eventCardNames, int gameCount, GameUI ui) {
        LilithStartupInfo lsi = LilithDataStore.loadDefault().getStartupInfo(lilithName);
        String lilithDeck = lsi.getDeckDescsText(gameType == 0);
        return playCustomLilithGame(playerDeck, lilithDeck, heroLv, lsi.getCardAtBuff(), lsi.getCardHpBuff(),
                gameType, remainingGuard, remainingHp, eventCardNames, gameCount, ui);
    }

    public static MapGameResult playMapGame(String playerDeck, String mapName, int heroLv, int gameCount, GameUI ui) {
        PveEngine engine = new PveEngine(ui, MapStages.loadDefault());
        PlayerInfo player = PlayerBuilder.build(true, "玩家", playerDeck, heroLv);
        MapGameResult result = new MapGameResult();
        PveGameResultStat stat = engine.massivePlay(player, mapName, gameCount);
        result.setTimeoutCount(stat.getStat(PveGameResult.TIMEOUT));
        result.setLostCount(stat.getStat(PveGameResult.LOSE));
        result.setWinCount(stat.getStat(PveGameResult.BASIC_WIN));
        result.setAdvWinCount(stat.getStat(PveGameResult.ADVANCED_WIN));
        result.setUnknownCount(stat.getStat(PveGameResult.UNKNOWN));
        result.setValidationResult(getDeckValidationResult(null, player));
        return result;
    }

    public static BossGameResult playBossGame(GameSetup setup) {
        OneDimensionDataStat stat = new OneDimensionDataStat();
        GameStartupInfo gsi = initGame(setup);
        int totalCostForCoolDown = 0;
        for (Card card : gsi.getP2().getCards()) {
            totalCostForCoolDown += card.getBaseCost();
        }
        int coolDown = 60 + totalCostForCoolDown * 2;
        int totalCost = 0;
        for (Card card : gsi.getP2().getCards()) {
            totalCost += card.getCost();
        }
        int timeoutCount = 0;
        GameResult trialResult = BattleEngine.play1v1(setup.getUi(), gsi.getRule(), gsi.getP1(), gsi.getP2());
        if (trialResult.getCause() == GameEndCause.战斗超时) {
            ++timeoutCount;
        }
        stat.addData(trialResult.getDamageToBoss());
        if (setup.getGameCount() == 1) {
            BossGameResult result = new BossGameResult(gsi.getValidationResult(), coolDown, totalCost, timeoutCount, stat, trialResult);
            result.setLastDetail(trialResult);
            return result;
        }
        int gameRound = trialResult.getRound();
        
        GameResult lastDetail = trialResult;
        if (setup.getGameCount() > 0) {
            for (int i = 0; i < 99; ++i) {
                lastDetail = BattleEngine.play1v1(setup.getUi(), gsi.getRule(), gsi.getP1(), gsi.getP2());
                if (lastDetail.getCause() == GameEndCause.战斗超时) {
                    ++timeoutCount;
                }                            
                int damageToBoss = lastDetail.getDamageToBoss();
                if (damageToBoss < 0) {
                    damageToBoss = 0;
                }
                stat.addData(damageToBoss);
                gameRound += lastDetail.getRound();
            }
            
            // To avoid long boss battle from taking too much system resources by
            // doing 100 trial games and calculate the game count out of the time the trial game takes.
            int gameCount = 1000000 / gameRound;
            if (gameCount < 100) {
                gameCount = 100;
            }
            if (gameCount > 1000) {
                gameCount = 1000;
            }
            
            for (int i = 0; i < gameCount - 100; ++i) {
                lastDetail = BattleEngine.play1v1(setup.getUi(), gsi.getRule(), gsi.getP1(), gsi.getP2());
                if (lastDetail.getCause() == GameEndCause.战斗超时) {
                    ++timeoutCount;
                }                            
                int damageToBoss = lastDetail.getDamageToBoss();
                if (damageToBoss < 0) {
                    damageToBoss = 0;
                }
                stat.addData(damageToBoss);
            }
        }

        BossGameResult result = new BossGameResult(gsi.getValidationResult(), coolDown, totalCost, timeoutCount, stat, lastDetail);
        return result;
    }
}

class GameStartupInfo {
    private GameUI ui;
    private Rule rule;
    private PlayerInfo p1;
    private PlayerInfo p2;
    private String validationResult;
    public String getValidationResult() {
        return validationResult;
    }
    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
    }
    public GameUI getUi() {
        return ui;
    }
    public void setUi(GameUI ui) {
        this.ui = ui;
    }
    public Rule getRule() {
        return rule;
    }
    public void setRule(Rule rule) {
        this.rule = rule;
    }
    public PlayerInfo getP1() {
        return p1;
    }
    public void setP1(PlayerInfo p1) {
        this.p1 = p1;
    }
    public PlayerInfo getP2() {
        return p2;
    }
    public void setP2(PlayerInfo p2) {
        this.p2 = p2;
    }
    
}