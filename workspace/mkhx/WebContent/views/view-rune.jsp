<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/CardFantasyTags.tld" %>
<%@ include file="header.jsp"%>
<title>${runeName} - 魔卡幻想WIKI</title>
<meta name="description" content="${runeName}" />
<meta name="keywords" content="${runeName}" />
</head>
<body>
    <%@ include file="wiki-header.jsp" %>
    <table class="view-rune-table wiki-table">
        <tbody>
            <tr>
                <td id="view-rune-name" class="title" colspan="4">
                    <div>${runeName}</div>
                    <div><cf:runeLogo runeName="${runeName}" runeNameVisible="false" starBarVisible="false"  /></div>
                </td>
            </tr>
            <tr>
                <td class="label">属性</td>
                <td id="view-rune-property" class="value">${runeInfo.propertyName}</td>
                <td class="label">星级</td>
                <td id="view-rune-star" class="value">${runeInfo.star}</td>
            </tr>
            <tr>
                <td class="label">出售价格</td>
                <td id="view-rune-price" class="value">${runeInfo.price}</td>
                <td class="label">冥想可得</td>
                <td id="view-rune-think-get" class="value">${runeInfo.thinkGet ? "是" : "否"}</td>
            </tr>
            <tr>
                <td class="label">发动条件</td>
                <td id="view-rune-condition" class="value">${runeInfo.condition}</td>
                <td class="label">碎片兑换</td>
                <td id="view-rune-fragment" class="value">${runeInfo.fragmentDesc}</td>
            </tr>
        </tbody>
    </table>
    <table class="view-rune-table wiki-table">
        <tbody>
            <tr>
                <td class="title" colspan="3">符文技能</td>
            </tr>
            <c:forEach var="i" begin="1" end="5" step="1">
            <tr>
                <td class="label">LEVEL ${i - 1}</td>
                <td class="skill-category-icon">
                    <cf:skillCategoryIcon categoryId="${runeInfo.getSkill(i).category}" />
                </td>
                <td id="view-rune-skill${i}" class="skill" colspan="4">
                    <div><a href="../Skills/${runeInfo.getSkill(i).name}" target="_self">${runeInfo.getSkill(i).name}</a></div>
                    <div>${runeInfo.getSkill(i).description}</div>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>