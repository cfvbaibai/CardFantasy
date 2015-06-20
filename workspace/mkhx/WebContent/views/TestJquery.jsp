<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp"%>
<title>Test Canvas</title>
<script src='<c:url value="/resources/js/all-card.js" />?version=<%= java.util.Calendar.getInstance().getTimeInMillis() %>'></script>
<script src='<c:url value="/resources/js/all-skill.js" />?version=<%= java.util.Calendar.getInstance().getTimeInMillis() %>'></script>
<script>
var races = ['王国', '森林', '蛮荒', '地狱'];
function generate() {
    var cardName = $('#card-name').val();
    var card = null;
    for (var i = 0; i < allCards.Cards.length; ++i) {
        if (allCards.Cards[i].CardName == cardName) {
            card = allCards.Cards[i];
            break;
        }
    }
    if (card == null) {
        $('#card-xml').text("Invalid card name " + cardName);
        return;
    }
    var result = "";
    /*
    <Card id="4432" name="邪魔屠夫" wikiId="" race="地狱" speed="4" star="4" cost="13" incrCost="2" at="260" hp="970" incrAT="23" incrHP="26">
        <Skill type="瘟疫" level="6" unlock="0" />
        <Skill type="鲜血盛宴" level="5" unlock="5" />
        <Skill type="嗜血" level="6" unlock="10" />
    </Card>
    */
    var race = races[card.Race - 1];
    var speed = card.Wait;
    var star = card.Color;
    var cost = card.Cost;
    var incrCost = card.EvoCost - cost;
    var at = card.AttackArray[0];
    var hp = card.HpArray[0];
    var incrAT = card.AttackArray[1] - card.AttackArray[0];
    var incrHP = card.HpArray[1] - card.HpArray[0];
    result += '&lt;Card id="" name="' + cardName + '" wikiId="" race="' + race + '" speed="' + speed + '" star="' + star + '" ';
    result += 'cost="' + cost + '" incrCost="' + incrCost + '" at="' + at + '" hp="' + hp + '" incrAT="' + incrAT + '" incrHP="' + incrHP + '"&gt;';
    result += '<br />';
    result += '&nbsp;&nbsp;&nbsp;&nbsp;' + generateSkill(card.Skill, 0) + '<br />';
    result += '&nbsp;&nbsp;&nbsp;&nbsp;' + generateSkill(card.LockSkill1, 5) + '<br />';
    result += '&nbsp;&nbsp;&nbsp;&nbsp;' + generateSkill(card.LockSkill2, 10) + '<br />';
    result += '&lt;/Card&gt;'
    $('#card-xml').html(result);
}

function generateSkill(skillId, unlockLevel) {
    console.log('SkillId = ' + skillId + ', unlockLevel = ' + unlockLevel);
    var skill = null;
    for (var i = 0; i < allSkills.Skills.length; ++i) {
        if (allSkills.Skills[i].SkillId == skillId) {
            skill = allSkills.Skills[i];
            break;
        }
    }
    if (skill == null) {
        return 'Invalid Skill ID: ' + skillId;
    }
    var skillNameRegex = /^(\[.+\])?([^0-9]+)([0-9]+)?$/gi;
    var match = skillNameRegex.exec(skill.Name);
    if (match == null) {
        return 'Invalid skill name: ' + skill.Name;
    }
    var special = match[1];
    var name = match[2];
    var level = match[3];
    if (!level) {
        level = 0;
    }
    var result = '&lt;Skill type="' + name + '" level="' + level + '" unlock="' + unlockLevel + '" ';
    if (special == '[降临]') {
        result += 'summon="true" ';
    } else if (special == '[死契]') {
        result += 'death="true" ';
    }
    result += '/&gt;';
    return result;
}

function showSkillLaunchTypes() {
   var skillLaunchTypes = {};
   var i;
   for (i = 0; i < allSkills.Skills.length; ++i) {
       var skill = allSkills.Skills[i];
       var launchTypeKey = 'LT' + skill.LanchType;
       var skillName = skill.Name.replace(/\d/g, '');
       if (!skillLaunchTypes[launchTypeKey]) {
           skillLaunchTypes[launchTypeKey] = [ skillName ];
       } else if (skillLaunchTypes[launchTypeKey].indexOf(skillName) < 0) {
           skillLaunchTypes[launchTypeKey].push(skillName);
       }
   }
   var result = '';
   for (var launchTypeKey in skillLaunchTypes) {
       result += launchTypeKey + ': ';
       for (i = 0; i < skillLaunchTypes[launchTypeKey].length; ++i) {
           result += skillLaunchTypes[launchTypeKey][i] + ', ';
       }
       result += '<br />';
   }
   $('#skill-launch-types').html(result);
}

function showBossHelpers() {
    var i = 0;
    var card;
    var result = "";
    for (i = 0; i < allCards.Cards.length; ++i) {
        card = allCards.Cards[i];
        if (card.Color == 5 && card.BossHelper == 1)
            result += '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"' + card.CardName + '",<br />';
    }
    $('#boss-helpers').html(result);
}
</script>
</head>
<body>
    <div>Card Name: <input type="text" id="card-name" value="森林女神" /><button onclick="generate();">生成</button></div>
    <div id="card-xml"></div>
    <div><button onclick="showSkillLaunchTypes();">显示技能发动类型</button></div>
    <div id="skill-launch-types"></div>
    <div><button onclick="showBossHelpers();">显示魔神小兵</button></div>
    <div id="boss-helpers"></div>
</body>
</html>