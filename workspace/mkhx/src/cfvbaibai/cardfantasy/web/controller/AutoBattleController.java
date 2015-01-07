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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.Legion;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.GameEndCause;
import cfvbaibai.cardfantasy.engine.GameEngine;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Rule;
import cfvbaibai.cardfantasy.game.DeckBuilder;
import cfvbaibai.cardfantasy.game.DummyGameUI;
import cfvbaibai.cardfantasy.game.GameResultStat;
import cfvbaibai.cardfantasy.game.MapInfo;
import cfvbaibai.cardfantasy.game.MapStages;
import cfvbaibai.cardfantasy.game.PlayerBuilder;
import cfvbaibai.cardfantasy.game.PveEngine;
import cfvbaibai.cardfantasy.game.PveGameResult;
import cfvbaibai.cardfantasy.game.PveGameResultStat;
import cfvbaibai.cardfantasy.web.ErrorHelper;
import cfvbaibai.cardfantasy.web.OneDimensionDataStat;
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
import cfvbaibai.cardfantasy.web.dao.BossBattleStatEntry;

@Controller
public class AutoBattleController {
    
    @Autowired
    private JsonHandler jsonHandler;
    
    @Autowired
    private UserActionRecorder userActionRecorder;
    
    @Autowired
    private MapStages maps;
    
    @Autowired
    private Logger logger;
    
    @Autowired
    private ErrorHelper errorHelper;
    
    //@Autowired
    //@Qualifier("user-error")
    //private java.util.logging.Logger userErrorLogger;
    
    //@Autowired
    //private CommunicationService service;

    private static GameResultStat play(PlayerInfo p1, PlayerInfo p2, int count, Rule rule) {
        GameResultStat stat = new GameResultStat(p1, p2);
        GameUI ui = new DummyGameUI();
        for (int i = 0; i < count; ++i) {
            stat.addResult(GameEngine.play1v1(ui, rule, p1, p2));
        }
        return stat;
    }

    @RequestMapping(value = "/PlayAuto1MatchGame")
    public void playAuto1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck1") String deck1, @RequestParam("count") int count,
            @RequestParam("deck2") String deck2, @RequestParam("hlv1") int heroLv1, @RequestParam("hlv2") int heroLv2,
            @RequestParam("firstAttack") int firstAttack) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            if (firstAttack != 0 && firstAttack != 1 && firstAttack != -1) {
                throw new IllegalArgumentException("无效的先攻：" + firstAttack);
            }
            logger.info("PlayAuto1MatchGame from " + request.getRemoteAddr() + ":");
            logger.info("FirstAttack = " + firstAttack);
            logger.info("Lv1 = " + heroLv1 + ", Deck1 = " + deck1);
            logger.info("Lv2 = " + heroLv2 + ", Deck2 = " + deck2);
            
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Auto 1Match Game",
                    String.format("Deck1=%s<br />Deck2=%s<br />Lv1=%d, Lv2=%d, FirstAttack=%d",
                            deck1, deck2, heroLv1, heroLv2, firstAttack)));
            
            PlayerInfo player1 = PlayerBuilder.build(true, "玩家1", deck1, heroLv1);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家2", deck2, heroLv2);
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            GameEngine engine = new GameEngine(ui, new Rule(5, 999, firstAttack, false));
            engine.registerPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            writer.print(Utils.getCurrentDateTime() + "<br />" + ui.getAllText());
            logger.info("Winner: " + gameResult.getWinner().getId());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, false));
        }
    }

    @RequestMapping(value = "/SimAuto1MatchGame", headers = "Accept=application/json")
    public void simulateAuto1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck1") String deck1, @RequestParam("count") int count,
            @RequestParam("deck2") String deck2, @RequestParam("hlv1") int heroLv1, @RequestParam("hlv2") int heroLv2,
            @RequestParam("firstAttack") int firstAttack) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        try {
            if (firstAttack != 0 && firstAttack != 1 && firstAttack != -1) {
                throw new IllegalArgumentException("无效的先攻：" + firstAttack);
            }
            logger.info("SimulateAuto1MatchGame from " + request.getRemoteAddr() + ":");
            logger.info("FirstAttack = " + firstAttack);
            logger.info("Lv1 = " + heroLv1 + ", Deck1 = " + deck1);
            logger.info("Lv2 = " + heroLv2 + ", Deck2 = " + deck2);
            
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Simulate Auto 1Match Game",
                    String.format("Deck1=%s<br />Deck2=%s<br />Lv1=%d, Lv2=%d, FirstAttack=%d",
                            deck1, deck2, heroLv1, heroLv2, firstAttack)));
            
            PlayerInfo player1 = PlayerBuilder.build(true, "玩家1", deck1, heroLv1);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家2", deck2, heroLv2);
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            GameEngine engine = new GameEngine(ui, new Rule(5, 999, firstAttack, false));
            engine.registerPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            BattleRecord record = ui.getRecord();
            writer.print(jsonHandler.toJson(record));
            logger.info("Winner: " + gameResult.getWinner().getId());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, true));
        }
    }
    
    @RequestMapping(value = "/PlayAutoMassiveGame")
    public void playAutoMassiveGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck1") String deck1,
            @RequestParam("deck2") String deck2, @RequestParam("hlv1") int heroLv1, @RequestParam("hlv2") int heroLv2,
            @RequestParam("firstAttack") int firstAttack, @RequestParam("count") int count) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            if (firstAttack != 0 && firstAttack != 1 && firstAttack != -1) {
                throw new IllegalArgumentException("无效的先攻：" + firstAttack);
            }
            logger.info("PlayAutoMassiveGame from " + request.getRemoteAddr() + ":");
            logger.info("Count = " + count);
            logger.info("FirstAttack = " + firstAttack);
            logger.info("Lv1 = " + heroLv1 + ", Deck1 = " + deck1);
            logger.info("Lv2 = " + heroLv2 + ", Deck2 = " + deck2);
            
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Auto Massive Game",
                    String.format("Deck1=%s<br />Deck2=%s<br />Lv1=%d, Lv2=%d, FirstAttack=%d, Count=%d",
                            deck1, deck2, heroLv1, heroLv2, firstAttack, count)));
            PlayerInfo player1 = PlayerBuilder.build(true, "玩家1", deck1, heroLv1);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家2", deck2, heroLv2);
            GameResultStat stat = play(player1, player2, count, new Rule(5, 999, firstAttack, false));
            writer.append(Utils.getCurrentDateTime() + "<br />");
            writer.append("<table>");
            writer.append("<tr><td>超时: </td><td>" + stat.getTimeoutCount() + "</td></tr>");
            writer.append("<tr><td>玩家1获胜: </td><td>" + stat.getP1Win() + "</td></tr>");
            writer.append("<tr><td>玩家2获胜: </td><td>" + stat.getP2Win() + "</td></tr>");
            writer.append("</table>");
            logger.info("TO:P1:P2 = " + stat.getTimeoutCount() + ":" + stat.getP1Win() + ":" + stat.getP2Win());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, false));
        }
    }
    
    private CardData getBossGuard() {
        while (true) {
            CardData guard = this.store.getRandomCard();
            if (guard.getStar() < 3) {
                continue;
            }
            if (guard.getRace() != Race.KINGDOM &&
                guard.getRace() != Race.FOREST &&
                guard.getRace() != Race.SAVAGE &&
                guard.getRace() != Race.HELL) {
                continue;
            }
            for (Skill skill : guard.getSkills()) {
                if (skill.getType() == SkillType.回魂 || skill.getType() == SkillType.复活) {
                    continue;
                }
            }
            return guard;
        }
    }
    
    private void addBossGuards(PlayerInfo player) {
        for (int i = 0; i < 9; ++i) {
            CardData bossGuard = getBossGuard();
            player.addCard(new Card(bossGuard, 10, "BOSS" + i));
        }
        StringBuffer sb = new StringBuffer();
        for (Card card : player.getCards()) {
            sb.append(card.getParsableDesc() + ", ");
        }
        logger.info("Boss deck: " + sb.toString());
    }
    
    @RequestMapping(value = "/PlayBoss1MatchGame")
    public void playBoss1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck, @RequestParam("count") int count, @RequestParam("gt") int guardType,
            @RequestParam("hlv") int heroLv, @RequestParam("bn") String bossName, @RequestParam("bk") int buffKingdom,
            @RequestParam("bf") int buffForest, @RequestParam("bs") int buffSavage, @RequestParam("bh") int buffHell) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            logger.info("PlayBoss1MatchGame from " + request.getRemoteAddr() + ":");
            logger.info("Deck = " + deck);
            logger.info(String.format("Hero LV = %d, Boss = %s, Guard Type = %d", heroLv, bossName, guardType));
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Boss 1Match Game",
                    String.format("Deck=%s<br />HeroLV=%d, Boss=%s, GuardType=%d", deck, heroLv, bossName, guardType)));
            PlayerInfo player1 = PlayerBuilder.build(false, "BOSS", bossName, 999999, null);
            if (guardType == 1) {
                addBossGuards(player1);
            }
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, new Legion(buffKingdom, buffForest,
                    buffSavage, buffHell));
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            GameEngine engine = new GameEngine(ui, Rule.getBossBattle());
            engine.registerPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            writer.print(Utils.getCurrentDateTime() + "<br />");
            writer.print("造成伤害：" + gameResult.getDamageToBoss() + "<br />");
            writer.print("------------------ 战斗过程 ------------------<br />");
            writer.print(ui.getAllText());
            logger.info("Winner: " + gameResult.getWinner().getId() + ", Damage to boss: " + gameResult.getDamageToBoss());
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
            PlayerInfo player1 = PlayerBuilder.build(false, "BOSS", bossName, 99999, null);
            if (guardType == 1) {
                addBossGuards(player1);
            }
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, new Legion(buffKingdom, buffForest,
                    buffSavage, buffHell));
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            GameEngine engine = new GameEngine(ui, Rule.getBossBattle());
            engine.registerPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            BattleRecord record = ui.getRecord();
            writer.print(jsonHandler.toJson(record));
            logger.info("Winner: " + gameResult.getWinner().getId() + ", Damage to boss: " + gameResult.getDamageToBoss());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, true));
        }
    }

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
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Boss Massive Game",
                    String.format("Deck=%s<br />HeroLV=%d, Boss=%s, Count=%d, GuardType=%d", deck, heroLv, bossName, count, guardType)));
            PlayerInfo player1 = PlayerBuilder.build(false, "BOSS", bossName, 99999, null);
            if (guardType == 1) {
                addBossGuards(player1);
            }
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, new Legion(buffKingdom, buffForest,
                    buffSavage, buffHell));
            writer.append(Utils.getCurrentDateTime() + "<br />");
            OneDimensionDataStat stat = new OneDimensionDataStat();
            int timeoutCount = 0;
            Rule rule = Rule.getBossBattle();
            GameUI ui = new DummyGameUI();
            GameResult trialResult = GameEngine.play1v1(ui, rule, player1, player2);
            if (trialResult.getCause() == GameEndCause.战斗超时) {
                ++timeoutCount;
            }
            stat.addData(trialResult.getDamageToBoss());
            int gameCount = 5000 / trialResult.getRound();
            if (gameCount <= 1) {
                gameCount = 1;
            }
            writer.append("模拟场次: " + gameCount + "<br />");
            
            int totalCost = 0;
            for (Card card : player2.getCards()) {
                totalCost += card.getCost();
            }
            int coolDown = 60 + totalCost * 2;
            
            if (gameCount > 0) {
                for (int i = 0; i < gameCount - 1; ++i) {
                    GameResult gameResult = GameEngine.play1v1(ui, rule, player1, player2);
                    if (gameResult.getCause() == GameEndCause.战斗超时) {
                        ++timeoutCount;
                    }
                    stat.addData(gameResult.getDamageToBoss());
                }
            }
            
            if (timeoutCount > 0) {
                writer.append("超时次数(大于999回合): " + timeoutCount + "<br />");
                writer.append("您的卡组实在太厉害了！已经超出模拟器的承受能力，结果可能不准确，建议直接实测。<br />");
            }
            
            long averageDamageToBoss = Math.round(stat.getAverage());
            long cvPercentage = Math.round(stat.getCoefficientOfVariation() * 100);
            //int damageToBossPerMinute = averageDamageToBoss * 60 / coolDown;
            long minDamage = Math.round(stat.getMin());
            long maxDamage = Math.round(stat.getMax());
            
            BossBattleStatEntry entry = new BossBattleStatEntry();
            entry.setBossName(bossName);
            entry.setHeroLv(heroLv);
            entry.setBattleCount(gameCount);
            entry.setMinDamage(minDamage);
            entry.setAvgDamage(averageDamageToBoss);
            entry.setMaxDamage(maxDamage);
            String sortedDeck = DeckBuilder.getSortedDeckDesc(player2);
            entry.setSortedDeck(sortedDeck);
            //service.newBossBattleStatEntry(entry);
            
            long testMinute = 99999;
            long testBattleCount = 1 + (60 * testMinute / coolDown);
            long testTotalDamage = testBattleCount * averageDamageToBoss;
            long averageDamagePerMinute = testTotalDamage / testMinute;
            
            writer.append("<table>");
            //result.append("<tr><td>战斗次数: </td><td>" + count + "</td></tr>");
            //result.append("<tr><td>总伤害: </td><td>" + totalDamageToBoss + "</td></tr>");
            //result.append("<tr><td>平均伤害: </td><td>" + averageDamageToBoss + "</td></tr>");
            writer.append("<tr><td>卡组总COST: </td><td>" + totalCost + "</td></tr>");
            writer.append("<tr><td>冷却时间: </td><td>" + coolDown + "</td></tr>");
            writer.append("<tr><td>总体平均每分钟伤害: </td><td>" + averageDamagePerMinute + "</td></tr>");
            writer.append("<tr><td>最小伤害: </td><td>" + minDamage + "</td></tr>");
            writer.append("<tr><td>平均每次伤害: </td><td>" + averageDamageToBoss + "</td></tr>");
            writer.append("<tr><td>最大伤害: </td><td>" + maxDamage + "</td></tr>");
            writer.append("<tr><td>不稳定度: </td><td>" + cvPercentage + "%</td></tr>");
            //result.append("<tr><td>平均每分钟伤害（理想）: </td><td>" + damageToBossPerMinute + "</td></tr>");
            writer.append("<tr><td colspan='2'><table style='text-align: center'><tr style='font-weight: bold'><td>魔神存活</td><td>战斗次数</td><td>总伤害</td><td>平均每分钟伤害</td></tr>");
            for (int i = 1; i <= 20; ++i) {
                int attackCount = 1 + (60 * i / coolDown);
                long totalDamage = attackCount * averageDamageToBoss;
                writer.append("<tr><td>" + i + "分钟</td><td>" + attackCount + "</td><td>" + totalDamage + "</td><td>" + (totalDamage / i) + "</td></tr>");
            }
            writer.append("</table></td></tr>");
            writer.append("</table>");
            logger.info("Average damage to boss: " + averageDamageToBoss);
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
            PlayerInfo player = PlayerBuilder.build(true, "玩家", deck, heroLv);
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            PveEngine engine = new PveEngine(ui, Rule.getDefault(), this.maps);
            PveGameResult gameResult = engine.play(player, map);
            writer.print(Utils.getCurrentDateTime() + "<br />" + ui.getAllText());
            logger.info("Result: " + gameResult.name());
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
                    String.format("Deck=%s<br />Lv=%d, Count=%d, Map=%s",
                            deck, heroLv, count, map)));
            PveEngine engine = new PveEngine(new DummyGameUI(), Rule.getDefault(), this.maps);
            PlayerInfo player = PlayerBuilder.build(true, "玩家", deck, heroLv);
            PveGameResultStat stat = engine.massivePlay(player, map, count);
            writer.append(Utils.getCurrentDateTime() + "<br />");
            writer.append("<table>");
            for (PveGameResult gameResult : PveGameResult.values()) {
                writer.append(String.format("<tr><td>%s: </td><td>%d</td></tr>",
                        gameResult.getDescription(), stat.getStat(gameResult)));
            }
            writer.append("</table>");
            logger.info(String.format("TO:LO:BW:AW:UN = %d:%d:%d:%d:%d",
                    stat.getStat(PveGameResult.TIMEOUT),
                    stat.getStat(PveGameResult.LOSE),
                    stat.getStat(PveGameResult.BASIC_WIN),
                    stat.getStat(PveGameResult.ADVANCED_WIN),
                    stat.getStat(PveGameResult.UNKNOWN)));
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
                entities.add(new EntityDataRuntimeInfo(card));
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
    
    /*
    @RequestMapping(value = "/RecommendBossBattleDeck", headers = "Accept=application/json")
    public void recommendBossBattleDeck(
            HttpServletRequest request, HttpServletResponse response,
            @RequestParam("bossName") String bossName,
            @RequestParam("maxHeroLv") int maxHeroLv) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        try {
            RecommendedBossBattleDecks decks = service.recommendBossBattleDeck(bossName, maxHeroLv);
            writer.print(jsonHandler.toJson(decks));
        } catch (Exception e) {
            writer.print(handleError(e, true));
        }
    }
    */
}
