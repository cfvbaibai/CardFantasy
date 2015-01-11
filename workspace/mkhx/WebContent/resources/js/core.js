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

var getParam = function(originalName) {
    var paramName = originalName.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regex = new RegExp("[\\?&]" + paramName + "=([^&#]*)"),
        results = regex.exec(location.href);
    return results == null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
};
Core.getParam = getParam;

var uploadToCnzzUrl = function(url) {
    /*
    $.get(
            'http://cnrdn.com/rd.htm?id=1344758&r=' + url + '&seed=' + seed,
            function(data) { console.log('Visit to ' + url + ' uploaded to CNZZ.'); }
    );
    */
};
Core.uploadToCnzzUrl = uploadToCnzzUrl;

var sendJsonRequest = function(attrs) {
    attrs.requestType = 'json';
    sendAjaxRequest(attrs);
};
Core.sendJsonRequest = sendJsonRequest;

var sendAjaxRequest = function(attrs) {
    //alert('服务器今晚出现极度不稳定现象，暂时关闭维护，明天开放。抱歉各位');
    //return;
    var url = attrs.url;
    var cnzzUrl = attrs.cnzzUrl || url;
    var postData = attrs.postData;
    var requestType = attrs.requestType; // json for JSON request or undefined for plain text request
    var dataHandler = attrs.dataHandler || function(context, data) {};
    var errorHandler = attrs.errorHandler || function(context, xhr, status, error) { alert('Error: ' + status + '! ' + error); };
    var completeHandler = attrs.completeHandler || function(context) {};

    uploadToCnzzUrl(cnzzUrl);
    var buttons = $('a.battle-button');
    buttons.addClass('ui-disbaled');
    $.mobile.loading('show');
    var context = {};
    $.post(url, postData, function(data) {
        dataHandler(context, data);
    }, requestType).fail(function(xhr, status, error) {
        errorHandler(context, xhr, status, error);
    }).complete(function () {
        $.mobile.loading('hide');
        buttons.removeClass("ui-disabled");
        completeHandler(context);
    });
};
Core.sendAjaxRequest = sendAjaxRequest;

/**
 * json should be a list of hashes.
 * headers should a list of strings.
 */
var createTable = function(rows, colNames, colLabels) {
    var table = $('<table />');
    var thead = $('<thead />').appendTo(table);
    var headerTr = $('<tr />').appendTo(thead);
    $.each(colLabels, function(i, colLabel) { $('<th>' + colLabel + '</th>').appendTo(headerTr); });
    var tbody = $('<tbody />').appendTo(table);
    $.each(rows, function(iRow, row) {
        var rowTr = $('<tr />').appendTo(tbody);
        $.each(colNames, function(i, colName) {
            $('<td>' + row[colName] + '</td>').appendTo(rowTr);
        });
    });
    return table;
};
Core.createTable = createTable;

var createDivTable = function(rows, colNames, colLabels) {
    var baseDiv = $('<div />');
    var headerDiv = $('<div />').appendTo(baseDiv);
    $.each(colLabels, function(i, colLabel) {
        $('<div style="float: left">' + colLabel + '</div>').appendTo(headerDiv);
    });
    $('<div style="clear: both" />').appendTo(baseDiv);
    $.each(rows, function(iRow, row) {
        var rowDiv = $('<div />').appendTo(baseDiv);
        $.each(colNames, function(i, colName) {
            $('<div style="float: left">' + row[colName] + '</div>').appendTo(rowDiv);
        });
        $('<div style="clear: both" />').appendTo(rowDiv);
    });
    return baseDiv;
};
Core.createDivTable = createDivTable;

var sendRequest = function(url, postData, outputDivId, isAnimation) {
    var buttons = $('a.battle-button');
    buttons.addClass("ui-disabled");
    var errorHandler = function(context, xhr, status, error) {
        context.result = "<span style='COLOR: red'>发生错误! 状态=" + status + ", 细节=" + error + "</span>";
        if (status == 'error' && error == 'Not Found') {
            context.result += "<div>服务器可能正在更新，请稍等1分钟左右再试试看</div>";
        }
    };
    var completeHandler = function(context) {
        buttons.removeClass("ui-disabled");
        if (outputDivId) {
            var outputDiv = $("#" + outputDivId);
            outputDiv.parent().removeClass('ui-collapsible-content-collapsed');
            outputDiv.html(context.result);
        }
    };
    if (isAnimation) {
        sendJsonRequest({
            url: url,
            postData: postData,
            requestType: 'json',
            dataHandler: function(context, data) {
                if (data.error) {
                    context.result = data.message;
                } else {
                    context.result = JSON.stringify(data);
                    $.mobile.changePage("#arena", { transition : 'flip', role : 'dialog' });
                    CardFantasy.BattleAnimation.showBattle(data);
                }
            },
            errorHandler: errorHandler,
            completeHandler: completeHandler
        });
    } else {
        sendAjaxRequest({
            url: url,
            postData: postData,
            dataHandler: function(context, data) {
                context.result = data;
            },
            errorHandler: errorHandler,
            completeHandler: completeHandler
        });
    }
};
Core.sendRequest = sendRequest;

var playAutoGame = function(count) {
    var deck1 = $('#deck1').val().trim();
    var deck2 = $('#deck2').val().trim();
    var heroLv1 = $('#hero1Lv').val();
    var heroLv2 = $('#hero2Lv').val();
    var firstAttack = $('#arena-first-attack').val();
    var deckOrder = $('#arena-deck-order').val();
    var isAnimation = false;
    var url = '';
    var postData = {
        'deck1': deck1,
        'deck2': deck2,
        'hlv1': heroLv1,
        'hlv2': heroLv2,
        'fa': firstAttack,
        'do': deckOrder,
        'count': count
    };

    console.log('saving cookie in arena-battle...');
    $.cookie('arena-battle', JSON.stringify(postData), { expires: 365 });

    if (count == 1) {
        url = 'PlayAuto1MatchGame';
    } else if (count == -1) {
        isAnimation = true;
        url = 'SimAuto1MatchGame';
    } else {
        url = 'PlayAutoMassiveGame';
    }
    sendRequest(url, postData, 'battle-output', isAnimation);
};
Core.playAutoGame = playAutoGame;

var playBossGame = function(count) {
    var deck = $('#deck').val().trim();
    var heroLv = $('#heroLv').val();
    var bossName = $('#boss-name').val();
    var guardType = $('#guard-type').val();
    var buffKingdom = $('#buff-kingdom').val();
    var buffForest = $('#buff-forest').val();
    var buffSavage = $('#buff-savage').val();
    var buffHell = $('#buff-hell').val();
    var postData = {
        deck: deck,
        hlv: heroLv,
        bn: bossName,
        bk: buffKingdom,
        bf: buffForest,
        bs: buffSavage,
        bh: buffHell,
        count: count,
        gt: guardType
    };

    $.cookie('boss-battle', JSON.stringify(postData), { expires: 365 });
    var isAnimation = false;
    var url;
    if (count == 1) {
        url = 'PlayBoss1MatchGame';
    } else if (count == -1) {
        url = 'SimulateBoss1MatchGame';
        isAnimation = true;
    } else {
        url = 'PlayBossMassiveGame';
    }
    sendRequest(url, postData, 'boss-battle-output', isAnimation);
};
Core.playBossGame = playBossGame;

var playLilithGame = function(count) {
    var deck = $('#lilith-player-deck').val().trim();
    var heroLv = $('#lilith-player-heroLv').val();
    var lilithName = $('#lilith-name').val();
    var gameType = $('#lilith-game-type').val();
    var targetRemainingGuardCount = $('#lilith-target-remaining-guard-count').val();
    var remainingHP = $('#lilith-remaining-hp').val();
    var postData = {
        deck: deck,
        hlv: heroLv,
        ln: lilithName,
        count: count,
        gt: gameType,
        tc: targetRemainingGuardCount,
        rhp: remainingHP
    };

    $.cookie('lilith-battle', JSON.stringify(postData), { expires: 365 });
    var isAnimation = false;
    var url;
    if (count == 1) {
        url = 'PlayLilith1MatchGame';
    } else if (count == -1) {
        url = 'SimulateLilith1MatchGame';
        isAnimation = true;
    } else {
        url = 'PlayLilithMassiveGame';
    }
    sendRequest(url, postData, 'lilith-battle-output', isAnimation);
};
Core.playLilithGame = playLilithGame;

var playMapGame = function(count) {
    var deck = $('#map-deck').val().trim();
    var heroLv = $('#map-hero-lv').val();
    var map = getMap();
    var postData = {
        deck: deck,
        hlv: heroLv,
        map: map,
        count: count
    };

    $.cookie('map-battle', JSON.stringify(postData), { expires: 365 });
    var isAnimation = false;
    var url;
    if (count == 1) {
        url = 'PlayMap1MatchGame';
    } else if (count == -1) {
        url = 'SimulateMap1MatchGame';
        isAnimation = true;
    } else {
        url = 'PlayMapMassiveGame';
    }
    sendRequest(url, postData, 'map-battle-output', isAnimation);
};
Core.playMapGame = playMapGame;

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

(function () {
    var leftPanelInited = false;
    $(document).on('pageinit', 'div.main-page', function (event) {
        var currentPage = event.target;
        var currentPanelId = currentPage.id + '-left-panel';
        console.log('div.main-page -> #' + currentPanelId + '.pageinit begins');

        if (!leftPanelInited) {
            $('div.main-page').each(function(i, page) {
                $('#left-panel-template ul').append(
                        "<li><a href='#" + page.id + "'>" + $(page).attr('data-title') + "</a></li>");
            });
            leftPanelInited = true;
        }

        var header = $('#header-template').clone();
        header.find("a.nav-button").attr('href', '#' + currentPanelId);
        header.find("h3.header-title").text($(currentPage).attr('data-title'));
        $(currentPage).prepend(header);
        
        var panel = $('#left-panel-template').clone().attr('id', currentPanelId);
        panel.find("a[href='#" + currentPage.id + "']").addClass('ui-disabled');
        $(currentPage).prepend(panel);
        
        // Hide right-nav-button by default. Sub page could show it in its own pageinit event handler.
        header.find('a.right-nav-button').hide();

        $(this).trigger('pagecreate');
        console.log('div.main-page -> #' + currentPanelId + '.pageinit ends');
    });
})();

$(document).ready(function() {
    var tiebaUrl = 'http://tieba.baidu.com/f?kw=%E9%AD%94%E5%8D%A1%E5%B9%BB%E6%83%B3%E6%A8%A1%E6%8B%9F%E5%99%A8';
    $('a[data-type="bug"]').attr('href', tiebaUrl).attr('target', '_blank');
    $('x').each(function (i, x) {
        x.innerHTML = '<a href="http://tieba.baidu.com/home/main?un=' + encodeURI(x.innerHTML) + '&ie=utf-8&fr=pb" target="_blank">' + x.innerHTML + '</a>';
    });
    $('#news').trigger('create');
});

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
        /*
        $.get(
                'http://cnrdn.com/rd.htm?id=1344758&r=ShowVictoryCondition&seed=' + seed,
                function(data) { console.log('ShowVictoryCondition'); });
        */
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
        if (data.bn) { $('#boss-name').val(data.bn).selectmenu('refresh'); }
        if (data.bk) { $('#buff-kingdom').val(data.bk).selectmenu('refresh'); }
        if (data.bf) { $('#buff-forest').val(data.bf).selectmenu('refresh'); }
        if (data.bs) { $('#buff-savage').val(data.bs).selectmenu('refresh'); }
        if (data.bh) { $('#buff-hell').val(data.bh).selectmenu('refresh'); }
    }
    $('#play-boss-1-game-button').attr('href', 'javascript:CardFantasy.Core.playBossGame(1);');
    $('#simulate-boss-1-game-button').attr('href', 'javascript:CardFantasy.Core.playBossGame(-1);');
    $('#play-boss-massive-game-button').attr('href', 'javascript:CardFantasy.Core.playBossGame(1000);');
    /*
    var page = $(event.target);
    page.find('a.right-nav-button .ui-btn-text').text('推荐卡组');
    page.find('a.right-nav-button')
        .attr('href', '#recommend-boss-battle-deck')
        .attr('data-icon', 'info')
        .show().buttonMarkup('refresh');
    */
})
.on("pageinit", "#lilith-battle", function(event) {
    var updateRemainingHp = function() {
        var lilithName = $('#lilith-name').val();
        if (lilithName.indexOf('困难') >= 0) {
            $('#lilith-remaining-hp').val(90000);
        } else if (lilithName.indexOf('噩梦') >= 0) {
            $('#lilith-remaining-hp').val(160000);
        } else if (lilithName.indexOf('炼狱') >= 0) {
            $('#lilith-remaining-hp').val(250000);
        }
    };
    $('#lilith-game-type').change(function() { 
        var gameType = $(this).children('option:selected').val(); 
        if (gameType == 0) {
            $('#lilith-config-0').show();
            $('#lilith-config-1').hide();
        } else {
            $('#lilith-config-1').show();
            $('#lilith-config-0').hide();
        }
    });
    $('#lilith-name').change(updateRemainingHp);
    var dataText = $.cookie('lilith-battle');
    if (dataText) {
        var data = JSON.parse(dataText);
        if (data.deck) { $('#lilith-player-deck').val(data.deck); }
        if (data.hlv) { $('#lilith-player-heroLv').val(data.hlv); }
        if (data.bn) { $('#lilith-name').val(data.bn).selectmenu('refresh'); }
        updateRemainingHp();
    }
    $('#play-lilith-1-game-button').attr('href', 'javascript:CardFantasy.Core.playLilithGame(1);');
    $('#simulate-lilith-1-game-button').attr('href', 'javascript:CardFantasy.Core.playLilithGame(-1);');
    $('#play-lilith-massive-game-button').attr('href', 'javascript:CardFantasy.Core.playLilithGame(1000);');
    $('#lilith-config-1').hide();
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
});

// END OF OUTERMOST IIFE
})(CardFantasy.Core);