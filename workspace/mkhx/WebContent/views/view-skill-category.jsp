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
    <table class="view-skill-category-table wiki-table">
        <tbody>
            <tr>
                <td class="title" colspan="2">
                    <div><cf:skillCategoryIcon categoryId="${categoryId}" /></div>
                    <div>${category}</div>
                </td>
            </tr>
            <c:forEach var="subCategory" items="${subCategories}">
            <tr>
                <td class="label">${subCategory.name}</td>
                <td class="content">
                    <c:forEach var="skillType" items="${subCategory.items}">
                        <div><a href="<c:url value="/Wiki" />/Skills/${skillType}" target="_self">${skillType}</a></div>
                    </c:forEach>
                    <div></div>
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