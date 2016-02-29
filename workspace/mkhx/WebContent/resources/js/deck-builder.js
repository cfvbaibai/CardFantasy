CardFantasy.DeckBuilder = {};

// OUTERMOST IIFE
(function(DeckBuilder) {

var store = null;
var outputDivId = null;

var getWikiUrl = function(type, name) {
    return rootDir + 'Wiki/' + type + 's/' + name;
};

DeckBuilder.buildDeck = function(_outputDivId) {
    outputDivId = _outputDivId;
    //$.get('http://cnrdn.com/rd.htm?id=1344758&r=BuildDeck' + outputDivId + '&seed=' + seed, function(data) { console.log('BuildDeck'); });
    if (store == null) {
        loadStore();
    } else {
        showDeckBuilder();
    }
};

var loadStore = function() {
    $.mobile.loading('show');
    //$.get('http://cnrdn.com/rd.htm?id=1344758&r=LoadDeck&seed=' + seed, function(data) { console.log('LoadDeck'); });
    $.get('GetDataStore', function(data) { store = data; }, 'json')
    .fail(function(xhr, status, error) {
        var result = "<span style='COLOR: red'>Error! Status=" + status + ", Detail=" + error + "</span>";
        $("#battle-output").parent().removeClass('ui-collapsible-content-collapsed');
        $("#battle-output").html(result);
        alert('无法启用卡组构建器！');
    })
    .complete(function () {
        $.mobile.loading('hide');
        showDeckBuilder();
    });
};

var showDeckBuilder = function() {
    $.mobile.changePage("#deck-builder", { transition : 'flip', role : 'dialog' });
    initDeckBuilder();
};

var initDeckBuilder = function() {
    console.log(JSON.stringify(store));
    $('#deck-output').html('');
    var currentDeck = $('#' + outputDivId).val();
    var parts = currentDeck.replace(/[，　 \r\n]/g, ',').replace(/[＊×]/g, '*').replace(/＋/g, '+').split(',');
    $.each(parts, function(i, part) {
        addEntity(part.trim());
    });
    initExtraSkillNames();
    filterCard();
    filterRune();
};

var isSkillGrowable = function(skillName) {
    for (var i = 0; i < store.skills.length; ++i) {
        var skill = store.skills[i];
        if (skill.name == skillName) {
            return skill.growable; 
        }
    }
    return false;
};

DeckBuilder.updateDeck = function() {
    var outputDiv = $('#' + outputDivId);
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
    
    console.log('update deck to ' + outputDivId + ': ' + descs);
    outputDiv.val(descs);
    history.back(-1);
};

var updateSkillDetailButtonHref = function() {
    var button = $('#extra-skill-props a.skill-detail-button');
    var skillName = $('#extra-skill-name').val();
    var skill = null;
    for (var i = 0; i < store.skills.length; ++i) {
        if (store.skills[i].name == skillName) {
            skill = store.skills[i];
            break;
        }
    }
    if (skill && skill.wikiId) {
        button.attr('href', getWikiUrl('Skill', skill.name));
    } else {
        button.attr('href', '#');
    }
};

var initExtraSkillNames = function() {
    $('#extra-skill-name').html('');
    var skills = store.skills;
    $.each(skills, function(i, skill) {
        $('#extra-skill-name').append('<option value="' + skill.name + '">' + skill.name + '</option>');
    });
    updateSkillDetailButtonHref();
};

var extraSkillNameChanged = function() {
    var skillName = $('#extra-skill-name').val();
    var growable = isSkillGrowable(skillName);
    if (growable) {
        $('#extra-skill-level').selectmenu('enable');
    } else {
        $('#extra-skill-level').selectmenu('disable');
    }
    updateSkillDetailButtonHref();
};

DeckBuilder.onAddCardButtonClick = function(id) {
    var entity = $('#' + id).data('entity');
    $.mobile.changePage("#new-card-props", { transition : 'slidedown', role : 'dialog' });
    $('#new-card-props div.entity-title span.entity-title-text').text(entity.name);
    $('#new-card-props a.entity-detail-button').attr('href', getWikiUrl('Card', entity.name));
};

DeckBuilder.onAddRuneButtonClick = function(id) {
    var entity = $('#' + id).data('entity');
    $.mobile.changePage("#new-rune-props", { transition : 'slidedown', role : 'dialog' });
    $('#new-rune-props div.entity-title span.entity-title-text').text(entity.name);
    $('#new-rune-props a.entity-detail-button').attr('href', getWikiUrl('Rune', entity.name));
};

var filterCard = function() {
    var candidateDiv = $('#card-candidate');
    candidateDiv.html('');
    var race = $('#card-race-filter').val();
    var star = $('#card-star-filter').val();
    var entityCount = store.entities.length;
    console.debug("Filter card...race = " + race + ", star = " + star + ", entity count = " + entityCount);
    for (var i = 0; i < entityCount; ++i) {
        var entity = store.entities[i];
        if (entity.type != 'card') {
            continue;
        }
        if (race != 'ALL' && entity.race != race) {
            continue;
        }
        if (star != 0 && entity.star != star) {
            continue;
        }
        console.debug("Find card: " + entity.name);
        var id = 'add-card-' + entity.name;
        var a = $('<a data-mini="true" data-role="button" data-inline="true" ' +
                  '   data-icon="plus" data-iconpos="right">' + entity.name + '</a>');
        a.attr('id', id);
        a.attr('href', 'javascript:CardFantasy.DeckBuilder.onAddCardButtonClick("' + id + '")');
        a.data('entity', entity);
        candidateDiv.append(a);
    }
    candidateDiv.trigger('create');
};

DeckBuilder.addCard = function() {
    var cardDesc = '';
    cardDesc += $('#new-card-props div.entity-title span.entity-title-text').text();
    var extraSkillEnabled = $('#enable-extra-skill').prop('checked');
    var extraSkillLevel = $('#extra-skill-level').val();
    var cardLevel = $('#new-card-props select.level').val();
    var cardCount = $('#new-card-props select.count').val();
    if (extraSkillEnabled) {
        cardDesc += '+';
        cardDesc += $('input[name=card-extra-skill-flag]:radio:checked').val();
        var extraSkillName = $('#extra-skill-name').val();
        cardDesc += extraSkillName;
        if (isSkillGrowable(extraSkillName)) {
            cardDesc += extraSkillLevel;
        }
    }
    cardDesc += '-' + cardLevel;
    if (cardCount > 1) {
        cardDesc += '*' + cardCount;
    }
    addEntity(cardDesc);
    history.back(-1);
};

var filterRune = function() {
    var candidateDiv = $('#rune-candidate');
    candidateDiv.html('');
    var race = $('#rune-class-filter').val();
    var star = $('#rune-star-filter').val();
    var entityCount = store.entities.length;
    console.debug("Filter rune...race = " + race + ", star = " + star + ", entity count = " + entityCount);
    for (var i = 0; i < entityCount; ++i) {
        var entity = store.entities[i];
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
        var id = 'add-rune-' + entity.name;
        a.attr('id', id);
        a.attr('href', 'javascript:CardFantasy.DeckBuilder.onAddRuneButtonClick("' + id + '")');
        candidateDiv.append(a);
    }
    candidateDiv.trigger('create');
};

DeckBuilder.addRune = function() {
    var runeDesc = '';
    runeDesc += $('#new-rune-props div.entity-title span.entity-title-text').text();
    var runeLevel = $('#new-rune-props select.level').val();
    runeDesc += '-' + runeLevel;
    addEntity(runeDesc);
    history.back(-1);
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
};

var enableExtraSkill = function() {
    if ($('#enable-extra-skill').prop('checked')) {
        $('#new-card-props select.level').val('15').selectmenu('refresh', false);
        $('#extra-skill-props').show('fast');
    } else {
        $('#extra-skill-props').hide('fast');
    }
};

$(document)
.on("pageinit", "#arena-battle", function(event) {
    $('#build-deck1-button').attr('href', "javascript:CardFantasy.DeckBuilder.buildDeck('deck1');");
    $('#build-deck2-button').attr('href', "javascript:CardFantasy.DeckBuilder.buildDeck('deck2');");
})
.on("pageinit", "#boss-battle", function(event) {
    $('#build-boss-deck-button').attr('href', "javascript:CardFantasy.DeckBuilder.buildDeck('deck');");
})
.on("pageinit", "#lilith-battle", function(event) {
    $('#build-lilith-deck-button').attr('href', "javascript:CardFantasy.DeckBuilder.buildDeck('lilith-player-deck');");
})
.on("pageinit", "#map-battle", function(event) {
    $('#build-map-deck-button').attr('href', "javascript:CardFantasy.DeckBuilder.buildDeck('map-deck');");
})
.on("pageinit", "#deck-builder", function(event) {
    $('#update-deck-button').attr('href', 'javascript:CardFantasy.DeckBuilder.updateDeck();');
    $('#card-filter select').change(function(e, ui) { filterCard(); });
    $('#rune-filter select').change(function(e, ui) { filterRune(); });
})
.on("pageinit", "#new-card-props", function(event) {
    $('#add-card-button').attr('href', 'javascript:CardFantasy.DeckBuilder.addCard();');
    $('#enable-extra-skill').click(function(e, ui) { enableExtraSkill(); });
    $('#extra-skill-name').change(function(e, ui) { extraSkillNameChanged(); });
})
.on("pageinit", "#new-rune-props", function(event) {
    $('#add-rune-button').attr('href', 'javascript:CardFantasy.DeckBuilder.addRune();');
});

// END OF OUTERMOST IIFE
})(CardFantasy.DeckBuilder);