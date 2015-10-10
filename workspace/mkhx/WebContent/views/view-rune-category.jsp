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
                    <c:if test="${propertyName != null}">
                    <div><cf:propertyIcon propertyName="${propertyName}" /></div>
                    </c:if>
                    <div>${category}</div>
                </td>
            </tr>
            <c:forEach var="subCategory" items="${subCategories}">
            <tr>
                <td class="label">
                    <c:if test="${propertyName == null}">
                    <div><cf:propertyIcon propertyName="${subCategory.name}" /></div>
                    </c:if>
                    <div>${subCategory.name}</div>
                </td>
                <td>
                    <div class="logos-container">
                    <c:forEach var="rune" items="${subCategory.items}">
                        <cf:runeLogo runeName="${rune.runeName}" />
                    </c:forEach>
                    </div>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>