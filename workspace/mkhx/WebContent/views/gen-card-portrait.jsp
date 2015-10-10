<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/CardFantasyTags.tld" %>
<%@ include file="header.jsp"%>
<title>魔卡幻想WIKI自建卡图</title>
<meta name="description" content="自建卡图" />
<meta name="keywords" content="自建卡图" />
<script src='<c:url value="/resources/js/wiki.js" />?version=<%= version %>'></script>
<script>
var genCardPortrait = null;
var onImgSrcTypeChanged = null;
var genCardPortrait = null;
$(document).ready(function () {
    $('#raise-bug-link')[0].href = CardFantasy.Core.tiebaUrl;

    onImgSrcTypeChanged = function(selectObj) {
        if ($(selectObj).val() == '1') {
            $('#network-card-img-input').show();
            $('#local-card-img-input').hide();
        } else {
            $('#network-card-img-input').hide();
            $('#local-card-img-input').show();
        }
    };

    showPreview = function(fileObj) {
        var fr = new FileReader();
        fr.onloadend = function(e) {
            $('#card-img-preview')[0].src = e.target.result;
        };
        fr.readAsDataURL(fileObj.files[0]);
    };
    
    $('#gen-card-portrait-button').click(function() {
        var lastInput = {
            raceName: $('#race-name').val(),
            star: $('#star').val(),
            cardName: $('#card-name').val(),
            limit: $('#limit').val(),
            cost: $('#cost').val(),
            wait: $('#wait').val(),
            atk: $('#atk').val(),
            hp: $('#hp').val(),
            cardImgSrcType: $('#card-img-source-type').val(),
            skill1: { name: $('#skill-name-1').val(), category: $('#skill-category-1').val(), desc: $('#skill-desc-1').val() },
            skill2: { name: $('#skill-name-2').val(), category: $('#skill-category-2').val(), desc: $('#skill-desc-2').val() },
            skill3: { name: $('#skill-name-3').val(), category: $('#skill-category-3').val(), desc: $('#skill-desc-3').val() },
        }
        $.cookie('custom-portrait', JSON.stringify(lastInput), { expires: 365 });
        var src = lastInput.cardImgSrcType == 1 ? $('#network-card-img-url').val() : $('#card-img-preview')[0].src;
        CardFantasy.Wiki.fillPortrait('custom-portrait', {
            imgUrl: src,
            raceName: lastInput.raceName,
            star: lastInput.star,
            cardName: lastInput.cardName,
            cost: lastInput.cost,
            wait: lastInput.wait,
            atk: lastInput.atk,
            hp: lastInput.hp,
            skill1: lastInput.skill1,
            skill2: lastInput.skill2,
            skill3: lastInput.skill3,
        });
        $('#desc-card-name').text($('#card-name').val());
        $('#desc-race-name').text($('#race-name').val());
        for (var i = 1; i <= 3; ++i) {
            $('#desc-skill-name-' + i).text($('#skill-name-' + i).val());
            $('#desc-skill-desc-' + i).text($('#skill-desc-' + i).val());
        }
        if (lastInput.limit) {
            $('#limit-img')[0].src = resDir + '/img/frame/Limit_' + lastInput.limit + '.png';
            $('#limit-img').show();
        } else {
            $('#limit-img').hide();
        }
        $('div.float-div').show();
    });

    var dataText = $.cookie('custom-portrait');
    if (!dataText) {
        return;
    }
    var lastInput = JSON.parse(dataText);
    if (lastInput.raceName) {
        $('#race-name').val(lastInput.raceName).selectmenu('refresh');
    }
    $('#card-name').val(lastInput.cardName);
    $('#star').val(lastInput.star);
    if (lastInput.limit) {
        $('#limit').val(lastInput.limit).selectmenu('refresh');
    }
    $('#cost').val(lastInput.cost);
    $('#wait').val(lastInput.wait);
    $('#atk').val(lastInput.atk);
    $('#hp').val(lastInput.hp);
    for (var i = 1; i <= 3; ++i) {
        var skill = lastInput['skill' + i];
        if (skill) {
            $('#skill-name-' + i).val(skill.name);
            if (skill.category) {
                $('#skill-category-' + i).val(skill.category).selectmenu('refresh');
            }
            $('#skill-desc-' + i).val(skill.desc);
        }
    }
});
</script>
</head>
<body>
    <div class="float-div" style="display: none; text-shadow: none;"><table><tr><td>
        <div class="float-header">右键另存为是无效的，请直接用截图工具截图</div>
        <div class="content" style="color: white; width: 700px">
            <table style="width: 100%; border-collapse: collapse">
                <tr>
                    <td style="height: 450px; vertical-align: top; width: 300px; border-width: 0">
                        <div id="custom-portrait" style="position: relative">
                            <%@ include file="card-portrait-template.jsp" %>
                        </div>
                    </td>
                    <td style="border-width: 0; vertical-align: top; font-family: 微软雅黑; text-align: left; padding-left: 20px; padding-right: 20px; position: relative">
                        <div style="position: absolute; right: 0; top: 0; width: 97px; height: 97px">
                            <img id="limit-img" src="" /> 
                        </div>
                        <div style="position: absolute; right: 8px; bottom: 8px; color: rgba(255, 255, 255, 0.25); font-size: smaller">
                            <span>生成器：http://www.mkhx.cc/Wiki/GenCardPortrait</span>
                        </div>
                        <div>
                            <div id="desc-card-name" style="margin-top: 10px; font-size: 18px; font-weight: bold; text-align: center"></div>
                            <div>种族: <span id="desc-race-name"></span></div>
                            <c:forEach var="i" begin="1" end="3">
                                <div id="desc-skill-name-${i}" style="font-size: 16px; margin-top: 20px; font-weight: bold"></div>
                                <div id="desc-skill-desc-${i}" style="margin-top: 5px"></div>
                            </c:forEach>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
        <div class="float-footer">
            <a id='raise-bug-link' href="#" target="_blank">提需求报BUG</a>
            <a href="javascript:$('div.float-div').hide();">关闭</a>
        </div>
    </td></tr></table></div>
    <input type="hidden" id="view-card-internal-id" value="${internalId}" />
    <%@ include file="wiki-header.jsp" %>
    <div data-role="content">
        <table class="form">
            <tr>
                <td>底图</td>
                <td>
                    <select id="card-img-source-type" data-mini="true" onchange="onImgSrcTypeChanged(this)">
                        <option value="1" selected="selected">网络图片</option>
                        <option value="2">本地图片</option>
                    </select>
                    <div id="local-card-img-input" style="display: none">
                        <input id="card-img" type="file" data-mini="true" onchange="showPreview(this)" />
                        <img id="card-img-preview" src="" style="width: 100px; height: 143px; display: none" />
                    </div>
                    <div id="network-card-img-input">
                        <input id="network-card-img-url" type="text" value="http://gz.dhyedu.com/student/works/10jy0127/images/bolilingmeng.jpg" />
                    </div>
                </td>
            </tr>
            <tr>
                <td>卡名</td>
                <td><input id="card-name" type="text" data-mini="true" value="博丽灵梦" /></td>
            </tr>
            <tr>
                <td>种族</td>
                <td>
                    <select id="race-name" data-mini="true">
                        <option value="王国">王国</option>
                        <option value="森林">森林</option>
                        <option value="蛮荒">蛮荒</option>
                        <option value="地狱">地狱</option>
                        <option value="魔王" selected="selected">魔王</option>
                        <option value="魔神">魔神</option>
                        <option value="道具">道具</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>星数</td>
                <td>
                    <select id="star" data-mini="true">
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="4">4</option>
                        <option value="5" selected="selected">5</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>限定</td>
                <td>
                    <select id="limit" data-mini="true">
                        <option value="">无限定</option>
                        <option value="1" selected="selected">限定一张</option>
                        <option value="2">限定两张</option>
                    </select>
                </td>
            <tr>
                <td>COST</td>
                <td><input id="cost" type="number" value="99" /></td>
            </tr>
            <tr>
                <td>等待</td>
                <td><input id="wait" type="number" value="6" /></td>
            </tr>
            <tr>
                <td>ATK</td>
                <td><input id="atk" type="number" value="777" /></td>
            </tr>
            <tr>
                <td>HP</td>
                <td><input id="hp" type="number" value="7777" /></td>
            </tr>
            <c:forEach begin="1" end="3" step="1" var="i">
            <tr>
                <td>技能${i}名字</td>
                <td><input id="skill-name-${i}" type="text" value="胡吃海喝${i}" /></td>
            </tr>
            <tr>
                <td>技能${i}类型</td>
                <td>
                    <select id="skill-category-${i}" data-mini="true">
                        <option value="1">攻击</option>
                        <option value="2" selected="selected">魔法</option>
                        <option value="3">防御</option>
                        <option value="4">治疗</option>
                        <option value="5">辅助</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td>技能${i}描述</td>
                <td><textarea id="skill-desc-${i}" data-mini="true">大吃特吃，对每张敌方卡牌造成等同于自身当前HP${i * 2}0%的魔法伤害，可被免疫和反射</textarea></td>
            </tr>
            </c:forEach>
        </table>
        <button id="gen-card-portrait-button">生成</button>
    </div> 
</body>
</html>