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
import cfvbaibai.cardfantasy.CardFantasyUserRuntimeException;
import cfvbaibai.cardfantasy.Compressor;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.OneDimensionDataStat;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.LilithCardBuffSkill;
import cfvbaibai.cardfantasy.data.PlayerCardBuffSkill;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.engine.BattleEngine;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.GameEndCause;
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
import cfvbaibai.cardfantasy.game.PveGameResultStat;
import cfvbaibai.cardfantasy.game.PvlEngine;
import cfvbaibai.cardfantasy.game.PvlGameResult;
import cfvbaibai.cardfantasy.game.PvlGameTimeoutException;
import cfvbaibai.cardfantasy.game.SkillBuilder;
import cfvbaibai.cardfantasy.game.VictoryCondition;
import cfvbaibai.cardfantasy.web.ErrorHelper;
import cfvbaibai.cardfantasy.web.Utils;
import cfvbaibai.cardfantasy.web.animation.BattleRecord;
import cfvbaibai.cardfantasy.web.animation.EntityDataRuntimeInfo;
import cfvbaibai.cardfantasy.web.animation.SkillTypeRuntimeInfo;
import cfvbaibai.cardfantasy.web.animation.StructuredRecordGameUI;
import cfvbaibai.cardfantasy.web.animation.WebPlainTextGameUI;
import cfvbaibai.cardfantasy.web.beans.BossMassiveGameResult;
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

    private static GameResultStat play(PlayerInfo p1, PlayerInfo p2, int count, Rule rule) {
        GameResultStat stat = new GameResultStat(p1, p2, rule);
        GameUI ui = new DummyGameUI();
        for (int i = 0; i < count; ++i) {
            stat.addResult(BattleEngine.play1v1(ui, rule, p1, p2));
        }
        return stat;
    }

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
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Auto 1Match Game", logMessage));
            outputBattleOptions(writer, firstAttack, deckOrder, p1HeroHpBuff, p1CardAtBuff, p1CardHpBuff, p2HeroHpBuff, p2CardAtBuff, p2CardHpBuff, vc1);
            List<Skill> p1CardBuffs = new ArrayList<Skill>();
            if (p1CardAtBuff != 100) {
                p1CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始攻击调整, p1CardAtBuff - 100));
            }
            if (p1CardHpBuff != 100) {
                p1CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始体力调整, p1CardHpBuff - 100));
            }
            PlayerInfo player1 = PlayerBuilder.build(heroLv1 != 0, "玩家1", deck1, heroLv1, p1CardBuffs, p1HeroHpBuff);
            List<Skill> p2CardBuffs = new ArrayList<Skill>();
            if (p2CardAtBuff != 100) {
                p2CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始攻击调整, p2CardAtBuff - 100));
            }
            if (p2CardHpBuff != 100) {
                p2CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始体力调整, p2CardHpBuff - 100));
            }
            PlayerInfo player2 = PlayerBuilder.build(heroLv2 != 0, "玩家2", deck2, heroLv2, p2CardBuffs, p2HeroHpBuff);
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            BattleEngine engine = new BattleEngine(ui, new Rule(5, 999, firstAttack, deckOrder, false, vc1));
            engine.registerPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            writer.print(Utils.getCurrentDateTime() + "<br />" + getDeckValidationResult(player1, player2) + ui.getAllText());
            logger.info("Winner: " + gameResult.getWinner().getId());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, false));
        }
    }

    private String getDeckValidationResult(PlayerInfo player1, PlayerInfo player2) {
        String result = "";
        if (player1 != null) {
            try {
                BattleEngine.validateDeck(player1);
            } catch (CardFantasyUserRuntimeException e) {
                result += "<div style='color: red'>" + e.getMessage() + "</div>";
            }
        }
        if (player2 != null) {
            try {
                BattleEngine.validateDeck(player2);
            } catch (CardFantasyUserRuntimeException e) {
                result += "<div style='color: red'>" + e.getMessage() + "</div>";
            }
        }
        return result;
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

            List<Skill> p1CardBuffs = new ArrayList<Skill>();
            if (p1CardAtBuff != 100) {
                p1CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始攻击调整, p1CardAtBuff - 100));
            }
            if (p1CardHpBuff != 100) {
                p1CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始体力调整, p1CardHpBuff - 100));
            }
            PlayerInfo player1 = PlayerBuilder.build(heroLv1 != 0, "玩家1", deck1, heroLv1, p1CardBuffs, p1HeroHpBuff);
            List<Skill> p2CardBuffs = new ArrayList<Skill>();
            if (p2CardAtBuff != 100) {
                p2CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始攻击调整, p2CardAtBuff - 100));
            }
            if (p2CardHpBuff != 100) {
                p2CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始体力调整, p2CardHpBuff - 100));
            }
            PlayerInfo player2 = PlayerBuilder.build(heroLv2 != 0, "玩家2", deck2, heroLv2, p2CardBuffs, p2HeroHpBuff);
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            BattleEngine engine = new BattleEngine(ui, new Rule(5, 999, firstAttack, deckOrder, false, VictoryCondition.parse(victoryConditionText1)));
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
            List<Skill> p1CardBuffs = new ArrayList<Skill>();
            if (p1CardAtBuff != 100) {
                p1CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始攻击调整, p1CardAtBuff - 100));
            }
            if (p1CardHpBuff != 100) {
                p1CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始体力调整, p1CardHpBuff - 100));
            }
            PlayerInfo player1 = PlayerBuilder.build(heroLv1 != 0, "玩家1", deck1, heroLv1, p1CardBuffs, p1HeroHpBuff);
            List<Skill> p2CardBuffs = new ArrayList<Skill>();
            if (p2CardAtBuff != 100) {
                p2CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始攻击调整, p2CardAtBuff - 100));
            }
            if (p2CardHpBuff != 100) {
                p2CardBuffs.add(new PlayerCardBuffSkill(SkillType.原始体力调整, p2CardHpBuff - 100));
            }
            PlayerInfo player2 = PlayerBuilder.build(heroLv2 != 0, "玩家2", deck2, heroLv2, p2CardBuffs, p2HeroHpBuff);
            GameResultStat stat = play(player1, player2, count, new Rule(5, 999, firstAttack, deckOrder, false, vc1));
            writer.append(Utils.getCurrentDateTime() + "<br />");
            writer.append(this.getDeckValidationResult(player1, player2));
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

    private CardData getBossGuard(String[] bossGuardCandidates) {
        Randomizer random = Randomizer.getRandomizer();
        int guardNameIndex = random.next(0, bossGuardCandidates.length);
        String guardName = bossGuardCandidates[guardNameIndex];
        CardData guard = this.store.getCard(guardName);
        if (guard == null) {
            throw new CardFantasyRuntimeException("Invalid guard name " + guardName);
        }
        return guard;
    }
    
    private void addBossGuards(PlayerInfo player, int guardType) {
        if (guardType == 0) {
            return;
        }
        List<CardData> bossGuards = new ArrayList<CardData>();
        if (guardType == 1) {
            while (true) {
                bossGuards.clear();
                int totalCost = 0;
                for (int i = 0; i < 9; ++i) {
                    CardData bossGuard = getBossGuard(CardDataStore.weakBossGuardians);
                    bossGuards.add(bossGuard);
                    totalCost += bossGuard.getBaseCost();
                }
                if (totalCost <= 101) {
                    break;
                }
            }
        }
        else if (guardType == 2) {
            while (true) {
                bossGuards.clear();
                int totalCost = 0;
                for (int i = 0; i < 3; ++i) {
                    CardData fiveStarBossGuard = getBossGuard(CardDataStore.fiveStarBossGuardians);
                    bossGuards.add(fiveStarBossGuard);
                    totalCost += fiveStarBossGuard.getBaseCost();
                }
                for (int i = 3; i < 9; ++i) {
                    CardData bossGuard = getBossGuard(CardDataStore.weakBossGuardians);
                    bossGuards.add(bossGuard);
                    totalCost += bossGuard.getBaseCost();
                }
                if (totalCost <= 141) {
                    break;
                }
            }
        }
        for (int i = 0; i < 9; ++i) {
            CardData bossGuard = bossGuards.get(i);
            player.addCard(new Card(bossGuard, 10, "BOSS" + i));
        }
        StringBuffer sb = new StringBuffer();
        for (Card card : player.getCards()) {
            sb.append(card.getParsableDesc() + ", ");
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
            PlayerInfo player1 = PlayerBuilder.build(false, "BOSS", bossName, 999999, null, 100);
            addBossGuards(player1, guardType);
            List<Skill> legionBuffs = SkillBuilder.buildLegionBuffs(buffKingdom, buffForest, buffSavage, buffHell);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, legionBuffs, 100);
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            BattleEngine engine = new BattleEngine(ui, Rule.getBossBattle());
            engine.registerPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            writer.print(Utils.getCurrentDateTime() + "<br />");
            writer.print(this.getDeckValidationResult(null, player2));
            writer.print("造成伤害：" + gameResult.getDamageToBoss() + "<br />");
            writer.print("------------------ 战斗过程 ------------------<br />");
            writer.print(ui.getAllText());
            logger.info("Winner: " + gameResult.getWinner().getId() + ", Damage to boss: " + gameResult.getDamageToBoss());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, false));
        }
    }

    @RequestMapping(value = "/PlayLilith1MatchGame")
    public void playLilith1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck, @RequestParam("count") int count, @RequestParam("gt") int gameType,
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
            PlayerInfo player1 = PlayerBuilder.buildLilith(lilithDataStore, lilithName, gameType == 0);
            List<Skill> player2Buffs = buildBuffsForLilithEvents(eventCardNames);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, player2Buffs, 100);
            PvlEngine engine = new PvlEngine(ui, Rule.getDefault());
            PvlGameResult result = null;
            if (gameType == 0) {
                result = engine.clearGuards(player1, player2, targetRemainingGuardCount);
            } else {
                result = engine.rushBoss(player1, remainingHP, player2);
            }
            writer.print(Utils.getCurrentDateTime() + "<br />");
            String resultMessage = String.format("共进行 %d 轮进攻，平均每轮对莉莉丝造成 %d 点伤害<br />",
                result.getBattleCount(), result.getAvgDamageToLilith());
            writer.print(resultMessage);
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
            PlayerInfo player1 = PlayerBuilder.build(false, "BOSS", bossName, 99999, null, 100);
            addBossGuards(player1, guardType);
            List<Skill> legionBuffs = SkillBuilder.buildLegionBuffs(buffKingdom, buffForest, buffSavage, buffHell);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, legionBuffs, 100);
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            BattleEngine engine = new BattleEngine(ui, Rule.getBossBattle());
            engine.registerPlayers(player1, player2);
            GameResult gameResult = engine.playGame();
            BattleRecord record = ui.getRecord();
            writer.print(jsonHandler.toJson(record));
            logger.info("Winner: " + gameResult.getWinner().getId() + ", Damage to boss: " + gameResult.getDamageToBoss());
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, true));
        }
    }

    @RequestMapping(value = "/SimulateLilith1MatchGame", headers = "Accept=application/json")
    public void simulateLilith1MatchGame(HttpServletRequest request, HttpServletResponse response,
        @RequestParam("deck") String deck, @RequestParam("count") int count, @RequestParam("gt") int gameType,
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
            PlayerInfo player1 = PlayerBuilder.buildLilith(lilithDataStore, lilithName, gameType == 0);
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
            List<Skill> legionBuffs = SkillBuilder.buildLegionBuffs(buffKingdom, buffForest, buffSavage, buffHell);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, legionBuffs, 100);

            String validationResult = this.getDeckValidationResult(null, player2);
            OneDimensionDataStat stat = new OneDimensionDataStat();
            int timeoutCount = 0;
            Rule rule = Rule.getBossBattle();
            GameUI ui = new DummyGameUI();
            PlayerInfo player1 = PlayerBuilder.build(false, "BOSS", bossName, 99999, null, 100);
            addBossGuards(player1, guardType);
            GameResult trialResult = BattleEngine.play1v1(ui, rule, player1, player2);
            if (trialResult.getCause() == GameEndCause.战斗超时) {
                ++timeoutCount;
            }
            stat.addData(trialResult.getDamageToBoss());
            int gameCount = 10000 / trialResult.getRound();
            if (gameCount < 100) {
                gameCount = 100;
            }
            if (gameCount > 1000) {
                gameCount = 1000;
            }
            
            int totalCostForCoolDown = 0;
            for (Card card : player2.getCards()) {
                totalCostForCoolDown += card.getBaseCost();
            }
            int coolDown = 60 + totalCostForCoolDown * 2;

            int totalCost = 0;
            for (Card card : player2.getCards()) {
                totalCost += card.getCost();
            }
            if (gameCount > 0) {
                for (int i = 0; i < gameCount - 1; ++i) {
                    player1 = PlayerBuilder.build(false, "BOSS", bossName, 99999, null, 100);
                    addBossGuards(player1, guardType);
                    GameResult gameResult = BattleEngine.play1v1(ui, rule, player1, player2);
                    if (gameResult.getCause() == GameEndCause.战斗超时) {
                        ++timeoutCount;
                    }
                    int damageToBoss = gameResult.getDamageToBoss();
                    if (damageToBoss < 0) {
                        damageToBoss = 0;
                    }
                    stat.addData(damageToBoss);
                }
            }

            long averageDamageToBoss = Math.round(stat.getAverage());
            logger.info("Average damage to boss: " + averageDamageToBoss);
            BossMassiveGameResult result = new BossMassiveGameResult(
                validationResult, coolDown, totalCost, timeoutCount, stat);
            writer.print(jsonHandler.toJson(result));
        } catch (Exception e) {
            response.setStatus(500);
            writer.print("{'error':'" + e.getMessage() + "'}");
            this.logger.error(e);
        }
    }

    @RequestMapping(value = "/PlayLilithMassiveGame")
    public void playLilithMassiveGame(HttpServletRequest request, HttpServletResponse response,
        @RequestParam("deck") String deck, @RequestParam("count") int count, @RequestParam("gt") int gameType,
        @RequestParam("hlv") int heroLv, @RequestParam("ln") String lilithName,
        @RequestParam("tc") int targetRemainingGuardCount, @RequestParam("rhp") int remainingHP,
        @RequestParam("ec") String eventCardNames
        ) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            String logMessage = String.format("Deck=%s<br />HeroLV=%d, Lilith=%s, GameType=%d, EventCards=%s", deck, heroLv, lilithName, gameType, eventCardNames);
            logger.info("PlayLilithMassiveGame: " + logMessage);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Lilith Massive Game", logMessage));

            PlayerInfo player1 = PlayerBuilder.buildLilith(lilithDataStore, lilithName, gameType == 0);
            List<Skill> player2Buffs = buildBuffsForLilithEvents(eventCardNames);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, player2Buffs, 100);
            OneDimensionDataStat statBattleCount = new OneDimensionDataStat();
            OneDimensionDataStat statAvgDamageToLilith = new OneDimensionDataStat();
            int gameCount = 100;
            writer.append(Utils.getCurrentDateTime() + "<br />");
            writer.append("模拟场次: " + gameCount + "<br />");
            writer.print(this.getDeckValidationResult(null, player2));
            try {
                for (int i = 0; i < gameCount; ++i) {
                    PvlEngine engine = new PvlEngine(new DummyGameUI(), Rule.getDefault());
                    PvlGameResult result = null;
                    if (gameType == 0) {
                        result = engine.clearGuards(player1, player2, targetRemainingGuardCount);
                    } else {
                        result = engine.rushBoss(player1, remainingHP, player2);
                    }
                    statBattleCount.addData(result.getBattleCount());
                    statAvgDamageToLilith.addData(result.getAvgDamageToLilith());
                }
                writer.append("<table>");
                writer.append("<tr><td>平均需要进攻次数: </td><td>" + statBattleCount.getAverage() + "</td></tr>");
                writer.append("<tr><td>不稳定度: </td><td>" + Math.round(statBattleCount.getCoefficientOfVariation() * 100) + "%</td></tr>");
                writer.append("<tr><td>平均每轮进攻对莉莉丝伤害: </td><td>" + Math.round(statAvgDamageToLilith.getAverage()) + "</td></tr>");
                writer.append("<tr><td>不稳定度: </td><td>" + Math.round(statAvgDamageToLilith.getCoefficientOfVariation() * 100) + "%</td></tr>");
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
            PlayerInfo player = PlayerBuilder.build(true, "玩家", deck, heroLv);
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            PveEngine engine = new PveEngine(ui, Rule.getDefault(), this.maps);
            PveGameResult gameResult = engine.play(player, map);
            writer.print(Utils.getCurrentDateTime() + "<br />" + this.getDeckValidationResult(null, player) + ui.getAllText());
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
            writer.append(this.getDeckValidationResult(null, player));
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
