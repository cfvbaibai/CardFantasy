<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<title>魔卡幻想模拟器</title>
<%
    String dataTheme = "c";
    String dataContentTheme = "d";
%>

</head>
<body>
    <div data-role="page" data-title="地图战" data-mini="true" id="map-battle">
        <div data-role="header" data-id="navbar" data-position="fixed" data-theme="c">
            <div data-role="navbar" data-theme="c">
                <ul>
                    <li><a href="#boss-battle">魔神战</a></li>
                    <li><a href="#map-battle" class="ui-btn-active ui-state-persist">地图战</a></li>
                    <li><a href="#arena-battle">竞技场战</a></li>
                </ul>
            </div>
        </div>
        <div data-role="content">
            <div data-role="collapsible" data-mini="true" data-content-theme="<%=dataContentTheme%>" data-collapsed-icon="info" data-theme="c"
                data-expanded-icon="info">
                <h3>点击查看帮助</h3>
                <div id="help">
                    <ul data-theme="c">
                        <li>在下面的输入框里输入卡组符文的信息，卡牌与符文之间用逗号隔开，不要留多余的空格，例如： 金属巨龙,降临天使,冰封,永冻</li>
                        <li>默认卡牌等级10级，符文等级4级，想要改变等级的话，在名称后面添加"-数字"。例如10级金属就是"金属巨龙-10"。</li>
                        <li>重复添加同一个卡牌可以使用星号，例如"凤凰*5"表示5张10级凤凰，"凤凰-15*5"表示5张15级凤凰，注意符文不能重复。</li>
                        <li>可以使用加号设置卡牌的洗炼技能，例如"凤凰+转生5-15*5"表示5张15级的转生5的凤凰。</li>
                        <li>洗炼技能也可以设定成降临或者死契，例如"骷髅法师+降临火墙3"表示有了降临火墙的骷髅法师，"独眼巨人+死契摧毁"表示有了死契摧毁的独眼巨人。</li>
                        <li>设置了洗炼技能的卡牌默认15级，所以"凤凰+转生5"等同于"凤凰+转生5-15"。</li>
                        <li>制作人：<a href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Fi%2F13567034%2Fprofile%3Ffrom%3Dmap" target="_blank">白白</a></li>
                        <li>友情支持：<a href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Ff%3Fkw%3D%25C4%25A7%25BF%25A8%25BB%25C3%25CF%25EB%3Ffrom%3Dmap" target="_blank">魔卡幻想贴吧</a></li>
                    </ul>
                </div>
            </div>
            <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>设置阵容</h3>
                <div>
                    <table style="WIDTH: 100%">
                        <tr>
                            <td>地图</td>
                            <td>
                                <select data-theme="c" name="map-id" id="map-id" class="map-select" data-mini="true" data-native-menu="false">
                                    <optgroup label="12-后燃烧平原">
                                        <option value="12-1" selected="selected">12-1 火腹石</option>
                                        <option value="12-2">12-2 绝望壁</option>
                                        <option value="12-3">12-3 焦炭岭</option>
                                        <option value="12-4">12-4 乌鼻熔炉</option>
                                        <option value="12-5">12-5 火晶炼场</option>
                                        <option value="12-6">12-6 残渣废墟</option>
                                        <option value="12-7">12-7 熔火之心</option>
                                        <option value="12-8">12-8 毁灭之锤</option>
                                        <option value="12-9">12-9 流桨河</option>
                                        <option value="12-10">12-10 烙铁牢笼</option>
                                        <option value="12-11">12-11 红龙巢穴</option>
                                        <option value="12-H">12-H (隐藏)熔岩口</option>
                                    </optgroup>
                                    <optgroup label="11-蛮荒火山">
                                        <option value="11-1">11-1 石浪岸</option>
                                        <option value="11-2">11-2 火龙遗骸</option>
                                        <option value="11-3">11-3 火象人村</option>
                                        <option value="11-4">11-4 石像堡</option>
                                        <option value="11-5">11-5 荒芜之地</option>
                                        <option value="11-6">11-6 火海之森</option>
                                        <option value="11-7">11-7 古湖遗迹</option>
                                        <option value="11-8">11-8 炙热荆林</option>
                                        <option value="11-9">11-9 赤红裂谷</option>
                                        <option value="11-10">11-10 断石桥</option>
                                        <option value="11-11">11-11 火山口</option>
                                        <option value="11-H">11-H (隐藏)巨树之根</option>
                                    </optgroup>
                                    <optgroup label="10-海底界">
                                        <option value="10-1">10-1 迷雾礁石</option>
                                        <option value="10-2">10-2 失落神像</option>
                                        <option value="10-3">10-3 深渊海沟</option>
                                        <option value="10-4">10-4 鲸鱼坟场</option>
                                        <option value="10-5">10-5 远古沉船</option>
                                        <option value="10-6">10-6 海神之叉</option>
                                        <option value="10-7">10-7 海底古城</option>
                                        <option value="10-8">10-8 幽冥古洞</option>
                                        <option value="10-9">10-9 恐怖漏斗</option>
                                        <option value="10-10">10-10 海底山</option>
                                        <option value="10-H">10-H (隐藏)鱼人村</option>
                                    </optgroup>
                                    <optgroup label="9-海龟岛">
                                        <option value="9-1">9-1 巨大石块</option>
                                        <option value="9-2">9-2 长脊山</option>
                                        <option value="9-3">9-3 灰熊森林</option>
                                        <option value="9-4">9-4 鱼人小屋</option>
                                        <option value="9-5">9-5 菊石巨螺</option>
                                        <option value="9-6">9-6 神秘蛋坑</option>
                                        <option value="9-7">9-7 木船废墟</option>
                                        <option value="9-8">9-8 废弃战场</option>
                                        <option value="9-9">9-9 珊瑚山</option>
                                        <option value="9-10">9-10 东侧平原</option>
                                        <option value="9-H">9-H (隐藏)象形石门</option>
                                    </optgroup>
                                    <optgroup label="8-天空之城">
                                        <option value="8-1">8-1 巨人旷野</option>
                                        <option value="8-2">8-2 金色沙漠</option>
                                        <option value="8-3">8-3 脚印绿洲</option>
                                        <option value="8-4">8-4 绿色通道</option>
                                        <option value="8-5">8-5 三岔路口</option>
                                        <option value="8-6">8-6 水晶湖</option>
                                        <option value="8-7">8-7 闪耀之碑</option>
                                        <option value="8-8">8-8 传送门</option>
                                        <option value="8-9">8-9 静谧宫殿</option>
                                        <option value="8-10">8-10 远古避难所</option>
                                        <option value="8-H">8-H (隐藏)陨石坑</option>
                                    </optgroup>
                                    <optgroup label="7-末日峡谷">
                                        <option value="7-1">7-1 赤色沼泽</option>
                                        <option value="7-2">7-2 迷雾湿地</option>
                                        <option value="7-3">7-3 回声森林</option>
                                        <option value="7-4">7-4 暗黑之门</option>
                                        <option value="7-5">7-5 幽暗山脉</option>
                                        <option value="7-6">7-6 无光之峰</option>
                                        <option value="7-7">7-7 抉择峭壁</option>
                                        <option value="7-8">7-8 末日之镜</option>
                                        <option value="7-9">7-9 远古通道</option>
                                        <option value="7-H">7-H (隐藏)恐惧回廊</option>
                                    </optgroup>
                                    <optgroup label="6-乌木地下城">
                                        <option value="6-1">6-1 大裂痕</option>
                                        <option value="6-2">6-2 嚎叫深渊</option>
                                        <option value="6-3">6-3 尖叫长桥</option>
                                        <option value="6-4">6-4 乌木城</option>
                                        <option value="6-5">6-5 乌木神庙</option>
                                        <option value="6-6">6-6 暗影长廊</option>
                                        <option value="6-7">6-7 暗影祭坛</option>
                                        <option value="6-8">6-8 永生渡口</option>
                                        <option value="6-H">6-H (隐藏)魔力之泉</option>
                                    </optgroup>
                                    <optgroup label="5-燃烧平原">
                                        <option value="5-1">5-1 冒险者营地</option>
                                        <option value="5-2">5-2 冒险者岗哨</option>
                                        <option value="5-3">5-3 黑石矿坑</option>
                                        <option value="5-4">5-4 灰烬之谷</option>
                                        <option value="5-5">5-5 黑炭洞窟</option>
                                        <option value="5-6">5-6 遗忘神庙</option>
                                        <option value="5-7">5-7 废弃墓园</option>
                                        <option value="5-8">5-8 灼热小径</option>
                                        <option value="5-H">5-H (隐藏)焦炭遗迹</option>
                                    </optgroup>
                                    <optgroup label="4-翡翠森林">
                                        <option value="4-1">4-1 微风湾</option>
                                        <option value="4-2">4-2 巨木村</option>
                                        <option value="4-3">4-3 坠星湖</option>
                                        <option value="4-4">4-4 蓝鹰瀑布</option>
                                        <option value="4-5">4-5 月神祭坛</option>
                                        <option value="4-6">4-6 月影之井</option>
                                        <option value="4-7">4-7 耳语渡口</option>
                                        <option value="4-H">4-H (隐藏)迷雾之谷</option>
                                    </optgroup>
                                    <optgroup label="3-西风岛">
                                        <option value="3-1">3-1 风暴岛</option>
                                        <option value="3-2">3-2 南港</option>
                                        <option value="3-3">3-3 星辰学院</option>
                                        <option value="3-4">3-4 竞技场</option>
                                        <option value="3-5">3-5 星象塔</option>
                                        <option value="3-6">3-6 龙牙山</option>
                                        <option value="3-7">3-7 神秘山洞</option>
                                        <option value="3-H">3-H (隐藏)地下图书馆</option>
                                    </optgroup>
                                    <optgroup label="2-落日荒原">
                                        <option value="2-1">2-1 泰坦山道</option>
                                        <option value="2-2">2-2 荒蛮古道</option>
                                        <option value="2-3">2-3 部落遗迹</option>
                                        <option value="2-4">2-4 余晖渡口</option>
                                        <option value="2-5">2-5 黄昏镇</option>
                                        <option value="2-6">2-6 银月港</option>
                                    </optgroup>
                                    <optgroup label="1-试炼森林">
                                        <option value="1-1">1-1 森林入口</option>
                                        <option value="1-2">1-2 森林小径</option>
                                        <option value="1-3">1-3 守林人小屋</option>
                                        <option value="1-4">1-4 小镜湖</option>
                                        <option value="1-5">1-5 密林深处</option>
                                        <option value="1-6">1-6 废弃兽穴</option>
                                    </optgroup>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>难度</td>
                            <td>
                                <select data-theme="c" name="map-difficulty" id="map-difficulty" class="map-select" data-mini="true" data-native-menu="false">
                                    <option value="1" selected="selected">简单</option>
                                    <option value="2">普通</option>
                                    <option value="3">困难</option>
                                </select>
                            </td>
                        </tr>
                        <tr><td>过关条件</td><td><span id="map-victory-condition">未知</span></td></tr>
                    </table>
                    <div id="player" class="player ui-grid-c">
                        <div class="ui-block-a ui-block-label-number">
                            <span>玩家等级: </span>
                        </div>
                        <div class="ui-block-b">
                            <input data-theme="c" type="number" id="map-hero-lv" name="map-hero-lv" data-mini="true" value="75" />
                        </div>
                        <div class="ui-block-c ui-block-label-number">
                            <span>玩家卡组: </span>
                        </div>
                        <div data-theme="c" class="ui-block-d">
                            <a id="build-map-deck-button" data-role="button" data-rel="dialog" data-mini="true">组卡</a>
                        </div>
                    </div>
                    <div>
                        <textarea data-theme="c" id="map-deck" name="map-deck" rows="5" cols="40" data-mini="true">精灵法师-10</textarea>
                    </div>
                </div>
            </div>
            <div data-mini="true" data-role="controlgroup" data-type="horizontal" data-disabled="false">
                <a id="play-map-1-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">文字战斗</a>
                <a id="simulate-map-1-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">动画战斗</a>
                <a id="play-map-massive-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">连续千场</a>
                <a data-role="button" data-mini="true" data-theme="c" href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Fp%2F2660663919%3Ffrom%3Dmap" target="blank">提BUG</a>
            </div>
            <div id="map-battle-div" data-mini="true" data-role="collapsible" data-collapsed="false" data-theme="c" data-content-theme="d">
                <h3>战斗记录</h3>
                <div id="map-battle-output" class="battle-output">没有战斗</div>
            </div>
        </div>
    </div>

    <div data-role="page" data-title="竞技场战" data-mini="true" id="arena-battle">
        <div data-role="header" data-id="navbar" data-position="fixed" data-theme="c">
            <div data-role="navbar" data-theme="c">
                <ul>
                    <li><a href="#boss-battle">魔神战</a></li>
                    <li><a href="#map-battle">地图战</a></li>
                    <li><a href="#arena-battle" class="ui-btn-active ui-state-persist">竞技场战</a></li>
                </ul>
            </div>
        </div>

        <div data-role="content">
            <div data-role="collapsible" data-mini="true" data-content-theme="<%=dataContentTheme%>" data-collapsed-icon="info" data-theme="c"
                data-expanded-icon="info">
                <h3>点击查看帮助</h3>
                <div id="help">
                    <ul>
                        <li>在下面的输入框里输入卡组符文的信息，卡牌与符文、卡牌与卡牌、符文与符文之间都用逗号隔开，例如： 金属巨龙,降临天使,冰封,永冻</li>
                        <li>默认卡牌等级10级，符文等级4级，想要改变等级的话，在名称后面添加"-数字"。例如10级金属就是"金属巨龙-10"。</li>
                        <li>重复添加同一个卡牌可以使用星号，例如"凤凰*5"表示5张10级凤凰，"凤凰-15*5"表示5张15级凤凰，注意符文不能重复。</li>
                        <li>可以使用加号设置卡牌的洗炼技能，例如"凤凰+转生5-15*5"表示5张15级的转生5的凤凰。</li>
                        <li>洗炼技能也可以设定成降临或者死契，例如"骷髅法师+降临火墙3"表示有了降临火墙的骷髅法师，"独眼巨人+死契摧毁"表示有了死契摧毁的独眼巨人。</li>
                        <li>设置了洗炼技能的卡牌默认15级，所以"凤凰+转生5"等同于"凤凰+转生5-15"。</li>
                        <li>制作人：<a href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Fi%2F13567034%2Fprofile%3Ffrom%3D1v1" target="_blank">白白</a></li>
                        <li>友情支持：<a href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Ff%3Fkw%3D%25C4%25A7%25BF%25A8%25BB%25C3%25CF%25EB%3Ffrom%3D1v1" target="_blank">魔卡幻想贴吧</a></li>
                    </ul>
                </div>
            </div>
            <div data-role="collapsible" data-mini="true" data-collapsed="false" data-theme="c" data-content-theme="d">
                <h3>设置双方阵容</h3>
                <div id="attackFirst" data-mini="true" data-role="controlgroup" data-type="horizontal">
                    <input data-theme="c" data-mini="true" type="radio" name="firstAttack" id="autoFirst" value="-1" checked="checked" />
                    <label for="autoFirst">按规则决定先攻</label>
                    <input data-theme="c" data-mini="true" type="radio" name="firstAttack" id="player1First" value="0" />
                    <label for="player1First">玩家1先攻</label>
                    <input data-theme="c" data-mini="true" type="radio" name="firstAttack" id="player2First" value="1" />
                    <label for="player2First">玩家2先攻</label>
                </div>
                <div id="player1" class="player ui-grid-c">
                    <div data-theme="c" class="ui-block-a ui-block-label-number">
                        <span>玩家1等级: </span>
                    </div>
                    <div data-theme="c" class="ui-block-b">
                        <input data-theme="c" type="number" id="hero1Lv" data-mini="true" value="75" />
                    </div>
                    <div data-theme="c" class="ui-block-c ui-block-label-number">
                        <span>玩家1卡组: </span>
                    </div>
                    <div data-theme="c" class="ui-block-d">
                        <a id="build-deck1-button" data-role="button" data-rel="dialog" data-mini="true">组卡</a>
                    </div>
                </div>
                <div>
                    <textarea data-theme="c" id="deck1" rows="5" cols="40" data-mini="true">金属巨龙*5,降临天使*5,冰封,永冻,雷盾,春风</textarea>
                </div>
                <div id="player2" class="player ui-grid-c">
                    <div data-theme="c" class="ui-block-a ui-block-label-number">
                        <span>玩家2等级: </span>
                    </div>
                    <div data-theme="c" class="ui-block-b">
                        <input data-theme="c" type="number" id="hero2Lv" data-mini="true" value="75" />
                    </div>
                    <div class="ui-block-c ui-block-label-number">
                        <span>玩家2卡组: </span>
                    </div>
                    <div data-theme="c" class="ui-block-d">
                        <a id="build-deck2-button" data-role="button" data-rel="dialog" data-mini="true">组卡</a>
                    </div>
                </div>
                <div>
                    <textarea data-theme="c" id="deck2" rows="5" cols="40" data-mini="true">战斗猛犸象+降临传送*5,堕落精灵+转生5*5,岩壁,赤谷,秽土,灼魂</textarea>
                </div>
            </div>
            <div id="command" data-mini="true" data-role="controlgroup" data-type="horizontal">
                <a id="play-auto-1-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">文字战斗</a>
                <a id="simulate-auto-1-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">动画战斗</a>
                <a id="play-auto-massive-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">连续千场</a>
                <a data-role="button" data-mini="true" data-theme="c" target="blank"
                    href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Fp%2F2660663919%3Ffrom%3D1v1">提BUG</a>
            </div>
            <div id="battle-div" data-mini="true" data-role="collapsible" data-collapsed="false" data-theme="c"
                data-content-theme="<%=dataContentTheme%>">
                <h3>战斗记录</h3>
                <div id="battle-output" class="battle-output">没有战斗</div>
            </div>
        </div>
    </div>
    
    <div data-role="page" data-title="魔神战" data-mini="true" id="boss-battle">
        <div data-role="header" data-id="navbar" data-position="fixed" data-theme="c">
            <div data-role="navbar" data-theme="c">
                <ul>
                    <li><a href="#boss-battle" class="ui-btn-active ui-state-persist">魔神战</a></li>
                    <li><a href="#map-battle">地图战</a></li>
                    <li><a href="#arena-battle">竞技场战</a></li>
                </ul>
            </div>
        </div>

        <div id="boss-battle-content" data-role="content">
            <div data-role="collapsible" data-mini="true" data-content-theme="<%=dataContentTheme%>" data-collapsed-icon="info" data-theme="c"
                data-expanded-icon="info">
                <h3>点击查看帮助</h3>
                <div id="help">
                    <ul data-theme="c">
                        <li>在下面的输入框里输入卡组符文的信息，卡牌与符文之间用逗号隔开，不要留多余的空格，例如： 金属巨龙,降临天使,冰封,永冻</li>
                        <li>默认卡牌等级10级，符文等级4级，想要改变等级的话，在名称后面添加"-数字"。例如10级金属就是"金属巨龙-10"。</li>
                        <li>重复添加同一个卡牌可以使用星号，例如"凤凰*5"表示5张10级凤凰，"凤凰-15*5"表示5张15级凤凰，注意符文不能重复。</li>
                        <li>可以使用加号设置卡牌的洗炼技能，例如"凤凰+转生5-15*5"表示5张15级的转生5的凤凰。</li>
                        <li>洗炼技能也可以设定成降临或者死契，例如"骷髅法师+降临火墙3"表示有了降临火墙的骷髅法师，"独眼巨人+死契摧毁"表示有了死契摧毁的独眼巨人。</li>
                        <li>设置了洗炼技能的卡牌默认15级，所以"凤凰+转生5"等同于"凤凰+转生5-15"。</li>
                        <li>制作人：<a href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Fi%2F13567034%2Fprofile%3Ffrom%3Dboss" target="_blank">白白</a></li>
                        <li>友情支持：<a href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Ff%3Fkw%3D%25C4%25A7%25BF%25A8%25BB%25C3%25CF%25EB%3Ffrom%3Dboss" target="_blank">魔卡幻想贴吧</a></li>
                    </ul>
                </div>
            </div>
            <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>设置阵容</h3>
                <div>
                    <select data-theme="c" name="boss-name" id="boss-name" data-mini="true" data-native-menu="false">
                        <option value="复仇女神">魔神：复仇女神</option>
                        <option value="邪龙之神">魔神：邪龙之神</option>
                        <option value="噩梦之主">魔神：噩梦之主</option>
                        <option value="毁灭之神">魔神：毁灭之神</option>
                        <option value="深渊影魔">魔神：深渊影魔</option>
                        <option value="万蛛之后">魔神：万蛛之后</option>
                    </select>

<!-- 军团加成：10级， -->
                    <fieldset data-theme="c" data-role="controlgroup" data-type="horizontal">
                        <select data-theme="c" name="buff-kingdom" id="buff-kingdom" data-mini="true" data-native-menu="false">
                            <option value="0">王国军团加成0</option>
                            <option value="1">王国军团加成1</option>
                            <option value="2">王国军团加成2</option>
                            <option value="3">王国军团加成3</option>
                            <option value="4">王国军团加成4</option>
                            <option value="5">王国军团加成5</option>
                            <option value="6">王国军团加成6</option>
                            <option value="7">王国军团加成7</option>
                            <option value="8">王国军团加成8</option>
                            <option value="9">王国军团加成9</option>
                            <option value="10" selected="selected">王国军团加成10</option>
                        </select>
                        <select data-theme="c" name="buff-savage" id="buff-savage" data-mini="true" data-native-menu="false">
                            <option value="0">蛮荒军团加成0</option>
                            <option value="1">蛮荒军团加成1</option>
                            <option value="2">蛮荒军团加成2</option>
                            <option value="3">蛮荒军团加成3</option>
                            <option value="4">蛮荒军团加成4</option>
                            <option value="5">蛮荒军团加成5</option>
                            <option value="6">蛮荒军团加成6</option>
                            <option value="7">蛮荒军团加成7</option>
                            <option value="8">蛮荒军团加成8</option>
                            <option value="9">蛮荒军团加成9</option>
                            <option value="10" selected="selected">蛮荒军团加成10</option>
                        </select>
                    </fieldset>
                    <fieldset data-theme="c" data-role="controlgroup" data-type="horizontal">
                        <select data-theme="c" name="buff-forest" id="buff-forest" data-mini="true" data-native-menu="false">
                            <option value="0">森林军团加成0</option>
                            <option value="1">森林军团加成1</option>
                            <option value="2">森林军团加成2</option>
                            <option value="3">森林军团加成3</option>
                            <option value="4">森林军团加成4</option>
                            <option value="5">森林军团加成5</option>
                            <option value="6">森林军团加成6</option>
                            <option value="7">森林军团加成7</option>
                            <option value="8">森林军团加成8</option>
                            <option value="9">森林军团加成9</option>
                            <option value="10" selected="selected">森林军团加成10</option>
                        </select>
                        <select data-theme="c" name="buff-hell" id="buff-hell" data-mini="true" data-native-menu="false">
                            <option value="0">地狱军团加成0</option>
                            <option value="1">地狱军团加成1</option>
                            <option value="2">地狱军团加成2</option>
                            <option value="3">地狱军团加成3</option>
                            <option value="4">地狱军团加成4</option>
                            <option value="5">地狱军团加成5</option>
                            <option value="6">地狱军团加成6</option>
                            <option value="7">地狱军团加成7</option>
                            <option value="8">地狱军团加成8</option>
                            <option value="9">地狱军团加成9</option>
                            <option value="10" selected="selected">地狱军团加成10</option>
                        </select>
                    </fieldset>
                    
                    <div id="player" class="player ui-grid-c">
                        <div class="ui-block-a ui-block-label-number">
                            <span>玩家等级: </span>
                        </div>
                        <div class="ui-block-b">
                            <input data-theme="c" type="number" id="heroLv" name="heroLv" data-mini="true" value="75" />
                        </div>
                        <div class="ui-block-c ui-block-label-number">
                            <span>玩家卡组: </span>
                        </div>
                        <div data-theme="c" class="ui-block-d">
                            <a id="build-boss-deck-button" data-role="button" data-rel="dialog" data-mini="true">组卡</a>
                        </div>
                    </div>
                    <div>
                        <textarea data-theme="c" id="deck" name="deck" rows="5" cols="40" data-mini="true">堕落精灵*2,淬炼,绝杀</textarea>
                    </div>
                </div>
            </div>
            <div id="boss-command" data-mini="true" data-role="controlgroup" data-type="horizontal" data-disabled="false">
                <a id="play-boss-1-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">文字战斗</a>
                <a id="simulate-boss-1-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">动画战斗</a>
                <a id="play-boss-massive-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">卡组强度分析</a>
                <a data-role="button" data-mini="true" data-theme="c" target="blank"
                    href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Fp%2F2660663919%3Ffrom%3Dboss">提BUG</a>
            </div>
            <div id="battle-div" data-mini="true" data-role="collapsible" data-collapsed="false" data-theme="c" data-content-theme="d">
                <h3>战斗记录</h3>
                <div id="boss-battle-output" class="battle-output">没有战斗</div>
            </div>
        </div>
    </div>


    <div data-role="page" data-title="战场" data-mini="true" id="arena" class="fixed-width">
        <div data-role="header" data-theme="c" data-position="fixed">
            <h3 style="text-align: center">战斗</h3>
        </div>
        <div data-role="content" data-theme="c">
            <div id="canvas-outline" style="overflow: auto; margin-left: auto; margin-right: auto">
                <span>若动画空白，请重启浏览器</span>
                <div id="battle-canvas"></div>
            </div>
            <div id="arena-control-panel" data-mini="true" data-role="controlgroup" data-type="horizontal">
                <a id="playButton" data-role="button" data-mini="true" data-theme="c" href="javascript:togglePlayButton()"></a>
                <a id="fasterButton" data-role="button" data-mini="true" data-theme="c" href="javascript:faster()">加快</a>
                <a id="slowerButton" data-role="button" data-mini="true" data-theme="c" href="javascript:slower()">减慢</a>
                <a id="backButton" data-role="button" data-mini="true" data-theme="c" href="javascript:history.go(-1)">返回</a>
                <a data-role="button" data-mini="true" data-theme="c" href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Fp%2F2660663919%3Ffrom%3Danimation" target="blank">提BUG</a>
            </div>
            <div id="playerStatus"></div>
        </div>
    </div>
    
    <div data-role="page" data-title="组卡" data-mini="true" id="deck-builder">
        <div data-role="header" data-theme="c" data-position="fixed">
            <h3 style="text-align: center">组卡</h3>
        </div>
        <div data-role="content" data-theme="c">
            <div id="deck-chooser">
                <div data-role="collapsible-set" data-theme="c" data-content-theme="d">
                    <div data-role="collapsible" data-mini="true" data-collapsed="false">
                        <h3>卡牌</h3>
                        <div>
                            <div id="card-filter">
                                <table style="width: 100%">
                                    <tr>
                                        <td>筛选</td>
                                        <td style="width: 40%">
                                            <select id="card-race-filter" data-mini="true" data-native-menu="false">
                                                <option value="KINGDOM">王国</option>
                                                <option value="FOREST">森林</option>
                                                <option value="SAVAGE">蛮荒</option>
                                                <option value="HELL">地狱</option>
                                            </select>
                                        </td>
                                        <td style="width: 40%">
                                            <select id="card-star-filter" data-mini="true" data-native-menu="false">
                                                <option value="1">一星</option>
                                                <option value="2">二星</option>
                                                <option value="3">三星</option>
                                                <option value="4">四星</option>
                                                <option value="5" selected="selected">五星</option>
                                            </select>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div id="card-candidate" class="candidate"></div>
                        </div>
                    </div>
                    <div data-role="collapsible" data-mini="true">
                        <h3>符文</h3>
                        <div>
                            <div id="rune-filter">
                                <table style="width: 100%">
                                    <tr>
                                        <td>筛选</td>
                                        <td style="width: 40%">
                                            <select id="rune-class-filter" data-mini="true" data-native-menu="false">
                                                <option value="WATER">冰</option>
                                                <option value="WIND">风</option>
                                                <option value="GROUND">地</option>
                                                <option value="FIRE">火</option>
                                            </select>
                                        </td>
                                        <td style="width: 40%">
                                            <select id="rune-star-filter" data-mini="true" data-native-menu="false">
                                                <option value="1">一星</option>
                                                <option value="2">二星</option>
                                                <option value="3">三星</option>
                                                <option value="4">四星</option>
                                                <option value="5" selected="selected">五星</option>
                                            </select>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div id="rune-candidate" class="candidate"></div>
                        </div>
                    </div>
                </div>
            </div>
            <div data-role="collapsible" data-mini="true" data-content-theme="d" data-collapsed="false">
                <h3>牌组</h3>
                <div id="deck-output" style="OVERFLOW: auto; height: 100px">
                    <a href="#a" data-role="button" data-mini="true" data-inline="true" data-icon="delete" data-iconpos="right">金属巨龙+吸血-15</a>
                    <a href="#a" data-role="button" data-mini="true" data-inline="true" data-icon="delete" data-iconpos="right">降临天使+不动-15</a>
                    <a href="#a" data-role="button" data-mini="true" data-inline="true" data-icon="delete" data-iconpos="right">降临天使+降临趁胜追击-15*10</a>
                    <a href="#a" data-role="button" data-mini="true" data-inline="true" data-icon="delete" data-iconpos="right">降临天使+不动-15</a>
                </div>
            </div>
            <div id="deck-builder-control-panel" data-mini="true" data-role="controlgroup" data-type="horizontal">
                <a id="update-deck-button" data-role="button" data-mini="true" data-theme="c">确定</a>
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:history.go(-1)">取消</a>
            </div>
        </div>
    </div>

    <div data-role="page" data-title="设定符文属性" data-mini="true" id="new-rune-props" class="fixed-width">
        <div data-role="header" data-theme="c" data-position="fixed">
            <h3 style="text-align: center">设定符文属性</h3>
        </div>
        <div data-role="content" data-theme="c">
            <div class="entity-title"></div>
            <table style="WIDTH: 100%">
                <tr>
                    <td>等级</td>
                    <td>
                        <select class="level" data-mini="true" data-native-menu="false">
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4" selected="selected">4</option>
                        </select>
                    </td>
                </tr>
            </table>
            <div>
                <a id="add-rune-button" data-role="button" data-mini="true">确定</a>
                <a href="javascript:history.go(-1)" data-role="button" data-mini="true">取消</a>
            </div>
        </div>
    </div>
    
    <div data-role="page" data-title="设定卡牌属性" data-mini="true" id="new-card-props" class="fixed-width">
        <div data-role="header" data-theme="c" data-position="fixed">
            <h3 style="text-align: center">设定卡牌属性</h3>
        </div>
        <div data-role="content" data-theme="c">
            <div class="entity-title"></div>
            <table style="WIDTH: 100%">
                <tr>
                    <td>等级</td>
                    <td>
                        <select class='level' data-mini="true" data-native-menu="false">
                            <option value="0">0</option>
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10" selected="selected">10</option>
                            <option value="11">11</option>
                            <option value="12">12</option>
                            <option value="13">13</option>
                            <option value="14">14</option>
                            <option value="15">15</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>数量</td>
                    <td>
                        <select class='count' data-mini="true" data-native-menu="false">
                            <option value="1" selected="selected">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="6">6</option>
                            <option value="7">7</option>
                            <option value="8">8</option>
                            <option value="9">9</option>
                            <option value="10">10</option>
                        </select>
                    </td>
            </table>
            <input type="checkbox" id="enable-extra-feature" data-mini="true" />
            <label for="enable-extra-feature">添加洗炼技能</label>
            <div id="extra-feature-props" style="DISPLAY: none">
                <table style="WIDTH: 100%">
                    <tr>
                        <td>技能</td>
                        <td>
                            <select id="extra-feature-name" data-mini="true" data-native-menu="false">
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>等级</td>
                        <td>
                            <select id="extra-feature-level" data-mini="true" data-native-menu="false">
                                <option value="1" selected="selected">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>特殊</td>
                        <td>
                            <div data-role="controlgroup" data-type="horizontal">
                                <input type="radio" data-mini="true" name="card-extra-feature-flag" id="card-extra-feature-none" value="" checked="checked" />
                                <label for="card-extra-feature-none">普通</label>
            
                                <input type="radio" data-mini="true" name="card-extra-feature-flag" id="card-extra-feature-summon" value="降临" />
                                <label for="card-extra-feature-summon">降临</label>
            
                                <input type="radio" data-mini="true" name="card-extra-feature-flag" id="card-extra-feature-death" value="死契" />
                                <label for="card-extra-feature-death">死契</label>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>
            <div>
                <a id="add-card-button" data-role="button" data-mini="true">确定</a>
                <a href="javascript:history.go(-1)" data-role="button" data-mini="true">取消</a>
            </div>
        </div>
    </div>

    <script src="http://s25.cnzz.com/stat.php?id=5496691&web_id=5496691&online=1&show=line"></script>
</body>
</html>