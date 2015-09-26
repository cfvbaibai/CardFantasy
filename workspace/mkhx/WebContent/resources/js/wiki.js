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
        case '0': return '王国';
        case '1': return '森林';
        case '2': return '蛮荒';
        case '3': return '地狱';
        case '97': return '魔王';
        case '100': return '魔神';
        default: return '未知';
    }
};

var store = {};
$(document).on("pageinit", "#wiki", function(event) {
    $('#wiki-card-search').click(function() {
        var starFilter = $('#wiki-card-star-filter').val();
        var raceFilter = $('#wiki-card-race-filter').val();
        var skillTypeFilter = $('#wiki-card-skill-type-filter').val();
        var queryUrl ='OfficialData/Cards?stars=' + starFilter + '&races=' + raceFilter + '&skillTypes=' + skillTypeFilter;

        $.get(queryUrl, function(data) {
            var wideResult = "";
            var narrowResult = "";
            wideResult += "<table class='wiki-card-result-wide'>";
            wideResult += "<tr><td>卡牌</td><td>星数</td><td>种族</td><td colspan='5'>技能</td></tr>";
            narrowResult += "<table class='wiki-card-result-narrow'>";
            narrowResult += "<tr><td>卡牌</td><td>星数</td><td>种族</td></tr>";
            $.each(data, function(i, cardInfo) {
                var card = cardInfo.card;
                var skill1 = (cardInfo.skill1 ? cardInfo.skill1.Name : '');
                var skill2 = (cardInfo.skill2 ? cardInfo.skill2.Name : '');
                var skill3 = (cardInfo.skill3 ? cardInfo.skill3.Name : '');
                var skill4 = (cardInfo.skill4 ? cardInfo.skill4.Name : '');
                var skill5 = (cardInfo.skill5 ? cardInfo.skill5.Name : '');
                var cardName = "<a href='Cards/" + card.CardName + "' target='_blank'>" + card.CardName + "</a>";
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
        });
    });
});

})(CardFantasy.Wiki);