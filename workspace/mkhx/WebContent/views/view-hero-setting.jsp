<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/CardFantasyTags.tld" %>
<%@ include file="header.jsp"%>
<title>魔卡幻想WIKI</title>
<meta name="description" content="英雄体力表" />
<meta name="keywords" content="魔卡幻想WIKI 英雄体力表" />
</head>
<body>
    <div class="main-page" data-role="page">
    <%@ include file="wiki-header.jsp" %>
    <div data-role="content">
    <table class="wiki-table">
        <tbody>
            <tr>
                <td class="title" colspan="4">英雄数据表</td>
            </tr>
            <tr>
                <td class="label">等级</td>
                <td class="label">体力</td>
                <td class="label">COST</td>
            </tr>
            <c:forEach var="levelSetting" items="${heroSetting.levelSettings}">
            <tr>
                <td class="value">${levelSetting.level}</td>
                <td class="value">${levelSetting.maxHP}</td>
                <td class="value">${levelSetting.maxCost}</td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
    </div>
    </div>
</body>
</html>