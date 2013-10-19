var DeckBuilder = {};

var initDeckBuilder = function(store, outputDivId) {
    DeckBuilder.store = store;
    DeckBuilder.outputDivId = outputDivId;
    console.log(JSON.stringify(store));
    $('#deck-output').html('');
    initExtraFeatureNames();
    filterCard();
    filterRune();
};

var updateDeck = function() {
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

var initExtraFeatureNames = function() {
    $('#extra-feature-name').html('');
    var featureNames = DeckBuilder.store.features;
    $.each(featureNames, function(i, feature) {
        $('#extra-feature-name').append('<option value="' + feature + '">' + feature + '</option>');
    });
};

var filterCard = function() {
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
            $('#new-card-props .entity-title').text(name);
            $.mobile.changePage("#new-card-props", { transition : 'slidedown', role : 'dialog' });
        });
        candidateDiv.append(a);
    }
    candidateDiv.trigger('create');
};

var addCard = function() {
    var cardDesc = '';
    cardDesc += $('#new-card-props .entity-title').text();
    var extraFeatureEnabled = $('#enable-extra-feature').prop('checked');
    var extraFeatureLevel = $('#extra-feature-level').val();
    var cardLevel = $('#new-card-props select.level').val();
    var cardCount = $('#new-card-props select.count').val();
    if (extraFeatureEnabled) {
        cardDesc += '+';
        cardDesc += $('input[name=card-extra-feature-flag]:radio:checked').val();
        cardDesc += $('#extra-feature-name').val();
        if (extraFeatureLevel > 0) {
            cardDesc += extraFeatureLevel;
        }
    }
    if (extraFeatureEnabled && cardLevel != 15 ||
        !extraFeatureEnabled && cardLevel != 10) {
        cardDesc += '-' + cardLevel;
    }
    if (cardCount > 1) {
        cardDesc += '*' + cardCount;
    }
    addEntity(cardDesc);
};

var filterRune = function() {
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

var addRune = function() {
    var runeDesc = '';
    runeDesc += $('#new-rune-props .entity-title').text();
    var runeLevel = $('#new-rune-props select.level').val();
    if (runeLevel != 4) {
        runeDesc += '-' + runeLevel;
    }
    addEntity(runeDesc);
};

var addEntity = function(desc) {
    // <a href="#a" data-role="button" data-mini="true" data-inline="true" data-icon="delete" data-iconpos="right">
    // 金属巨龙+吸血-15</a>
    var entityButton = $(
            '<a data-role="button" data-mini="true" data-inline="true" data-icon="delete" data-iconpos="right" ' +
            '>' + desc + "</a>")
        .click(function() { $(this).remove(); })
        .data('desc', desc);
    $('#deck-output').append(entityButton).trigger('create');
    history.back(-1);
};

var enableExtraFeature = function() {
    if ($('#enable-extra-feature').prop('checked')) {
        $('#extra-feature-props').show('fast');
    } else {
        $('#extra-feature-props').hide('fast');
    }
};