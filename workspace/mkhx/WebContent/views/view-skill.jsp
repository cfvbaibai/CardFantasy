<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/CardFantasyTags.tld" %>
<%@ include file="header.jsp"%>
<title>魔卡幻想技能信息</title>
<meta name="description" content="${skillType}" />
<meta name="keywords" content="${skillType}" />
</head>
<body>
    <%@ include file="wiki-header.jsp" %>
    <c:set var="uniqueSkill" value="${skillInfos.size() == 1 && skillInfos.get(0).name.equals(skillType)}" />
    <table class="view-skill-table wiki-table">
        <tbody>
            <tr>
                <td id="view-skill-type" class="title" colspan="4">
                    <div><cf:skillCategoryIcon categoryId="${skillInfos.get(0).categoryId}" /></div>
                    <div>${skillType}</div>
                </td>
            </tr>
            <c:if test="${!uniqueSkill}">
            <tr class="head">
                <td class="label">名称</td>
                <td class="label">描述</td>
            </tr>
            </c:if>
            <c:forEach var="skillInfo" items="${skillInfos}">
            <c:set var="anyOwning" value="${skillInfo.owningCards.size() + skillInfo.owningRunes.size() > 0}" />
            <tr class="content">
                <c:if test="${!uniqueSkill}">
                    <td class="skill-name" rowspan="${anyOwning ? 3 : 1}">${skillInfo.name}</td>
                </c:if>
                <td class="skill-desc">${skillInfo.description}</td>
            </tr>
            <c:if test="${anyOwning}">
            <tr><td class="label small">拥有者</td></tr>
            <tr class="owning">
                <td>
                    <div class="logos-container small-logos-container">
                    <c:forEach var="owningCard" items="${skillInfo.owningCards}">
                        <cf:cardLogo cardName="${owningCard.cardName}" responsive="true" />
                    </c:forEach>
                    <c:forEach var="owningRune" items="${skillInfo.owningRunes}">
                        <cf:runeLogo runeName="${owningRune.runeName}" responsive="true" />
                    </c:forEach>
                    </div>
                </td>
            </tr>
            </c:if>
            </c:forEach>    
        </tbody>
    </table>
</body>
</html>