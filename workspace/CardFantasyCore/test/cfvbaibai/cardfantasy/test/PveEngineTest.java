package cfvbaibai.cardfantasy.test;

import java.util.List;

import org.junit.Test;

import cfvbaibai.cardfantasy.Combination;
import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.Rune;
import cfvbaibai.cardfantasy.game.DeckEvaluation;
import cfvbaibai.cardfantasy.game.DeckStartupInfo;
import cfvbaibai.cardfantasy.game.DummyGameUI;
import cfvbaibai.cardfantasy.game.MapStages;
import cfvbaibai.cardfantasy.game.PlayerBuilder;
import cfvbaibai.cardfantasy.game.PveEngine;
import cfvbaibai.cardfantasy.game.PveGameResultStat;

public class PveEngineTest {

    protected PveEngine engine;
    protected PveEngine massiveEngine;

    public PveEngineTest() {
        MapStages maps = new MapStages();
        engine = new PveEngine(new TestGameUI(), maps);
        massiveEngine = new PveEngine(new DummyGameUI(), maps);
    }

    @Test
    public void TestMap1_1_1() {
        PlayerInfo player = PlayerBuilder.build(true, "ME", 50, "C森林狼-1*3");
        engine.play(player, "1-1-3");
    }

    @Test
    public void TestMap10_10_1() {
        PlayerInfo player = PlayerBuilder.build(true, "ME", 46, "R飓风-2", "R淬炼-3", "R清泉-4", "R岩壁-3", "C熊人武士-10", "C雷兽-10",
                "C战斗猛犸象-10*2", "C蜘蛛人女王-10*2", "C皇家卫队将领-10*2", "C大主教-10", "C秘银巨石像-10");
        engine.play(player, "10-10-1");
    }

    @Test
    public void TestMap9_10_1() {
        PlayerInfo p1 = PlayerBuilder.build(true, "ME", 47, "R淬炼-4", "R石林-4", "R赤谷-4", "R岩壁-4", "C凤凰-10", "C光明之龙-10",
                "C美杜莎女王-10", "C水源制造者-10", "C战斗猛犸象-10*2", "C秘银巨石像-10", "C蜘蛛人女王-10*3");
        engine.play(p1, "9-10-1");
    }

    @Test
    public void TestMassivePlayMap1_1_1() {
        PlayerInfo player = PlayerBuilder.build(true, "ME", 50, "C城镇弓箭兵-1*1");
        PveGameResultStat stat = massiveEngine.massivePlay(player, "1-1-3", 1000);
        showStat(stat);
    }

    @Test
    public void TestMassivePlayMap8_H_3() {
        PlayerInfo player = PlayerBuilder.build(true, "ME", 50, "C金属巨龙-10*3", "C月之神兽-10", "C光明之龙-10", "C魔法协会长-10",
                "C美杜莎女王-10", "C水源制造者-10*2", "C半鹿人守护者-10", "C森林女神-10");// ,
                                                                      // "R冰封-4",
                                                                      // "R雷狱-4",
                                                                      // "R雷盾-4",
                                                                      // "R淬炼-4");
        PveGameResultStat stat = massiveEngine.massivePlay(player, "8-H-3", 1000);
        showStat(stat);
    }

    @Test
    public void TestMassivePlayMap10_10_1() {
        PlayerInfo player = PlayerBuilder.build(true, "ME", 50, "C金属巨龙-10*3", "C月之神兽-10", "C光明之龙-10", "C魔法协会长-10",
                "C美杜莎女王-10", "C水源制造者-10*2", "C半鹿人守护者-10", "C森林女神-10");// ,
                                                                      // "R冰封-4",
                                                                      // "R雷狱-4",
                                                                      // "R雷盾-4",
                                                                      // "R淬炼-4");
        PveGameResultStat stat = massiveEngine.massivePlay(player, "10-10-1", 1000);
        showStat(stat);
    }
    
    @Test
    public void TestMassivePlayMap10_10_2() {
        PlayerInfo player = PlayerBuilder.build(true, "ME", 46,
                "C毁灭之龙-10", "C降临天使-10", "C雷兽-10", "C大剑圣-10", "C秘银巨石像-10",
                "C美杜莎女王-10", "C水源制造者-10", "C降临天使-10", "C蜘蛛人女王-10", "C战斗猛犸象-10",
                "R清泉-4", "R淬炼-4", "R冰封-4", "R寒伤-4");// ,
                                                                      // "R冰封-4",
                                                                      // "R雷狱-4",
                                                                      // "R雷盾-4",
                                                                      // "R淬炼-4");
        engine.play(player, "10-10-2");
        PveGameResultStat stat = massiveEngine.massivePlay(player, "10-10-2", 1000);
        showStat(stat);
    }

    private void showStat(PveGameResultStat stat) {
        System.out.println("Total: " + stat.count());
        System.out.println("Lose: " + stat.countLost());
        System.out.println("Win: " + stat.countAllWin() + " (" + stat.getAllWinRate() + "%)");
        System.out.println("Adv Win: " + stat.countAdvWin() + " (" + stat.getAdvWinRate() + "%)");
    }

    @Test
    public void TestCombination() {
        List<List<Integer>> result = Combination.calculate(3, 1, 2, 3, 4, 5, 6);
        StringBuffer sb = new StringBuffer();
        for (List<Integer> entry : result) {
            for (Integer element : entry) {
                sb.append(element);
                sb.append(", ");
            }
            sb.append("\r\n");
        }
        System.out.println(sb.toString());
    }

    @Test
    public void TestOptimizeDeck() {
        List<DeckEvaluation> evals = massiveEngine.optimizeDeck(0, 1, "1-2-3", 5, -1, "C白斑蜘蛛-1", "C金属巨龙-1");
        for (int i = 0; i < evals.size(); ++i) {
            DeckEvaluation eval = evals.get(i);
            System.out.println("#" + i + ": AdvWinRate: " + eval.getStat().getAdvWinRate() + "%");
            showDeck(eval.getDeck());
        }
    }

    /**
     * 谢谢楼主了。 符文清泉4，淬炼3，飓风2，石壁3， 卡组熊战，雷兽，大象，2女王，队长，主教，密银，重甲骑兵，美杜沙。全10级
     * 备用卡有队长10级，大象10，黑甲5，火灵5，
     */
    // TODO: Optimize this deck

    @Test
    public void TestOptimizeDeck_10_10_1() {
        List<DeckEvaluation> evals = massiveEngine.optimizeDeck(4, 10, "10-10-1", 46, 5, "R飓风-2", "R淬炼-3", "R清泉-4",
                "R岩壁-3", "C熊人武士-10", "C雷兽-10", "C战斗猛犸象-10*2", "C蜘蛛人女王-10*2", "C皇家卫队将领-10*2", "C大主教-10", "C秘银巨石像-10",
                "C重甲骑兵-10", "C美杜莎女王-10", "C黑甲铁骑士-5", "C火灵操控者-5");
        // "C独眼巨人-10*4", "C机械飞龙-10", "C狮鹫骑士-10", "C暗影巨蟒-10",
        // "C大恶魔-10",
        // "C狂暴狼人酋长-10", "C荣耀巨人-10", "C大主教-10", "C翡翠龙-10", "C风暴召唤者-10",
        // "C痛苦之王-10", "C邪灵女巫-10", "C战斗猛犸象-10*3",
        // "C牛头人酋长-10", "C皇家卫队将领-10*2", "C水源制造者-10*4");

        for (int i = 0; i < evals.size(); ++i) {
            DeckEvaluation eval = evals.get(i);
            System.out.println("#" + i + ": AdvWinRate: " + eval.getStat().getAdvWinRate() + "%");
            showDeck(eval.getDeck());
        }
    }

    /*
     * 我的卡有：1凤凰、1光龙、1美杜沙、1水源、2猛犸、1密银、3女王（其实还有1女巫1黑骑）都是强十。 符文有淬炼、石林、赤谷、岩壁。都强满了
     * 等级是47级。
     */

    @Test
    public void TestOptimizeDeck_9_10_1() {
        List<DeckEvaluation> evals = massiveEngine.optimizeDeck(4, 10, "9-10-1", 47, 5, "R淬炼-4", "R石林-4", "R赤谷-4",
                "R岩壁-4", "C凤凰-10", "C光明之龙-10", "C美杜莎女王-10", "C水源制造者-10", "C战斗猛犸象-10*2", "C秘银巨石像-10", "C蜘蛛人女王-10*3",
                "C邪灵女巫-10", "C黑甲铁骑士-10");
        // "C独眼巨人-10*4", "C机械飞龙-10", "C狮鹫骑士-10", "C暗影巨蟒-10",
        // "C大恶魔-10",
        // "C狂暴狼人酋长-10", "C荣耀巨人-10", "C大主教-10", "C翡翠龙-10", "C风暴召唤者-10",
        // "C痛苦之王-10", "C邪灵女巫-10", "C战斗猛犸象-10*3",
        // "C牛头人酋长-10", "C皇家卫队将领-10*2", "C水源制造者-10*4");

        for (int i = 0; i < evals.size(); ++i) {
            DeckEvaluation eval = evals.get(i);
            System.out.println("#" + i + ": AdvWinRate: " + eval.getStat().getAdvWinRate() + "%");
            showDeck(eval.getDeck());
        }
    }

    private void showDeck(DeckStartupInfo deck) {
        StringBuffer sb = new StringBuffer();
        sb.append("Runes: ");
        for (Rune rune : deck.getRunes()) {
            sb.append(rune.getName());
            sb.append(rune.getLevel());
            sb.append(", ");
        }
        sb.append("\r\nCards: ");
        for (Card card : deck.getCards()) {
            sb.append(card.getName());
            sb.append(card.getLevel());
            sb.append(", ");
        }
        System.out.println(sb.toString());
    }
}