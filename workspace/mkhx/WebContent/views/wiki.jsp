<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="wiki" class="main-page" data-role="page" data-title="卡牌WIKI" data-mini="true">
    <div id="wiki-content" data-role="content">
        <div id="wiki-card-filter">
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
                            <option value="5">5星</option>
                        </select>
                        <select id="wiki-card-race-filter" data-theme="c" data-mini="true" data-native-menu="false">
                            <option value="All">全部种族</option>
                            <option value="KINGDOM">王国</option>
                            <option value="FOREST">森林</option>
                            <option value="SAVAGE">蛮荒</option>
                            <option value="HELL">地狱</option>
                        </select>
                    </fieldset>
                </div>
                <a data-role="button" id="wiki-card-search" class="ui-disabled" data-theme="c" data-mini="true">搜索</a>
            </div>
        </div>
        <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
            <h3>搜索结果</h3>
            <div id="wiki-result-content">
            </div>
        </div>
    </div>
</div>