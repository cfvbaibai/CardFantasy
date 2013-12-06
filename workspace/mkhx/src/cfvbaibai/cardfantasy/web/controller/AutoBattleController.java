package cfvbaibai.cardfantasy.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.Collator;
import java.text.DateFormat;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cfvbaibai.cardfantasy.CardFantasyUserRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.FeatureTag;
import cfvbaibai.cardfantasy.data.FeatureType;
import cfvbaibai.cardfantasy.data.Legion;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.engine.GameEndCause;
import cfvbaibai.cardfantasy.engine.GameEngine;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Rule;
import cfvbaibai.cardfantasy.game.DummyGameUI;
import cfvbaibai.cardfantasy.game.GameResultStat;
import cfvbaibai.cardfantasy.game.MapInfo;
import cfvbaibai.cardfantasy.game.MapStages;
import cfvbaibai.cardfantasy.game.PlayerBuilder;
import cfvbaibai.cardfantasy.game.PveEngine;
import cfvbaibai.cardfantasy.game.PveGameResult;
import cfvbaibai.cardfantasy.game.PveGameResultStat;
import cfvbaibai.cardfantasy.web.Utils;
import cfvbaibai.cardfantasy.web.animation.BattleRecord;
import cfvbaibai.cardfantasy.web.animation.EntityDataRuntimeInfo;
import cfvbaibai.cardfantasy.web.animation.FeatureTypeRuntimeInfo;
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
    private Logger logger;
    
    @Autowired
    @Qualifier("user-error")
    private java.util.logging.Logger userErrorLogger;
    
    @RequestMapping(value = "/")
    public ModelAndView home(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home");
        this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Visit Home", ""));
        return mv;
    }

    private String handleError(Exception e, boolean isJson) {
        String errorMessage = "";
        logger.info(errorMessage);
        if (e instanceof CardFantasyUserRuntimeException) {
            CardFantasyUserRuntimeException cfure = (CardFantasyUserRuntimeException)e;
            userErrorLogger.severe(cfure.getMessage());
            errorMessage = cfure.getMessage() + cfure.getHelpMessage();
        } else {
            logger.error(e);
            errorMessage = Utils.getAllMessage(e);
        }
        
        String message = String.format("<font color='red'>%s<br />发生错误！<br />%s<br />",
                getCurrentTime(), errorMessage);
        if (isJson) {
            message = "{ \"error\": true, \"message\": \"" + message + "\" }";
        }
        return message;
    }

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
            @RequestParam("deck1") String deck1,
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
            
            PlayerInfo player1 = PlayerBuilder.build("玩家1", deck1, heroLv1);
            PlayerInfo player2 = PlayerBuilder.build("玩家2", deck2, heroLv2);
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            GameEngine engine = new GameEngine(ui, new Rule(5, 999, firstAttack, false));
            engine.RegisterPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            writer.print(getCurrentTime() + "<br />" + ui.getAllText());
            logger.info("Winner: " + gameResult.getWinner().getId());
        } catch (Exception e) {
            writer.print(handleError(e, false));
        }
    }

    @RequestMapping(value = "/SimAuto1MatchGame", headers = "Accept=application/json")
    public void simulateAuto1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck1") String deck1,
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
            
            PlayerInfo player1 = PlayerBuilder.build("玩家1", deck1, heroLv1);
            PlayerInfo player2 = PlayerBuilder.build("玩家2", deck2, heroLv2);
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            GameEngine engine = new GameEngine(ui, new Rule(5, 999, firstAttack, false));
            engine.RegisterPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            BattleRecord record = ui.getRecord();
            writer.print(jsonHandler.toJson(record));
            logger.info("Winner: " + gameResult.getWinner().getId());
        } catch (Exception e) {
            writer.print(handleError(e, true));
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
            PlayerInfo player1 = PlayerBuilder.build("玩家1", deck1, heroLv1);
            PlayerInfo player2 = PlayerBuilder.build("玩家2", deck2, heroLv2);
            GameResultStat stat = play(player1, player2, count, new Rule(5, 999, firstAttack, false));
            writer.append(getCurrentTime() + "<br />");
            writer.append("<table>");
            writer.append("<tr><td>超时: </td><td>" + stat.getTimeoutCount() + "</td></tr>");
            writer.append("<tr><td>玩家1获胜: </td><td>" + stat.getP1Win() + "</td></tr>");
            writer.append("<tr><td>玩家2获胜: </td><td>" + stat.getP2Win() + "</td></tr>");
            writer.append("</table>");
            logger.info("TO:P1:P2 = " + stat.getTimeoutCount() + ":" + stat.getP1Win() + ":" + stat.getP2Win());
        } catch (Exception e) {
            writer.print(handleError(e, false));
        }
    }
    
    @RequestMapping(value = "/PlayBoss1MatchGame")
    public void playBoss1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck,
            @RequestParam("hlv") int heroLv, @RequestParam("bn") String bossName, @RequestParam("bk") int buffKingdom,
            @RequestParam("bf") int buffForest, @RequestParam("bs") int buffSavage, @RequestParam("bh") int buffHell) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            logger.info("PlayBoss1MatchGame from " + request.getRemoteAddr() + ":");
            logger.info("Deck = " + deck);
            logger.info("Hero LV = " + heroLv + ", Boss = " + bossName);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Boss 1Match Game",
                    String.format("Deck=%s<br />HeroLV=%d, Boss=%s", deck, heroLv, bossName)));
            PlayerInfo player1 = PlayerBuilder.build("BOSS", bossName, 99999, null);
            PlayerInfo player2 = PlayerBuilder.build("玩家", deck, heroLv, new Legion(buffKingdom, buffForest,
                    buffSavage, buffHell));
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            GameEngine engine = new GameEngine(ui, Rule.getBossBattle());
            engine.RegisterPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            writer.print(getCurrentTime() + "<br />" + ui.getAllText());
            logger.info("Winner: " + gameResult.getWinner().getId() + ", Damage to boss: " + gameResult.getDamageToBoss());
        } catch (Exception e) {
            writer.print(handleError(e, false));
        }
    }
    
    @RequestMapping(value = "/SimulateBoss1MatchGame", headers = "Accept=application/json")
    public void simulateBoss1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck,
            @RequestParam("hlv") int heroLv, @RequestParam("bn") String bossName, @RequestParam("bk") int buffKingdom,
            @RequestParam("bf") int buffForest, @RequestParam("bs") int buffSavage, @RequestParam("bh") int buffHell) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        try {
            logger.info("SimulateBoss1MatchGame from " + request.getRemoteAddr() + ":");
            logger.info("Deck = " + deck);
            logger.info("Hero LV = " + heroLv + ", Boss = " + bossName);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Simulate Boss 1Match Game",
                    String.format("Deck=%s<br />HeroLV=%d, Boss=%s", deck, heroLv, bossName)));
            PlayerInfo player1 = PlayerBuilder.build("BOSS", bossName, 99999, null);
            PlayerInfo player2 = PlayerBuilder.build("玩家", deck, heroLv, new Legion(buffKingdom, buffForest,
                    buffSavage, buffHell));
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            GameEngine engine = new GameEngine(ui, Rule.getBossBattle());
            engine.RegisterPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            BattleRecord record = ui.getRecord();
            writer.print(jsonHandler.toJson(record));
            logger.info("Winner: " + gameResult.getWinner().getId() + ", Damage to boss: " + gameResult.getDamageToBoss());
        } catch (Exception e) {
            writer.print(handleError(e, true));
        }
    }

    @RequestMapping(value = "/PlayBossMassiveGame")
    public void playBossMassiveGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck,
            @RequestParam("hlv") int heroLv, @RequestParam("bn") String bossName, @RequestParam("bk") int buffKingdom,
            @RequestParam("bf") int buffForest, @RequestParam("bs") int buffSavage, @RequestParam("bh") int buffHell,
            @RequestParam("count") int count) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            logger.info("PlayBossMassiveGame from " + request.getRemoteAddr() + ":");
            logger.info("Deck = " + deck);
            logger.info("Count = " + count + ", Hero LV = " + heroLv + ", Boss = " + bossName);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Boss Massive Game",
                    String.format("Deck=%s<br />HeroLV=%d, Boss=%s, Count=%d", deck, heroLv, bossName, count)));
            PlayerInfo player1 = PlayerBuilder.build("BOSS", bossName, 99999, null);
            PlayerInfo player2 = PlayerBuilder.build("玩家", deck, heroLv, new Legion(buffKingdom, buffForest,
                    buffSavage, buffHell));
            writer.append(getCurrentTime() + "<br />");
            int totalDamageToBoss = 0;
            int timeoutCount = 0;
            Rule rule = Rule.getBossBattle();
            GameUI ui = new DummyGameUI();
            GameResult trialResult = GameEngine.play1v1(ui, rule, player1, player2);
            if (trialResult.getCause() == GameEndCause.战斗超时) {
                ++timeoutCount;
            }
            totalDamageToBoss = trialResult.getDamageToBoss();
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
                    totalDamageToBoss += gameResult.getDamageToBoss();
                }
            }
            
            if (timeoutCount > 0) {
                writer.append("超时次数(大于999回合): " + timeoutCount + "<br />");
                writer.append("您的卡组实在太厉害了！已经超出模拟器的承受能力，结果可能不准确，建议直接实测。<br />");
            }
            
            int averageDamageToBoss = totalDamageToBoss / gameCount;
            //int damageToBossPerMinute = averageDamageToBoss * 60 / coolDown;
            
            writer.append("<table>");
            //result.append("<tr><td>战斗次数: </td><td>" + count + "</td></tr>");
            //result.append("<tr><td>总伤害: </td><td>" + totalDamageToBoss + "</td></tr>");
            //result.append("<tr><td>平均伤害: </td><td>" + averageDamageToBoss + "</td></tr>");
            writer.append("<tr><td>卡组总COST: </td><td>" + totalCost + "</td></tr>");
            writer.append("<tr><td>冷却时间: </td><td>" + coolDown + "秒</td></tr>");
            //result.append("<tr><td>平均每分钟伤害（理想）: </td><td>" + damageToBossPerMinute + "</td></tr>");
            writer.append("<tr><td colspan='2'><table style='text-align: center'><tr style='font-weight: bold'><td>魔神存活</td><td>战斗次数</td><td>总伤害</td><td>平均每分钟伤害</td></tr>");
            for (int i = 1; i <= 20; ++i) {
                int attackCount = 1 + (60 * i / coolDown);
                int totalDamage = attackCount * averageDamageToBoss;
                writer.append("<tr><td>" + i + "分钟</td><td>" + attackCount + "</td><td>" + totalDamage + "</td><td>" + (totalDamage / i) + "</td></tr>");
            }
            writer.append("</table></td></tr>");
            writer.append("</table>");
            logger.info("Average damage to boss: " + averageDamageToBoss);
        } catch (Exception e) {
            writer.print(handleError(e, false));
        }
    }
    
    @RequestMapping(value = "/PlayMap1MatchGame")
    public void playMap1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck,
            @RequestParam("hlv") int heroLv, @RequestParam("map") String map) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            logger.info("PlayMap1MatchGame from " + request.getRemoteAddr() + ":");
            logger.info("Deck = " + deck);
            logger.info("Hero LV = " + heroLv + ", Map = " + map);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Map 1Match Game",
                    String.format("Deck=%s<br />HeroLV=%d, Map=%s", deck, heroLv, map)));
            PlayerInfo player = PlayerBuilder.build("玩家", deck, heroLv);
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            PveEngine engine = new PveEngine(ui, Rule.getDefault(), this.maps);
            PveGameResult gameResult = engine.play(player, map);
            writer.print(getCurrentTime() + "<br />" + ui.getAllText());
            logger.info("Result: " + gameResult.name());
        } catch (Exception e) {
            writer.print(handleError(e, false));
        }
    }
    
    @RequestMapping(value = "/SimulateMap1MatchGame", headers = "Accept=application/json")
    public void simulateMap1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck,
            @RequestParam("hlv") int heroLv, @RequestParam("map") String map) throws IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        try {
            logger.info("SimulateMap1MatchGame from " + request.getRemoteAddr() + ":");
            logger.info("Deck = " + deck);
            logger.info("Hero LV = " + heroLv + ", Map = " + map);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Simulate Map 1Match Game",
                    String.format("Deck=%s<br />HeroLV=%d, Map=%s", deck, heroLv, map)));
            PlayerInfo player = PlayerBuilder.build("玩家", deck, heroLv);
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            PveEngine engine = new PveEngine(ui, Rule.getDefault(), this.maps);
            PveGameResult gameResult = engine.play(player, map);
            BattleRecord record = ui.getRecord();
            writer.println(jsonHandler.toJson(record));
            logger.info("Result: " + gameResult.name());
        } catch (Exception e) {
            writer.println(handleError(e, true));
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
            PlayerInfo player = PlayerBuilder.build("玩家", deck, heroLv);
            PveGameResultStat stat = engine.massivePlay(player, map, count);
            writer.append(getCurrentTime() + "<br />");
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
            writer.print(handleError(e, false));
        }
    }
    
    @RequestMapping(value = "/GetCardDetail", headers = "Accept=application/json")
    public void getCardDetail(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("playerId") String playerId, @RequestParam("uniqueName") String uniqueName,
            @RequestParam("type") String type) throws IOException {
    }
    
    @RequestMapping(value = "/GetDataStore", headers = "Accept=application/json")
    public void getDataStore(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        try {
            logger.info("Getting card data store...");
            CardDataStore store = CardDataStore.loadDefault();
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

            List<FeatureTypeRuntimeInfo> featureList = new ArrayList<FeatureTypeRuntimeInfo>(); 
            for (FeatureType featureType : FeatureType.values()) {
                if (!featureType.containsTag(FeatureTag.不可洗炼)) {
                    featureList.add(new FeatureTypeRuntimeInfo(featureType));
                }
            }
            Collections.sort(featureList, new Comparator<FeatureTypeRuntimeInfo>() {
                private Comparator<Object> comparer = Collator.getInstance(Locale.CHINA);
                @Override
                public int compare(FeatureTypeRuntimeInfo arg0, FeatureTypeRuntimeInfo arg1) {
                    return comparer.compare(arg0.getName(), arg1.getName());
                }
            });
            result.put("features", featureList);
            writer.print(jsonHandler.toJson(result));
        } catch (Exception e) {
            writer.print(handleError(e, true));
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
            writer.print(handleError(e, true));
        }
    }

    private static String getCurrentTime() {
        return "时间: " + DateFormat.getTimeInstance().format(new Date());
    }
}
