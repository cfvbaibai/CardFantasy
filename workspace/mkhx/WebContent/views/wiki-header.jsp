<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div class="header-template" data-role="header" data-theme="c" data-position="fixed">
    <h3 class="header-title">魔卡幻想WIKI<span class="wiki-home-link"> - <a href="<c:url value="/Wiki" />" target="_self">返回WIKI主页</a></span></h3>
    <a href="javascript:CardFantasy.Core.goBackOrGoto()" data-icon="arrow-l" data-iconpos="left" target="_self">上一页</a>
    <a href="<c:url value="/" />" target="_self" data-icon="home" data-iconpos="left">模拟器</a>
</div>
<div class="wiki-footer" data-role="footer" data-theme="c" data-position="fixed">
    <div data-role="navbar">
        <ul><li><a href="<c:url value="/Wiki" />" target="_self">返回WIKI主页</a></li></ul>
    </div>
</div>