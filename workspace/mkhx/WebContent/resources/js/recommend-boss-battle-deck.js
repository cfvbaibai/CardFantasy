CardFantasy.RecommendBossBattleDeck = {};

(function(Me) {
var Core = CardFantasy.Core;

$(document).on("pageinit", "#recommend-boss-battle-deck", function(event) {
    var page = $(event.target);
    page.find('a.right-nav-button .ui-btn-text').text('回顶部');
    page.find('a.right-nav-button')
        .attr('href', 'javascript:window.scrollTo(0, 0)')
        .attr('data-icon', 'arrow-u')
        .show().buttonMarkup('refresh');
    page.find('#recommend-boss-battle-deck-button').attr('href', 'javascript:CardFantasy.RecommendBossBattleDeck.recommend();');
    page.find('div.result-frame').hide();
    page.trigger('create');
});

var recommend = function() {
    var baseDiv = $('#recommend-boss-battle-deck');
    Core.sendJsonRequest({
        url : 'RecommendBossBattleDeck',
        postData : {
            bossName : baseDiv.find('select[name="boss-name"]').val().trim(),
            maxHeroLv : baseDiv.find('input[name="max-hero-lv"]').val().trim()
        },
        dataHandler : function(context, data) {
            console.log(JSON.stringify(data));
            var colNames = ['heroLv', 'minDamage', 'avgDamage', 'maxDamage', 'sortedDeck'];
            var colLabels = ['英雄等级', '最小伤害', '平均伤害', '最大伤害', ''];
            Core.createDivTable(data.decksWithBestMaxDamage, colNames, colLabels)
                .addClass('recommend-boss-battle-result')
                .appendTo(baseDiv.find('div.best-max-damage-deck').html(''));
            Core.createDivTable(data.decksWithBestAvgDamage, colNames, colLabels)
                .addClass('recommend-boss-battle-result')
                .appendTo(baseDiv.find('div.best-avg-damage-deck').html(''));
            baseDiv.find('div.best-max-damage-deck').removeClass('ui-collapsible-content-collapsed');
            baseDiv.find('div.result-frame').show();
        }
    });
};
Me.recommend = recommend;

})(CardFantasy.RecommendBossBattleDeck);