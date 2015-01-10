package cfvbaibai.cardfantasy.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import cfvbaibai.cardfantasy.Table;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.engine.GameEngine;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Rule;
import cfvbaibai.cardfantasy.game.DummyGameUI;
import cfvbaibai.cardfantasy.game.GameResultStat;
import cfvbaibai.cardfantasy.game.LilithDataStore;
import cfvbaibai.cardfantasy.game.PlayerBuilder;
import cfvbaibai.cardfantasy.game.SkillBuilder;

public class FreeTest extends PveEngineTest {

    private static GameResultStat massivePlay10v10(String card1, String card2, int level) {
        return play(
                PlayerBuilder.build(true, "英雄" + card1, level, "C" + card1 + "-10*10"),
                PlayerBuilder.build(true, "英雄" + card2, level, "C" + card2 + "-10*10"), 1000);
    }

    private static GameResultStat massivePlay1v1(String card1, String card2, int level) {
        return play(
                PlayerBuilder.build(true, "英雄" + card1, level, "C" + card1 + "-10*1"),
                PlayerBuilder.build(true, "英雄" + card2, level, "C" + card2 + "-10*1"), 1000);
    }

    private static GameResultStat massivePlay1v1Lv15(String card1, String card2, int level) {
        return play(
                PlayerBuilder.build(true, "英雄" + card1, level, "C" + card1 + "-15"),
                PlayerBuilder.build(true, "英雄" + card2, level, "C" + card2 + "-10"), 1000);
    }

    private static GameResultStat massivePlay10v10Lv15(String card1, String card2, int level) {
        return play(
                PlayerBuilder.build(true, "英雄" + card1, level, "C" + card1 + "-15*10"),
                PlayerBuilder.build(true, "英雄" + card2, level, "C" + card2 + "-10*10"), 1000);
    }

    private static void showStat(GameResultStat stat) {
        System.out.println("Total: " + stat.count());
        System.out.println(String.format("%s wins %d (%d %%)", stat.getP1().getId(), stat.getP1Win(),
                stat.getP1WinRate()));
        System.out.println(String.format("%s wins %d (%d %%)", stat.getP2().getId(), stat.getP2Win(),
                stat.getP2WinRate()));
    }

    private static GameResultStat play(PlayerInfo p1, PlayerInfo p2, int count) {
        GameResultStat stat = new GameResultStat(p1, p2);
        for (int i = 0; i < count; ++i) {
            GameEngine engine = new GameEngine(new DummyGameUI(), Rule.getDefault());
            engine.registerPlayers(p1, p2);
            GameResult result = engine.playGame();
            stat.addResult(result);
        }
        return stat;
    }

    @Test
    public void 堕落精灵vs五星_1000() throws IOException {
        FiveStarChallenge("堕落精灵", true, false);
    }

    @Test
    public void 背主之影vs五星_1000() throws IOException {
        FiveStarChallenge("背主之影", true, false);
    }

    @Test
    public void 大剑圣vs五星_1000() throws IOException {
        FiveStarChallenge("大剑圣", true, false);
    }

    @Test
    public void 降临天使vs五星_1000() throws IOException {
        FiveStarChallenge("降临天使", true, false);
    }

    @Test
    public void 幽灵巨龙vs五星_1000() throws IOException {
        FiveStarChallenge("幽灵巨龙", true, false);
    }

    @Test
    public void 恐惧之王vs五星_1000() throws IOException {
        FiveStarChallenge("恐惧之王", true, false);
    }

    @Test
    public void 穿刺鬼树人vs五星_1000_10v10() throws IOException {
        FiveStarChallenge("穿刺鬼树人", true, true);
    }

    @Test
    public void 暴怒霸龙vs五星_1000() throws IOException {
        FiveStarChallenge("暴怒霸龙", false, false);
    }

    @Test
    public void 暴怒霸龙vs五星_10vs10_1000() throws IOException {
        FiveStarChallenge("暴怒霸龙", true, false);
    }

    @Test
    public void 纯洁圣女vs五星_1000() throws IOException {
        FiveStarChallenge("纯洁圣女", false, false);
    }

    @Test
    public void 纯洁圣女vs五星_10vs10_1000() throws IOException {
        FiveStarChallenge("纯洁圣女", true, false);
    }

    @Test
    public void 独眼巨人vs五星_1000() throws IOException {
        FiveStarChallenge("独眼巨人", false, false);
    }

    @Test
    public void 独眼巨人vs五星_10vs10_1000() throws IOException {
        FiveStarChallenge("独眼巨人", true, false);
    }

    @Test
    public void 森林女神vs五星_10vs10_1000() throws IOException {
        FiveStarChallenge("森林女神", true, false);
    }

    @Test
    public void 不动蜘蛛vs五星_10vs10_1000() throws IOException {
        FiveStarChallenge("不动蜘蛛", true, true);
    }

    @Test
    public void 九头妖蛇vs五星_2vs2_1000() throws IOException {
        FiveStarChallenge("九头妖蛇", false, false);
    }

    @Test
    public void 皇家驯兽师vs五星_10vs10_1000() throws IOException {
        FiveStarChallenge("皇家驯兽师", true, false);
    }
    
    @Test
    public void 魔神战() {
        PlayerInfo player = PlayerBuilder.build(true, "玩家", 75, SkillBuilder.buildLegionBuffs(10, 10, 10, 10), "堕落精灵*2", "淬炼");
        TestGameBuilder.playBossBattle(player, "复仇女神");
    }
    
    @Test
    public void 莉莉丝战() {
        LilithDataStore store = LilithDataStore.loadDefault();
        PlayerInfo lilith = PlayerBuilder.buildLilith(store, "困难莉莉丝+法力反射8", 0, false);
        PlayerInfo player = PlayerBuilder.build(true, "玩家", 100, "金属巨龙*10");
        TestGameBuilder.play(lilith, player);
    }
    
    @Test
    public void 横扫Bug() {
        TestGameBuilder.play5v5("光明之龙", "金属巨龙");
    }
    
    @Test
    public void 横扫岩壁Bug() {
        PlayerInfo p1 = PlayerBuilder.build(true, "王国英雄", 75, "光明之龙*5");
        PlayerInfo p2 = PlayerBuilder.build(true, "蛮荒英雄", 75, "牛头人酋长*5", "岩壁");
        TestGameBuilder.play(p1, p2);
    }
    
    @Test
    public void 横扫正常() {
        TestGameBuilder.play5v5("光明之龙", "降临天使");
    }
    
    @Test
    public void 绝杀Bug() {
        PlayerInfo player = PlayerBuilder.build(true, "玩家", "降临天使*4,光明之龙*1,毁灭之龙*3,灼魂,冰封,清泉,绝杀", 75);
        TestGameBuilder.playBossBattle(player, "复仇女神");
    }
    
    @Test
    public void 死契诅咒Bug() {
        PlayerInfo p1 = PlayerBuilder.build(true, "玩家1", "金属巨龙*5,雷盾", 75);
        PlayerInfo p2 = PlayerBuilder.build(true, "玩家2", "战斗猛犸象+死契诅咒1,秽土", 75);
        TestGameBuilder.play(p1, p2);
    }
    
    @Test
    public void 守护祈祷Bug() {
        PlayerInfo p1 = PlayerBuilder.build(true, "玩家1", "邪龙之神", 75);
        PlayerInfo p2 = PlayerBuilder.build(true, "玩家2", "魔剑士+祈祷7*2", 75);
        TestGameBuilder.play(p1, p2);
    }
    
    @Test
    public void 王国最强vs森林最强_1000() {
        for (int i = 6; i < 10; ++i) {
            System.out.println("Level: " + (i * 10));
            PlayerInfo p1 = PlayerBuilder.build(true, "王国英雄", i * 10, "C光明之龙-10*10", "R寒伤-4", "R清泉-4", "R冰封-4");// ,
                                                                                                          // "R怒涛-4");
            PlayerInfo p2 = PlayerBuilder.build(true, "森林英雄", i * 10, "C金属巨龙-10*10", "R雷盾-4", "R春风-4", "R轻灵-4");// ,
                                                                                                          // "R淬炼-4");
            showStat(play(p1, p2, 1000));
        }
    }

    @Test
    public void 王国最强vs王森最强_1000() {
        for (int i = 6; i < 10; ++i) {
            System.out.println("Level: " + (i * 10));
            PlayerInfo p1 = PlayerBuilder.build(true, "王国英雄", i * 10, "C光明之龙-10*10", "R寒伤-4", "R清泉-4", "R冰封-4", "R怒涛-4");
            PlayerInfo p2 = PlayerBuilder.build(true, "王森英雄", i * 10, "C金属巨龙-10*5", "C降临天使-10*5", "R雷盾-4", "R春风-4", "R冰封-4",
                    "R清泉-4");
            showStat(play(p2, p1, 10000));
        }
    }

    @Test
    public void 王国最强vs蛮荒最强_1000() {
        for (int i = 6; i < 10; ++i) {
            System.out.println("Level: " + (i * 10));
            PlayerInfo p1 = PlayerBuilder.build(true, "王国英雄", i * 10, "C光明之龙-10*10", "R寒伤-4", "R清泉-4", "R冰封-4", "R洞察-4");
            PlayerInfo p2 = PlayerBuilder.build(true, "蛮荒英雄", i * 10, "C雷兽-10*10", "R岩壁-4", "R赤谷-4", "R飞岩-4", "R秽土-4");
            showStat(play(p2, p1, 1000));
        }
    }

    @Test
    public void 转生一刀vs十五剑圣_1000() {
        for (int i = 6; i < 10; ++i) {
            System.out.println("Level: " + (i * 10));
            PlayerInfo p1 = PlayerBuilder.build(true, "一刀英雄", i * 10, "C转生堕落精灵-15*10", "R灼魂-4", "R灭世-4", "R淬炼-4", "R飓风-4");
            PlayerInfo p2 = PlayerBuilder.build(true, "剑圣英雄", i * 10, "C大剑圣-15*10", "R永冻-4", "R冰封-4", "R清泉-4", "R寒伤-4");
            showStat(play(p1, p2, 1000));
        }
    }

    @Test
    public void 光龙vs树人_1000() {
        for (int i = 6; i < 10; ++i) {
            System.out.println("Level: " + (i * 10));
            PlayerInfo p1 = PlayerBuilder.build(true, "光龙英雄", i * 10, "C光明之龙-10*10", "R冰封-4", "R清泉-4", "R寒伤-4", "R永冻-4");
            PlayerInfo p2 = PlayerBuilder.build(true, "树人英雄", i * 10, "C霜雪树人-10*6", "C树人祭司-10*4", "R雷盾-4", "R春风-4", "R轻灵-4");
            showStat(play(p1, p2, 1000));
        }
    }

    @Test
    public void 光龙vs大象_1000() {
        for (int i = 6; i < 10; ++i) {
            System.out.println("Level: " + (i * 10));
            PlayerInfo p1 = PlayerBuilder.build(true, "光龙英雄", i * 10, "C光明之龙-10*10", "R冰封-4", "R清泉-4", "R飓风-4", "R永冻-4");
            PlayerInfo p2 = PlayerBuilder.build(true, "大象英雄", i * 10, "C战斗猛犸象-10*10", "R赤谷-4", "R飞岩-4", "R秽土-4", "R怒涛-4");
            showStat(play(p2, p1, 1000));
        }
    }

    @Test
    public void 光龙vs大象() {
        PlayerInfo p1 = PlayerBuilder.build(true, "光龙英雄", 90, "C光明之龙-10*10", "R冰封-4", "R清泉-4", "R飓风-4", "R永冻-4");
        PlayerInfo p2 = PlayerBuilder.build(true, "大象英雄", 90, "C战斗猛犸象-10*10", "R赤谷-4", "R飞岩-4", "R秽土-4", "R怒涛-4");
        TestGameBuilder.play(p1, p2);
    }

    @Test
    public void 天使vs蜘蛛() {
        PlayerInfo p1 = PlayerBuilder.build(true, "天使英雄", 90, "C降临天使-10*10");
        PlayerInfo p2 = PlayerBuilder.build(true, "蜘蛛英雄", 90, "C蜘蛛人女王-10*10");
        showStat(play(p1, p2, 1000));
        showStat(play(p2, p1, 1000));
    }

    @Test
    public void 不动大象vs转生天使() {
        PlayerInfo p1 = PlayerBuilder.build(true, "大象英雄", "战斗猛犸象+不动*5,战斗猛犸象+横扫*5,赤谷", 90);
        PlayerInfo p2 = PlayerBuilder.build(true, "天使英雄", "降临天使+转生5*5,降临天使+陷阱2*5,冰封", 90);
        TestGameBuilder.play(p1, p2);
    }
    
    @Test
    public void 横扫大象vs转生天使() { 
        PlayerInfo p1 = PlayerBuilder.build(true, "大象英雄", "战斗猛犸象+横扫*5", 90);
        PlayerInfo p2 = PlayerBuilder.build(true, "天使英雄", "降临天使+转生5*5", 90);
        TestGameBuilder.play(p1, p2);
    }
    
    @Test
    public void 放心妥妥儿的() {
        PlayerInfo p1 = PlayerBuilder.build(true, "玩家1", "降临天使*3,金属巨龙*3,魔法协会长,秘银巨石像-15,震源岩蟾-15,复活节兔女郎,冰封,永冻,清泉,雷盾", 68);
        PlayerInfo p2 = PlayerBuilder.build(true, "玩家2", "降临天使*5,世界树之灵*2,金属巨龙*2,冰封,永冻,清泉,雷盾", 64);
        TestGameBuilder.play(p1, p2);
    }

    @Test
    public void 竞技场测试1() {
        PlayerInfo p1 = PlayerBuilder.build(true, "我方英雄", 74, "C金属巨龙-10*4", "C恐惧之王-10*4", "C灵魂收割者-10*2", "R雷盾-4", "R雷狱-4",
                "R灼魂-4", "R绝杀-4");
        PlayerInfo p2 = PlayerBuilder.build(true, "敌方英雄", 84, "C金属巨龙-10*4", "C降临天使-10*6", "R冰封-4", "R清泉-4", "R雷盾-4", "R永冻-4");
        showStat(play(p1, p2, 1000));
        showStat(play(p2, p1, 1000));
        PlayerInfo p3 = PlayerBuilder.build(true, "敌方英雄", 84, "C金属巨龙-10*5", "C降临天使-10*5", "R冰封-4", "R清泉-4", "R雷盾-4", "R轻灵-4");
        showStat(play(p1, p3, 1000));
        showStat(play(p3, p1, 1000));
    }

    @Test
    public void 竞技场测试2() {
        PlayerInfo p0 = PlayerBuilder.build(true, "敌方英雄", 75, "C金属巨龙-10*5", "C降临天使-10*5", "R冰封-4", "R清泉-4", "R雷盾-4", "R轻灵-4");
        PlayerInfo p1 = PlayerBuilder.build(true, "我方英雄", 75, "C光明之龙-10*2", "C降临天使-10*2", "C魔法协会长-10", "C灵魂收割者-10*2",
                "C金属巨龙-10*3", "R雷盾-4", "R清泉-4", "R永冻-4", "R冰封-4");
        showStat(play(p0, p1, 1000));
        showStat(play(p1, p0, 1000));
        PlayerInfo p2 = PlayerBuilder.build(true, "我方英雄", 75, "C光明之龙-10*2", "C降临天使-10*2", "C魔法协会长-10", "C灵魂收割者-10",
                "C恐惧之王-10", "C金属巨龙-10*3", "R雷盾-4", "R清泉-4", "R永冻-4", "R冰封-4");
        showStat(play(p0, p2, 1000));
        showStat(play(p2, p0, 1000));
        PlayerInfo p3 = PlayerBuilder.build(true, "我方英雄", 75, "C光明之龙-10", "C降临天使-10*3", "C魔法协会长-10", "C灵魂收割者-10*2",
                "C金属巨龙-10*3", "R雷盾-4", "R清泉-4", "R永冻-4", "R冰封-4");
        showStat(play(p0, p3, 1000));
        showStat(play(p3, p0, 1000));
        PlayerInfo p4 = PlayerBuilder.build(true, "我方英雄", 75, "C光明之龙-10", "C降临天使-10*3", "C魔法协会长-10", "C灵魂收割者-10", "C恐惧之王-10",
                "C金属巨龙-10*3", "R雷盾-4", "R清泉-4", "R永冻-4", "R冰封-4");
        showStat(play(p0, p4, 1000));
        showStat(play(p4, p0, 1000));
    }

    @Test
    public void 竞技场测试3() {
        PlayerInfo p0 = PlayerBuilder.build(true, "敌方英雄", 75, "C金属巨龙-10*6", "C降临天使-10*4", "R冰封-4", "R清泉-4", "R雷盾-4", "R永冻-4");
        PlayerInfo p1 = PlayerBuilder.build(true, "我方英雄", 75, "C金属巨龙-10*3", "C复活节兔女郎-10*2", "C雷兽-10*3", "C震源岩蟾-10*1",
                "C羽翼化蛇-10*1", "R雷盾-4", "R春风-4", "R岩壁-4", "R赤谷-4");
        showStat(play(p0, p1, 1000));
        showStat(play(p1, p0, 1000));
        PlayerInfo p2 = PlayerBuilder.build(true, "我方英雄", 75, "C金属巨龙-10*3", "C复活节兔女郎-10*1", "C雷兽-10*4", "C震源岩蟾-10*1",
                "C羽翼化蛇-10*1", "R雷盾-4", "R春风-4", "R岩壁-4", "R赤谷-4");
        showStat(play(p0, p2, 1000));
        showStat(play(p2, p0, 1000));
        PlayerInfo p3 = PlayerBuilder.build(true, "我方英雄", 75, "C金属巨龙-10*4", "C雷兽-10*4", "C震源岩蟾-10*1", "C羽翼化蛇-10*1", "R雷盾-4",
                "R春风-4", "R岩壁-4", "R赤谷-4");
        showStat(play(p0, p3, 1000));
        showStat(play(p3, p0, 1000));
        PlayerInfo p4 = PlayerBuilder.build(true, "我方英雄", 75, "C金属巨龙-10*4", "C复活节兔女郎-10*1", "C雷兽-10*4", "C羽翼化蛇-10*1",
                "R雷盾-4", "R春风-4", "R岩壁-4", "R赤谷-4");
        showStat(play(p0, p4, 1000));
        showStat(play(p4, p0, 1000));
    }

    @Test
    public void 竞技场测试4() {
        PlayerInfo p0 = PlayerBuilder.build(true, "阵容1", 75, "C金属巨龙-10*5", "C降临天使-10*5", "R冰封-4", "R清泉-4", "R雷盾-4", "R雷狱-4");
        PlayerInfo p1 = PlayerBuilder.build(true, "阵容2", 75, "C不动猛犸-15*10", "R秽土-4", "R石林-4", "R淬炼-4", "R赤谷-4");
        PlayerInfo p2 = PlayerBuilder.build(true, "阵容3", 75, "C降临天使-10*5", "C灵魂收割者-10*2", "C亡灵守护神-10", "C恐惧之王-10*2", "R冰封-4",
                "R清泉-4", "R灼魂-4", "R绝杀-4");
        PlayerInfo p3 = PlayerBuilder.build(true, "阵容4", 75, "C不动猛犸-15*4", "C穿刺蛤蟆-10*2", "C恐惧之王-10*3", "C亡灵守护神-10", "R石林-4",
                "R灼魂-4", "R绝杀-4", "R赤谷-4");
        showStat(play(p0, p1, 1000));
        showStat(play(p0, p2, 1000));
        showStat(play(p0, p3, 1000));

        showStat(play(p1, p0, 1000));
        showStat(play(p1, p2, 1000));
        showStat(play(p1, p3, 1000));

        showStat(play(p2, p0, 1000));
        showStat(play(p2, p1, 1000));
        showStat(play(p2, p3, 1000));

        showStat(play(p3, p0, 1000));
        showStat(play(p3, p1, 1000));
        showStat(play(p3, p2, 1000));
    }
    
    @Test
    public void 竞技场测试5() {
        PlayerInfo p0 = PlayerBuilder.build(true, "最强王森", 75, "C金属巨龙-10*5", "C降临天使-10*5", "R冰封-4", "R清泉-4", "R雷盾-4", "R永冻-4");
        PlayerInfo p1 = PlayerBuilder.build(true, "船夫队", 75, "C冥河船夫-10","C灵魂收割者-10*3", "C魔法协会长-10", "C降临天使-10*5",
                "R冰封-4", "R清泉-4", "R永冻-4", "R灼魂-4");
        PlayerInfo p2 = PlayerBuilder.build(true, "老头战神1队", 75, "C魔法协会长-10", "C战神-10*2", "C降临天使-10*3", "C灵魂收割者-10*4",
                "R冰封-4", "R清泉-4", "R永冻-4", "R灼魂-4");
        PlayerInfo p3 = PlayerBuilder.build(true, "老头战神2队", 75, "C魔法协会长-10", "C战神-10*2", "C降临天使-10*3", "C灵魂收割者-10*3", "C冥河船夫-10",
                "R冰封-4", "R清泉-4", "R永冻-4", "R灼魂-4");
        showStat(play(p0, p1, 1000));
        showStat(play(p1, p0, 1000));
        showStat(play(p0, p2, 1000));
        showStat(play(p2, p0, 1000));
        showStat(play(p0, p3, 1000));
        showStat(play(p3, p0, 1000));
    }
    
    @Test
    public void 竞技场测试6() {
        PlayerInfo p0 = PlayerBuilder.build(true, "最强王森", 75, "C金属巨龙-10*5", "C降临天使-10*5", "R冰封-4", "R春风-4", "R雷盾-4", "R永冻-4");
        //4雷兽1化蛇3金属2兔子 赤谷岩壁雷盾春风
        PlayerInfo p1 = PlayerBuilder.build(true, "蛮森一队", 75, "C雷兽-10*4", "C羽翼化蛇-10", "C金属巨龙-10*3", "C复活节兔女郎-10*2",
                "R赤谷-4", "R岩壁-4", "R雷盾-4", "R春风-4");
        //2雷兽2毒雾1化蛇3金属2兔子
        PlayerInfo p2 = PlayerBuilder.build(true, "蛮森二队", 75, "C雷兽-10*2", "C毒雾羽龙-10*2", "C羽翼化蛇-10", "C金属巨龙-10*3", "C复活节兔女郎-10*2",
                "R赤谷-4", "R岩壁-4", "R雷盾-4", "R春风-4");
        showStat(play(p0, p1, 1000));
        showStat(play(p1, p0, 1000));
        showStat(play(p0, p2, 1000));
        showStat(play(p2, p0, 1000));
    }
    
    
    @Test
    public void 竞技场测试7() {
        PlayerInfo p0 = PlayerBuilder.build(true, "最强王森", 75, "C金属巨龙-10*5", "C降临天使-10*5", "R冰封-4", "R春风-4", "R雷盾-4", "R永冻-4");
        // 3雷兽1毒龙1蛤蟆3金属2兔子 雷盾春风赤谷岩壁
        PlayerInfo p00 = PlayerBuilder.build(true, "最强蛮森", 75, "C雷兽-10*3", "C毒雾羽龙-10", "C震源岩蟾-10", "C金属巨龙-10*3", "C复活节兔女郎-10*2",
                "R赤谷-4", "R春风-4", "R雷盾-4", "R岩壁-4");
        //4天使，老头，2恐惧，2萝莉，1黑龙，符文灼魂冰封清泉永冻
        PlayerInfo p1 = PlayerBuilder.build(true, "打脸一队", 75, "C降临天使-10*4", "C魔法协会长-10", "C恐惧之王-10*2", "C灵魂收割者-10*2", "C毁灭之龙-10",
                "R灼魂-4", "R冰封-4", "R清泉-4", "R永冻-4");
        //4天使，老头，4恐惧，1黑龙
        PlayerInfo p2 = PlayerBuilder.build(true, "打脸二队", 75, "C降临天使-10*4", "C魔法协会长-10", "C恐惧之王-10*4", "C毁灭之龙-10",
                "R灼魂-4", "R冰封-4", "R清泉-4", "R永冻-4");
        showStat(play(p0, p1, 1000));
        showStat(play(p1, p0, 1000));
        showStat(play(p0, p2, 1000));
        showStat(play(p2, p0, 1000));
        
        showStat(play(p00, p1, 1000));
        showStat(play(p1, p00, 1000));
        showStat(play(p00, p2, 1000));
        showStat(play(p2, p00, 1000));
    }
    
    
    @Test
    public void 竞技场测试8() {
        PlayerInfo p0 = PlayerBuilder.build(true, "最强王森", 85, "C金属巨龙-10*5", "C降临天使-10*5", "R冰封-4", "R春风-4", "R雷盾-4", "R永冻-4");
        // 金属*1，水源*1，凤凰*2，老头*1， 秘银*1，机械飞龙*1，剑圣*1，萝莉*1，蛤蟆*1, 雷盾，春风，冰封，淬炼
        PlayerInfo p1 = PlayerBuilder.build(true, "阵容1", 85, "C金属巨龙-10", "C水源制造者-10", "C凤凰-10*2", "C魔法协会长-10", "C秘银巨石像-10", "C机械飞龙-10",
                "C大剑圣-10", "C灵魂收割者-10", "C震源岩蟾-10", "R冰封-4", "R春风-4", "R雷盾-4", "R淬炼-4");
        // 金属*1，水源*1，凤凰*2，老头*1， 秘银*1，机械飞龙*1，皇家卫队长*1，萝莉*1，蛤蟆*1, 雷盾，春风，冰封，淬炼
        PlayerInfo p2 = PlayerBuilder.build(true, "阵容2", 85, "C金属巨龙-10", "C水源制造者-10", "C凤凰-10*2", "C魔法协会长-10", "C秘银巨石像-10", "C机械飞龙-10",
                "C皇家卫队将领-10", "C灵魂收割者-10", "C震源岩蟾-10", "R冰封-4", "R春风-4", "R雷盾-4", "R淬炼-4");
        // 金属*1，水源*1，凤凰*2，老头*1， 秘银*1，机械飞龙*1，大主教*1，萝莉*1，蛤蟆*1, 雷盾，春风，冰封，淬炼
        PlayerInfo p3 = PlayerBuilder.build(true, "阵容3", 85, "C金属巨龙-10", "C水源制造者-10", "C凤凰-10*2", "C魔法协会长-10", "C秘银巨石像-10", "C机械飞龙-10",
                "C大主教-10", "C灵魂收割者-10", "C震源岩蟾-10", "R冰封-4", "R春风-4", "R雷盾-4", "R淬炼-4");
        // 金属*1，水源*1，凤凰*2，老头*1， 秘银*1，机械飞龙*1，皇家驯兽师，萝莉*1，蛤蟆*1, 雷盾，春风，冰封，淬炼
        PlayerInfo p4 = PlayerBuilder.build(true, "阵容4", 85, "C金属巨龙-10", "C水源制造者-10", "C凤凰-10*2", "C魔法协会长-10", "C秘银巨石像-10", "C机械飞龙-10",
                "C皇家驯兽师-10", "C灵魂收割者-10", "C震源岩蟾-10", "R冰封-4", "R春风-4", "R雷盾-4", "R淬炼-4");
        // 金属1，水源1,凤凰2，森女1，老头1，萝莉1，蛤蟆1，熊人武士1，猛犸1, 春风雷盾赤谷淬炼
        PlayerInfo p5 = PlayerBuilder.build(true, "阵容5", 85, "C金属巨龙-10", "C水源制造者-10", "C凤凰-10*2", "C森林女神-10", "C魔法协会长-10", 
                "C灵魂收割者-10", "C震源岩蟾-10", "C熊人武士-10", "C战斗猛犸象-10", "R赤谷-4", "R春风-4", "R雷盾-4", "R淬炼-4");
        // 金属1，水源2,凤凰2，老头1，萝莉1，蛤蟆1，熊人武士1，猛犸1, 春风雷盾赤谷淬炼
        PlayerInfo p6 = PlayerBuilder.build(true, "阵容6", 85, "C金属巨龙-10", "C水源制造者-10*2", "C凤凰-10*2", "C魔法协会长-10", 
                "C灵魂收割者-10", "C震源岩蟾-10", "C熊人武士-10", "C战斗猛犸象-10", "R赤谷-4", "R春风-4", "R雷盾-4", "R淬炼-4");
        
        showStat(play(p0, p1, 1000));
        showStat(play(p1, p0, 1000));
        showStat(play(p0, p2, 1000));
        showStat(play(p2, p0, 1000));
        showStat(play(p0, p3, 1000));
        showStat(play(p3, p0, 1000));
        showStat(play(p0, p4, 1000));
        showStat(play(p4, p0, 1000));
        showStat(play(p0, p5, 1000));
        showStat(play(p5, p0, 1000));
        showStat(play(p0, p6, 1000));
        showStat(play(p6, p0, 1000));
        
        showStat(play(p1, p2, 1000));
        showStat(play(p1, p3, 1000));
        showStat(play(p1, p4, 1000));
        showStat(play(p1, p5, 1000));
        showStat(play(p1, p6, 1000));
        
        showStat(play(p2, p1, 1000));
        showStat(play(p2, p3, 1000));
        showStat(play(p2, p4, 1000));
        showStat(play(p2, p5, 1000));
        showStat(play(p2, p6, 1000));
        
        showStat(play(p3, p1, 1000));
        showStat(play(p3, p2, 1000));
        showStat(play(p3, p4, 1000));
        showStat(play(p3, p5, 1000));
        showStat(play(p3, p6, 1000));
        
        showStat(play(p4, p1, 1000));
        showStat(play(p4, p2, 1000));
        showStat(play(p4, p3, 1000));
        showStat(play(p4, p5, 1000));
        showStat(play(p4, p6, 1000));
        
        showStat(play(p5, p1, 1000));
        showStat(play(p5, p2, 1000));
        showStat(play(p5, p3, 1000));
        showStat(play(p5, p4, 1000));
        showStat(play(p5, p6, 1000));
        
        showStat(play(p6, p1, 1000));
        showStat(play(p6, p2, 1000));
        showStat(play(p6, p3, 1000));
        showStat(play(p6, p4, 1000));
        showStat(play(p6, p5, 1000));
        
        
    }
    
    @Test
    public void 幽灵之龙vs恐惧_1000() {
        PlayerInfo p1 = PlayerBuilder.build(true, "英雄幽灵龙", 80, "C幽灵巨龙-10*1");
        PlayerInfo p2 = PlayerBuilder.build(true, "英雄恐惧之王", 80, "C恐惧之王-10*1");
        showStat(play(p2, p1, 1000));
    }

    private void FiveStarChallenge(String cardName, boolean is10v10, boolean isLevel15) throws IOException {
        CardDataStore store = CardDataStore.loadDefault();
        int[] heroLevels = new int[] { 60, 70, 80, 90 };
        Table<String> table = new Table<String>();
        table.setCell(0, 0, "英雄等级");
        for (int i = 0; i < heroLevels.length; ++i) {
            table.setCell(0, i + 1, String.valueOf(heroLevels[i]));
        }
        List<CardData> cards = store.getCardOfStar(5);
        for (int i = 0; i < cards.size(); ++i) {
            table.setCell(i + 1, 0, cards.get(i).getName());
        }
        for (int i = 0; i < heroLevels.length; ++i) {
            int heroLevel = heroLevels[i];
            for (int j = 0; j < cards.size(); ++j) {
                System.out.println("Level: " + heroLevel + ", Card: " + cards.get(j).getName());
                GameResultStat stat = null;
                if (is10v10) {
                    if (isLevel15) {
                        stat = massivePlay10v10Lv15(cardName, cards.get(j).getName(), heroLevel);
                    } else {
                        stat = massivePlay10v10(cardName, cards.get(j).getName(), heroLevel);
                    }
                } else {
                    if (isLevel15) {
                        stat = massivePlay1v1Lv15(cardName, cards.get(j).getName(), heroLevel);
                    } else {
                        stat = massivePlay1v1(cardName, cards.get(j).getName(), heroLevel);
                    }
                }
                table.setCell(j + 1, i + 1, String.valueOf(stat.getP1Win()));
            }
        }
        table.outputToCsv(new File("test_gen/FallenElfvsStar5s.csv"));
    }

    @Test
    public void TestCsvWriter() throws IOException {
        CsvWriter writer = new CsvWriter(new File("test_gen/Test.csv"));
        writer.writeFields(new Object[] { "A", "b", "C" });
        writer.writeFields(new Object[] { "A", "b", "C" });
        writer.writeFields(new Object[] { "A", "b", "C" });
        writer.close();
    }

    @Test
    public void 堕落精灵vs纯洁圣女() {
        TestGameBuilder.play10v10("堕落精灵", "金属巨龙");
    }

    @Test
    public void 堕落精灵vs恐惧之王() {
        TestGameBuilder.play10v10("堕落精灵", "恐惧之王");
    }

    @Test
    public void 恐惧之王vs堕落精灵() {
        TestGameBuilder.play10v10("恐惧之王", "堕落精灵");
    }

    @Test
    public void 大剑圣vs战神() {
        TestGameBuilder.play10v10("大剑圣", "战神");
    }

    @Test
    public void 降临天使vs月亮女神() {
        TestGameBuilder.play10v10("降临天使", "月亮女神");
    }

    @Test
    public void 降临天使vs隐世先知() {
        TestGameBuilder.play10v10("降临天使", "隐世先知");
    }

    @Test
    public void 堕落精灵vs圣诞老人() {
        TestGameBuilder.play1v1("堕落精灵", "圣诞老人");
    }

    @Test
    public void 九头妖蛇vs凤凰() {
        TestGameBuilder.play2v2("九头妖蛇", "凤凰");
    }
    

    @Test
    public void 九头妖蛇vs九头妖蛇() {
        TestGameBuilder.play2v2("九头妖蛇", "九头妖蛇");
    }
    
    @Test
    public void 皇家驯兽师vs九头妖蛇() {
        TestGameBuilder.play10v10("皇家驯兽师", "九头妖蛇", 62);
    }
}