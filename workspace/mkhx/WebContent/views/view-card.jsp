<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>
<title>${cardName} - 魔卡幻想WIKI</title>
<meta name="description" content="${cardName}" />
<meta name="keywords" content="${cardName}" />
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
                <td colspan="5">
                    <div style="float: left; height: 450px">
                        <div style="position: relative; display: inline-block; width: 300px; margin: 10px">
                            <div style="position: absolute; left: 0; top: 0; width: 100%">
                                <img src="${cardInfo.largePortraitUrl}" style="width: 100%" />
                            </div>
                            <div style="position: absolute; left: 0; top: 0; width: 100%">
                                <img src="<c:url value="/resources/img/frame" />/Race_${cardInfo.raceName}_Border.png" style="width: 100%" />
                            </div>
                            <div style="position: absolute; left: 0; top: 0; width: 100%">
                                <img src="<c:url value="/resources/img/frame" />/star_${cardInfo.color}.png" style="width: 100%" />
                            </div>
                            <div style="position: absolute; left: 85px; top: 0; color: white; font-size: 22px; text-shadow: 1px 0 1px #000000, 1px 1px 1px #000000, -1px -1px 1px #000000, 0 1px 1px #000000, -1px 1px 1px #000000, -1px 0 1px #000000, 0 -1px 1px #000000, 0 0 1px #000000; font-family:微软雅黑">
                                ${cardInfo.cardName}
                            </div>
                            <div style="position: absolute; left: 222px; top: 23px; color: #ede739; font-style: italic; width: 70px; font-size: 26px; font-family: Arial">
                                ${cardInfo.cost} 
                            </div>
                            <div style="position: absolute; left: 32px; top: 320px; width: 32px">
                                <img src="<c:url value="/resources/img/frame/blueblock.png" />" />
                            </div>
                            <div style="position: absolute; left: 8px; top: 320px; width: 42px">
                                <img src="<c:url value="/resources/img/frame/timeglass.png" />" />
                            </div>
                            <div style="position: absolute; left: 45px; top: 330px; color: white; font-style: italic; font-size: 26px; font-family :Arial; text-shadow: 1px 0 1px #000000, 1px 1px 1px #000000, -1px -1px 1px #000000, 0 1px 1px #000000, -1px 1px 1px #000000, -1px 0 1px #000000, 0 -1px 1px #000000, 0 0 1px #000000">
                                ${cardInfo.wait}
                            </div>
                            <div style="position: absolute; left: 25px; top: 390px; color: white; font-style: italic; font-size: 26px; font-family: Arial"> 
                                10
                            </div>
                            <div style="position: absolute; left: 120px; top: 330px">
                                <img src="<c:url value="/resources/img/frame/ATK.png" />" style="width: 55px" />
                            </div>
                            <div class="ability-text" style="left: 185px; top: 330px">
                                ${cardInfo.attackArray[10]}
                            </div>
                            <div style="position: absolute; left: 75px; top: 380px">
                                <img src="<c:url value="/resources/img/frame/HP.png" />" style="width: 55px" />
                            </div>
                            <div class="ability-text" style="left: 125px; top: 380px">
                                ${cardInfo.hpArray[10]}
                            </div>
                            <!-- FIFTH LINE -->
                            <c:if test="${cardInfo.skill1 != null || cardInfo.skill2 != null}">
                            <div style="position: absolute; right: 15px; top: 295px">
                                <img src="<c:url value="/resources/img/frame/blackshadow.png" />" style="width: 130px" />
                            </div>
                            <div class="skill-text" style="right: 52px; top: 298px">
                                <c:choose>
                                    <c:when test="${cardInfo.skill5 != null}">${cardInfo.skill5.name}</c:when>
                                    <c:when test="${cardInfo.skill4 != null}">${cardInfo.skill4.name}</c:when>
                                    <c:when test="${cardInfo.skill3 != null}">${cardInfo.skill3.name}</c:when>
                                    <c:when test="${cardInfo.skill2 != null}">${cardInfo.skill2.name}</c:when>
                                    <c:otherwise>${cardInfo.skill1.name}</c:otherwise>
                                </c:choose>
                            </div>
                            <div style="position: absolute; right: 15px; top: 295px">
                                <c:choose>
                                    <c:when test="${cardInfo.skill5 != null}"><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill5.category}.png" style="width: 30px" /></c:when>
                                    <c:when test="${cardInfo.skill4 != null}"><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill4.category}.png" style="width: 30px" /></c:when>
                                    <c:when test="${cardInfo.skill3 != null}"><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill3.category}.png" style="width: 30px" /></c:when>
                                    <c:when test="${cardInfo.skill2 != null}"><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill2.category}.png" style="width: 30px" /></c:when>
                                    <c:otherwise><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill1.category}.png" style="width: 30px" /></c:otherwise>
                                </c:choose>
                            </div>
                            </c:if>
                            <!-- FOURTH LINE -->
                            <c:if test="${cardInfo.skill2 != null}">
                            <div style="position: absolute; right: 15px; top: 266px">
                                <img src="<c:url value="/resources/img/frame/blackshadow.png" />" style="width: 130px" />
                            </div>
                            <div class="skill-text" style="right: 52px; top: 269px">
                                <c:choose>
                                    <c:when test="${cardInfo.skill5 != null}">${cardInfo.skill4.name}</c:when>
                                    <c:when test="${cardInfo.skill4 != null}">${cardInfo.skill3.name}</c:when>
                                    <c:when test="${cardInfo.skill3 != null}">${cardInfo.skill2.name}</c:when>
                                    <c:otherwise>${cardInfo.skill1.name}</c:otherwise>
                                </c:choose>
                            </div>
                            <div style="position: absolute; right: 15px; top: 266px">
                                <c:choose>
                                    <c:when test="${cardInfo.skill5 != null}"><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill4.category}.png" style="width: 30px" /></c:when>
                                    <c:when test="${cardInfo.skill4 != null}"><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill3.category}.png" style="width: 30px" /></c:when>
                                    <c:when test="${cardInfo.skill3 != null}"><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill2.category}.png" style="width: 30px" /></c:when>
                                    <c:otherwise><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill1.category}.png" style="width: 30px" /></c:otherwise>
                                </c:choose>
                            </div>
                            </c:if>
                            <!-- THIRD LINE -->
                            <c:if test="${cardInfo.skill3 != null && (cardInfo.skill4 != null || cardInfo.skill5 != null || cardInfo.skill1 != null)}">
                            <div style="position: absolute; right: 15px; top: 237px">
                                <img src="<c:url value="/resources/img/frame/blackshadow.png" />" style="width: 130px" />
                            </div>
                            <div class="skill-text" style="right: 52px; top: 240px">
                                <c:choose>
                                    <c:when test="${cardInfo.skill5 != null}">${cardInfo.skill3.name}</c:when>
                                    <c:when test="${cardInfo.skill4 != null}">${cardInfo.skill2.name}</c:when>
                                    <c:otherwise>${cardInfo.skill1.name}</c:otherwise>
                                </c:choose>
                            </div>
                            <div style="position: absolute; right: 15px; top: 237px">
                                <c:choose>
                                    <c:when test="${cardInfo.skill5 != null}"><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill3.category}.png" style="width: 30px" /></c:when>
                                    <c:when test="${cardInfo.skill4 != null}"><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill2.category}.png" style="width: 30px" /></c:when>
                                    <c:otherwise><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill1.category}.png" style="width: 30px" /></c:otherwise>
                                </c:choose>
                            </div>
                            </c:if>
                            <!-- SECOND LINE -->
                            <c:if test="${cardInfo.skill4 != null}">
                            <div style="position: absolute; right: 15px; top: 208px">
                                <img src="<c:url value="/resources/img/frame/blackshadow.png" />" style="width: 130px" />
                            </div>
                            <div class="skill-text" style="right: 52px; top: 211px">
                                <c:choose>
                                    <c:when test="${cardInfo.skill5 != null}">${cardInfo.skill2.name}</c:when>
                                    <c:otherwise>${cardInfo.skill1.name}</c:otherwise>
                                </c:choose>
                            </div>
                            <div style="position: absolute; right: 15px; top: 208px">
                                <c:choose>
                                    <c:when test="${cardInfo.skill5 != null}"><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill2.category}.png" style="width: 30px" /></c:when>
                                    <c:otherwise><img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill1.category}.png" style="width: 30px" /></c:otherwise>
                                </c:choose>
                            </div>
                            </c:if>
                            <!-- FIRST LINE -->
                            <c:if test="${cardInfo.skill5 != null}">
                            <div style="position: absolute; right: 15px; top: 178px">
                                <img src="<c:url value="/resources/img/frame/blackshadow.png" />" style="width: 130px" />
                            </div>
                            <div class="skill-text" style="right: 52px; top: 182px">
                                ${cardInfo.skill1.name}
                            </div>
                            <div style="position: absolute; right: 15px; top: 178px">
                                <img src="<c:url value="/resources/img/frame" />/Skill_Category_${cardInfo.skill1.category}.png" style="width: 30px" />
                            </div>
                            </c:if>
                        </div>
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
                    <div><a href="../Skills/${cardInfo.skill1.name}">${cardInfo.skill1.name}</a></div>
                    <div>${cardInfo.skill1.description}</div>
                </td>
            </tr>
            <tr>
                <td class="label">技能2</td>
                <td id="view-card-skill2" class="skill" colspan="4">
                    <div><a href="../Skills/${cardInfo.skill2.name}">${cardInfo.skill2.name}</a></div>
                    <div>${cardInfo.skill2.description}</div>
                </td>
            </tr>
            <tr>
                <td class="label">技能3</td>
                <td id="view-card-skill3" class="skill" colspan="4">
                  <div><a href="../Skills/${cardInfo.skill3.name}">${cardInfo.skill3.name}</a></div>
                    <div>${cardInfo.skill3.description}</div>
                </td>
            </tr>
            <tr>
                <td class="label">技能4</td>
                <td id="view-card-skill4" class="skill" colspan="4">
                    <div><a href="../Skills/${cardInfo.skill4.name}">${cardInfo.skill4.name}</a></div>
                    <div>${cardInfo.skill4.description}</div>
                </td>
            </tr>
            <tr>
                <td class="label">技能5</td>
                <td id="view-card-skill5" class="skill" colspan="4">
                    <div><a href="../Skills/${cardInfo.skill5.name}">${cardInfo.skill5.name}</a></div>
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