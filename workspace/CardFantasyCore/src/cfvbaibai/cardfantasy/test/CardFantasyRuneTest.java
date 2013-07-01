package cfvbaibai.cardfantasy.test;

import org.junit.Test;

import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.RuneData;
import cfvbaibai.cardfantasy.game.PlayerBuilder;

public class CardFantasyRuneTest {

    @Test
    public void 九头妖蛇v远古蝎皇_荒芜() {
        TestGameBuilder.play5v5withRunes("九头妖蛇", RuneData.荒芜, "远古蝎皇", RuneData.荒芜);
    }

    @Test
    public void 金属巨龙v远古蝎皇_沼泽() {
        TestGameBuilder.play5v5withRunes("金属巨龙", RuneData.荒芜, "远古蝎皇", RuneData.沼泽);
    }

    @Test
    public void 凤凰v远古蝎皇_沼泽_荒芜() {
        PlayerInfo playerA = PlayerBuilder.build("【A】", 50, "C凤凰-10*5", "R沼泽-4", "R荒芜-4");
        PlayerInfo playerB = PlayerBuilder.build("【B】", 50, "C远古蝎皇-10*5", "R沼泽-4", "R荒芜-4");
        TestGameBuilder.build(playerA, playerB).playGame();
    }

    @Test
    public void 金属巨龙v远古蝎皇_岩晶() {
        TestGameBuilder.play5v5withRunes("金属巨龙", RuneData.岩晶, "远古蝎皇", RuneData.岩晶);
    }

    @Test
    public void 城镇弓箭兵v精灵狙击者_毒砂() {
        PlayerInfo playerA = PlayerBuilder.build("【A】", 100, "C城镇弓箭兵-10*15", "R毒砂-4");
        PlayerInfo playerB = PlayerBuilder.build("【B】", 100, "C精灵狙击者-10*15", "R毒砂-4");
        TestGameBuilder.build(playerA, playerB).playGame();
    }

    @Test
    public void 牛头人酋长vs战斗猛犸象_岩壁() {
        TestGameBuilder.play5v5withRunes("牛头人酋长", RuneData.岩壁, "战斗猛犸象", RuneData.岩壁);
    }

    @Test
    public void 水源制造者vs牛头人酋长_石林() {
        TestGameBuilder.play5v5withRunes("水源制造者", RuneData.岩壁, "牛头人酋长", RuneData.石林);
    }

    @Test
    public void 金属巨龙vs独眼巨人_赤谷() {
        TestGameBuilder.play5v5withRunes("金属巨龙", RuneData.岩壁, "独眼巨人", RuneData.赤谷);
    }

    @Test
    public void 凤凰vs蜘蛛人女王_飞岩() {
        TestGameBuilder.play5v5withRunes("凤凰", RuneData.岩壁, "蜘蛛人女王", RuneData.飞岩);
    }

    @Test
    public void 金属巨龙vs蜘蛛人女王_秽土() {
        TestGameBuilder.play5v5withRunes("金属巨龙", RuneData.岩壁, "蜘蛛人女王", RuneData.秽土);
    }

    @Test
    public void 城镇长矛兵vs城镇突击兵_霜冻寒潮() {
        TestGameBuilder.play5v5withRunes("城镇长矛兵", RuneData.霜冻, "城镇突击兵", RuneData.寒潮);
    }

    @Test
    public void 魔法结晶体vs魔法结晶体_冰锥() {
        TestGameBuilder.play5v5withRunes("魔法结晶体", RuneData.冰锥, "魔法结晶体", RuneData.冰锥);
    }

    @Test
    public void 魔法结晶体v堕落精灵法师_漩涡() {
        TestGameBuilder.play5v5withRunes("魔法结晶体", RuneData.漩涡, "堕落精灵法师", RuneData.冰锥);
    }

    @Test
    public void 城镇长矛兵vs城镇突击兵_暴雨清泉() {
        TestGameBuilder.play5v5withRunes("城镇长矛兵", RuneData.暴雨, "城镇突击兵", RuneData.清泉);
    }

    @Test
    public void 魔法结晶体v堕落精灵法师_雪崩怒涛() {
        PlayerInfo playerA = PlayerBuilder.build("【A】", 50, "C魔法结晶体-10*5", "R雪崩-4");
        PlayerInfo playerB = PlayerBuilder.build("【B】", 10, "C堕落精灵法师-10*5", "R怒涛-4");
        TestGameBuilder.build(playerA, playerB).playGame();
    }

    @Test
    public void 皇家卫队将领vs灵魂收割者_冰封() {
        TestGameBuilder.play5v5withRunes("皇家卫队将领", RuneData.冰封, "灵魂收割者", RuneData.清泉);
    }

    @Test
    public void 魔法协会长vs大恶魔_圣泉() {
        TestGameBuilder.play5v5withRunes("魔法协会长", RuneData.圣泉, "大恶魔", RuneData.清泉);
    }

    @Test
    public void 城镇长矛兵vs城镇突击兵_永冻寒伤() {
        TestGameBuilder.play5v5withRunes("城镇长矛兵", RuneData.永冻, "城镇突击兵", RuneData.寒伤);
    }

    @Test
    public void 水源制造者vs地岭拥有者_闪电雷云() {
        PlayerInfo playerA = PlayerBuilder.build("【A】", 50, "C水源制造者-10*5", "R闪电-4");
        PlayerInfo playerB = PlayerBuilder.build("【B】", 20, "C地岭拥有者-10*5", "R雷云-4");
        TestGameBuilder.build(playerA, playerB).playGame();
    }

    @Test
    public void 水源制造者vs地岭拥有者_霹雳飞羽() {
        TestGameBuilder.play5v5withRunes("水源制造者", RuneData.霹雳, "地岭拥有者", RuneData.飞羽);
    }

    @Test
    public void 树人祭司vs牛头人卫士_复苏飓风() {
        PlayerInfo playerA = PlayerBuilder.build("【A】", 50, "C树人祭司-10*5", "R复苏-4");
        PlayerInfo playerB = PlayerBuilder.build("【B】", 20, "C牛头人卫士-10*5", "R飓风-4");
        TestGameBuilder.build(playerA, playerB).playGame();
    }

    @Test
    public void 树人祭司vs牛头人卫士_春风洞察() {
        PlayerInfo playerA = PlayerBuilder.build("【A】", 50, "C树人祭司-10*5", "R春风-4", "R洞察-4");
        PlayerInfo playerB = PlayerBuilder.build("【B】", 50, "C牛头人卫士-10*5");
        TestGameBuilder.build(playerA, playerB).playGame();
    }
    
    @Test
    public void 水源制造者vs月亮女神_扬旗雷狱轻灵() {
        PlayerInfo playerA = PlayerBuilder.build("【A】", 50, "C水源制造者-10*10", "R扬旗-4", "R雷狱-4", "R轻灵-4");
        PlayerInfo playerB = PlayerBuilder.build("【B】", 50, "C月亮女神-10*10");
        TestGameBuilder.build(playerA, playerB).playGame();
    }
    
    @Test
    public void 黑甲铁骑士vs美杜莎女王_火拳热浪() {
        PlayerInfo playerA = PlayerBuilder.build("【A】", 50, "C黑甲铁骑士-10*5", "R火拳-4");
        PlayerInfo playerB = PlayerBuilder.build("【B】", 30, "C美杜莎女王-10*5", "R热浪-4");
        TestGameBuilder.build(playerA, playerB).playGame();
    }

    @Test
    public void 黑甲铁骑士vs美杜莎女王_流火红莲() {
        TestGameBuilder.play5v5withRunes("黑甲铁骑士", RuneData.流火, "美杜莎女王", RuneData.红莲);
    }
    
    @Test
    public void 黑甲铁骑士vs美杜莎女王_冥火淬炼() {
        PlayerInfo playerA = PlayerBuilder.build("【A】", 50, "C黑甲铁骑士-10*5", "R冥火-4");
        PlayerInfo playerB = PlayerBuilder.build("【B】", 20, "C美杜莎女王-10*5", "R淬炼-4");
        TestGameBuilder.build(playerA, playerB).playGame();
    }
    
    @Test
    public void 黑甲铁骑士vs金属巨龙_雷盾焚天炎甲() {
        PlayerInfo playerA = PlayerBuilder.build("【A】", 50, "C黑甲铁骑士-10*5", "R焚天-4", "R炎甲-4");
        PlayerInfo playerB = PlayerBuilder.build("【B】", 20, "C金属巨龙-10*5", "R雷狱-4", "R雷盾-4");
        TestGameBuilder.build(playerA, playerB).playGame();
    }
    
    @Test
    public void 黑甲铁骑士vs金属巨龙_爆裂灼魂() {
        PlayerInfo playerA = PlayerBuilder.build("【A】", 50, "C黑甲铁骑士-10*5", "R爆裂-4", "R灼魂-4");
        PlayerInfo playerB = PlayerBuilder.build("【B】", 20, "C金属巨龙-10*5");
        TestGameBuilder.build(playerA, playerB).playGame();
    }

    @Test
    public void 大恶魔vs金属巨龙_灭世绝杀() {
        PlayerInfo playerA = PlayerBuilder.build("【A】", 50, "C大恶魔-10*10", "R灭世-4", "R绝杀-4");
        PlayerInfo playerB = PlayerBuilder.build("【B】", 20, "C金属巨龙-10*10");
        TestGameBuilder.build(playerA, playerB).playGame();
    }
}
