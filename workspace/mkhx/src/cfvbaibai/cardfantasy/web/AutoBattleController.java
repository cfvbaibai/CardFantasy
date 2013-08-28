package cfvbaibai.cardfantasy.web;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cfvbaibai.cardfantasy.NonSerializableStrategy;
import cfvbaibai.cardfantasy.PlayerJsonSerializer;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.Legion;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.engine.GameEngine;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.Rule;
import cfvbaibai.cardfantasy.game.DummyGameUI;
import cfvbaibai.cardfantasy.game.GameResultStat;
import cfvbaibai.cardfantasy.game.PlayerBuilder;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Controller
public class AutoBattleController {

    private Gson gson;
    public AutoBattleController() {
        gson = new GsonBuilder()
        .setExclusionStrategies(new NonSerializableStrategy())
        .registerTypeAdapter(Player.class, new PlayerJsonSerializer())
        .setPrettyPrinting()
        .create();
    }
    
    @RequestMapping(value = "/")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home");
        return mv;
    }

    public static String encodeStr(String str) {
        try {
            return new String(str.getBytes("ISO-8859-1"), "UTF-8").replace('，', ',');
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static String getCurrentDateTime() {
        return DATE_FORMAT.format(new Date());
    }

    private static void log(String message) {
        System.out.println("[" + getCurrentDateTime() + "] " + message);
    }

    private static void logE(Exception e) {
        System.err.print("[" + getCurrentDateTime() + "] ");
        e.printStackTrace();
    }

    private static ResponseEntity<String> handleError(Exception e, boolean isJson) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/html;charset=UTF-8");
        log(e.getMessage());
        logE(e);
        String message = String.format("<font color='red'>%s<br />发生错误！<br />%s<br />", getCurrentTime(),
                e.getMessage());
        if (isJson) {
            message = "{ \"error\": true, \"message\": \"" + message + "\" }";
        }
        return new ResponseEntity<String>(message, responseHeaders, HttpStatus.CREATED);
    }

    private static GameResultStat play(PlayerInfo p1, PlayerInfo p2, int count, Rule rule) {
        GameResultStat stat = new GameResultStat(p1, p2);
        for (int i = 0; i < count; ++i) {
            GameEngine engine = new GameEngine(new DummyGameUI(), rule);
            engine.RegisterPlayers(p1, p2);
            GameResult result = engine.playGame();
            stat.addResult(result);
        }
        return stat;
    }

    @RequestMapping(value = "/PlayAuto1MatchGame")
    public ResponseEntity<String> playAuto1MatchGame(HttpServletRequest request, @RequestParam("deck1") String deck1,
            @RequestParam("deck2") String deck2, @RequestParam("hlv1") int heroLv1, @RequestParam("hlv2") int heroLv2,
            @RequestParam("firstAttack") int firstAttack) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/html;charset=UTF-8");
        try {
            if (firstAttack != 0 && firstAttack != 1 && firstAttack != -1) {
                throw new IllegalArgumentException("无效的先攻：" + firstAttack);
            }
            log("PlayAuto1MatchGame from " + request.getRemoteAddr() + ":");
            log("FirstAttack = " + firstAttack);
            log("Deck1 = " + deck1);
            log("Deck2 = " + deck2);
            PlayerInfo player1 = PlayerBuilder.build("玩家1", deck1, heroLv1);
            PlayerInfo player2 = PlayerBuilder.build("玩家2", deck2, heroLv2);
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            GameEngine engine = new GameEngine(ui, new Rule(5, 100, firstAttack, false));
            engine.RegisterPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            String result = getCurrentTime() + "<br />" + ui.getAllText();
            log("Winner: " + gameResult.getWinner().getId());
            return new ResponseEntity<String>(result, responseHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            return handleError(e, false);
        }
    }

    @RequestMapping(value = "/SimAuto1MatchGame", headers = "Accept=application/json")
    public ResponseEntity<String> simulateAuto1MatchGame(HttpServletRequest request, @RequestParam("deck1") String deck1,
            @RequestParam("deck2") String deck2, @RequestParam("hlv1") int heroLv1, @RequestParam("hlv2") int heroLv2,
            @RequestParam("firstAttack") int firstAttack) {
        log("!!!");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=UTF-8");
        responseHeaders.add("Charset", "UTF-8");
        try {
            if (firstAttack != 0 && firstAttack != 1 && firstAttack != -1) {
                throw new IllegalArgumentException("无效的先攻：" + firstAttack);
            }
            log("SimulateAuto1MatchGame from " + request.getRemoteAddr() + ":");
            log("FirstAttack = " + firstAttack);
            log("Deck1 = " + deck1);
            log("Deck2 = " + deck2);
            PlayerInfo player1 = PlayerBuilder.build("玩家1", deck1, heroLv1);
            PlayerInfo player2 = PlayerBuilder.build("玩家2", deck2, heroLv2);
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            GameEngine engine = new GameEngine(ui, new Rule(5, 100, firstAttack, false));
            engine.RegisterPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            BattleRecord record = ui.getRecord();
            String result = gson.toJson(record);
            log("Winner: " + gameResult.getWinner().getId());
            return new ResponseEntity<String>(result, responseHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            return handleError(e, true);
        }
    }
    
    @RequestMapping(value = "/PlayAutoMassiveGame")
    public ResponseEntity<String> playAutoMassiveGame(HttpServletRequest request, @RequestParam("deck1") String deck1,
            @RequestParam("deck2") String deck2, @RequestParam("hlv1") int heroLv1, @RequestParam("hlv2") int heroLv2,
            @RequestParam("firstAttack") int firstAttack, @RequestParam("count") int count) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/html;charset=UTF-8");
        try {
            if (firstAttack != 0 && firstAttack != 1 && firstAttack != -1) {
                throw new IllegalArgumentException("无效的先攻：" + firstAttack);
            }
            log("PlayAutoMassiveGame from " + request.getRemoteAddr() + ":");
            log("Count = " + count);
            log("FirstAttack = " + firstAttack);
            log("Deck1 = " + deck1);
            log("Deck2 = " + deck2);
            PlayerInfo player1 = PlayerBuilder.build("玩家1", deck1, heroLv1);
            PlayerInfo player2 = PlayerBuilder.build("玩家2", deck2, heroLv2);
            GameResultStat stat = play(player1, player2, count, new Rule(5, 100, firstAttack, false));
            StringBuffer result = new StringBuffer();
            result.append(getCurrentTime() + "<br />");
            result.append("<table>");
            result.append("<tr><td>超时: </td><td>" + stat.getTimeoutCount() + "</td></tr>");
            result.append("<tr><td>玩家1获胜: </td><td>" + stat.getP1Win() + "</td></tr>");
            result.append("<tr><td>玩家2获胜: </td><td>" + stat.getP2Win() + "</td></tr>");
            result.append("</table>");
            log("TO:P1:P2 = " + stat.getTimeoutCount() + ":" + stat.getP1Win() + ":" + stat.getP2Win());
            return new ResponseEntity<String>(result.toString(), responseHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            return handleError(e, false);
        }
    }
    
    @RequestMapping(value = "/PlayBoss1MatchGame")
    public ResponseEntity<String> playBoss1MatchGame(HttpServletRequest request, @RequestParam("deck") String deck,
            @RequestParam("hlv") int heroLv, @RequestParam("bn") String bossName, @RequestParam("bk") int buffKingdom,
            @RequestParam("bf") int buffForest, @RequestParam("bs") int buffSavage, @RequestParam("bh") int buffHell) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/html;charset=UTF-8");
        try {
            log("PlayBoss1MatchGame from " + request.getRemoteAddr() + ":");
            log("Deck = " + deck);
            log("Hero LV = " + heroLv + ", Boss = " + bossName);
            PlayerInfo player1 = PlayerBuilder.build("魔神", bossName, 9999, null);
            PlayerInfo player2 = PlayerBuilder.build("玩家", deck, heroLv, new Legion(buffKingdom, buffForest,
                    buffSavage, buffHell));
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            GameEngine engine = new GameEngine(ui, Rule.getBossBattle());
            engine.RegisterPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            String result = getCurrentTime() + "<br />" + ui.getAllText();
            log("Winner: " + gameResult.getWinner().getId() + ", Damage to boss: " + gameResult.getDamageToBoss());
            return new ResponseEntity<String>(result, responseHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            return handleError(e, false);
        }
    }
    
    @RequestMapping(value = "/SimulateBoss1MatchGame", headers = "Accept=application/json")
    public ResponseEntity<String> simulateBoss1MatchGame(HttpServletRequest request, @RequestParam("deck") String deck,
            @RequestParam("hlv") int heroLv, @RequestParam("bn") String bossName, @RequestParam("bk") int buffKingdom,
            @RequestParam("bf") int buffForest, @RequestParam("bs") int buffSavage, @RequestParam("bh") int buffHell) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "application/json;charset=UTF-8");
        responseHeaders.add("Charset", "UTF-8");
        try {
            log("SimulateBoss1MatchGame from " + request.getRemoteAddr() + ":");
            log("Deck = " + deck);
            log("Hero LV = " + heroLv + ", Boss = " + bossName);
            PlayerInfo player1 = PlayerBuilder.build("魔神", bossName, 9999, null);
            PlayerInfo player2 = PlayerBuilder.build("玩家", deck, heroLv, new Legion(buffKingdom, buffForest,
                    buffSavage, buffHell));
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            GameEngine engine = new GameEngine(ui, Rule.getBossBattle());
            engine.RegisterPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            BattleRecord record = ui.getRecord();
            String result = gson.toJson(record);
            log("Winner: " + gameResult.getWinner().getId() + ", Damage to boss: " + gameResult.getDamageToBoss());
            return new ResponseEntity<String>(result, responseHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            return handleError(e, true);
        }
    }

    @RequestMapping(value = "/PlayBossMassiveGame")
    public ResponseEntity<String> playBossMassiveGame(HttpServletRequest request, @RequestParam("deck") String deck,
            @RequestParam("hlv") int heroLv, @RequestParam("bn") String bossName, @RequestParam("bk") int buffKingdom,
            @RequestParam("bf") int buffForest, @RequestParam("bs") int buffSavage, @RequestParam("bh") int buffHell,
            @RequestParam("count") int count) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("Content-Type", "text/html;charset=UTF-8");
        try {
            log("PlayBossMassiveGame from " + request.getRemoteAddr() + ":");
            log("Deck = " + deck);
            log("Count = " + count + ", Hero LV = " + heroLv + ", Boss = " + bossName);
            PlayerInfo player1 = PlayerBuilder.build("魔神", bossName, 9999, null);
            PlayerInfo player2 = PlayerBuilder.build("玩家", deck, heroLv, new Legion(buffKingdom, buffForest,
                    buffSavage, buffHell));
            int totalCost = 0;
            for (Card card : player2.getCards()) {
                totalCost += card.getCost();
            }
            int coolDown = 60 + totalCost * 2;
            int totalDamageToBoss = 0;
            for (int i = 0; i < count; ++i) {
                GameEngine engine = new GameEngine(new DummyGameUI(), Rule.getBossBattle());
                engine.RegisterPlayers(player1, player2);
                GameResult gameResult = engine.playGame();
                totalDamageToBoss += gameResult.getDamageToBoss();
            }
            StringBuffer result = new StringBuffer();
            int averageDamageToBoss = totalDamageToBoss / count;
            //int damageToBossPerMinute = averageDamageToBoss * 60 / coolDown;
            result.append(getCurrentTime() + "<br />");
            result.append("<table>");
            //result.append("<tr><td>战斗次数: </td><td>" + count + "</td></tr>");
            //result.append("<tr><td>总伤害: </td><td>" + totalDamageToBoss + "</td></tr>");
            //result.append("<tr><td>平均伤害: </td><td>" + averageDamageToBoss + "</td></tr>");
            result.append("<tr><td>卡组总COST: </td><td>" + totalCost + "</td></tr>");
            result.append("<tr><td>冷却时间: </td><td>" + coolDown + "秒</td></tr>");
            //result.append("<tr><td>平均每分钟伤害（理想）: </td><td>" + damageToBossPerMinute + "</td></tr>");
            result.append("<tr><td colspan='2'><table style='text-align: center'><tr style='font-weight: bold'><td>魔神存活</td><td>战斗次数</td><td>总伤害</td><td>平均每分钟伤害</td></tr>");
            for (int i = 1; i <= 20; ++i) {
                int attackCount = 1 + (60 * i / coolDown);
                int totalDamage = attackCount * averageDamageToBoss;
                result.append("<tr><td>" + i + "分钟</td><td>" + attackCount + "</td><td>" + totalDamage + "</td><td>" + (totalDamage / i) + "</td></tr>");
            }
            result.append("</table></td></tr>");
            result.append("</table>");
            log("Average damage to boss: " + averageDamageToBoss);
            return new ResponseEntity<String>(result.toString(), responseHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            return handleError(e, false);
        }
    }

    private static String getCurrentTime() {
        return "时间: " + DateFormat.getTimeInstance().format(new Date());
    }
}
