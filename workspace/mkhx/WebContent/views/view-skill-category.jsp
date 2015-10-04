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
                    ${category}
                </td>
            </tr>
            <tr>
                <td class="content">
                    <c:forEach var="skillType" items="${skillTypes}">
                        <div><a href="<c:url value="/" />Wiki/Skills/${skillType}">${skillType}</a></div>
                    </c:forEach>
                    <div></div>
                </td>
            </tr>
        </tbody>
    </table>
    <!-- CNZZ Begins -->
    <script src="http://s25.cnzz.com/stat.php?id=5496691&web_id=5496691&online=1"></script>
    <!-- CNZZ Ends -->
</body>
</html>