<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/CardFantasyTags.tld" %>
<%@ include file="header.jsp"%>
<title>魔卡幻想WIKI - ${stageInfo.fullName}</title>
<meta name="description" content="${stageInfo.name}" />
<meta name="keywords" content="${stageInfo.name}" />
</head>
<body>
    <div class="main-page" data-role="page">
    <%@ include file="wiki-header.jsp" %>
    <div data-role="content">
    <table class="view-stage-basic-table wiki-table">
        <tbody>
            <tr>
                <td class="title" colspan="4">
                    ${stageInfo.fullName}
                </td>
            </tr>
            <tr>
                <td class="label">地图收益</td>
                <td class="value">${stageInfo.everydayReward}</td>
                <td class="label">迷宫塔</td>
                <td class="value">${stageInfo.mazeExists ? "存在" : "不存在"}</td>
            </tr>
        </tbody>
    </table>
    <c:forEach var="stageDetailInfo" items="${stageInfo.detailInfos}">
    <c:if test="${stageDetailInfo.type <= 2}">
        <table class="view-stage-detail-table wiki-table">
            <tr>
                <td class="title" colspan="4">${stageDetailInfo.fullName}</td>
            </tr>
            <tr>
                <td class="label">难度</td>
                <td class="label">奖励及过关条件</td>
                <td class="label">敌方阵容</td>
            </tr>
            <c:forEach var="levelInfo" items="${stageDetailInfo.levelInfos}">
            <tr>
                <td class="label" rowspan="2">${levelInfo.name}</td>
                <td class="bonus" rowspan="2">
                    <div>胜利奖励： 金钱${levelInfo.winBonusGold} 经验值${levelInfo.winBonusExp}</div>
                    <div>失败奖励： 金钱${levelInfo.loseBonusGold} 经验值${levelInfo.loseBonusExp}</div>
                    <div>探索奖励： 金钱${levelInfo.exploreBonusGold} 经验值${levelInfo.exploreBonusExp}</div>
                    <div>过关条件： ${levelInfo.winConditionText}</div>
                    <c:if test="${levelInfo.firstWinBonus.size() > 0}">
                    <div>固定奖励：
                    <c:forEach var="bonus" items="${levelInfo.firstWinBonus}">
                        <a href="<c:url value="/Wiki" />/${bonus.typeName}s/${bonus.id}.shtml" target="_self">${bonus.name}</a>&nbsp;
                    </c:forEach>
                    </div>
                    </c:if>
                </td>
                <td class="value cards">
                    <c:forEach var="card" items="${levelInfo.deckInfo.cards}">
                        <div class="item">
                            <a href="<c:url value="/Wiki" />/Cards/${card.id}.shtml" target="_self">${card.cardName}</a>-${card.level}<c:if test="${card.extraSkill != null}">+<a href="<c:url value="/Wiki" />/Skills/${card.extraSkill.id}.shtml" target="_self">${card.extraSkill.name}</a></c:if>,
                        </div>
                    </c:forEach>
                    <div class="clear"></div>
                </td>
            </tr>
            <tr>
                <td class="value runes">
                    <c:forEach var="rune" items="${levelInfo.deckInfo.runes}">
                        <div class="item">
                            <a href="<c:url value="/Wiki" />/Runes/${rune.id}.shtml" target="_self">${rune.runeName}</a>-${rune.level}
                        </div>
                    </c:forEach>
                    <div class="clear"></div>
                </td>
            </tr>
            </c:forEach>
        </table>
    </c:if>
    </c:forEach>
    <%--
    <c:forEach var="stageDetailInfo" items="${stageInfo.detailInfos}">
        <c:if test="${stageDetailInfo.type <= 2}">
            <c:forEach var="levelInfo" items="${stageDetailInfo.levelInfos}">
                <div>
                    &lt;Map victory="${levelInfo.winConditionText}" heroHP="${levelInfo.heroHp}" id="${stageDetailInfo.stageId}-${stageDetailInfo.rankName}-${levelInfo.levelId}"&gt;
                    <c:forEach var="rune" items="${levelInfo.deckInfo.runes}">${rune.runeName}-${rune.level},</c:forEach>
                    <c:forEach var="card" items="${levelInfo.deckInfo.cards}">${card.cardName}<c:if test="${card.extraSkill != null}">+${card.extraSkill.name}-${card.level}</c:if>,</c:forEach>
                    &lt;/Map&gt;
                </div>
            </c:forEach>
        </c:if>
    </c:forEach>
     --%>
    </div>
    </div>
</body>
</html>