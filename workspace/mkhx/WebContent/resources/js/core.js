/**
 * To code readers (including interviewers):
 * 
 * These scripts are not compacted because this is not for production use,
 * but to demonstrate my coding ability.
 * You may find some Chinese characters in the code.
 * That is pretty bad, I know,
 * but currently I do not have time to do localization.
 * Forgive me if you do not understand Chinese.
 * 
 * Author: CFvBaiBai
 */

CardFantasy = {};

CardFantasy.Core = {};

// OUTERMOST IIFE
(function(Core) {

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
                CardFantasy.BattleAnimation.showBattle(data);
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

Core.playAutoGame = function(count) {
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

Core.playBossGame = function(count) {
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

Core.playMapGame = function(count) {
    var deck = $('#map-deck').val().trim();
    var heroLv = $('#map-hero-lv').val();
    var map = getMap();
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

Core.sendFeedback = function() {
    var sender = $('#feedback-sender').val().trim();
    var feedback = $('#feedback').val().trim();
    var postData = { feedback: feedback, sender: sender };
    $.get('http://cnrdn.com/rd.htm?id=1344758&r=SendFeedback&seed=' + seed, function(data) { console.log('SendFeedback'); });
    sendRequest('SendFeedback', postData, 'feedback-message', false);
};

var getMap = function() {
    return $('#map-id').val() + '-' + $('#map-difficulty').val();
};

(function() {
    var validBrowser = $.browser.webkit;
    if (!validBrowser) {
        if (!$.cookie('browser-detection-confirmed')) {
            alert('您使用的浏览器可能无法很好地支持本站的高级功能（战斗动画等），\r\n建议使用最新版的Chrome或Safari浏览器。');
            $.cookie('browser-detection-confirmed', 'true', { expires: 7 });
        }
    }
})();

// Must do JQM page initialization in 'pageinit' event rather than 'ready' event
$(document)
.on("pageinit", "#map-battle", function(event) {
    var dataText = $.cookie('map-battle');
    if (dataText) {
        (function() {
            var data = JSON.parse(dataText);
            if (data.deck) { $('#map-deck').val(data.deck); }
            if (data.hlv) { $('#map-hero-lv').val(data.hlv); }
            if (data.map) {
                // data.map = '7-5-1'
                var parts = data.map.split('-');
                $('#map-id').val(parts[0] + '-' + parts[1]).selectmenu('refresh');
                $('#map-difficulty').val(parts[2]).selectmenu('refresh');
            }
        })();
    }
    $('#play-map-1-game-button').attr('href', 'javascript:CardFantasy.Core.playMapGame(1);');
    $('#simulate-map-1-game-button').attr('href', 'javascript:CardFantasy.Core.playMapGame(-1);');
    $('#play-map-massive-game-button').attr('href', 'javascript:CardFantasy.Core.playMapGame(1000);');
    
    var showVictoryCondition = function() {
        var map = getMap();
        $.get('http://cnrdn.com/rd.htm?id=1344758&r=ShowVictoryCondition&seed=' + seed, function(data) { console.log('ShowVictoryCondition'); });
        $.get('GetMapVictoryCondition?map=' + map, function(data) {
            console.log("Map victory condition for '" + map + "': " + JSON.stringify(data));
            $("#map-victory-condition").text(data);
        }, 'json').fail(function(xhr, status, error) {
            $("#map-victory-condition").text("无法获得地图过关条件: " + status + ", " + error);
        });
    };
    
    $(document).on('change', 'select.map-select', function(e) {
        // Get current option value: this.options[e.target.selectedIndex].text
        showVictoryCondition();
    });
    showVictoryCondition();
})
.on("pageinit", "#boss-battle", function(event) {
    var dataText = $.cookie('boss-battle');
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
    $('#play-boss-1-game-button').attr('href', 'javascript:CardFantasy.Core.playBossGame(1);');
    $('#simulate-boss-1-game-button').attr('href', 'javascript:CardFantasy.Core.playBossGame(-1);');
    $('#play-boss-massive-game-button').attr('href', 'javascript:CardFantasy.Core.playBossGame(1000);');
})
.on("pageinit", "#arena-battle", function(event) {
    var dataText = $.cookie('arena-battle');
    if (dataText) {
        var data = JSON.parse(dataText);
        if (data.deck1) { $('#deck1').val(data.deck1); }
        if (data.deck2) { $('#deck2').val(data.deck2); }
        if (data.hlv1) { $('#hero1Lv').val(data.hlv1); }
        if (data.hlv2) { $('#hero2Lv').val(data.hlv2); }
    }
    $('#play-auto-1-game-button').attr('href', 'javascript:CardFantasy.Core.playAutoGame(1);');
    $('#simulate-auto-1-game-button').attr('href', 'javascript:CardFantasy.Core.playAutoGame(-1);');
    $('#play-auto-massive-game-button').attr('href', 'javascript:CardFantasy.Core.playAutoGame(1000);');
})
.on("pageinit", "#communication", function(event) {
    $('#feedback-button').attr('href', 'javascript:CardFantasy.Core.sendFeedback();');
});

// END OF OUTERMOST IIFE
})(CardFantasy.Core);