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
    if (race == 'KINGDOM') {
        return '王国';
    }
    if (race == 'FOREST') {
        return '森林';
    }
    if (race == 'SAVAGE') {
        return '蛮荒';
    }
    if (race == 'HELL') {
        return '地狱';
    }
    return '未知';
};

var store = {};
$(document).on("pageinit", "#wiki", function(event) {
    $.get('GetDataStore', function(data) { store = data; }, 'json').complete(function() {
        $('#wiki-card-search').removeClass('ui-disabled');
        $('#wiki-card-search').click();
    });

    $('#wiki-card-search').click(function() {
        var result = "<table class='data'><tr><td>卡牌</td><td>星数</td><td>种族</td></tr>";
        var starFilter = $('#wiki-card-star-filter').val();
        var raceFilter = $('#wiki-card-race-filter').val();
        $.each(store.entities, function(i, entity) {
            if (entity.type != 'card') { return; }
            if (starFilter != 0 && entity.star != starFilter) { return; }
            if (raceFilter != 'All' && entity.race != raceFilter) { return; }
            result += "<tr><td><a href='Cards/" + entity.name + "' target='_blank'>" + entity.name + "</a></td><td>" + toStarText(entity.star) + "</td>";
            result += "<td>" + toRaceText(entity.race) + "</td></tr>";
        });
        result += "</table>";
        $('#wiki-result-content').html(result);
    });
});

})(CardFantasy.Wiki);