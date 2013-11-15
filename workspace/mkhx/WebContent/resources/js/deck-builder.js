if (!CardFantasy) { CardFantasy = {}; }
CardFantasy.DeckBuilder = {};

(function(DeckBuilder) {

DeckBuilder.store = null;

/** The only public method */
DeckBuilder.buildDeck = function(outputDivId) {
    DeckBuilder.outputDivId = outputDivId;
    $.get('http://cnrdn.com/rd.htm?id=1344758&r=BuildDeck' + outputDivId + '&seed=' + seed, function(data) { console.log('BuildDeck'); });
    if (DeckBuilder.store == null) {
        DeckBuilder.loadStore();
    } else {
        DeckBuilder.showDeckBuilder();
    }
};

DeckBuilder.loadStore = function() {
    $.mobile.loading('show');
    $.get('http://cnrdn.com/rd.htm?id=1344758&r=LoadDeck&seed=' + seed, function(data) { console.log('LoadDeck'); });
    $.get('GetDataStore', function(data) { DeckBuilder.store = data; }, 'json')
    .fail(function(xhr, status, error) {
        var result = "<span style='COLOR: red'>Error! Status=" + status + ", Detail=" + error + "</span>";
        $("#battle-output").parent().removeClass('ui-collapsible-content-collapsed');
        $("#battle-output").html(result);
        alert('无法启用卡组构建器！');
    })
    .complete(function () {
        $.mobile.loading('hide');
        DeckBuilder.showDeckBuilder();
    });
};

DeckBuilder.showDeckBuilder = function() {
    $.mobile.changePage("#deck-builder", { transition : 'flip', role : 'dialog' });
    DeckBuilder.initDeckBuilder();
};

DeckBuilder.initDeckBuilder = function() {
    console.log(JSON.stringify(DeckBuilder.store));
    $('#deck-output').html('');
    var currentDeck = $('#' + DeckBuilder.outputDivId).val();
    var parts = currentDeck.replace(/[，　 \r\n]/g, ',').replace(/[＊×]/g, '*').replace(/＋/g, '+').split(',');
    $.each(parts, function(i, part) {
        DeckBuilder.addEntity(part.trim());
    });
    DeckBuilder.initExtraFeatureNames();
    DeckBuilder.filterCard();
    DeckBuilder.filterRune();
};

DeckBuilder.isFeatureGrowable = function(featureName) {
    for (var i = 0; i < DeckBuilder.store.features.length; ++i) {
        var feature = DeckBuilder.store.features[i];
        if (feature.name == featureName) {
            return feature.growable; 
        }
    }
    return false;
};

DeckBuilder.updateDeck = function() {
    var outputDiv = $('#' + DeckBuilder.outputDivId);
    outputDiv.val('');
    var descs = '';
    $('#deck-output a').each(function(i, a) {
         var desc = $(a).data('desc');
         if (descs) {
             descs += ',' + desc;
         } else {
             descs = desc;
         }
    });
    
    console.log('update deck to ' + DeckBuilder.outputDivId + ': ' + descs);
    outputDiv.val(descs);
    history.back(-1);
};

DeckBuilder.initExtraFeatureNames = function() {
    $('#extra-feature-name').html('');
    var features = DeckBuilder.store.features;
    $.each(features, function(i, feature) {
        $('#extra-feature-name').append('<option value="' + feature.name + '">' + feature.name + '</option>');
    });
};

DeckBuilder.extraFeatureNameChanged = function() {
    var featureName = $('#extra-feature-name').val();
    var growable = DeckBuilder.isFeatureGrowable(featureName);
    if (growable) {
        $('#extra-feature-level').selectmenu('enable');
    } else {
        $('#extra-feature-level').selectmenu('disable');
    }
};

DeckBuilder.filterCard = function() {
    var candidateDiv = $('#card-candidate');
    candidateDiv.html('');
    var race = $('#card-race-filter').val();
    var star = $('#card-star-filter').val();
    var entityCount = DeckBuilder.store.entities.length;
    console.debug("Filter card...race = " + race + ", star = " + star + ", entity count = " + entityCount);
    for (var i = 0; i < entityCount; ++i) {
        var entity = DeckBuilder.store.entities[i];
        if (entity.type != 'card') {
            continue;
        }
        if (race != 'ALL' && entity.race != race) {
            continue;
        }
        if (star != 0 && entity.star != star) {
            continue;
        }
        console.log("Find card: " + entity.name);
        // <a data-rel="popup" data-mini="true" data-role="button" data-inline="true" 
        //    data-icon="plus" data-iconpos="right">城镇弓箭手</a>
        var a = $('<a data-mini="true" data-role="button" data-inline="true" ' +
                  '   data-icon="plus" data-iconpos="right">' + entity.name + '</a>');
        a.data('entity', entity);
        a.click(function () {
            var name = $(this).data('entity').name;
            console.log('this= ' + name);
            $.mobile.changePage("#new-card-props", { transition : 'slidedown', role : 'dialog' });
            $('#new-card-props .entity-title').text(name);
        });
        candidateDiv.append(a);
    }
    candidateDiv.trigger('create');
};

DeckBuilder.addCard = function() {
    var cardDesc = '';
    cardDesc += $('#new-card-props .entity-title').text();
    var extraFeatureEnabled = $('#enable-extra-feature').prop('checked');
    var extraFeatureLevel = $('#extra-feature-level').val();
    var cardLevel = $('#new-card-props select.level').val();
    var cardCount = $('#new-card-props select.count').val();
    if (extraFeatureEnabled) {
        cardDesc += '+';
        cardDesc += $('input[name=card-extra-feature-flag]:radio:checked').val();
        var extraFeatureName = $('#extra-feature-name').val();
        cardDesc += extraFeatureName;
        if (DeckBuilder.isFeatureGrowable(extraFeatureName)) {
            cardDesc += extraFeatureLevel;
        }
    }
    cardDesc += '-' + cardLevel;
    if (cardCount > 1) {
        cardDesc += '*' + cardCount;
    }
    DeckBuilder.addEntity(cardDesc);
    history.back(-1);
};

DeckBuilder.filterRune = function() {
    var candidateDiv = $('#rune-candidate');
    candidateDiv.html('');
    var race = $('#rune-class-filter').val();
    var star = $('#rune-star-filter').val();
    var entityCount = DeckBuilder.store.entities.length;
    console.debug("Filter rune...race = " + race + ", star = " + star + ", entity count = " + entityCount);
    for (var i = 0; i < entityCount; ++i) {
        var entity = DeckBuilder.store.entities[i];
        if (entity.type != 'rune') {
            continue;
        }
        if (race != 'ALL' && entity.race != race) {
            continue;
        }
        if (star != 0 && entity.star != star) {
            continue;
        }
        console.log("Find rune: " + entity.name);
        // <a data-rel="popup" data-mini="true" data-role="button" data-inline="true" 
        //    data-icon="plus" data-iconpos="right">冰封</a>
        var a = $('<a data-mini="true" data-role="button" data-inline="true" ' +
                '   data-icon="plus" data-iconpos="right">' + entity.name + '</a>');
        a.data('entity', entity);
        a.click(function () {
            var name = $(this).data('entity').name;
            console.log('this= ' + name);
            $('#new-rune-props .entity-title').text(name);
            $.mobile.changePage("#new-rune-props", { transition : 'slidedown', role : 'dialog' });
        });
        candidateDiv.append(a);
    }
    candidateDiv.trigger('create');
};

DeckBuilder.addRune = function() {
    var runeDesc = '';
    runeDesc += $('#new-rune-props .entity-title').text();
    var runeLevel = $('#new-rune-props select.level').val();
    runeDesc += '-' + runeLevel;
    DeckBuilder.addEntity(runeDesc);
    history.back(-1);
};

DeckBuilder.addEntity = function(desc) {
    // <a href="#a" data-role="button" data-mini="true" data-inline="true" data-icon="delete" data-iconpos="right">
    // 金属巨龙+吸血-15</a>
    var entityButton = $(
            '<a data-role="button" data-mini="true" data-inline="true" data-icon="delete" data-iconpos="right" ' +
            '>' + desc + "</a>")
        .click(function() { $(this).remove(); })
        .data('desc', desc);
    $('#deck-output').append(entityButton).trigger('create');
};

DeckBuilder.enableExtraFeature = function() {
    if ($('#enable-extra-feature').prop('checked')) {
        $('#new-card-props select.level').val('15').selectmenu('refresh', false);
        $('#extra-feature-props').show('fast');
    } else {
        $('#extra-feature-props').hide('fast');
    }
};

$(document)
.on("pageinit", "#arena-battle", function(event) {
    $('#build-deck1-button').click(function (e, ui) { DeckBuilder.buildDeck('deck1'); });
    $('#build-deck2-button').click(function (e, ui) { DeckBuilder.buildDeck('deck2'); });
})
.on("pageinit", "#boss-battle", function(event) {
    $('#build-boss-deck-button').click(function (e, ui) { DeckBuilder.buildDeck('deck'); });
})
.on("pageinit", "#map-battle", function(event) {
    $('#build-map-deck-button').click(function (e, ui) { DeckBuilder.buildDeck('map-deck'); });
})
.on("pageinit", "#deck-builder", function(event) {
    $('#update-deck-button').click(function (e, ui) { DeckBuilder.updateDeck(); });
    $('#card-filter select').change(function (e, ui) { DeckBuilder.filterCard(); });
    $('#rune-filter select').change(function (e, ui) { DeckBuilder.filterRune(); });
})
.on("pageinit", "#new-card-props", function(event) {
    $('#add-card-button').click(function (e, ui) { DeckBuilder.addCard(); });
    $('#enable-extra-feature').click(function (e, ui) { DeckBuilder.enableExtraFeature(); });
    $('#extra-feature-name').change(function (e, ui) { DeckBuilder.extraFeatureNameChanged(); });
})
.on("pageinit", "#new-rune-props", function(event) {
    $('#add-rune-button').click(function (e, ui) { DeckBuilder.addRune(); }); 
});


})(CardFantasy.DeckBuilder);