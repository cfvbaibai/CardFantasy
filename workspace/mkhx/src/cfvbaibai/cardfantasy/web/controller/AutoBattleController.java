package cfvbaibai.cardfantasy.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cfvbaibai.cardfantasy.Base64Encoder;
import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Compressor;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.LilithCardBuffSkill;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.BattleEngine;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.Rule;
import cfvbaibai.cardfantasy.game.DeckBuilder;
import cfvbaibai.cardfantasy.game.DummyGameUI;
import cfvbaibai.cardfantasy.game.DummyVictoryCondition;
import cfvbaibai.cardfantasy.game.GameResultStat;
import cfvbaibai.cardfantasy.game.LilithDataStore;
import cfvbaibai.cardfantasy.game.MapInfo;
import cfvbaibai.cardfantasy.game.MapStages;
import cfvbaibai.cardfantasy.game.PlayerBuilder;
import cfvbaibai.cardfantasy.game.PveEngine;
import cfvbaibai.cardfantasy.game.PveGameResult;
import cfvbaibai.cardfantasy.game.PvlEngine;
import cfvbaibai.cardfantasy.game.PvlGameTimeoutException;
import cfvbaibai.cardfantasy.game.VictoryCondition;
import cfvbaibai.cardfantasy.game.launcher.ArenaGameResult;
import cfvbaibai.cardfantasy.game.launcher.BossGameResult;
import cfvbaibai.cardfantasy.game.launcher.GameLauncher;
import cfvbaibai.cardfantasy.game.launcher.GameSetup;
import cfvbaibai.cardfantasy.game.launcher.LilithGameResult;
import cfvbaibai.cardfantasy.game.launcher.MapGameResult;
import cfvbaibai.cardfantasy.game.launcher.TrivialBossGameResult;
import cfvbaibai.cardfantasy.web.ErrorHelper;
import cfvbaibai.cardfantasy.web.Utils;
import cfvbaibai.cardfantasy.web.animation.BattleRecord;
import cfvbaibai.cardfantasy.web.animation.EntityDataRuntimeInfo;
import cfvbaibai.cardfantasy.web.animation.SkillTypeRuntimeInfo;
import cfvbaibai.cardfantasy.web.animation.StructuredRecordGameUI;
import cfvbaibai.cardfantasy.web.animation.WebPlainTextGameUI;
import cfvbaibai.cardfantasy.web.beans.JsonHandler;
import cfvbaibai.cardfantasy.web.beans.Logger;
import cfvbaibai.cardfantasy.web.beans.UserAction;
import cfvbaibai.cardfantasy.web.beans.UserActionRecorder;

@Controller
public class AutoBattleController {
    @Autowired
    private JsonHandler jsonHandler;

    @Autowired
    private UserActionRecorder userActionRecorder;

    @Autowired
    private MapStages maps;

    @Autowired
    private LilithDataStore lilithDataStore;

    @Autowired
    private Logger logger;

    @Autowired
    private ErrorHelper errorHelper;

    private static List<Skill> buildBuffsForLilithEvents(String eventCardNames) {
        List<Skill> player2Buffs = new ArrayList<Skill>();
        if (eventCardNames != null) {
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

    private void outputBattleOptions(PrintWriter writer, int firstAttack, int deckOrder, int p1hhpb, int p1catb, int p1chpb, int p2hhpb, int p2catb, int p2chpb, VictoryCondition vc1) {
        if (firstAttack == -1) {
            writer.write("按规则决定先攻");
        } else if (firstAttack == 0) {
            writer.write("玩家1先攻");
        } else {
            writer.write("玩家2先攻");
        }
        writer.write("; ");
        if (deckOrder == 0) {
            writer.write("随机出牌");
        } else {
            writer.write("按指定顺序出牌");
        }
        writer.write("<br />");
        if (p1hhpb != 100) {
            writer.write("玩家1英雄体力调整: " + p1hhpb + "%<br />");
        }
        if (p1catb != 100) {
            writer.write("玩家1卡牌攻击调整: " + p1catb + "%<br />");
        }
        if (p1chpb != 100) {
            writer.write("玩家1卡牌体力调整: " + p1chpb + "%<br />");
        }
        if (p2hhpb != 100) {
            writer.write("玩家2英雄体力调整: " + p2hhpb + "%<br />");
        }
        if (p2catb != 100) {
            writer.write("玩家2卡牌攻击调整: " + p2catb + "%<br />");
        }
        if (p2chpb != 100) {
            writer.write("玩家2卡牌体力调整: " + p2chpb + "%<br />");
        }
        if (vc1 != null && !(vc1 instanceof DummyVictoryCondition)) {
            writer.write("玩家1胜利条件: " + vc1.getDescription() + "<br />");
        }
    }

    @RequestMapping(value = "/PlayAuto1MatchGame")
    public void playAuto1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck1") String deck1, @RequestParam("count") int count,
            @RequestParam("deck2") String deck2, @RequestParam("hlv1") int heroLv1, @RequestParam("hlv2") int heroLv2,
            @RequestParam("fa") int firstAttack, @RequestParam("do") int deckOrder,
            @RequestParam("p1hhpb") int p1HeroHpBuff, @RequestParam("p1catb") int p1CardAtBuff, @RequestParam("p1chpb") int p1CardHpBuff,
            @RequestParam("p2hhpb") int p2HeroHpBuff, @RequestParam("p2catb") int p2CardAtBuff, @RequestParam("p2chpb") int p2CardHpBuff,
            @RequestParam("vc1") String victoryConditionText1
            ) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            logger.info("PlayAuto1MatchGame from " + request.getRemoteAddr() + ":");
            String logMessage = String.format(
                "Deck1=%s<br />Deck2=%s<br />Lv1=%d, Lv2=%d, FirstAttack=%d, DeckOrder=%d, VictoryCondition1=%s",
                deck1, deck2, heroLv1, heroLv2, firstAttack, deckOrder, victoryConditionText1);
            logger.info(logMessage);
            VictoryCondition vc1 = VictoryCondition.parse(victoryConditionText1);
            outputBattleOptions(writer, firstAttack, deckOrder, 
                    p1HeroHpBuff, p1CardAtBuff, p1CardHpBuff, p2HeroHpBuff, p2CardAtBuff, p2CardHpBuff, vc1);
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            GameSetup setup = GameSetup.setupArenaGame(
                    deck1, deck2, heroLv1, heroLv2,
                    p1CardAtBuff, p1CardHpBuff, p1HeroHpBuff,
                    p2CardAtBuff, p2CardHpBuff, p2HeroHpBuff,
                    firstAttack, deckOrder, vc1,
                    1, ui);
            ArenaGameResult result = GameLauncher.playArenaGame(setup);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Auto 1Match Game", logMessage));
            writer.print(Utils.getCurrentDateTime() + "<br />" + result.getDeckValidationResult() + ui.getAllText());
            logger.info("Winner: " + result.getStat().getLastResult().getWinner().getId());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, false));
        }
    }

    @RequestMapping(value = "/SimAuto1MatchGame", headers = "Accept=application/json")
    public void simulateAuto1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck1") String deck1, @RequestParam("count") int count,
            @RequestParam("deck2") String deck2, @RequestParam("hlv1") int heroLv1, @RequestParam("hlv2") int heroLv2,
            @RequestParam("fa") int firstAttack, @RequestParam("do") int deckOrder,
            @RequestParam("p1hhpb") int p1HeroHpBuff, @RequestParam("p1catb") int p1CardAtBuff, @RequestParam("p1chpb") int p1CardHpBuff,
            @RequestParam("p2hhpb") int p2HeroHpBuff, @RequestParam("p2catb") int p2CardAtBuff, @RequestParam("p2chpb") int p2CardHpBuff,
            @RequestParam("vc1") String victoryConditionText1
    ) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        try {
            logger.info("SimulateAuto1MatchGame from " + request.getRemoteAddr() + ":");
            String logMessage = String.format(
                "Deck1=%s<br />Deck2=%s<br />Lv1=%d, Lv2=%d, FirstAttack=%d, DeckOrder=%d, VictoryCondition1=%s",
                deck1, deck2, heroLv1, heroLv2, firstAttack, deckOrder, victoryConditionText1);
            logger.info(logMessage);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Simulate Auto 1Match Game", logMessage));
            VictoryCondition vc1 = VictoryCondition.parse(victoryConditionText1);
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            GameSetup setup = GameSetup.setupArenaGame(
                    deck1, deck2, heroLv1, heroLv2,
                    p1CardAtBuff, p1CardHpBuff, p1HeroHpBuff,
                    p2CardAtBuff, p2CardHpBuff, p2HeroHpBuff,
                    firstAttack, deckOrder, vc1,
                    1, ui);
            ArenaGameResult result = GameLauncher.playArenaGame(setup);
            BattleRecord record = ui.getRecord();
            writer.print(jsonHandler.toJson(record));
            logger.info("Winner: " + result.getStat().getLastResult().getWinner().getId());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, true));
        }
    }
    
    @RequestMapping(value = "/PlayAutoMassiveGame")
    public void playAutoMassiveGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck1") String deck1, @RequestParam("deck2") String deck2,
            @RequestParam("hlv1") int heroLv1, @RequestParam("hlv2") int heroLv2, @RequestParam("count") int count,
            @RequestParam("fa") int firstAttack, @RequestParam("do") int deckOrder,
            @RequestParam("p1hhpb") int p1HeroHpBuff, @RequestParam("p1catb") int p1CardAtBuff, @RequestParam("p1chpb") int p1CardHpBuff,
            @RequestParam("p2hhpb") int p2HeroHpBuff, @RequestParam("p2catb") int p2CardAtBuff, @RequestParam("p2chpb") int p2CardHpBuff,
            @RequestParam("vc1") String victoryConditionText1
    ) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            logger.info("PlayAutoMassiveGame from " + request.getRemoteAddr() + ":");
            String logMessage = String.format("Deck1=%s<br />Deck2=%s<br />Lv1=%d, Lv2=%d, FirstAttack=%d, DeckOrder=%d, Count=%d, VictoryCondition1=%s",
                deck1, deck2, heroLv1, heroLv2, firstAttack, deckOrder, count, victoryConditionText1);
            logger.info(logMessage);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Auto Massive Game", logMessage));
            VictoryCondition vc1 = VictoryCondition.parse(victoryConditionText1);
            outputBattleOptions(writer, firstAttack, deckOrder, p1HeroHpBuff, p1CardAtBuff, p1CardHpBuff, p2HeroHpBuff, p2CardAtBuff, p2CardHpBuff, vc1);
            GameSetup setup = GameSetup.setupArenaGame(
                    deck1, deck2, heroLv1, heroLv2,
                    p1CardAtBuff, p1CardHpBuff, p1HeroHpBuff, p2CardAtBuff, p2CardHpBuff, p2HeroHpBuff,
                    firstAttack, deckOrder, vc1, count, new DummyGameUI());
            ArenaGameResult result = GameLauncher.playArenaGame(setup);
            GameResultStat stat = result.getStat();
            writer.append(Utils.getCurrentDateTime() + "<br />");
            writer.append(result.getDeckValidationResult());
            writer.append("<table>");
            writer.append("<tr><td>超时: </td><td>" + stat.getTimeoutCount() + "</td></tr>");
            writer.append("<tr><td>玩家1获胜: </td><td>" + stat.getP1Win() + "</td></tr>");
            writer.append("<tr><td>玩家2获胜: </td><td>" + stat.getP2Win() + "</td></tr>");
            if (!(vc1 instanceof DummyVictoryCondition)) {
                writer.append("<tr><td>条件符合: </td><td>" + stat.getConditionMet() + "</td></tr>");
            }
            writer.append("</table>");
            logger.info("TO:P1:P2 = " + stat.getTimeoutCount() + ":" + stat.getP1Win() + ":" + stat.getP2Win());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, false));
        }
    }

    @RequestMapping(value = "/PlayBoss1MatchGame")
    public void playBoss1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck, @RequestParam("count") int count, @RequestParam("gt") int guardType,
            @RequestParam("hlv") int heroLv, @RequestParam("bn") String bossName, @RequestParam("bk") int buffKingdom,
            @RequestParam("bf") int buffForest, @RequestParam("bs") int buffSavage, @RequestParam("bh") int buffHell) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            String logMessage = String.format("Deck=%s<br />HeroLV=%d, Boss=%s, GuardType=%d", deck, heroLv, bossName, guardType);
            logger.info("PlayBoss1MatchGame: " + logMessage);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Boss 1Match Game", logMessage));
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            GameSetup setup = GameSetup.setupBossGame(
                    deck, bossName, heroLv, buffKingdom, buffForest, buffSavage, buffHell, guardType, 1, ui);
            BossGameResult result = GameLauncher.playBossGame(setup);
            writer.print(Utils.getCurrentDateTime() + "<br />");
            writer.print("<div style='color: red'>" + result.getValidationResult() + "</div>");
            GameResult detail = result.getLastDetail();
            writer.print("造成伤害：" + detail.getDamageToBoss() + "<br />");
            writer.print("------------------ 战斗过程 ------------------<br />");
            writer.print(ui.getAllText());
            logger.info("Winner: " + detail.getWinner().getId() + ", Damage to boss: " + detail.getDamageToBoss());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, false));
        }
    }

    @RequestMapping(value = "/PlayLilith1MatchGame")
    public void playLilith1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck, @RequestParam("ecg") boolean enableCustomGuards, @RequestParam("cg") String customGuards,
            @RequestParam("cgab") int customGuardsAtBuff, @RequestParam("cghb") int customGuardsHpBuff,
            @RequestParam("count") int count, @RequestParam("gt") int gameType,
            @RequestParam("hlv") int heroLv, @RequestParam("ln") String lilithName,
            @RequestParam("tc") int targetRemainingGuardCount, @RequestParam("rhp") int remainingHP,
            @RequestParam("ec") String eventCardNames
            ) throws IOException {
        PrintWriter writer = response.getWriter();
        WebPlainTextGameUI ui = new WebPlainTextGameUI();
        try {
            String logMessage = String.format("Deck=%s<br />HeroLV=%d, Lilith=%s, GameType=%d, EventCards=%s", deck, heroLv, lilithName, gameType, eventCardNames);
            logger.info("PlayLilith1MatchGame: " + logMessage);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Lilith 1Match Game", logMessage));
            LilithGameResult result = null;
            if (enableCustomGuards && gameType == 0) {
                result = GameLauncher.playCustomLilithGame(
                        deck, lilithName + "," + customGuards, heroLv, customGuardsAtBuff, customGuardsHpBuff,
                        gameType, targetRemainingGuardCount, remainingHP, eventCardNames, 1, ui);
            } else {
                result = GameLauncher.playLilithGame(
                        deck, lilithName, heroLv, gameType, 
                        targetRemainingGuardCount, remainingHP, eventCardNames, 1, ui);
            }

            writer.print(Utils.getCurrentDateTime() + "<br />");
            String resultMessage = String.format("共进行 %f 轮进攻，平均每轮对莉莉丝造成 %f 点伤害<br />",
                result.getAvgBattleCount(), result.getAvgDamageToLilith());
            writer.print(resultMessage);
            writer.print("<div style='color: red'>" + result.getValidationResult() + "</div>");
            writer.print("------------------ 战斗过程 ------------------<br />");
            writer.print(ui.getAllText());
            logger.info(resultMessage);
        } catch (PvlGameTimeoutException e) {
            writer.print("进攻超过最大次数，你的卡组太弱了<br />");
            writer.print("------------------ 战斗过程 ------------------<br />");
            writer.print(ui.getAllText());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, false));
        }
    }

    @RequestMapping(value = "/SimulateBoss1MatchGame", headers = "Accept=application/json")
    public void simulateBoss1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck, @RequestParam("count") int count, @RequestParam("gt") int guardType,
            @RequestParam("hlv") int heroLv, @RequestParam("bn") String bossName, @RequestParam("bk") int buffKingdom,
            @RequestParam("bf") int buffForest, @RequestParam("bs") int buffSavage, @RequestParam("bh") int buffHell) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        try {
            logger.info("SimulateBoss1MatchGame from " + request.getRemoteAddr() + ":");
            logger.info("Deck = " + deck);
            logger.info("Hero LV = " + heroLv + ", Boss = " + bossName);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Simulate Boss 1Match Game",
                    String.format("Deck=%s<br />HeroLV=%d, Boss=%s, GuardType=%d", deck, heroLv, bossName, guardType)));
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            GameSetup setup = GameSetup.setupBossGame(
                    deck, bossName, heroLv, buffKingdom, buffForest, buffSavage, buffHell, guardType, 1, ui);
            BossGameResult result = GameLauncher.playBossGame(setup);

            BattleRecord record = ui.getRecord();
            writer.print(jsonHandler.toJson(record));
            GameResult detail = result.getLastDetail();
            logger.info("Winner: " + detail.getWinner().getId() + ", Damage to boss: " + detail.getDamageToBoss());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, true));
        }
    }

    @RequestMapping(value = "/SimulateLilith1MatchGame", headers = "Accept=application/json")
    public void simulateLilith1MatchGame(HttpServletRequest request, HttpServletResponse response,
        @RequestParam("deck") String deck, @RequestParam("ecg") boolean enableCustomGuards,
        @RequestParam("cg") String customGuards, @RequestParam("cgab") int customGuardsAtBuff, @RequestParam("cghb") int customGuardsHpBuff,
        @RequestParam("count") int count, @RequestParam("gt") int gameType,
        @RequestParam("hlv") int heroLv, @RequestParam("ln") String lilithName,
        @RequestParam("tc") int targetRemainingGuardCount, @RequestParam("rhp") int remainingHP,
        @RequestParam("ec") String eventCardNames
        ) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        try {
            String logMessage = String.format("Deck=%s<br />HeroLV=%d, Lilith=%s, GameType=%d, EventCards=%s", deck, heroLv, lilithName, gameType, eventCardNames);
            logger.info("SimulateLilith1MatchGame: " + logMessage);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Simulate Lilith 1Match Game", logMessage));

            PlayerInfo player1 = null;
            if (enableCustomGuards && gameType == 0) {
                List<Skill> player1Buffs = PvlEngine.getCardBuffs(customGuardsAtBuff, customGuardsHpBuff);
                player1 = PlayerBuilder.build(false, "莉莉丝", lilithName + "," + customGuards, 9999999, player1Buffs, 100);
            } else {
                player1 = PlayerBuilder.buildLilith(lilithDataStore, lilithName, gameType == 0);
            }
            List<Skill> player2Buffs = buildBuffsForLilithEvents(eventCardNames);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, player2Buffs, 100);
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            BattleEngine engine = new BattleEngine(ui, Rule.getDefault());
            engine.registerPlayers(player1, player2);
            if (gameType == 1) {
                Player lilithPlayer = engine.getStage().getPlayers().get(0);
                CardInfo lilithCard = lilithPlayer.getDeck().toList().get(0);
                lilithCard.setRemainingHP(remainingHP);
            }
            engine.playGame();
            BattleRecord record = ui.getRecord();
            writer.print(jsonHandler.toJson(record));
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, true));
        }
    }

    /**
     * 
     * @param request
     * @param response
     * @param deck
     * @param count
     * @param guardType
     * @param heroLv
     * @param bossName
     * @param buffKingdom
     * @param buffForest
     * @param buffSavage
     * @param buffHell
     * @throws IOException
     * Response JSON: BossMassiveGameResult
     */
    @RequestMapping(value = "/PlayBossMassiveGame")
    public void playBossMassiveGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck, @RequestParam("count") int count, @RequestParam("gt") int guardType,
            @RequestParam("hlv") int heroLv, @RequestParam("bn") String bossName, @RequestParam("bk") int buffKingdom,
            @RequestParam("bf") int buffForest, @RequestParam("bs") int buffSavage, @RequestParam("bh") int buffHell
            ) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            logger.info("PlayBossMassiveGame from " + request.getRemoteAddr() + ":");
            logger.info("Deck = " + deck);
            logger.info("Count = " + count + ", Hero LV = " + heroLv + ", Boss = " + bossName);
            response.setContentType("application/json");
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Boss Massive Game",
                    String.format("Deck=%s<br />HeroLV=%d, Boss=%s, Count=%d, GuardType=%d", deck, heroLv, bossName, count, guardType)));
            GameUI ui = new DummyGameUI();
            GameSetup setup = GameSetup.setupBossGame(deck, bossName, heroLv, buffKingdom, buffForest, buffSavage, buffHell, guardType, count, ui);
            BossGameResult result = GameLauncher.playBossGame(setup);
            TrivialBossGameResult resultBean = new TrivialBossGameResult(result);
            resultBean.setValidationResult("<div style='color: red'>" + resultBean.getValidationResult() + "</div>");
            long averageDamageToBoss = Math.round(result.getAvgDamage());
            logger.info("Average damage to boss: " + averageDamageToBoss);
            writer.print(jsonHandler.toJson(resultBean));
        } catch (Exception e) {
            response.setStatus(500);
            writer.print("{'error':'" + e.getMessage() + "'}");
            this.logger.error(e);
        }
    }

    @RequestMapping(value = "/PlayLilithMassiveGame")
    public void playLilithMassiveGame(HttpServletRequest request, HttpServletResponse response,
        @RequestParam("deck") String deck, @RequestParam("ecg") boolean enableCustomGuards, @RequestParam("cg") String customGuards,
        @RequestParam("cgab") int customGuardsAtBuff, @RequestParam("cghb") int customGuardsHpBuff,
        @RequestParam("count") int count, @RequestParam("gt") int gameType,
        @RequestParam("hlv") int heroLv, @RequestParam("ln") String lilithName,
        @RequestParam("tc") int targetRemainingGuardCount, @RequestParam("rhp") int remainingHP,
        @RequestParam("ec") String eventCardNames
        ) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            String logMessage = String.format("Deck=%s<br />HeroLV=%d, Lilith=%s, GameType=%d, EventCards=%s", deck, heroLv, lilithName, gameType, eventCardNames);
            logger.info("PlayLilithMassiveGame: " + logMessage);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Lilith Massive Game", logMessage));

            writer.append(Utils.getCurrentDateTime() + "<br />");
            writer.append("模拟场次: " + count + "<br />");

            try {
                LilithGameResult result = null;
                GameUI ui = new DummyGameUI();
                if (enableCustomGuards && gameType == 0) {
                    result = GameLauncher.playCustomLilithGame(
                            deck, lilithName + "," + customGuards, heroLv, customGuardsAtBuff, customGuardsHpBuff,
                            gameType, targetRemainingGuardCount, remainingHP, eventCardNames, count, ui);
                } else {
                    result = GameLauncher.playLilithGame(
                            deck, lilithName, heroLv, gameType, 
                            targetRemainingGuardCount, remainingHP, eventCardNames, count, ui);
                }
                writer.print("<div style='color: red'>" + result.getValidationResult() + "</div>");
                writer.append("<table>");
                writer.append("<tr><td>平均需要进攻次数: </td><td>" + result.getAvgBattleCount() + "</td></tr>");
                writer.append("<tr><td>不稳定度: </td><td>" + Math.round(result.getCvBattleCount() * 100) + "%</td></tr>");
                writer.append("<tr><td>平均每轮进攻对莉莉丝伤害: </td><td>" + Math.round(result.getAvgDamageToLilith()) + "</td></tr>");
                writer.append("<tr><td>不稳定度: </td><td>" + Math.round(result.getCvDamageToLilith() * 100) + "%</td></tr>");
                writer.append("</td></tr></table>");
            } catch (PvlGameTimeoutException e) {
                writer.append("进攻次数超过最大次数，你的卡组太弱了");
            }
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, false));
        }
    }

    @RequestMapping(value = "/PlayMap1MatchGame")
    public void playMap1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck, @RequestParam("count") int count,
            @RequestParam("hlv") int heroLv, @RequestParam("map") String map) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            logger.info("PlayMap1MatchGame from " + request.getRemoteAddr() + ":");
            logger.info("Deck = " + deck);
            logger.info("Hero LV = " + heroLv + ", Map = " + map);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Map 1Match Game",
                    String.format("Deck=%s<br />HeroLV=%d, Map=%s", deck, heroLv, map)));
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            MapGameResult result = GameLauncher.playMapGame(deck, map, heroLv, 1, ui);
            writer.print(Utils.getCurrentDateTime() + "<br />");
            writer.print("<div style='color: red'>" + result.getValidationResult() + "</div>");
            writer.print(ui.getAllText());
            logger.info("Result: " + result.getLastResultName());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, false));
        }
    }
    
    @RequestMapping(value = "/SimulateMap1MatchGame", headers = "Accept=application/json")
    public void simulateMap1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck, @RequestParam("count") int count,
            @RequestParam("hlv") int heroLv, @RequestParam("map") String map) throws IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        try {
            logger.info("SimulateMap1MatchGame from " + request.getRemoteAddr() + ":");
            logger.info("Deck = " + deck);
            logger.info("Hero LV = " + heroLv + ", Map = " + map);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Simulate Map 1Match Game",
                    String.format("Deck=%s<br />HeroLV=%d, Map=%s", deck, heroLv, map)));
            PlayerInfo player = PlayerBuilder.build(true, "玩家", deck, heroLv);
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            PveEngine engine = new PveEngine(ui, Rule.getDefault(), this.maps);
            PveGameResult gameResult = engine.play(player, map);
            BattleRecord record = ui.getRecord();
            writer.println(jsonHandler.toJson(record));
            logger.info("Result: " + gameResult.name());
        } catch (Exception e) {
            writer.println(errorHelper.handleError(e, true));
        }
    }
    
    @RequestMapping(value = "/PlayMapMassiveGame")
    public void playMapMassiveGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck, @RequestParam("hlv") int heroLv, @RequestParam("map") String map,
            @RequestParam("count") int count) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            logger.info("PlayMapMassiveGame from " + request.getRemoteAddr() + ":");
            logger.info(String.format("Lv = %d, Map = %s, Count = %d", heroLv, map, count));
            logger.info("Deck = " + deck);
            
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Map Massive Game",
                    String.format("Deck=%s<br />Lv=%d, Count=%d, Map=%s", deck, heroLv, count, map)));
            MapGameResult result = GameLauncher.playMapGame(deck, map, heroLv, count, new DummyGameUI());
            writer.append(Utils.getCurrentDateTime() + "<br />");
            writer.print("<div style='color: red'>" + result.getValidationResult() + "</div>");
            writer.append("<table>");
            writer.append(String.format("<tr><td>战斗出错: </td><td>%d</td></tr>", result.getUnknownCount()));
            writer.append(String.format("<tr><td>失败: </td><td>%d</td></tr>", result.getLostCount()));
            writer.append(String.format("<tr><td>战斗超时: </td><td>%d</td></tr>", result.getTimeoutCount()));
            writer.append(String.format("<tr><td>胜利，过关条件符合: </td><td>%d</td></tr>", result.getAdvWinCount()));
            writer.append(String.format("<tr><td>胜利，过关条件不符合: </td><td>%d</td></tr>", result.getWinCount()));
            writer.append("</table>");
            logger.info(String.format("TO:LO:BW:AW:UN = %d:%d:%d:%d:%d",
                    result.getTimeoutCount(),
                    result.getLostCount(),
                    result.getWinCount(),
                    result.getAdvWinCount(),
                    result.getUnknownCount()));
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, false));
        }
    }
    
    @RequestMapping(value = "/GetCardDetail", headers = "Accept=application/json")
    public void getCardDetail(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("playerId") String playerId, @RequestParam("uniqueName") String uniqueName,
            @RequestParam("type") String type) throws IOException {
    }
    
    @Autowired
    private CardDataStore store;
    
    @RequestMapping(value = "/GetDataStore", headers = "Accept=application/json")
    public void getDataStore(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        try {
            logger.info("Getting card data store...");
            Map <String, Object> result = new HashMap <String, Object>();
            List<EntityDataRuntimeInfo> entities = new ArrayList<EntityDataRuntimeInfo>();
            List<CardData> cards = store.getAllCards();
            for (CardData card : cards) {
                if (!card.isMaterial()) {
                    entities.add(new EntityDataRuntimeInfo(card));
                }
            }
            RuneData[] runes = RuneData.values();
            for (RuneData rune : runes) {
                entities.add(new EntityDataRuntimeInfo(rune));
            }
            result.put("entities", entities);

            List<SkillTypeRuntimeInfo> skillList = new ArrayList<SkillTypeRuntimeInfo>(); 
            for (SkillType skillType : SkillType.values()) {
                if (!skillType.containsTag(SkillTag.不可洗炼)) {
                    skillList.add(new SkillTypeRuntimeInfo(skillType));
                }
            }

            Collections.sort(skillList, new Comparator<SkillTypeRuntimeInfo>() {
                private Comparator<Object> comparer = Collator.getInstance(Locale.CHINA);
                @Override
                public int compare(SkillTypeRuntimeInfo arg0, SkillTypeRuntimeInfo arg1) {
                    return comparer.compare(arg0.getName(), arg1.getName());
                }
            });

            result.put("skills", skillList);
            writer.print(jsonHandler.toJson(result));
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, true));
        }
    }
    
    @RequestMapping(value = "/GetMapVictoryCondition", headers = "Accept=application/json")
    public void getMapVictoryCondition(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("map") String map) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        try {
            logger.info("Getting map victory condition: " + map);
            String condition = "";
            MapInfo mapInfo = this.maps.getMap(map);
            if (mapInfo == null) {
                condition = "无效的地图：" + map;
            } else {
                condition = mapInfo.getCondition().getDescription();
            }
            writer.print(jsonHandler.toJson(condition));
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, true));
        }
    }

    @RequestMapping(value = "/CardSkills/{cardName}")
    public void getCardSkills(HttpServletRequest request, HttpServletResponse response,
        @PathVariable("cardName") String cardName) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            logger.info("Getting card skills: " + cardName);
            CardData cardData = this.store.getCard(cardName);
            if (cardData == null) {
                writer.println("无效的卡牌: " + cardName);
                response.setStatus(404);
                return;
            }
            for (Skill skill : cardData.getSkills()) {
                if (skill.getType() != SkillType.无效) {
                    writer.print(skill.getShortDesc());
                }
            }
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, true));
        }
    }
    
    @RequestMapping(value = "/GetMapDeckInfo", headers = "Accept=application/json")
    public void getMapDeckInfo(HttpServletRequest request, HttpServletResponse response,
        @RequestParam("map") String map) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        try {
            logger.info("Getting map stage: " + map);
            String deckInfo = "";
            MapInfo mapInfo = this.maps.getMap(map);
            if (mapInfo == null) {
                deckInfo = "无效的地图：" + map;
            } else {
                deckInfo = mapInfo.getDeckInfo();
            }
            writer.print(jsonHandler.toJson(deckInfo));
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, true));
        }
    }

    @RequestMapping(value = "/Video/{mode}", method = RequestMethod.POST, headers = "Accept=application/json")
    public void convertVideo(HttpServletRequest request, HttpServletResponse response,
        @PathVariable String mode, @RequestParam("videoData") String videoData) throws IOException {
        response.setContentType("plain/text");
        PrintWriter writer = response.getWriter();
        logger.info("Converting video: " + mode);
        if (videoData == null || videoData.length() == 0) {
            throw new CardFantasyRuntimeException("无效的录像数据");
        }
        if (mode.equalsIgnoreCase("compact")) {
            String compacted = Base64Encoder.encode(Compressor.compress(videoData));
            logger.info("Compacted: " + compacted);
            writer.print(compacted);
        } else if (mode.equalsIgnoreCase("decompact")) {
            String decompacted = Compressor.decompress(Base64Encoder.decode(videoData));
            logger.info("Decompacted: " + decompacted);
            writer.print(decompacted);
        }
    }
}
