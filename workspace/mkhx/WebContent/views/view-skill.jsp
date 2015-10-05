<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>
<title>魔卡幻想技能信息</title>
<meta name="description" content="${skillType}" />
<meta name="keywords" content="${skillType}" />
</head>
<body>
    <%@ include file="wiki-header.jsp" %>
    <table class="view-skill-table wiki-table">
        <tbody>
            <tr>
                <td id="view-skill-type" class="title" colspan="4">${skillType}</td>
            </tr>
            <tr class="head">
                <td class="label">名称</td>
                <td class="label">描述</td>
                <td class="label">卡牌</td>
                <td class="label">符文</td>
            </tr>
            <c:forEach var="skillInfo" items="${skillInfos}">
            <tr class="content">
                <td>${skillInfo.name}</td>
                <td>${skillInfo.description}</td>
                <td class="content">
                    <c:forEach var="owningCard" items="${skillInfo.owningCards}">
                    <div class="item">
                        <a href="../Cards/${owningCard.cardName}">${owningCard.cardName}</a>
                    </div>
                    </c:forEach>
                    <div></div>
                </td>
                <td>
                    <c:forEach var="owningRune" items="${skillInfo.owningRunes}">
                        <a href="../Runes/${owningRune.runeName}">${owningRune.runeName}</a>
                    </c:forEach>
                </td>
            </tr>
            </c:forEach>    
        </tbody>
    </table>
    <!-- CNZZ Begins -->
    <script src="http://s25.cnzz.com/stat.php?id=5496691&web_id=5496691&online=1"></script>
    <!-- CNZZ Ends -->
</body>
</html>