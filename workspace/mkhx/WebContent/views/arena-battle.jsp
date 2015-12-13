<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <div id="arena-battle" class="main-page" data-role="page" data-title="竞技场战" data-mini="true" data-theme="${theme}">
        <div data-role="content">
            <div data-role="collapsible" data-mini="true" data-collapsed="false">
                <h3>设置双方阵容</h3>
                <div>
                    <a id="show-arena-battle-options-button" data-role="button" data-rel="dialog" data-mini="true">点击设置战斗规则</a>
                    <div id="arena-battle-options-text"></div>
                    <div id="player1" class="player ui-grid-c">
                        <div class="ui-block-a ui-block-label-number">
                            <span>玩家1等级: </span>
                        </div>
                        <div class="ui-block-b">
                            <input type="number" id="hero1Lv" data-mini="true" value="75" />
                        </div>
                        <div class="ui-block-c ui-block-label-number">
                            <span>玩家1卡组: </span>
                        </div>
                        <div class="ui-block-d">
                            <a id="build-deck1-button" data-role="button" data-rel="dialog" data-mini="true">组卡</a>
                        </div>
                    </div>
                    <div>
                        <textarea id="deck1" rows="5" cols="40" data-mini="true">降临天使-10,羽蛇神-10,恶魔猎人-10,圣剑持有者-10,幻想炼金士-10,福音乐师-10,铁血剑豪-10,元素灵龙-10,震源岩蟾-10,冰封,清泉,赤谷,霜冻</textarea>
                    </div>
                    <div id="player2" class="player ui-grid-c">
                        <div class="ui-block-a ui-block-label-number">
                            <span>玩家2等级: </span>
                        </div>
                        <div class="ui-block-b">
                            <input type="number" id="hero2Lv" data-mini="true" value="75" />
                        </div>
                        <div class="ui-block-c ui-block-label-number">
                            <span>玩家2卡组: </span>
                        </div>
                        <div class="ui-block-d">
                            <a id="build-deck2-button" data-role="button" data-rel="dialog" data-mini="true">组卡</a>
                        </div>
                    </div>
                    <div>
                        <textarea id="deck2" rows="5" cols="40" data-mini="true">熊人武士+蛮荒之力3-15,熊人武士+不动-12,蜘蛛人女王+不动-15,蜘蛛人女王+暴击5-12,水源制造者+森林之力4-15,水源制造者+森林守护4-14,元素灵龙+不动-15,小矮人狙击者+森林守护3-15,雷兽+格挡8-11,暴怒霸龙+吸血6-15,石林-3,扬旗-3,雷盾-3,赤谷-3</textarea>
                    </div>
                </div>
            </div>
            <div id="command" data-mini="true" data-role="controlgroup" data-type="horizontal">
                <a id="play-auto-1-game-button" class="battle-button" data-role="button" data-mini="true">文字战斗</a>
                <a id="simulate-auto-1-game-button" class="battle-button" data-role="button" data-mini="true">动画战斗</a>
                <a id="play-auto-massive-game-button" class="battle-button" data-role="button" data-mini="true">连续千场</a>
                <a data-role="button" data-mini="true" data-type="bug" href="#">提BUG</a>
            </div>
            <div id="arena-battle-div" data-mini="true" data-role="collapsible" data-collapsed="false">
                <h3>战斗记录</h3>
                <div id="battle-output" class="battle-output">没有战斗</div>
            </div>
        </div>
    </div>