<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="communication" class="main-page" data-role="page" data-title="交流区" data-mini="true">
    <div data-role="content">
        <div class="threads-template" style="display: none">
            <div class="thread-template" style="border-bottom: solid 1px #CCCCFF; padding: 10px">
                <div>
                    <div class="thread-sender-id" style="float: left; padding-right: 5px; font-weight: bold;"></div>
                    <div class="thread-content" style="float: left"></div>
                    <div style="clear: both"></div>
                </div>
                <div style="padding-top: 5px">
                    <div class="thread-command" style="float: left; padding-right: 10px; font-size: 70%">
                        <a class="thread-reply-button" href="#" onclick="CardFantasy.Communication.openReply(this);">回复</a>
                    </div>
                    <div class="thread-created" style="float: right; font-size: 70%"></div>
                    <div style="clear: both"></div>
                </div>
                <div class="thread-reply" style="display: none">
                    <table class="form">
                        <tr>
                            <td>回复人:</td>
                            <td><input type="text" class="thread-replier" maxlength="50" placeholder="请输入您的名字" data-mini="true" /></td>
                        </tr>
                    </table>
                    <textarea class="thread-reply-content" maxlength="400" placeholder="请在这里输入回复内容" data-mini="true"></textarea>
                    <a class="thread-send-reply-button" data-role="button" data-mini="true">回复</a>
                </div>
                <div class="thread-replies" style="border: solid 1px #CCCCFF; border-top: none; margin-top: 10px; background-color: #EEEEFF"></div>
            </div>
        </div>
        <div class="replies-template" style="display: none">
            <div class="reply-template">
                <div style="border-top: solid 1px #CCCCFF; padding: 5px; font-size: 70%">
                    <div class="reply-sender-id" style="float: left; padding-right: 10px; font-weight: bold;"></div>
                    <div class="reply-content" style="float: left"></div>
                    <div class="reply-created" style="float: right"></div>
                    <div style="clear: both"></div>
                </div>
            </div>
        </div>
        <div id="threads-head"></div>
        <div id="threads">
        </div>
        <div id="pager"></div>
        <div style="clear: both"></div>
        <div id="new-thread-table" class="frame-outer">
            <div class="frame-inner">
            <table class="form">
                <tr>
                    <td>留言人:</td>
                    <td><input type="text" name="feedback-sender" id="feedback-sender" maxlength="50" placeholder="输入您的名字" data-mini="true" /></td>
                </tr>
            </table>
            <textarea id="feedback" name="feedback" placeholder="请在这里输入留言内容" maxlength="400"></textarea>
            <a id="feedback-button" class="battle-button" data-role="button" data-mini="true">留言</a>
            <div id="feedback-message"></div>
            </div>
        </div>
    </div>
</div>