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
    $.get(
            'http://cnrdn.com/rd.htm?id=1344758&r=' + url + '&seed=' + seed,
            function(data) { console.log('Visit to ' + url + ' uploaded to CNZZ.'); }
    );
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

var sendRequest = function(url, postData, outputDivId, isAnimation, completeHandler, errorHandler) {
    var buttons = $('a.battle-button');
    buttons.addClass("ui-disabled");
    errorHandler = errorHandler || function(context, xhr, status, error) {
        context.result = "<span style='COLOR: red'>发生错误! 状态=" + status + ", 细节=" + error + "</span>";
        if (status == 'error' && error == 'Not Found') {
            context.result += "<div>服务器可能正在更新，请稍等1分钟左右再试试看</div>";
        }
    };
    completeHandler = completeHandler || function(context) {
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
    var isAnimation = false;
    var url = '';
    var postData = {
        'deck1': deck1,
        'deck2': deck2,
        'hlv1': heroLv1,
        'hlv2': heroLv2,
        'fa': arenaBattleOptions.firstAttack,
        'do': arenaBattleOptions.deckOrder,
        'p1hhpb': arenaBattleOptions.p1HeroHpBuff,
        'p1catb': arenaBattleOptions.p1CardAtBuff,
        'p1chpb': arenaBattleOptions.p1CardHpBuff,
        'p2hhpb': arenaBattleOptions.p2HeroHpBuff,
        'p2catb': arenaBattleOptions.p2CardAtBuff,
        'p2chpb': arenaBattleOptions.p2CardHpBuff,
        'vc1': arenaBattleOptions.victoryCondition1,
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
    var completeHandler = null;
    if (count == 1) {
        url = 'PlayBoss1MatchGame';
        completeHandler = function(context) {
            $('#boss-battle-output').html(context.result);
        };
        $('#boss-battle-massive-output').hide();
        $('#boss-battle-output').show();
    } else if (count == -1) {
        url = 'SimulateBoss1MatchGame';
        isAnimation = true;
    } else {
        url = 'PlayBossMassiveGame';
        completeHandler = function(context) {
            var result = context.result;
            $('#boss-battle-game-count').text(result.gameCount);
            $('#boss-battle-timeout-count').text(result.timeoutCount);
            $('#boss-battle-cooldown').text(result.coolDown + '秒');
            $('#boss-battle-deck-cost').text(result.totalCost);
            $('#boss-battle-min-damage').text(result.minDamage);
            $('#boss-battle-max-damage').text(result.maxDamage);
            $('#boss-battle-avg-damage').text(Math.round(result.avgDamage));
            $('#boss-battle-avg-damage-per-minute').text(Math.round(result.avgDamagePerMinute));
            $('#boss-battle-cv-damage').text(Math.round(result.cvDamage * 100) + '%');
            $('#boss-battle-deck-validation-result').html(result.validationResult);
            var chartData = { labels: [], datasets: [{
                label: "伤害",
                fillColor: "rgba(151,187,205,0.5)",
                strokeColor: "rgba(151,187,205,0.8)",
                highlightFill: "rgba(151,187,205,0.75)",
                highlightStroke: "rgba(151,187,205,1)",
                data: []
            }]};
            for (var i = 0; i < result.dataItems.length; ++i) {
                chartData.labels.push(result.dataItems[i].label);
                chartData.datasets[0].data.push(result.dataItems[i].count);
            }
            $('#boss-battle-massive-output').show();
            $('#boss-battle-output').hide();
            Core.bossBattleChart.Bar(chartData);
        };
    }
    sendRequest(url, postData, 'boss-battle-output', isAnimation, completeHandler);
};
Core.playBossGame = playBossGame;

var playLilithGame = function(count) {
    var deck = $('#lilith-player-deck').val().trim();
    var heroLv = $('#lilith-player-heroLv').val();
    var lilithName = $('#lilith-name').val();
    var gameType = $('#lilith-game-type').val();
    var targetRemainingGuardCount = $('#lilith-target-remaining-guard-count').val();
    var remainingHP = $('#lilith-remaining-hp').val();
    var eventCards = $('#lilith-event-cards').val();
    var postData = {
        deck: deck,
        hlv: heroLv,
        ln: lilithName,
        count: count,
        gt: gameType,
        tc: targetRemainingGuardCount,
        rhp: remainingHP,
        ec: eventCards,
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

var BattleOptions = function() {
    this.firstAttack = -1;
    this.deckOrder = 0;
    this.p1HeroHpBuff = 100;
    this.p1CardAtBuff = 100;
    this.p1CardHpBuff = 100;
    this.p2HeroHpBuff = 100;
    this.p2CardAtBuff = 100;
    this.p2CardHpBuff = 100;
    this.victoryCondition1 = 'Any';

    this.toString = function() {
        var result = '';
        if (this.firstAttack == -1) {
            result += '按规则决定先攻';
        } else if (this.firstAttack == 0) {
            result += '玩家1先攻';
        } else {
            result += '玩家2先攻';
        }
        result += '; ';
        if (this.deckOrder == 0) {
            result += '随机出卡';
        } else {
            result += '按指定顺序出卡';
        }
        result += '; HHP1: ' + this.p1HeroHpBuff + '%';
        result += '; CAT1: ' + this.p1CardAtBuff + '%';
        result += '; CHP1: ' + this.p1CardHpBuff + '%';
        result += '; HHP2: ' + this.p2HeroHpBuff + '%';
        result += '; CAT2: ' + this.p2CardAtBuff + '%';
        result += '; CHP2: ' + this.p2CardHpBuff + '%';
        result += '; 玩家1胜利条件: ' + this.victoryCondition1;
        return result;
    }
};

var arenaBattleOptions = new BattleOptions();
Core.arenaBattleOptions = arenaBattleOptions;

var setBattleOptions = function(options, optionsDivId) {
    $.mobile.changePage("#battle-options", { transition : 'flip', role : 'dialog' });
    updateBattleOptions.currentOptions = options;
    updateBattleOptions.currentOptionsDivId = optionsDivId;
    $('#first-attack').val(options.firstAttack).selectmenu('refresh');
    $('#deck-order').val(options.deckOrder).selectmenu('refresh');
    $('#p1-hero-hp-buff').val(options.p1HeroHpBuff);
    $('#p1-card-at-buff').val(options.p1CardAtBuff);
    $('#p1-card-hp-buff').val(options.p1CardHpBuff);
    $('#p2-hero-hp-buff').val(options.p2HeroHpBuff);
    $('#p2-card-at-buff').val(options.p2CardAtBuff);
    $('#p2-card-hp-buff').val(options.p2CardHpBuff);
    $('#victory-condition-1').val(options.victoryCondition1);
};
Core.setBattleOptions = setBattleOptions;

var updateBattleOptions = function() {
    var options = updateBattleOptions.currentOptions;
    options.firstAttack = $('#first-attack').val();
    options.deckOrder = $('#deck-order').val();
    options.p1HeroHpBuff = $('#p1-hero-hp-buff').val();
    options.p1CardAtBuff = $('#p1-card-at-buff').val();
    options.p1CardHpBuff = $('#p1-card-hp-buff').val();
    options.p2HeroHpBuff = $('#p2-hero-hp-buff').val();
    options.p2CardAtBuff = $('#p2-card-at-buff').val();
    options.p2CardHpBuff = $('#p2-card-hp-buff').val();
    options.victoryCondition1 = $('#victory-condition-1').val();
    setBattleOptionsText(options, updateBattleOptions.currentOptionsDivId);
    history.go(-1);
};
Core.updateBattleOptions = updateBattleOptions;

var setBattleOptionsText = function(options, divId) {
    $('#' + divId).text(options.toString());
};
Core.setBattleOptionsText = setBattleOptionsText;

var getMap = function() {
    return $('#map-id').val() + '-' + $('#map-difficulty').val();
};

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

Core.showMapDeck = function() {
    var map = getMap();
    $.get('GetMapDeckInfo?map=' + map, function(data) {
        console.log("Map deck info for '" + map + "': " + JSON.stringify(data));
        $("#map-deck-info").text(data);
        $.mobile.changePage("#view-map-deck-page", { transition : 'flip', role : 'dialog' });
    }, 'json').fail(function(xhr, status, error) {
        $("#map-deck-info").text("无法获得地图关卡阵容: " + status + ", " + error);
        $.mobile.changePage("#view-map-deck-page", { transition : 'flip', role : 'dialog' });
    });
};

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
        $.get('GetMapVictoryCondition?map=' + map, function(data) {
            console.log("Map victory condition for '" + map + "': " + JSON.stringify(data));
            $("#map-victory-condition").text(data);
        }, 'json').fail(function(xhr, status, error) {
            $("#map-victory-condition").text("无法获得地图过关条件: " + status + ", " + error);
        });
    };

    $('#view-map-deck-link').attr('href', "javascript:CardFantasy.Core.showMapDeck();");

    $(document).on('change', 'select.map-select', function(e) {
        // Get current option value: this.options[e.target.selectedIndex].text
        showVictoryCondition();
    });
    showVictoryCondition();
})
.on("pageinit", "#boss-battle", function(event) {
    Core.bossBattleChart = new Chart($("#boss-battle-chart")[0].getContext("2d"));
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
    $('#show-arena-battle-options-button').attr('href', 'javascript:CardFantasy.Core.setBattleOptions(CardFantasy.Core.arenaBattleOptions, "arena-battle-options-text");');
    $('#update-battle-options-button').attr('href', 'javascript:CardFantasy.Core.updateBattleOptions();');
    setBattleOptionsText(arenaBattleOptions, 'arena-battle-options-text');
});

// END OF OUTERMOST IIFE
})(CardFantasy.Core);