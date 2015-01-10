<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="lilith-battle" class="main-page" data-role="page" data-title="莉莉丝战" data-mini="true">
    <div data-role="content">
        <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
            <h3>设置阵容</h3>
            <div>
                <fieldset class="select-6-4" data-theme="c" data-role="controlgroup" data-type="horizontal">
                    <select data-theme="c" id="lilith-name" data-mini="true" data-native-menu="false">
                    <c:forEach items="${lilithDatas}" var="data">
                        <option value="<c:out value="${data.bossId}" />"> <c:out value="${data.bossId}" /></option>
                    </c:forEach>
                    </select>
                    <select data-theme="c" id="lilith-game-type" data-mini="true" data-native-menu="false">
                        <option value="0">清怪模式</option>
                        <option value="1">尾刀模式</option>
                    </select>
                </fieldset>
                <div id="lilith-config-0" class="ui-grid-a">
                    <div class="ui-block-a ui-block-label-number">清怪至剩余怪数(包括莉莉丝)：</div>
                    <div class="ui-block-b">
                        <input data-theme="c" type="number" id="lilith-target-remaining-guard-count" data-mini="true" value="2" />
                    </div>
                </div>
                <div id="lilith-config-1" class="ui-grid-a">
                    <div class="ui-block-a ui-block-label-number">莉莉丝剩余HP：</div>
                    <div class="ui-block-b">
                        <input data-theme="c" type="number" id="lilith-remaining-hp" data-mini="true" value="300000" />
                    </div>
                </div>
                <div id="lilith-player" class="player ui-grid-c">
                    <div class="ui-block-a ui-block-label-number">
                        <span>玩家等级: </span>
                    </div>
                    <div class="ui-block-b">
                        <input data-theme="c" type="number" id="lilith-player-heroLv" name="lilith-player-heroLv" data-mini="true" value="75" />
                    </div>
                    <div class="ui-block-c ui-block-label-number">
                        <span>玩家卡组: </span>
                    </div>
                    <div data-theme="c" class="ui-block-d">
                        <a id="build-lilith-deck-button" data-role="button" data-rel="dialog" data-mini="true">组卡</a>
                    </div>
                </div>
                <div>
                    <textarea data-theme="c" id="lilith-player-deck" name="lilith-player-deck" rows="5" cols="40" data-mini="true">堕落精灵*2,淬炼,绝杀</textarea>
                </div>
            </div>
            <div id="lilith-command" data-mini="true" data-role="controlgroup" data-type="horizontal" data-disabled="false">
                <a id="play-lilith-1-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">文字战斗</a>
                <a id="simulate-lilith-1-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">动画战斗</a>
                <a id="play-lilith-massive-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">卡组强度分析</a>
                <a data-role="button" data-mini="true" data-theme="c" data-type="bug" href="#">提BUG</a>
            </div>
            <div id="lilith-battle-div" data-mini="true" data-role="collapsible" data-collapsed="false" data-theme="c" data-content-theme="d">
                <h3>战斗记录</h3>
                <div id="lilith-battle-output" class="battle-output">没有战斗</div>
            </div>
        </div>
    </div>
</div>