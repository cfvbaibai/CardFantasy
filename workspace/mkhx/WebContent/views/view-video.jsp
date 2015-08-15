<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="view-video-page" class="main-page" data-role="page" data-title="播放录像" data-mini="true">
    <div id="view-video-content" data-role="content">
        <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
            <h3>设置阵容</h3>
            <div>
                <div style="width: 100%; text-align: center">复制录像数据到下面文本框</div>
                <textarea id="video-content-to-view" data-theme="c" id="deck" name="deck" data-mini="true"
                    style="min-height: 250px; max-height: 250px; overflow-y: auto; word-wrap: break-word; resize: none"></textarea>
            </div>
        </div>
        <div id="view-video-command" data-mini="true" data-role="controlgroup" data-type="horizontal" data-disabled="false">
            <a id="view-video-button" data-role="button" data-mini="true" data-theme="c">播放录像</a>
            <a data-role="button" data-mini="true" data-theme="c" data-type="bug" href="#">提BUG</a>
        </div>
    </div>
</div>