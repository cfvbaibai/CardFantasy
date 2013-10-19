<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="header.jsp"%>
<title>魔卡幻想模拟器</title>
<%
    String dataTheme = "c";
    String dataContentTheme = "d";
%>

</head>
<body>
    <div data-role="page" data-mini="true" data-title="竞技场战" id="main-container">
        <div data-role="header" data-id="navbar" data-position="fixed" data-theme="c">
            <div data-role="navbar" data-theme="c">
                <ul>
                    <li><a href="#boss-battle">魔神战</a></li>
                    <li><a href="#map-battle">地图战</a></li>
                    <li><a href="#main-container" class="ui-btn-active ui-state-persist">竞技场战</a></li>
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
                        <li>友情支持：<a href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Ff%3Fkw%3D%25C4%25A7%25BF%25A8%25BB%25C3%25CF%25EB%3Ffrom%3D1v1" target="_blank">魔卡幻想贴吧</a></li>                    </ul>
                </div>
            </div>
            <div data-role="collapsible" data-mini="true" data-collapsed="false" data-theme="c" data-content-theme="d">
                <h3>设置双方阵容</h3>
                <!-- <input type="hidden" id="firstAttack" name="firstAttack" value="-1" /> -->
                <!--  
                <div id="attackFirst" class="ui-grid-b" data-mini="true">
                    <div class="ui-block-a">
                        <input data-mini="true" type="radio" name="firstAttack" id="autoFirst" value="-1" checked="checked" />
                        <label for="autoFirst">先攻</label>
                    </div>
                    <div class="ui-block-b">
                        <input data-mini="true" type="radio" name="firstAttack" id="player1First" value="0" />
                        <label for="player1First">1先攻</label>
                    </div>
                    <div class="ui-block-c">
                        <input data-mini="true" type="radio" name="firstAttack" id="player2First" value="1" />
                        <label for="player2First">2先攻</label>
                    </div>
                </div>
                -->
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
                        <a data-role="button" data-rel="dialog" href="javascript:buildDeck('deck1')" data-mini="true">组卡</a>
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
                        <a data-role="button" data-rel="dialog" href="javascript:buildDeck('deck2')" data-mini="true">组卡</a>
                    </div>
                </div>
                <div>
                    <textarea data-theme="c" id="deck2" rows="5" cols="40" data-mini="true">战斗猛犸象+降临传送*5,堕落精灵+转生5*5,岩壁,赤谷,秽土,灼魂</textarea>
                </div>
            </div>
            <div id="command" data-mini="true" data-role="controlgroup" data-type="horizontal">
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:playAutoGame(1)">文字战斗</a>
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:playAutoGame(-1)">动画战斗</a>
                <!-- <a data-role="button" data-mini="true" data-theme="c" href="javascript:playAutoGame(100)">打100场</a> -->
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:playAutoGame(1000)">连续千场</a>
                <a data-role="button" data-mini="true" data-theme="c" href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Fp%2F2548422450%3Ffrom%3D1v1" target="blank">提BUG</a>
                <!-- <button data-theme="c" onclick="alert('按CTRL+A全选，再按CTRL+C')">拷贝</button> -->
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
                    <li><a href="#main-container">竞技场战</a></li>
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
                    <select data-theme="c" name="boss-name" id="boss-name" data-mini="true">
                        <option value="复仇女神">魔神：复仇女神</option>
                        <option value="邪龙之神">魔神：邪龙之神</option>
                        <option value="噩梦之主">魔神：噩梦之主</option>
                        <option value="毁灭之神">魔神：毁灭之神</option>
                        <option value="深渊影魔">魔神：深渊影魔</option>
                        <option value="万蛛之后">魔神：万蛛之后</option>
                    </select>

<!-- 军团加成：10级， -->
                    <fieldset data-theme="c" data-role="controlgroup" data-type="horizontal">
                        <select data-theme="c" name="buff-kingdom" id="buff-kingdom" data-mini="true">
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
                        <select data-theme="c" name="buff-savage" id="buff-savage" data-mini="true">
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
                        <select data-theme="c" name="buff-forest" id="buff-forest" data-mini="true">
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
                        <select data-theme="c" name="buff-hell" id="buff-hell" data-mini="true">
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
                            <a data-role="button" data-rel="dialog" href="javascript:buildDeck('deck')" data-mini="true">组卡</a>
                        </div>
                    </div>
                    <div>
                        <textarea data-theme="c" id="deck" name="deck" rows="5" cols="40" data-mini="true">堕落精灵*2,淬炼,绝杀</textarea>
                    </div>
                </div>
            </div>
            <div id="boss-command" data-mini="true" data-role="controlgroup" data-type="horizontal" data-disabled="false">
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:playBossGame(1)">文字战斗</a>
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:playBossGame(-1)">动画战斗</a>
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:playBossGame(1000)">卡组强度分析</a>
                <a data-role="button" data-mini="true" data-theme="c" href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Fp%2F2548422450%3Ffrom%3Dboss" target="blank">提BUG</a>
                <!--  <button data-mini="true" data-theme="c" onclick="playBossGame(1000)"></button>-->
                <!-- <button data-theme="c" onclick="alert('按CTRL+A全选，再按CTRL+C')">拷贝</button> -->
            </div>
            <div id="battle-div" data-mini="true" data-role="collapsible" data-collapsed="false" data-theme="c" data-content-theme="d">
                <h3>战斗记录</h3>
                <div id="boss-battle-output" class="battle-output">没有战斗</div>
            </div>
        </div>
    </div>

    <div data-role="page" data-title="地图战" data-mini="true" id="map-battle">
        <div data-role="header" data-id="navbar" data-position="fixed" data-theme="c">
            <div data-role="navbar" data-theme="c">
                <ul>
                    <li><a href="#boss-battle">魔神战</a></li>
                    <li><a href="#map-battle" class="ui-btn-active ui-state-persist">地图战</a></li>
                    <li><a href="#main-container">竞技场战</a></li>
                </ul>
            </div>
        </div>
        <div data-role="content">
            <p>功能尚未开放</p>
            <a href="#arena" data-role="button" data-rel="dialog" data-transition="flip">Open</a>
        </div>
    </div>
    
    <div data-role="page" data-title="战场" data-mini="true" id="arena">
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
                <a data-role="button" data-mini="true" data-theme="c" href="http://cnrdn.com/rd.htm?id=1344758&r=http%3A%2F%2Ftieba.baidu.com%2Fp%2F2548422450%3Ffrom%3Danimation" target="blank">提BUG</a>
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
                                            <select id="card-race-filter" data-mini="true" data-native-menu="false" onchange="filterCard()">
                                                <option value="KINGDOM">王国</option>
                                                <option value="FOREST">森林</option>
                                                <option value="SAVAGE">蛮荒</option>
                                                <option value="HELL">地狱</option>
                                            </select>
                                        </td>
                                        <td style="width: 40%">
                                            <select id="card-star-filter" data-mini="true" data-native-menu="false" onchange="filterCard()">
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
                            <div id="card-candidate" style="WIDTH:100%; HEIGHT: 100px; OVERFLOW: auto"></div>
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
                                            <select id="rune-class-filter" data-mini="true" data-native-menu="false" onchange="filterRune()">
                                                <option value="WATER">冰</option>
                                                <option value="WIND">风</option>
                                                <option value="GROUND">地</option>
                                                <option value="FIRE">火</option>
                                            </select>
                                        </td>
                                        <td style="width: 40%">
                                            <select id="rune-star-filter" data-mini="true" data-native-menu="false" onchange="filterRune()">
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
                            <div id="rune-candidate" style="WIDTH:100%; HEIGHT: 100px; OVERFLOW: auto"></div>
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
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:updateDeck()">确定</a>
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:history.go(-1)">取消</a>
            </div>
        </div>
    </div>

    <div data-role="page" data-title="设定符文属性" data-mini="true" id="new-rune-props">
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
                <a href="javascript:addRune()" data-role="button" data-mini="true">确定</a>
                <a href="javascript:history.go(-1)" data-role="button" data-mini="true">取消</a>
            </div>
        </div>
    </div>
    
    <div data-role="page" data-title="设定卡牌属性" data-mini="true" id="new-card-props">
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
            <input type="checkbox" id="enable-extra-feature" onclick="enableExtraFeature()" data-mini="true" />
            <label for="enable-extra-feature">添加洗炼技能</label>
            <div id="extra-feature-props" style="DISPLAY: none">
                <table style="WIDTH: 100%">
                    <tr>
                        <td>技能</td>
                        <td>
                            <select id="extra-feature-name" data-mini="true" data-native-menu="false" onchange="extraFeatureNameChanged()">
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
                <a href="javascript:addCard()" data-role="button" data-mini="true">确定</a>
                <a href="javascript:history.go(-1)" data-role="button" data-mini="true">取消</a>
            </div>
        </div>
    </div>

    <script src="http://s25.cnzz.com/stat.php?id=5496691&web_id=5496691&online=1&show=line"></script>
</body>
</html>