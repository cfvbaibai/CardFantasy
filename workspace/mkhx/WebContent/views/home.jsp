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
                        <li>在下面的输入框里输入卡组符文的信息，卡牌与符文之间用半角的逗号隔开，不要留多余的空格，例如： 金属巨龙,降临天使,冰封,永冻</li>
                        <li>默认卡牌等级10级，符文等级4级，想要改变等级的话，在名称后面添加"-数字"。例如10级金属就是"金属巨龙-10"。</li>
                        <li>重复添加同一个卡牌可以使用星号，例如"凤凰*5"表示5张10级凤凰，"凤凰-15*5"表示5张15级凤凰，注意符文不能重复。</li>
                        <li>可以使用加号设置卡牌的洗炼技能，例如"凤凰+转生5-15*5"表示5张15级的转生5的凤凰。</li>
                        <li>洗炼技能也可以设定成降临或者死契，例如"骷髅法师+降临火墙3"表示有了降临火墙的骷髅法师，"独眼巨人+死契摧毁"表示有了死契摧毁的独眼巨人。</li>
                        <li>设置了洗炼技能的卡牌默认15级，所以"凤凰+转生5"等同于"凤凰+转生5-15"。</li>
                        <li>制作人：<a href="http://tieba.baidu.com/i/13567034/profile" target="_blank">白白</a></li>
                        <li>友情支持：<a href="http://tieba.baidu.com/f?kw=%C4%A7%BF%A8%BB%C3%CF%EB" target="_blank">魔卡幻想贴吧</a></li>
                    </ul>
                </div>
            </div>
            <div data-role="collapsible" data-mini="true" data-collapsed="false" data-theme="c" data-content-theme="d">
                <h3>设置双方阵容</h3>
                <input type="hidden" id="firstAttack" name="firstAttack" value="-1" />
                <!-- 
                <div id="attackFirst" class="ui-grid-b" data-mini="true">
                    <div class="ui-block-a">
                        <input data-mini="true" type="radio" name="firstAttack" id="autoFirst" value="-1" checked="checked" />
                        <label for="autoFirst">自然先攻</label>
                    </div>
                    <div class="ui-block-b">
                        <input data-mini="true" type="radio" name="firstAttack" id="player1First" value="0" />
                        <label for="player1First">玩家1先攻</label>
                    </div>
                    <div class="ui-block-c">
                        <input data-mini="true" type="radio" name="firstAttack" id="player2First" value="1" />
                        <label for="player2First">玩家2先攻</label>
                    </div>
                </div>
                -->
                <div id="player1" class="player ui-grid-b">
                    <div class="ui-block-a ui-block-label-number">
                        <span>玩家1等级: </span>
                    </div>
                    <div class="ui-block-b">
                        <input type="number" id="hero1Lv" data-mini="true" value="75" />
                    </div>
                    <div class="ui-block-c ui-block-label-number">
                        <span>玩家1卡组: </span>
                    </div>
                </div>
                <div>
                    <textarea id="deck1" rows="5" cols="40" data-mini="true">金属巨龙*5,降临天使*5,冰封,永冻,雷盾,春风</textarea>
                </div>
                <div id="player2" class="player ui-grid-b">
                    <div class="ui-block-a ui-block-label-number">
                        <span>玩家2等级: </span>
                    </div>
                    <div class="ui-block-b">
                        <input type="number" id="hero2Lv" data-mini="true" value="75" />
                    </div>
                    <div class="ui-block-c ui-block-label-number">
                        <span>玩家2卡组: </span>
                    </div>
                </div>
                <div>
                    <textarea id="deck2" rows="5" cols="40" data-mini="true">战斗猛犸象+降临传送*5,堕落精灵+转生5*5,岩壁,赤谷,秽土,灼魂</textarea>
                </div>
            </div>
            <div id="command" data-mini="true" data-role="controlgroup" data-type="horizontal">
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:playAutoGame(1)">文字战斗</a>
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:playAutoGame(-1)">动画战斗</a>
                <!-- <a data-role="button" data-mini="true" data-theme="c" href="javascript:playAutoGame(100)">打100场</a> -->
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:playAutoGame(1000)">连续千场</a>
                <a data-role="button" data-mini="true" data-theme="c" href="http://tieba.baidu.com/p/2548422450" target="blank">提BUG</a>
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
                    <ul>
                        <li>在下面的输入框里输入卡组符文的信息，卡牌与符文之间用半角的逗号隔开，不要留多余的空格，例如： 金属巨龙,降临天使,冰封,永冻</li>
                        <li>默认卡牌等级10级，符文等级4级，想要改变等级的话，在名称后面添加"-数字"。例如10级金属就是"金属巨龙-10"。</li>
                        <li>重复添加同一个卡牌可以使用星号，例如"凤凰*5"表示5张10级凤凰，"凤凰-15*5"表示5张15级凤凰，注意符文不能重复。</li>
                        <li>可以使用加号设置卡牌的洗炼技能，例如"凤凰+转生5-15*5"表示5张15级的转生5的凤凰。</li>
                        <li>洗炼技能也可以设定成降临或者死契，例如"骷髅法师+降临火墙3"表示有了降临火墙的骷髅法师，"独眼巨人+死契摧毁"表示有了死契摧毁的独眼巨人。</li>
                        <li>设置了洗炼技能的卡牌默认15级，所以"凤凰+转生5"等同于"凤凰+转生5-15"。</li>
                        <li>制作人：<a href="http://tieba.baidu.com/i/13567034/profile" target="_blank">白白</a></li>
                        <li>友情支持：<a href="http://tieba.baidu.com/f?kw=%C4%A7%BF%A8%BB%C3%CF%EB" target="_blank">魔卡幻想贴吧</a></li>
                    </ul>
                </div>
            </div>
            <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>设置阵容</h3>
                <div>
                    <select name="boss-name" id="boss-name" data-mini="true">
                        <option value="复仇女神">魔神：复仇女神</option>
                        <option value="邪龙之神">魔神：邪龙之神</option>
                        <option value="噩梦之主">魔神：噩梦之主</option>
                        <option value="毁灭之神">魔神：毁灭之神</option>
                        <option value="深渊影魔">魔神：深渊影魔</option>
                        <option value="万蛛之后">魔神：万蛛之后</option>
                    </select>

<!-- 军团加成：10级， -->
                    <fieldset data-role="controlgroup" data-type="horizontal">
                        <select name="buff-kingdom" id="buff-kingdom" data-mini="true">
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
                        <select name="buff-savage" id="buff-savage" data-mini="true">
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
                    <fieldset data-role="controlgroup" data-type="horizontal">
                        <select name="buff-forest" id="buff-forest" data-mini="true">
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
                        <select name="buff-hell" id="buff-hell" data-mini="true">
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
                    
                    <div id="player" class="player ui-grid-b">
                        <div class="ui-block-a ui-block-label-number">
                            <span>玩家等级: </span>
                        </div>
                        <div class="ui-block-b">
                            <input type="number" id="heroLv" name="heroLv" data-mini="true" value="75" />
                        </div>
                        <div class="ui-block-c ui-block-label-number">
                            <span>玩家卡组: </span>
                        </div>
                    </div>
                    <div>
                        <textarea id="deck" name="deck" rows="5" cols="40" data-mini="true">堕落精灵*2,淬炼,绝杀</textarea>
                    </div>
                </div>
            </div>
            <div id="command" data-mini="true" data-role="controlgroup" data-type="horizontal" data-disabled="false">
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:playBossGame(1)">模拟一次战斗</a>
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:playBossGame(-1)">观看一次战斗</a>
                <a data-role="button" data-mini="true" data-theme="c" href="javascript:playBossGame(1000)">卡组强度分析</a>
                <!--  <button data-mini="true" data-theme="c" onclick="playBossGame(1000)"></button>-->
                <!-- <button data-theme="c" onclick="alert('按CTRL+A全选，再按CTRL+C')">拷贝</button> -->
            </div>
            <div id="battle-div"data-mini="true" data-role="collapsible" data-collapsed="false" data-theme="c" data-content-theme="d">
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
            <h3>战斗</h3>
        </div>
        <div data-role="content" data-theme="c">
            <div id="canvas-outline" style="overflow: auto; margin-left: auto; margin-right: auto">
                <div id="battle-canvas">&nbsp;</div>
            </div>
            <a href="javascript:history.go(-1)" data-role="button">返回</a>
        </div>
    </div>
    <script src="http://s25.cnzz.com/stat.php?id=5496691&web_id=5496691&online=1&show=line"></script>
</body>
</html>