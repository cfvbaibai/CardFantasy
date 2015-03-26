<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>
<title>魔卡幻想卡牌信息</title>
<meta name="description" content="${keyword}" />
<meta name="keywords" content="${keyword}" />
<script src='<c:url value="/resources/js/all-card.js" />'></script>
<script src='<c:url value="/resources/js/all-skill.js" />'></script>
<script>
var races = [];
races[1] = '王国';
races[2] = '森林';
races[3] = '蛮荒';
races[4] = '地狱';
races[100] = '魔神';
races[97] = '魔王';
$(document).ready(generate);

function getSkillText(skillId) {
    if (!skillId) {
        return '';
    }
    var skill = null;
    for (var i = 0; i < allSkills.Skills.length; ++i) {
        if (allSkills.Skills[i].SkillId == skillId) {
            skill = allSkills.Skills[i];
            break;
        }
    }
    if (!skill) {
        return '';
    }
    var result = '<div>' + skill.Name + '</div>';
    result += '<div>' + skill.Desc + '</div>';
    return result;
}

function generate() {
    var card = null;
    var keyword = $('#view-card-search-keyword').val();
    if (!keyword) {
        return;
    }
    for (var i = 0; i < allCards.Cards.length; ++i) {
        if (allCards.Cards[i].CardId == keyword) {
            card = allCards.Cards[i];
            break;
        } else if (allCards.Cards[i].CardName == keyword) {
            card = allCards.Cards[i];
            break;
        }
    }
    if (card == null) {
        $('#view-card-name').text('暂时没有【' + keyword + '】的资料');
        return;
    }
    
    var internalId = $('#view-card-internal-id').val();
    $('#view-card-logo').html('<img src="' + resDir + '/img/cardlogo/' + internalId + '.jpg" />');
    $('#view-card-name').text(card.CardName);
    var race = races[card.Race] || '未知';
    $('#view-card-race-star').text(race + card.Color + '星');
    $('#view-card-delay').text(card.Wait);
    $('#view-card-cost').text(card.Cost);
    $('#view-card-evo-cost').text(card.EvoCost);
    $('#view-card-at0').text(card.AttackArray[0]);
    $('#view-card-at5').text(card.AttackArray[5]);
    $('#view-card-at10').text(card.AttackArray[10]);
    $('#view-card-at15').text(card.AttackArray[15]);
    $('#view-card-hp0').text(card.HpArray[0]);
    $('#view-card-hp5').text(card.HpArray[5]);
    $('#view-card-hp10').text(card.HpArray[10]);
    $('#view-card-hp15').text(card.HpArray[15]);
    $('#view-card-exp0').text(card.ExpArray[0]);
    $('#view-card-exp5').text(card.ExpArray[5]);
    $('#view-card-exp10').text(card.ExpArray[10]);
    $('#view-card-exp15').text(card.ExpArray[15]);
    $('#view-card-skill1').html(getSkillText(card.Skill));
    $('#view-card-skill2').html(getSkillText(card.LockSkill1));
    $('#view-card-skill3').html(getSkillText(card.LockSkill2));
    $('#view-card-skill4').html(getSkillText(card.LockSkill3));
}
</script>
</head>
<body>
    <input type="hidden" id="view-card-search-keyword" value="${keyword}" />
    <input type="hidden" id="view-card-internal-id" value="${internalId}" />
    <table class="view-card-table">
        <tbody>
            <tr>
                <td id="view-card-name" class="title" colspan="5"></td>
            </tr>
            <tr>
                <td id="view-card-logo" class="logo" rowspan="2">LOGO</td>
                <td class="label">种族星级</td>
                <td id="view-card-race-star" class="value"></td>
                <td class="label">等待</td>
                <td id="view-card-delay" class="value"></td>
            </tr>
            <tr>
                <td class="label">进化前COST</td>
                <td id="view-card-cost" class="value"></td>
                <td class="label">进化后COST</td>
                <td id="view-card-evo-cost" class="value"></td>
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
                <td id="view-card-at0" class="value"></td>
                <td id="view-card-at5" class="value"></td>
                <td id="view-card-at10" class="value"></td>
                <td id="view-card-at15" class="value"></td>
            </tr>
            <tr>
                <td class="label">HP</td>
                <td id="view-card-hp0" class="value"></td>
                <td id="view-card-hp5" class="value"></td>
                <td id="view-card-hp10" class="value"></td>
                <td id="view-card-hp15" class="value"></td>
            </tr>
            <tr>
                <td class="label">所需经验</td>
                <td id="view-card-exp0" class="value"></td>
                <td id="view-card-exp5" class="value"></td>
                <td id="view-card-exp10" class="value"></td>
                <td id="view-card-exp15" class="value"></td>
            </tr>
            <tr>
                <td class="title" colspan="5">卡牌技能</td>
            </tr>
            <tr>
                <td class="label">技能1</td>
                <td id="view-card-skill1" class="skill" colspan="4"></td>
            </tr>
            <tr>
                <td class="label">技能2</td>
                <td id="view-card-skill2" class="skill" colspan="4"></td>
            </tr>
            <tr>
                <td class="label">技能3</td>
                <td id="view-card-skill3" class="skill" colspan="4"></td>
            </tr>
            <tr>
                <td class="label">技能4</td>
                <td id="view-card-skill4" class="skill" colspan="4"></td>
            </tr>
        </tbody>
    </table>
    <!-- CNZZ Begins -->
    <script src="http://s25.cnzz.com/stat.php?id=5496691&web_id=5496691&online=1"></script>
    <!-- CNZZ Ends -->
</body>
</html>