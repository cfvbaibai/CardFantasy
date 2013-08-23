//$.ajaxSetup({ scriptCharset: "utf-8" ,contentType: "application/x-www-form-urlencoded; charset=UTF-8" });
var sendRequest = function(url, postData, outputDivId, isJson) {
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
        $("#" + outputDivId).parent().removeClass('ui-collapsible-content-collapsed');
        $("#" + outputDivId).html(result);
    });
};

var playAutoGame = function(count) {
    var deck1 = $('#deck1').val().trim();
    var deck2 = $('#deck2').val().trim();
    var heroLv1 = $('#hero1Lv').val();
    var heroLv2 = $('#hero2Lv').val();
    var firstAttack = $('input[name=firstAttack]').val();
    var isJson = false;
    var url = '';
    var postData = {
        deck1: deck1,
        deck2: deck2,
        hlv1: heroLv1,
        hlv2: heroLv2,
        firstAttack: firstAttack,
    };

    if (count == 1) {
        url = 'PlayAuto1MatchGame';
    } else if (count == -1) {
        isJson = true;
        url = 'SimAuto1MatchGame';
    } else {
        url = 'PlayAutoMassiveGame';
        postData["count"] = count;
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
    var isJson = false;
    if (count == 1) {
        url = 'PlayBoss1MatchGame' + url;
    } else if (count == -1) {
        url = 'SimulateBoss1MatchGame' + url;
        isJson = true;
    } else {
        url = 'PlayBossMassiveGame' + url;
        postData['count'] = count;
    }
    sendRequest(url, postData, 'boss-battle-output', isJson);
    //sendRequest(url, 'boss-battle-output');
};