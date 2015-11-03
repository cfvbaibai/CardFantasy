CardFantasy.Wiki = {};

(function(Wiki) {

var toStarText = function(star) {
    var result = '';
    for (var i = 0; i < star; ++i) {
        result += '★';
    }
    return result;
};

var toRaceText = function(race) {
    switch (race) {
        case 1: return '王国';
        case 2: return '森林';
        case 3: return '蛮荒';
        case 4: return '地狱';
        case 97: return '魔王';
        case 100: return '魔神';
        default: return '未知';
    }
};

var fillPortrait = function(portraitDivId, cardInfo) {
    var id = '#' + portraitDivId + ' ';
    $(id + 'img.cpt-large-portrait-img')[0].src = cardInfo.imgUrl;
    $(id + 'img.cpt-border-img')[0].src = resDir + '/img/frame/Race_' + cardInfo.raceName + '_Border.png';
    $(id + 'img.cpt-star-img')[0].src = resDir + '/img/frame/star_' + cardInfo.star + '.png';
    $(id + '.cpt-card-name').text(cardInfo.cardName);
    $(id + '.cpt-cost').text(cardInfo.cost);
    $(id + '.cpt-wait').text(cardInfo.wait);
    $(id + '.cpt-atk').text(cardInfo.atk);
    $(id + '.cpt-hp').text(cardInfo.hp);
    var availableSkills = [];
    if (cardInfo.skill5 && cardInfo.skill5.name) { availableSkills.push(cardInfo.skill5); }
    if (cardInfo.skill4 && cardInfo.skill4.name) { availableSkills.push(cardInfo.skill4); }
    if (cardInfo.skill3 && cardInfo.skill3.name) { availableSkills.push(cardInfo.skill3); }
    if (cardInfo.skill2 && cardInfo.skill2.name) { availableSkills.push(cardInfo.skill2); }
    if (cardInfo.skill1 && cardInfo.skill1.name) { availableSkills.push(cardInfo.skill1); }
    for (var i = 0; i < availableSkills.length; ++i) {
        $(id + '.cpt-skill-line-' + i).show();
        $(id + 'img.cpt-skill-category-' + i)[0].src = resDir + '/img/frame/Skill_Category_' + availableSkills[i].category + '.png';
        $(id + '.cpt-skill-name-' + i).text(availableSkills[i].name);
    }
};
Wiki.fillPortrait = fillPortrait;

var store = {};
//$(document).on("pageinit", "#wiki", function(event) {
$(document).ready(function () {
    console.log('Initing page: #wiki');
    CardFantasy.Core.uploadToCnzzUrl('wiki');
    if ($(window).width() < 400) {
        //$('#wiki > div > div').addClass('ui-collapsible-content-collapsed');
        //$(this).trigger('create');
    }
    $('#wiki-card-search').click(function() {
        var starFilter = $('#wiki-card-star-filter').val();
        var raceFilter = $('#wiki-card-race-filter').val();
        var skillTypeFilter = $('#wiki-card-skill-type-filter').val();
        var queryUrl ='Wiki/Cards?stars=' + starFilter + '&races=' + raceFilter + '&skillTypes=' + skillTypeFilter;

        $('#wiki-card-search').addClass('ui-disabled');
        $.mobile.loading('show');
        $.get(queryUrl, function(data) {
            var wideResult = "";
            var narrowResult = "";
            wideResult += "<table class='wiki-card-result-wide'>";
            wideResult += "<tr><td>卡牌</td><td>星数</td><td>种族</td><td colspan='5'>技能</td></tr>";
            narrowResult += "<table class='wiki-card-result-narrow'>";
            narrowResult += "<tr><td>卡牌</td><td>星数</td><td>种族</td></tr>";
            var getSkillHtml = function(skill) {
                if (!skill) { return ''; }
                return '<a href="Wiki/Skills/' + skill.Name + '" target="_blank">' + skill.Name + '</a>';
            }
            $.each(data, function(i, cardInfo) {
                var card = cardInfo.card;
                var skill1 = getSkillHtml(cardInfo.skill1);
                var skill2 = getSkillHtml(cardInfo.skill2);
                var skill3 = getSkillHtml(cardInfo.skill3);
                var skill4 = getSkillHtml(cardInfo.skill4);
                var skill5 = getSkillHtml(cardInfo.skill5);
                var cardName = "<a href='Wiki/Cards/" + card.CardName + "' target='_blank'>" + card.CardName + "</a>";
                var starText = toStarText(card.Color);
                var raceText = toRaceText(card.Race);

                wideResult += "<tr><td>" + cardName + "</td><td>" + starText + "</td><td>" + raceText + "</td>";
                wideResult += "<td>" + skill1 + "</td><td>" + skill2 + "</td><td>" + skill3 + "</td>";
                wideResult += "<td>" + skill4 + "</td><td>" + skill5 + "</td></tr>";

                narrowResult += "<tr class='normal-line'><td>" + cardName + "</td><td>" + starText + "</td><td>" + raceText + "</td></tr>";
                narrowResult += "<tr class='skill-extra-line'>";
                var skills = [];
                if (skill1) { skills.push(skill1); }
                if (skill2) { skills.push(skill2); }
                if (skill3) { skills.push(skill3); }
                if (skill4) { skills.push(skill4); }
                if (skill5) { skills.push(skill5); }
                narrowResult += "<td></td><td colspan='2'>" + skills.join(',') + "</td></tr>";
            });

            wideResult += "</table>";
            narrowResult += "</table>";
            $('#wiki-card-result').html(wideResult + narrowResult);
            $('#wiki-card-result').parent().removeClass('ui-collapsible-content-collapsed');
        }).complete(function () {
            $('#wiki-card-search').removeClass('ui-disabled');
            $.mobile.loading('hide');
        });
    });
});

})(CardFantasy.Wiki);