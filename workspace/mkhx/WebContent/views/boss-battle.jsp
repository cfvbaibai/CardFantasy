<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <div id="boss-battle" class="main-page" data-role="page" data-title="魔神战" data-mini="true">
        <div id="boss-battle-content" data-role="content">
            <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>设置阵容</h3>
                <div>
                    <select data-theme="c" name="boss-name" id="boss-name" data-mini="true" data-native-menu="false">
                        <option value="复仇女神">复仇女神(无杂兵)</option>
                        <option value="邪龙之神">邪龙之神(无杂兵)</option>
                        <option value="噩梦之主">噩梦之主(无杂兵)</option>
                        <option value="毁灭之神">毁灭之神(无杂兵)</option>
                        <option value="深渊影魔">深渊影魔(无杂兵)</option>
                        <option value="万蛛之后">万蛛之后(无杂兵)</option>
                        <!-- 
                        <option value="旧复仇女神">旧魔神：复仇女神</option>
                        <option value="旧邪龙之神">旧魔神：邪龙之神</option>
                        <option value="旧噩梦之主">旧魔神：噩梦之主</option>
                        <option value="旧毁灭之神">旧魔神：毁灭之神</option>
                        <option value="旧深渊影魔">旧魔神：深渊影魔</option>
                        <option value="旧万蛛之后">旧魔神：万蛛之后</option>
                         -->
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
                <a data-role="button" data-mini="true" data-theme="c" data-type="bug" href="#">提BUG</a>
            </div>
            <div id="battle-div" data-mini="true" data-role="collapsible" data-collapsed="false" data-theme="c" data-content-theme="d">
                <h3>战斗记录</h3>
                <div id="boss-battle-output" class="battle-output">没有战斗</div>
            </div>
        </div>
    </div>