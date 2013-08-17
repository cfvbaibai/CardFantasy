//$.ajaxSetup({ scriptCharset: "utf-8" ,contentType: "application/x-www-form-urlencoded; charset=UTF-8" });
var sendRequest = function(url, outputDivId, isJson) {
    $.mobile.loading('show');
    var result = '';
    var getFunc = isJson ? function() {
        return $.getJSON(url, function(data) {
            result = JSON.stringify(data);
            $.mobile.changePage("#arena", { transition : 'flip', role : 'dialog' });
            showBattle(data);
        });
    } : function() {
        return $.get(url, function(data) {
            result = data;
        });
    };
    getFunc()
    .fail(function(xhr, status, error) {
        result = "<span style='COLOR: red'>Error! Status=" + status + ", Detail=" + error + "</span>";
    })
    .complete(function () {
        $.mobile.loading('hide');
        $("#" + outputDivId).parent().removeClass('ui-collapsible-content-collapsed');
        $("#" + outputDivId).html(result);
    });
};

var playAutoGame = function(count) {
    var deck1 = $('#deck1').val();
    var deck2 = $('#deck2').val();
    var heroLv1 = $('#hero1Lv').val();
    var heroLv2 = $('#hero2Lv').val();
    var firstAttack = $('input[name=firstAttack]').val();
    var url = '';
    url += '?deck1=' + encodeURIComponent(deck1);
    url += '&deck2=' + encodeURIComponent(deck2);
    url += '&hlv1=' + encodeURIComponent(heroLv1);
    url += '&hlv2=' + encodeURIComponent(heroLv2);
    url += '&firstAttack=' + firstAttack;
    if (count == 1) {
        url = 'PlayAuto1MatchGame' + url;
    } else {
        url = 'PlayAutoMassiveGame' + url;
        url += '&count=' + count;
    }
    sendRequest(url, 'battle-output');
};

var playBossGame = function(count) {
    var deck = $('#deck').val();
    var heroLv = $('#heroLv').val();
    var bossName = $('#boss-name').val();
    var buffKingdom = $('#buff-kingdom').val();
    var buffForest = $('#buff-forest').val();
    var buffSavage = $('#buff-savage').val();
    var buffHell = $('#buff-hell').val();
    var url = '';
    url += '?deck=' + encodeURIComponent(deck);
    url += '&hlv=' + encodeURIComponent(heroLv);
    url += '&bn=' + encodeURIComponent(bossName);
    url += '&bk=' + encodeURIComponent(buffKingdom);
    url += '&bf=' + encodeURIComponent(buffForest);
    url += '&bs=' + encodeURIComponent(buffSavage);
    url += '&bh=' + encodeURIComponent(buffHell);
    var isJson = false;
    if (count == 1) {
        url = 'PlayBoss1MatchGame' + url;
    } else if (count == -1) {
        url = 'SimulateBoss1MatchGame' + url;
        isJson = true;
    } else {
        url = 'PlayBossMassiveGame' + url;
        url += '&count=' + count;
    }
    sendRequest(url, 'boss-battle-output', isJson);
    //sendRequest(url, 'boss-battle-output');
};