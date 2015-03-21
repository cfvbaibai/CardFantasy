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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.GameUI;
import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.data.Skill;
import cfvbaibai.cardfantasy.data.SkillTag;
import cfvbaibai.cardfantasy.data.SkillType;
import cfvbaibai.cardfantasy.data.TrivialSkill;
import cfvbaibai.cardfantasy.engine.BattleEngine;
import cfvbaibai.cardfantasy.engine.CardInfo;
import cfvbaibai.cardfantasy.engine.GameEndCause;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Player;
import cfvbaibai.cardfantasy.engine.Rule;
import cfvbaibai.cardfantasy.game.DeckBuilder;
import cfvbaibai.cardfantasy.game.DummyGameUI;
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
    private LilithDataStore lilithDataStore;
    
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
            stat.addResult(BattleEngine.play1v1(ui, rule, p1, p2));
        }
        return stat;
    }

    private void outputBattleOptions(PrintWriter writer, int firstAttack, int deckOrder, int p1hhpb, int p1catb, int p1chpb, int p2hhpb, int p2catb, int p2chpb) {
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
    }

    @RequestMapping(value = "/PlayAuto1MatchGame")
    public void playAuto1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck1") String deck1, @RequestParam("count") int count,
            @RequestParam("deck2") String deck2, @RequestParam("hlv1") int heroLv1, @RequestParam("hlv2") int heroLv2,
            @RequestParam("fa") int firstAttack, @RequestParam("do") int deckOrder,
            @RequestParam("p1hhpb") int p1HeroHpBuff, @RequestParam("p1catb") int p1CardAtBuff, @RequestParam("p1chpb") int p1CardHpBuff,
            @RequestParam("p2hhpb") int p2HeroHpBuff, @RequestParam("p2catb") int p2CardAtBuff, @RequestParam("p2chpb") int p2CardHpBuff
            ) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            logger.info("PlayAuto1MatchGame from " + request.getRemoteAddr() + ":");
            String logMessage = String.format(
                "Deck1=%s<br />Deck2=%s<br />Lv1=%d, Lv2=%d, FirstAttack=%d, DeckOrder=%d",
                deck1, deck2, heroLv1, heroLv2, firstAttack, deckOrder);
            logger.info(logMessage);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Auto 1Match Game", logMessage));
            outputBattleOptions(writer, firstAttack, deckOrder, p1HeroHpBuff, p1CardAtBuff, p1CardHpBuff, p2HeroHpBuff, p2CardAtBuff, p2CardHpBuff);
            List<Skill> p1CardBuffs = new ArrayList<Skill>();
            if (p1CardAtBuff != 100) {
                p1CardBuffs.add(new TrivialSkill(SkillType.原始攻击调整, p1CardAtBuff - 100));
            }
            if (p1CardHpBuff != 100) {
                p1CardBuffs.add(new TrivialSkill(SkillType.原始体力调整, p1CardHpBuff - 100));
            }
            PlayerInfo player1 = PlayerBuilder.build(heroLv1 != 0, "玩家1", deck1, heroLv1, p1CardBuffs, p1HeroHpBuff);
            List<Skill> p2CardBuffs = new ArrayList<Skill>();
            if (p2CardAtBuff != 100) {
                p2CardBuffs.add(new TrivialSkill(SkillType.原始攻击调整, p2CardAtBuff - 100));
            }
            if (p2CardHpBuff != 100) {
                p2CardBuffs.add(new TrivialSkill(SkillType.原始体力调整, p2CardHpBuff - 100));
            }
            PlayerInfo player2 = PlayerBuilder.build(heroLv2 != 0, "玩家2", deck2, heroLv2, p2CardBuffs, p2HeroHpBuff);
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            BattleEngine engine = new BattleEngine(ui, new Rule(5, 999, firstAttack, deckOrder, false));
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
            @RequestParam("fa") int firstAttack, @RequestParam("do") int deckOrder,
            @RequestParam("p1hhpb") int p1HeroHpBuff, @RequestParam("p1catb") int p1CardAtBuff, @RequestParam("p1chpb") int p1CardHpBuff,
            @RequestParam("p2hhpb") int p2HeroHpBuff, @RequestParam("p2catb") int p2CardAtBuff, @RequestParam("p2chpb") int p2CardHpBuff
    ) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        try {
            logger.info("SimulateAuto1MatchGame from " + request.getRemoteAddr() + ":");
            String logMessage = String.format(
                "Deck1=%s<br />Deck2=%s<br />Lv1=%d, Lv2=%d, FirstAttack=%d, DeckOrder=%d",
                deck1, deck2, heroLv1, heroLv2, firstAttack, deckOrder);
            logger.info(logMessage);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Simulate Auto 1Match Game", logMessage));

            List<Skill> p1CardBuffs = new ArrayList<Skill>();
            if (p1CardAtBuff != 100) {
                p1CardBuffs.add(new TrivialSkill(SkillType.原始攻击调整, p1CardAtBuff - 100));
            }
            if (p1CardHpBuff != 100) {
                p1CardBuffs.add(new TrivialSkill(SkillType.原始体力调整, p1CardHpBuff - 100));
            }
            PlayerInfo player1 = PlayerBuilder.build(heroLv1 != 0, "玩家1", deck1, heroLv1, p1CardBuffs, p1HeroHpBuff);
            List<Skill> p2CardBuffs = new ArrayList<Skill>();
            if (p2CardAtBuff != 100) {
                p2CardBuffs.add(new TrivialSkill(SkillType.原始攻击调整, p2CardAtBuff - 100));
            }
            if (p2CardHpBuff != 100) {
                p2CardBuffs.add(new TrivialSkill(SkillType.原始体力调整, p2CardHpBuff - 100));
            }
            PlayerInfo player2 = PlayerBuilder.build(heroLv2 != 0, "玩家2", deck2, heroLv2, p2CardBuffs, p2HeroHpBuff);
            StructuredRecordGameUI ui = new StructuredRecordGameUI();
            BattleEngine engine = new BattleEngine(ui, new Rule(5, 999, firstAttack, deckOrder, false));
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
            @RequestParam("p2hhpb") int p2HeroHpBuff, @RequestParam("p2catb") int p2CardAtBuff, @RequestParam("p2chpb") int p2CardHpBuff
    ) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            logger.info("PlayAutoMassiveGame from " + request.getRemoteAddr() + ":");
            String logMessage = String.format("Deck1=%s<br />Deck2=%s<br />Lv1=%d, Lv2=%d, FirstAttack=%d, DeckOrder=%d, Count=%d",
                deck1, deck2, heroLv1, heroLv2, firstAttack, deckOrder, count);
            logger.info(logMessage);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Auto Massive Game", logMessage));
            outputBattleOptions(writer, firstAttack, deckOrder, p1HeroHpBuff, p1CardAtBuff, p1CardHpBuff, p2HeroHpBuff, p2CardAtBuff, p2CardHpBuff);
            List<Skill> p1CardBuffs = new ArrayList<Skill>();
            if (p1CardAtBuff != 100) {
                p1CardBuffs.add(new TrivialSkill(SkillType.原始攻击调整, p1CardAtBuff - 100));
            }
            if (p1CardHpBuff != 100) {
                p1CardBuffs.add(new TrivialSkill(SkillType.原始体力调整, p1CardHpBuff - 100));
            }
            PlayerInfo player1 = PlayerBuilder.build(heroLv1 != 0, "玩家1", deck1, heroLv1, p1CardBuffs, p1HeroHpBuff);
            List<Skill> p2CardBuffs = new ArrayList<Skill>();
            if (p2CardAtBuff != 100) {
                p2CardBuffs.add(new TrivialSkill(SkillType.原始攻击调整, p2CardAtBuff - 100));
            }
            if (p2CardHpBuff != 100) {
                p2CardBuffs.add(new TrivialSkill(SkillType.原始体力调整, p2CardHpBuff - 100));
            }
            PlayerInfo player2 = PlayerBuilder.build(heroLv2 != 0, "玩家2", deck2, heroLv2, p2CardBuffs, p2HeroHpBuff);
            GameResultStat stat = play(player1, player2, count, new Rule(5, 999, firstAttack, deckOrder, false));
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

    private static String[] bossGuardCandidates = new String[] {
        "小矮人狙击者",
        "精灵法师",
        "精灵暗杀者",
        "兽语驾驭者",
        "独角兽",
        "神秘花精灵",
        "精灵长者",
        "守卫古树",
        "精灵祭司",
        "地岭拥有者",
        "水源制造者",
        "风暴召唤者",
        "火灵操控者",
        "翡翠龙",
        "金属巨龙",
        "凤凰",
        "魔剑士",
        "重甲骑兵",
        "喷火装甲车手",
        "火焰乌鸦",
        "狮鹫",
        "魔导师",
        "魔法结晶体",
        "圣骑士",
        "暴雪召唤士",
        "皇家卫队将领",
        "大主教",
        "秘银巨石像",
        "荣耀巨人",
        "大剑圣",
        "降临天使",
        "光明之龙",
        "蜘蛛人女王",
        "鳄鱼人酋长",
        "山羊人萨满",
        "犀牛人武士",
        "哥布林术士",
        "食人魔术士",
        "食人魔战士",
        "牛头人卫士",
        "蝎尾狮",
        "兽人将领",
        "狂暴狼人酋长",
        "牛头人酋长",
        "独眼巨人",
        "巨象人武士",
        "战斗猛犸象",
        "雷兽",
        "九头妖蛇",
        "地狱魔兽",
        "骷髅法师",
        "堕落精灵",
        "堕落精灵法师",
        "黑曜石像鬼",
        "血祭恶魔",
        "邪眼",
        "梦魇",
        "木乃伊法老王",
        "美杜莎女王",
        "黑甲铁骑士",
        "大恶魔",
        "骨龙",
        "地狱红龙",
        "毁灭之龙",
        "灵魂收割者",
        "时空旅者",
        "东方僧人",
        "月之神兽",
        "沙漠吞虫",
        "魅魔",
        "暗影巨蟒",
        "痛苦之王",
        "霜雪树人",
        "皇家雄狮",
        "半鹿人守护者",
        "狮鹫骑士",
        "荒野狂虫",
        "奇美拉",
        "月亮女神",
        "战神",
        "远古蝎皇",
        "恐惧之王",
        "树人祭司",
        "雷电树人",
        "半鹿人祭司",
        "精灵审判长",
        "独角兽之王",
        "海盗船长",
        "巨型电鳗",
        "机械飞龙",
        "钻石巨石像",
        "火象人卫士",
        "火象人萨满",
        "蛮鱼人",
        "火象人领主",
        "洪荒巨熊",
        "鬼树人",
        "幽灵巨鲸",
        "黑魔导士",
        "血炼巫妖",
        "圣诞树人",
        "圣诞老人",
        "圣诞麋鹿",
        "背主之影",
        "震源岩蟾",
        "疾奔刺猬",
        "半鹿人长老",
        "月神弩炮车手",
        "元素灵龙",
        "麒麟兽",
        "光影魔术师",
        "圣堂武士",
        "炼金机甲",
        "纯洁圣女",
        "隐世先知",
        "雷电幽魂",
        "暗夜魔影",
        "恶灵之瞳",
        "幽灵巨龙",
        "巫妖领主",
        "荒原猎手",
        "熊人武士",
        "地行龙骑士",
        "暴怒霸龙",
        "毒雾羽龙",
        "羽翼化蛇",
        "森林丘比特",
        "新年爆竹",
        "梦境治愈师",
        "象牙角虫",
        "蒲公英仙子",
        "叶莲河童",
        "驯鹰射手",
        "迷魅灵狐",
        "半鹿人号角手",
        "巨岛龟幼崽",
        "枭羊族祭司",
        "刀锋女王",
        "高等暗精灵",
        "世界树之灵",
        "精灵女王",
        "枪炮玫瑰",
        "海军水手",
        "皇室舞者",
        "炎刃暗骑士",
        "王城巡逻犬",
        "彩翼公主",
        "冰峰女猎",
        "科学怪人",
        "机械兵团长",
        "鬼眼斗士",
        "海军司令",
        "浴火龙女",
        "半鱼人士卒",
        "黯灭制裁者",
        "幻翼命运神",
        "魔鹰族萨满",
        "尖牙捕食者",
        "犀角领主",
        "海潮歌姬",
        "唤雨师",
        "荒漠仙人掌",
        "魔能射手",
        "半狮人武士",
        "蛮族酋长",
        "冰雪巨人",
        "淘气灯灵",
        "尸腐守卫",
        "斗角恶魔",
        "恶灵之剑",
        "赤红地狱战马",
        "影子魔",
        "虚空假面",
        "地狱狮鹫",
        "鬼武者",
        "幽境裁决官",
        "赤炎鬼武士",
        "亡灵守护神",
        "混沌之龙",
        "不死鸟化身",
        "堕落天使",
        "小矮人工匠",
        "密纹骑士",
        "东方禅师",
        "皇家驯兽师",
        "血瞳魔剑师",
        "裁决巨石像",
        "复仇血精灵",
        "梦境女神",
        "龙角将军",
        "仙狐巫女",
        "骸骨大将",
        "末日预言师",
        "魔能巨石像",
        "战场女武神",
        "时光女神",
        "圣堂执政官",
        "天界守护者",
    };

    private CardData getBossGuard() {
        Randomizer random = Randomizer.getRandomizer();
        int guardNameIndex = random.next(0, bossGuardCandidates.length);
        String guardName = bossGuardCandidates[guardNameIndex];
        CardData guard = this.store.getCard(guardName);
        if (guard == null) {
            throw new CardFantasyRuntimeException("Invalid guard name " + guardName);
        }
        /*
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
            for (CardSkill skill : guard.getSkills()) {
                if (skill.getType().containsTag(SkillTag.召唤)) {
                    continue;
                }
            }
            int cardId = Integer.parseInt(guard.getId());
            if (cardId > 4999) {
                continue;
            }
            if (
                cardId == 1514 || // 预言之神
                cardId == 1515 || // 制裁之雷神
                cardId == 1516 || // 均衡女神
                cardId == 1517 || // 星辰主宰
                cardId == 1518 || // 圣炎魔导师
                cardId == 1519 || // 陨星魔法使
                cardId == 2515 || // 幻翼神丽雅
                cardId == 2518 || // 黄金金属巨龙
                cardId == 2517 || // 梦境守护者
                cardId == 2516 || // 星夜女神
                cardId == 3515 || // 怒雪咆哮
                cardId == 4517 || // 谎言之神
                cardId == 4519 || // 骨灵巫女
                cardId == 4520 || // 月蚀兽
                false
            ) {
                continue;
            }
        }
        */
        return guard;
    }
    
    private void addBossGuards(PlayerInfo player) {
        List<CardData> bossGuards = new ArrayList<CardData>();
        while (true) {
            bossGuards.clear();
            int totalCost = 0;
            for (int i = 0; i < 9; ++i) {
                CardData bossGuard = getBossGuard();
                bossGuards.add(bossGuard);
                totalCost += bossGuard.getBaseCost();
            }
            if (totalCost <= 101) {
                break;
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
            if (guardType == 1) {
                addBossGuards(player1);
            }
            List<Skill> legionBuffs = SkillBuilder.buildLegionBuffs(buffKingdom, buffForest, buffSavage, buffHell);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, legionBuffs, 100);
            WebPlainTextGameUI ui = new WebPlainTextGameUI();
            BattleEngine engine = new BattleEngine(ui, Rule.getBossBattle());
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

    @RequestMapping(value = "/PlayLilith1MatchGame")
    public void playLilith1MatchGame(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("deck") String deck, @RequestParam("count") int count, @RequestParam("gt") int gameType,
            @RequestParam("hlv") int heroLv, @RequestParam("ln") String lilithName,
            @RequestParam("tc") int targetRemainingGuardCount, @RequestParam("rhp") int remainingHP
            ) throws IOException {
        PrintWriter writer = response.getWriter();
        WebPlainTextGameUI ui = new WebPlainTextGameUI();
        try {
            String logMessage = String.format("Deck=%s<br />HeroLV=%d, Lilith=%s, GameType=%d", deck, heroLv, lilithName, gameType);
            logger.info("PlayLilith1MatchGame: " + logMessage);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Lilith 1Match Game", logMessage));
            PlayerInfo player1 = PlayerBuilder.buildLilith(lilithDataStore, lilithName, gameType == 0);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, null, 100);
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
            if (guardType == 1) {
                addBossGuards(player1);
            }
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
        @RequestParam("tc") int targetRemainingGuardCount, @RequestParam("rhp") int remainingHP
        ) throws IOException {
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        try {
            String logMessage = String.format("Deck=%s<br />HeroLV=%d, Lilith=%s, GameType=%d", deck, heroLv, lilithName, gameType);
            logger.info("SimulateLilith1MatchGame: " + logMessage);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Simulate Lilith 1Match Game", logMessage));
            PlayerInfo player1 = PlayerBuilder.buildLilith(lilithDataStore, lilithName, gameType == 0);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, null, 100);
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
            List<Skill> legionBuffs = SkillBuilder.buildLegionBuffs(buffKingdom, buffForest, buffSavage, buffHell);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, legionBuffs, 100);
            writer.append(Utils.getCurrentDateTime() + "<br />");
            OneDimensionDataStat stat = new OneDimensionDataStat();
            int timeoutCount = 0;
            Rule rule = Rule.getBossBattle();
            GameUI ui = new DummyGameUI();
            PlayerInfo player1 = PlayerBuilder.build(false, "BOSS", bossName, 99999, null, 100);
            if (guardType == 1) {
                addBossGuards(player1);
            }
            GameResult trialResult = BattleEngine.play1v1(ui, rule, player1, player2);
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
                totalCost += card.getBaseCost();
            }
            int coolDown = 60 + totalCost * 2;

            if (gameCount > 0) {
                for (int i = 0; i < gameCount - 1; ++i) {
                    if (guardType == 1) {
                        player1 = PlayerBuilder.build(false, "BOSS", bossName, 99999, null, 100);
                        addBossGuards(player1);
                    }
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

    @RequestMapping(value = "/PlayLilithMassiveGame")
    public void playLilithMassiveGame(HttpServletRequest request, HttpServletResponse response,
        @RequestParam("deck") String deck, @RequestParam("count") int count, @RequestParam("gt") int gameType,
        @RequestParam("hlv") int heroLv, @RequestParam("ln") String lilithName,
        @RequestParam("tc") int targetRemainingGuardCount, @RequestParam("rhp") int remainingHP
        ) throws IOException {
        PrintWriter writer = response.getWriter();
        try {
            String logMessage = String.format("Deck=%s<br />HeroLV=%d, Lilith=%s, GameType=%d", deck, heroLv, lilithName, gameType);
            logger.info("PlayLilithMassiveGame: " + logMessage);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Play Lilith Massive Game", logMessage));

            PlayerInfo player1 = PlayerBuilder.buildLilith(lilithDataStore, lilithName, gameType == 0);
            PlayerInfo player2 = PlayerBuilder.build(true, "玩家", deck, heroLv, null, 100);
            OneDimensionDataStat statBattleCount = new OneDimensionDataStat();
            OneDimensionDataStat statAvgDamageToLilith = new OneDimensionDataStat();
            int gameCount = 100;
            writer.append(Utils.getCurrentDateTime() + "<br />");
            writer.append("模拟场次: " + gameCount + "<br />");
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
    
    @RequestMapping(value = "/Cards/{keyword}")
    public ModelAndView getCardById(HttpServletRequest request, @PathVariable String keyword) {
        CardData card = this.store.getCard(keyword);
        String internalId = null;
        if (card != null) {
            internalId = card.getId();
        }
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view-card");
        mv.addObject("keyword", keyword);
        if (internalId != null) {
            mv.addObject("internalId", internalId);
        }
        this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "View Card", keyword));
        return mv;
    }
}
