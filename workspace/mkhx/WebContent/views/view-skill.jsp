<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>
<title>魔卡幻想技能信息</title>
<meta name="description" content="${skillType}" />
<meta name="keywords" content="${skillType}" />
<script>
</script>
</head>
<body>
    <table class="view-skill-table">
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
                <td>
                    <c:forEach var="owningCard" items="${skillInfo.owningCards}">
                        <a href="../Cards/${owningCard.cardName}" target="_blank">${owningCard.cardName}</a>
                    </c:forEach>
                </td>
                <td></td>
            </tr>
            </c:forEach>    
        </tbody>
    </table>
    <!-- CNZZ Begins -->
    <script src="http://s25.cnzz.com/stat.php?id=5496691&web_id=5496691&online=1"></script>
    <!-- CNZZ Ends -->
</body>
</html>