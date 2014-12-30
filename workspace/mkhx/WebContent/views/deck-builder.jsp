<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <div id="deck-builder" data-role="page" data-title="组卡" data-mini="true">
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
            <div class="ui-grid-a" id="deck-builder-control-panel" data-mini="true">
                <div class="ui-block-a">
                    <a id="update-deck-button" data-role="button" data-mini="true" data-theme="c">确定</a>
                </div>
                <div class="ui-block-b">
                    <a data-role="button" data-mini="true" data-theme="c" href="javascript:history.go(-1)">取消</a>
                </div>
            </div>
        </div>
    </div>

    <div data-role="page" data-title="设定符文属性" data-mini="true" id="new-rune-props" class="fixed-width">
        <div data-role="header" data-theme="c" data-position="fixed">
            <h3 style="text-align: center">设定符文属性</h3>
        </div>
        <div data-role="content" data-theme="c">
            <div class="entity-title">
                <span class="entity-title-text"></span>
                <span><a class="entity-detail-button" target="_blank" data-role="button" data-mini="true" data-inline="true">详情</a></span>
            </div>
            <table class="form">
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
            <div class="ui-grid-a">
                <div class="ui-block-a">
                    <a id="add-rune-button" data-role="button" data-mini="true">确定</a>
                </div>
                <div class="ui-block-b">
                    <a href="javascript:history.go(-1)" data-role="button" data-mini="true">取消</a>
                </div>
            </div>
        </div>
    </div>
    
    <div data-role="page" data-title="设定卡牌属性" data-mini="true" id="new-card-props" class="fixed-width">
        <div data-role="header" data-theme="c" data-position="fixed">
            <h3 style="text-align: center">设定卡牌属性</h3>
        </div>
        <div data-role="content" data-theme="c">
            <div class="entity-title">
                <span class="entity-title-text"></span>
                <span><a class="entity-detail-button" target="_blank" data-role="button" data-mini="true" data-inline="true">详情</a></span>
            </div>
            <table class="form">
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
                <table class="form">
                    <tr>
                        <td>技能</td>
                        <td>
                            <table>
                                <tr>
                                    <td style="WIDTH: 100%">
                                        <select id="extra-feature-name" data-mini="true" data-native-menu="false">
                                        </select>
                                    </td>
                                    <td>
                                        <a class="feature-detail-button" target="_blank" data-role="button" data-mini="true" data-inline="true">详情</a>
                                    </td>
                                </tr>
                            </table>
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
            <div class="ui-grid-a">
                <div class="ui-block-a">
                    <a id="add-card-button" data-role="button" data-mini="true">确定</a>
                </div>
                <div class="ui-block-b">
                    <a href="javascript:history.go(-1)" data-role="button" data-mini="true">取消</a>
                </div>
            </div>
        </div>
    </div>