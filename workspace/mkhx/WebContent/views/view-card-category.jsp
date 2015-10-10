<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/CardFantasyTags.tld" %>
<%@ include file="header.jsp"%>
<title>魔卡幻想WIKI - ${category}</title>
<meta name="description" content="${category}" />
<meta name="keywords" content="${category}" />
</head>
<body>
    <%@ include file="wiki-header.jsp" %>
    <table class="view-category-table wiki-table">
        <tbody>
            <tr>
                <td class="title" colspan="2">
                    <div>${category}</div>
                    <c:if test="${raceName != null}">
                    <div><cf:raceIcon raceName="${raceName}" /></div>
                    </c:if>
                </td>
            </tr>
            <c:forEach var="subCategory" items="${subCategories}">
            <tr>
                <td class="label">
                    <div>${subCategory.name}</div>
                    <c:if test="${star != null && !subCategory.name.equals('素材')}">
                    <div><cf:raceIcon raceName="${subCategory.name}" /></div>
                    </c:if>
                </td>
                <td class="logos-container">
                    <c:forEach var="card" items="${subCategory.items}">
                        <cf:cardLogo cardName="${card.cardName}" />
                    </c:forEach>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>