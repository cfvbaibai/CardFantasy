<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="view-video-page" class="main-page" data-role="page" data-title="播放录像" data-mini="true" data-theme="${theme}">
    <div id="view-video-content" data-role="content">
        <div data-role="collapsible" data-collapsed="false" data-mini="true">
            <h3>复制录像数据到下面文本框</h3>
            <div>
                <textarea id="video-content-to-view" id="deck" name="deck" data-mini="true"
                    style="min-height: 250px; max-height: 250px; overflow-y: auto; word-wrap: break-word; resize: none"></textarea>
            </div>
        </div>
        <div id="view-video-command" data-mini="true" data-role="controlgroup" data-type="horizontal" data-disabled="false">
            <a id="view-video-button" data-role="button" data-mini="true">播放录像</a>
            <a data-role="button" data-mini="true" data-type="bug" href="#">提BUG</a>
        </div>
    </div>
</div>