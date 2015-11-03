<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <div id="arena" data-role="page" data-title="战场" data-mini="true" class="fixed-width">
        <div data-role="header" data-position="fixed">
            <h3 style="text-align: center">战斗</h3>
        </div>
        <div data-role="content">
            <div id="canvas-outline">
                <div>若动画空白，请重启浏览器</div>
                <div id="battle-canvas"></div>
            </div>
            <div id="video-content-panel" style="display: none">
                <div style="width: 100%; text-align: center">录像数据</div>
                <textarea id="video-content" style="max-height: 200px; resize: none; height: 200px; width: 98%; overflow-y: auto; word-wrap: break-word; margin-left: auto; margin-right: auto">
                </textarea>
                <div style="padding: 5px; width: 100%; font-size: smaller">
                    <a id="copy-video-button" data-role="button" data-mini="true" href="#" data-clipboard-target="video-content">点击复制录像数据（手机请自行复制）</a>
                </div>
            </div>
            <div id="arena-control-panel" data-mini="true" data-role="controlgroup" data-type="horizontal">
                <a id="play-button" data-role="button" data-mini="true"></a>
                <a id="faster-button" data-role="button" data-mini="true">加快</a>
                <a id="slower-button" data-role="button" data-mini="true">减慢</a>
                <!-- <a id="back-button" data-role="button" data-mini="true" href="javascript:history.go(-1)">返回</a> -->
                <a id="save-video-button" data-role="button" data-mini="true">保存</a>
                <a id="report-bug-button" data-role="button" data-mini="true" data-type="bug" href="#">提BUG</a>
            </div>
            <div id="player-status"></div>
        </div>
    </div>