//$.ajaxSetup({ scriptCharset: "utf-8" ,contentType: "application/x-www-form-urlencoded; charset=UTF-8" });
var sendRequest = function(url, postData, outputDivId, isJson) {
    var buttons = $('a.battle-button');
    buttons.addClass("ui-disabled");
    $.mobile.loading('show');
    var result = '';
    var getFunc = isJson ? function() {
        return $.post(url, postData, function(data) {
            result = JSON.stringify(data);
            if (data.error) {
                result = data.message;
            } else {
                $.mobile.changePage("#arena", { transition : 'flip', role : 'dialog' });
                showBattle(data);
            }
        }, 'json');
    } : function() {
        return $.post(url, postData, function(data) {
            result = data;
        });
    };
    getFunc()
    .fail(function(xhr, status, error) {
        result = "<span style='COLOR: red'>Error! Status=" + status + ", Detail=" + error + "</span>";
    })
    .complete(function () {
        $.mobile.loading('hide');
        buttons.removeClass("ui-disabled");
        $("#" + outputDivId).parent().removeClass('ui-collapsible-content-collapsed');
        $("#" + outputDivId).html(result);
    });
};

var playAutoGame = function(count) {
    var deck1 = $('#deck1').val().trim();
    var deck2 = $('#deck2').val().trim();
    var heroLv1 = $('#hero1Lv').val();
    var heroLv2 = $('#hero2Lv').val();
    var firstAttack = $('input[name=firstAttack]:radio:checked').val();
    var isJson = false;
    var url = '';
    var postData = {
        deck1: deck1,
        deck2: deck2,
        hlv1: heroLv1,
        hlv2: heroLv2,
        firstAttack: firstAttack,
    };

    console.log('saving cookie in arena-battle...');
    $.cookie('arena-battle', JSON.stringify(postData), { expires: 365 });

    if (count == 1) {
        url = 'PlayAuto1MatchGame';
        $.get('http://cnrdn.com/rd.htm?id=1344758&r=PlayAuto1MatchGame&seed=' + seed, function(data) { console.log('PlayAuto1MatchGame'); });
    } else if (count == -1) {
        isJson = true;
        url = 'SimAuto1MatchGame';
        $.get('http://cnrdn.com/rd.htm?id=1344758&r=SimAuto1MatchGame&seed=' + seed, function(data) { console.log('SimAuto1MatchGame'); });
    } else {
        url = 'PlayAutoMassiveGame';
        postData["count"] = count;
        $.get('http://cnrdn.com/rd.htm?id=1344758&r=PlayAutoMassiveGame&seed=' + seed, function(data) { console.log('PlayAutoMassiveGame'); });
    }
    sendRequest(url, postData, 'battle-output', isJson);
};

var playBossGame = function(count) {
    var deck = $('#deck').val().trim();
    var heroLv = $('#heroLv').val();
    var bossName = $('#boss-name').val();
    var buffKingdom = $('#buff-kingdom').val();
    var buffForest = $('#buff-forest').val();
    var buffSavage = $('#buff-savage').val();
    var buffHell = $('#buff-hell').val();
    var url = '';
    var postData = {
        deck: deck,
        hlv: heroLv,
        bn: bossName,
        bk: buffKingdom,
        bf: buffForest,
        bs: buffSavage,
        bh: buffHell,
    };

    $.cookie('boss-battle', JSON.stringify(postData), { expires: 365 });
    var isJson = false;
    if (count == 1) {
        url = 'PlayBoss1MatchGame' + url;
        $.get('http://cnrdn.com/rd.htm?id=1344758&r=PlayBoss1MatchGame&seed=' + seed, function(data) { console.log('PlayBoss1MatchGame'); });
    } else if (count == -1) {
        url = 'SimulateBoss1MatchGame' + url;
        $.get('http://cnrdn.com/rd.htm?id=1344758&r=SimulateBoss1MatchGame&seed=' + seed, function(data) { console.log('SimulateBoss1MatchGame'); });
        isJson = true;
    } else {
        url = 'PlayBossMassiveGame' + url;
        postData['count'] = count;
        $.get('http://cnrdn.com/rd.htm?id=1344758&r=PlayBossMassiveGame&seed=' + seed, function(data) { console.log('PlayBossMassiveGame'); });
    }
    sendRequest(url, postData, 'boss-battle-output', isJson);
};

var playMapGame = function(count) {
    var deck = $('#map-deck').val().trim();
    var heroLv = $('#map-hero-lv').val();
    var map = $('#map-id').val() + '-' + $('#map-difficulty').val();
    var url = '';
    var postData = {
        deck: deck,
        hlv: heroLv,
        map: map,
    };

    $.cookie('map-battle', JSON.stringify(postData), { expires: 365 });
    var isJson = false;
    if (count == 1) {
        url = 'PlayMap1MatchGame' + url;
        $.get('http://cnrdn.com/rd.htm?id=1344758&r=PlayMap1MatchGame&seed=' + seed, function(data) { console.log('PlayMap1MatchGame'); });
    } else if (count == -1) {
        url = 'SimulateMap1MatchGame' + url;
        $.get('http://cnrdn.com/rd.htm?id=1344758&r=SimulateMap1MatchGame&seed=' + seed, function(data) { console.log('SimulateMap1MatchGame'); });
        isJson = true;
    } else {
        url = 'PlayMapMassiveGame' + url;
        postData['count'] = count;
        $.get('http://cnrdn.com/rd.htm?id=1344758&r=PlayMapMassiveGame&seed=' + seed, function(data) { console.log('PlayMapMassiveGame'); });
    }
    sendRequest(url, postData, 'map-battle-output', isJson);
};

var store = null;
var showDeckBuilder = function(outputDivId) {
    $.mobile.changePage("#deck-builder", { transition : 'flip', role : 'dialog' });
    initDeckBuilder(store, outputDivId);
};

var buildDeck = function(outputDivId) {
    $.get('http://cnrdn.com/rd.htm?id=1344758&r=BuildDeck' + outputDivId + '&seed=' + seed, function(data) { console.log('BuildDeck'); });
    if (store == null) {
        loadStore(outputDivId);
    } else {
        showDeckBuilder(outputDivId);
    }
};

var loadStore = function(outputDivId) {
    $.mobile.loading('show');
    $.get('http://cnrdn.com/rd.htm?id=1344758&r=LoadDeck&seed=' + seed, function(data) { console.log('LoadDeck'); });
    $.get('GetDataStore', function(data) { store = data; }, 'json')
    .fail(function(xhr, status, error) {
        var result = "<span style='COLOR: red'>Error! Status=" + status + ", Detail=" + error + "</span>";
        $("#battle-output").parent().removeClass('ui-collapsible-content-collapsed');
        $("#battle-output").html(result);
        alert('无法启用卡组构建器！');
    })
    .complete(function () {
        $.mobile.loading('hide');
        showDeckBuilder(outputDivId);
    });
};

var detectBrowser = function() {
    var validBrowser = $.browser.webkit;
    if (!validBrowser) {
        if (!$.cookie('browser-detection-confirmed')) {
            alert('您使用的浏览器可能无法很好地支持本站的高级功能（战斗动画等），\r\n建议使用最新版的Chrome或Safari浏览器。');
            $.cookie('browser-detection-confirmed', 'true', { expires: 7 });
        }
    }
};

detectBrowser();

function isNumber(n) {
    return !isNaN(parseFloat(n)) && isFinite(n);
}

$(document).ready(function () {
    // load post data from cookie
    var dataText = $.cookie('arena-battle');
    if (dataText) {
        var data = JSON.parse(dataText);
        if (data.deck1) { $('#deck1').val(data.deck1); }
        if (data.deck2) { $('#deck2').val(data.deck2); }
        if (data.hlv1) { $('#hero1Lv').val(data.hlv1); }
        if (data.hlv2) { $('#hero2Lv').val(data.hlv2); }
        //if (isNumber(data.firstAttack)) {
            //$('input[name=firstAttack]:radio[value=' + data.firstAttack + ']').prop('checked', true);
            //$('input[name=firstAttack]:radio').checkboxradio('refresh');
        //}
    }
    dataText = $.cookie('boss-battle');
    if (dataText) {
        var data = JSON.parse(dataText);
        if (data.deck) { $('#deck').val(data.deck); }
        if (data.hlv) { $('#heroLv').val(data.hlv); }
        if (data.bn) { $('#boss-name').val(data.bn); }
        if (data.bk) { $('#buff-kingdom').val(data.bk); }
        if (data.bf) { $('#buff-forest').val(data.bf); }
        if (data.bs) { $('#buff-savage').val(data.bs); }
        if (data.bh) { $('#buff-hell').val(data.bh); }
    }
    dataText = $.cookie('map-battle');
    if (dataText) {
        var data = JSON.parse(dataText);
        if (data.deck) { $('#map-deck').val(data.deck); }
        if (data.hlv) { $('#map-hero-lv').val(data.hlv); }
        if (data.map) {
            // data.map = '7-5-1'
            var parts = data.map.split('-');
            $('#map-id').val(parts[0] + '-' + parts[1]);
            $('#map-difficulty').val(parts[2]);
        }
    }
});