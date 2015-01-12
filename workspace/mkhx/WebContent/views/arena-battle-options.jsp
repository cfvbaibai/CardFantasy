<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <div id="battle-options" data-role="page" data-title="战斗选项设置" data-mini="true">
        <div data-role="header" data-theme="c" data-position="fixed">
            <h3 style="text-align: center">战斗选项设置</h3>
        </div>
        <div data-role="content" data-theme="c">
            <div id="battle-options-setter">
                <div>
                    <fieldset class="select-2" data-theme="c" data-role="controlgroup" data-type="horizontal">
                        <select data-theme="c" id="first-attack" data-mini="true" data-native-menu="false">
                            <option value="-1">先攻：按规则</option>
                            <option value="0">先攻：玩家1</option>
                            <option value="1">先攻：玩家2</option>
                        </select>
                        <select data-theme="c" id="deck-order" data-mini="true" data-native-menu="false">
                            <option value="0">出牌顺序：随机</option>
                            <option value="1">出牌顺序：指定</option>
                        </select>
                    </fieldset>
                    <table class="form">
                    <!-- 
                        <tr>
                            <td>玩家1英雄体力调整</td>
                            <td><input data-theme="c" type="number" id="p1-hero-hp-buff" data-mini="true" value="100" /></td>
                            <td>%</td>
                        </tr>
                     -->
                        <tr>
                            <td>玩家1卡牌攻击调整</td>
                            <td><input data-theme="c" type="number" id="p1-card-at-buff" data-mini="true" value="100" /></td>
                            <td>%</td>
                        </tr>
                        <tr>
                            <td>玩家1卡牌体力调整</td>
                            <td><input data-theme="c" type="number" id="p1-card-hp-buff" data-mini="true" value="100" /></td>
                            <td>%</td>
                        </tr>
                        <!-- 
                        <tr>
                            <td>玩家2英雄体力调整</td>
                            <td><input data-theme="c" type="number" id="p2-hero-hp-buff" data-mini="true" value="100" /></td>
                            <td>%</td>
                        </tr>
                         -->
                        <tr>
                            <td>玩家2卡牌攻击调整</td>
                            <td><input data-theme="c" type="number" id="p2-card-at-buff" data-mini="true" value="100" /></td>
                            <td>%</td>
                        </tr>
                        <tr>
                            <td>玩家2卡牌体力调整</td>
                            <td><input data-theme="c" type="number" id="p2-card-hp-buff" data-mini="true" value="100" /></td>
                            <td>%</td>
                        </tr>
                    </table>
                </div>
            </div>
            <div class="ui-grid-a" data-mini="true">
                <div class="ui-block-a">
                    <a id="update-battle-options-button" data-role="button" data-mini="true" data-theme="c">确定</a>
                </div>
                <div class="ui-block-b">
                    <a data-role="button" data-mini="true" data-theme="c" href="javascript:history.go(-1)">取消</a>
                </div>
            </div>
        </div>
    </div>