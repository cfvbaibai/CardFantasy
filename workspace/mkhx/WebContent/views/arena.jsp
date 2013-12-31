<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <div id="arena" data-role="page" data-title="战场" data-mini="true" class="fixed-width">
        <div data-role="header" data-theme="c" data-position="fixed">
            <h3 style="text-align: center">战斗</h3>
        </div>
        <div data-role="content" data-theme="c">
            <div id="canvas-outline">
                <div>若动画空白，请重启浏览器</div>
                <div id="battle-canvas"></div>
            </div>
            <div id="arena-control-panel" data-mini="true" data-role="controlgroup" data-type="horizontal">
                <a id="play-button" data-role="button" data-mini="true" data-theme="c"></a>
                <a id="faster-button" data-role="button" data-mini="true" data-theme="c">加快</a>
                <a id="slower-button" data-role="button" data-mini="true" data-theme="c">减慢</a>
                <a id="back-button" data-role="button" data-mini="true" data-theme="c" href="javascript:history.go(-1)">返回</a>
                <a data-role="button" data-mini="true" data-theme="c" href="#communication" target="blank">提BUG</a>
            </div>
            <div id="player-status"></div>
        </div>
    </div>