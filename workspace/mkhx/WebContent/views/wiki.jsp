<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="wiki" class="main-page" data-role="page" data-title="魔卡幻想WIKI" data-mini="true">
    <h1 style="display: none">魔卡幻想百科</h1>
    <h1 style="display: none">魔卡幻想WIKI</h1>
    <div id="wiki-content" data-role="content">
        <div id="wiki-card-filter">
            <div data-role="collapsible-set" data-theme="c" data-content-theme="d">
                <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                    <h3>搜索卡牌</h3>
                    <div>
                        <fieldset class="select-2" data-theme="c" data-role="controlgroup" data-type="horizontal">
                            <select id="wiki-card-star-filter" data-theme="c" data-mini="true" data-native-menu="false">
                                <option value="0">全部星数</option>
                                <option value="1">1星</option>
                                <option value="2">2星</option>
                                <option value="3">3星</option>
                                <option value="4">4星</option>
                                <option value="5" selected="selected">5星</option>
                            </select>
                            <select id="wiki-card-race-filter" data-theme="c" data-mini="true" data-native-menu="false">
                                <option value="0">全部种族</option>
                                <option value="1" selected="selected">王国</option>
                                <option value="2">森林</option>
                                <option value="3">蛮荒</option>
                                <option value="4">地狱</option>
                                <option value="97">魔王</option>
                                <option value="100">魔神</option>
                            </select>
                        </fieldset>
                        <table class="form">
                            <tr>
                                <td>技能</td>
                                <td>
                                    <input id="wiki-card-skill-type-filter" data-theme="c" data-mini="true" type="text"
                                        placeholder="在这里输入技能搜索关键字，例如：复活" />
                                </td>
                            </tr>
                        </table>
                    </div>
                    <a data-role="button" id="wiki-card-search" data-theme="c" data-mini="true">搜索卡牌</a>
                </div>
            </div>
        </div>
        <div id="wiki-result-panel-set" data-role="collapsible-set" data-theme="c" data-content-theme="d">
            <div data-role="collapsible" data-collapsed="true" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>显示全部技能</h3>
                <div id="wiki-skill-result" class="wiki-result">
                </div>
            </div>
            <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>搜索卡牌结果</h3>
                <div id="wiki-card-result" class="wiki-result" style="padding-top: 5px; min-height: 200px">
                </div>
            </div>
        </div>
    </div>
</div>