<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/CardFantasyTags.tld" %>
<%@ include file="header.jsp"%>
<title>${cardName} - 魔卡幻想WIKI</title>
<meta name="description" content="${cardName}" />
<meta name="keywords" content="${cardName}" />
<script src='<c:url value="/resources/js/wiki.js" />?version=<%= version %>'></script>
<script>
$(document).ready(function() {
    CardFantasy.Wiki.fillPortrait('view-card-portrait', {
        imgUrl: '${cardInfo.largePortraitUrl}',
        cardName: '${cardInfo.cardName}',
        raceName: '${cardInfo.raceName}',
        star: '${cardInfo.color}',
        cost: '${cardInfo.cost}',
        wait: '${cardInfo.wait}',
        atk: '${cardInfo.attackArray[10]}',
        hp: '${cardInfo.hpArray[10]}',
        skill1: { name: '${cardInfo.skill1.name}', category: '${cardInfo.skill1.category}' },
        skill2: { name: '${cardInfo.skill2.name}', category: '${cardInfo.skill2.category}' },
        skill3: { name: '${cardInfo.skill3.name}', category: '${cardInfo.skill3.category}' },
        skill4: { name: '${cardInfo.skill4.name}', category: '${cardInfo.skill4.category}' },
        skill5: { name: '${cardInfo.skill5.name}', category: '${cardInfo.skill5.category}' },
    });
});
</script>
</head>
<body>
    <input type="hidden" id="view-card-internal-id" value="${internalId}" />
    <%@ include file="wiki-header.jsp" %>
    <table class="view-card-table wiki-table">
        <tbody>
            <tr>
                <td id="view-card-name" class="title" colspan="5">
                    ${cardName}
                    <c:if test="${cardInfo.maxInDeck > 0}">
                        <span>(限定 ${cardInfo.maxInDeck} 张)</span>
                    </c:if>
                    <c:if test="${cardInfo.isMaterial()}">
                        <span>(素材卡)</span>
                    </c:if>
                </td>
            </tr>
            <tr>
                <td colspan="5">
                    <div id="view-card-portrait" style="float: left; height: 450px">
                        <%@ include file="card-portrait-template.jsp" %>
                    </div>
                    <div style="float: left; width: 300px; height: auto; margin: 10px">
                        <img src="${cardInfo.largePortraitUrl}" style="width: 100%; height: auto" />
                    </div>
                    <div style="clear: both"></div>
                </td>
            </tr>
            <tr>
                <td id="view-card-logo" class="logo" rowspan="2">
                    <div>
                        <a href="${cardInfo.largePortraitUrl}" target="_blank">
                            <img alt="${cardInfo.cardName}" src="${cardInfo.logoUrl}" style="background: url('${defaultLogoUrl}')" />
                        </a>
                    </div>
                </td>
                <td class="label">种族星级</td>
                <td id="view-card-race-star" class="value">${cardInfo.raceName}${cardInfo.color}星</td>
                <td class="label">等待</td>
                <td id="view-card-delay" class="value">${cardInfo.wait}</td>
            </tr>
            <tr>
                <td class="label">官方评级</td>
                <td id="view-card-evo-cost" class="value">${cardInfo.rank == 0 ? "未评级" : cardInfo.rank}</td>
                <td class="label">COST</td>
                <td id="view-card-cost" class="value">${cardInfo.cost} / ${cardInfo.evoCost}</td>
            </tr>
        </tbody>
    </table>
    <table class="view-card-table wiki-table">
        <tbody>
            <tr>
                <td class="title" colspan="5">属性成长 <a href="#" data-type="bug">报告BUG</a></td>
            </tr>
            <tr>
                <td class="label"></td>
                <td class="label">0级</td>
                <td class="label">5级</td>
                <td class="label">10级</td>
                <td class="label">15级</td>
            </tr>
            <tr>
                <td class="label">AT</td>
                <td id="view-card-at0" class="value">${cardInfo.attackArray[0]}</td>
                <td id="view-card-at5" class="value">${cardInfo.attackArray[5]}</td>
                <td id="view-card-at10" class="value">${cardInfo.attackArray[10]}</td>
                <td id="view-card-at15" class="value">${cardInfo.attackArray[15]}</td>
            </tr>
            <tr>
                <td class="label">HP</td>
                <td id="view-card-hp0" class="value">${cardInfo.hpArray[0]}</td>
                <td id="view-card-hp5" class="value">${cardInfo.hpArray[5]}</td>
                <td id="view-card-hp10" class="value">${cardInfo.hpArray[10]}</td>
                <td id="view-card-hp15" class="value">${cardInfo.hpArray[15]}</td>
            </tr>
            <tr>
                <td class="label">所需经验</td>
                <td id="view-card-exp0" class="value">0</td>
                <td id="view-card-exp5" class="value">${cardInfo.toLevel5Exp}</td>
                <td id="view-card-exp10" class="value">${cardInfo.toLevel10Exp}</td>
                <td id="view-card-exp15" class="value">${cardInfo.toLevel15Exp}</td>
            </tr>
        </tbody>
    </table>
    <table class="view-card-table wiki-table">
        <tbody>
            <tr>
                <td class="title" colspan="3">卡牌技能</td>
            </tr>
            <c:forEach begin="1" end="5" step="1" var="i"> 
            <tr>
                <td class="label">技能${i}</td>
                <td class="skill-category-icon">
                <c:if test="${cardInfo.getSkill(i) != null}">
                    <cf:skillCategoryIcon categoryId="${cardInfo.getSkill(i).category}" />
                </c:if>
                </td>
                <td id="view-card-skill${i}" class="skill" colspan="4">
                <c:if test="${cardInfo.getSkill(i) != null}">
                    <div class="skill-name"><a href="../Skills/${cardInfo.getSkill(i).name}" target="_self">${cardInfo.getSkill(i).name}</a></div>
                    <div class="skill-desc">${cardInfo.getSkill(i).description}</div>
                </c:if>
                </td>
            </tr>
            </c:forEach>
        </tbody>
    </table>
    <table class="view-card-table wiki-table">
        <tbody>
            <tr>
                <td class="title" colspan="4">其它</td>
            </tr>
            <tr>
                <td class="label">合成所需碎片</td>
                <td class="value">${cardInfo.requiredFragmentCount == 0 ? "无法合成" : cardInfo.requiredFragmentCount}</td>
                <td class="label">可用万能碎片</td>
                <td class="value">${cardInfo.requiredFragmentCount == 0 ? "无法合成" : cardInfo.usableGenericFragmentCount}</td>
            </tr>
            <tr>
                <td class="label">合成价格</td>
                <td class="value">${cardInfo.requiredFragmentCount == 0 ? "无法合成" : cardInfo.composePrice}</td>
                <td class="label"></td>
                <td class="value"></td>
            </tr>
            <tr>
                <td class="label">可分解碎片</td>
                <td class="value">${cardInfo.canDecompose ? "是" : "否"}</td>
                <td class="label">可分解得到</td>
                <td class="value">${cardInfo.obtainableViaDecomposition ? "是" : "否" }</td>
            </tr>
            <tr>
                <td class="label">迷宫碎片</td>
                <td class="value">${cardInfo.fragmentObtainableInMaze ? "是" : "否"}</td>
                <td class="label">地下城碎片</td>
                <td class="value">${cardInfo.fragmentObtainableInDungeon ? "是" : "否"}</td>
            </tr>
            <tr>
                <td class="label">出现在魔神战</td>
                <td class="value">${cardInfo.bossHelper ? "是" : "否"}</td>
                <td class="label">出现在地下城</td>
                <td class="value">${cardInfo.dungeonCard ? "是" : "否"}</td>
            </tr>
            <%--
            <tr>
                <td class="label">活动包概率</td>
                <td class="value">${cardInfo.activityPackRollRank}</td>
                <td class="label">种族包概率</td>
                <td class="value">${cardInfo.racePackRollRank}</td>
            </tr>
             --%>
        </tbody>
    </table>
</body>
</html>