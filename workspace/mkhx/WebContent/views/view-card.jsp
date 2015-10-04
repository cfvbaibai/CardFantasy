<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>
<title>${cardName} - 魔卡幻想WIKI</title>
<meta name="description" content="${cardName}" />
<meta name="keywords" content="${cardName}" />
<script>
$(document).ready(function () {
    var internalId = $('#view-card-internal-id').val();
    if (!internalId) {
        return;
    }
    $('#view-card-logo').html('<img src="' + resDir + '/img/cardlogo/' + internalId + '.jpg" />');
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
                    <%--
                    <span>ImgId: ${cardInfo.imageId}</span>
                    <span>FullImgId: ${cardInfo.fullImageId}</span>
                    <span>CardId: ${cardInfo.cardId}</span>
                     --%>
                </td>
            </tr>
            <tr>
                <td id="view-card-logo" class="logo" rowspan="2">
                    <img src="<c:url value="/resources/img/cardlogo/" />/${internalId}.jpg" />
                </td>
                <td class="label">种族星级</td>
                <td id="view-card-race-star" class="value">${cardInfo.raceName}${cardInfo.color}星</td>
                <td class="label">等待</td>
                <td id="view-card-delay" class="value">${cardInfo.wait}</td>
            </tr>
            <tr>
                <td class="label">进化前COST</td>
                <td id="view-card-cost" class="value">${cardInfo.cost}</td>
                <td class="label">进化后COST</td>
                <td id="view-card-evo-cost" class="value">${cardInfo.evoCost}</td>
            </tr>
            <tr>
                <td class="title" colspan="5">属性成长 <a href="#" data-type="bug">报告BUG</a></td>
            </tr>
            <tr>
                <td class="label"></td>
                <td class="label">0级</td>
                <td class="label">5级</td>
                <td class="label">10级</td>
                <td class="label">15级(需进化)</td>
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
                <td id="view-card-exp0" class="value">${cardInfo.expArray[0]}</td>
                <td id="view-card-exp5" class="value">${cardInfo.expArray[5]}</td>
                <td id="view-card-exp10" class="value">${cardInfo.expArray[10]}</td>
                <td id="view-card-exp15" class="value">${cardInfo.expArray[15]}</td>
            </tr>
            <tr>
                <td class="title" colspan="5">卡牌技能</td>
            </tr>
            <tr>
                <td class="label">技能1</td>
                <td id="view-card-skill1" class="skill" colspan="4">
                    <div><a href="../Skills/${cardInfo.skill1.name}" target="_blank">${cardInfo.skill1.name}</a></div>
                    <div>${cardInfo.skill1.description}</div>
                </td>
            </tr>
            <tr>
                <td class="label">技能2</td>
                <td id="view-card-skill2" class="skill" colspan="4">
                    <div><a href="../Skills/${cardInfo.skill2.name}" target="_blank">${cardInfo.skill2.name}</a></div>
                    <div>${cardInfo.skill2.description}</div>
                </td>
            </tr>
            <tr>
                <td class="label">技能3</td>
                <td id="view-card-skill3" class="skill" colspan="4">
                  <div><a href="../Skills/${cardInfo.skill3.name}" target="_blank">${cardInfo.skill3.name}</a></div>
                    <div>${cardInfo.skill3.description}</div>
                </td>
            </tr>
            <tr>
                <td class="label">技能4</td>
                <td id="view-card-skill4" class="skill" colspan="4">
                    <div><a href="../Skills/${cardInfo.skill4.name}" target="_blank">${cardInfo.skill4.name}</a></div>
                    <div>${cardInfo.skill4.description}</div>
                </td>
            </tr>
            <tr>
                <td class="label">技能5</td>
                <td id="view-card-skill5" class="skill" colspan="4">
                    <div><a href="../Skills/${cardInfo.skill5.name}" target="_blank">${cardInfo.skill5.name}</a></div>
                    <div>${cardInfo.skill5.description}</div>
                </td>
            </tr>
        </tbody>
    </table>
    <!-- CNZZ Begins -->
    <script src="http://s25.cnzz.com/stat.php?id=5496691&web_id=5496691&online=1"></script>
    <!-- CNZZ Ends -->
</body>
</html>