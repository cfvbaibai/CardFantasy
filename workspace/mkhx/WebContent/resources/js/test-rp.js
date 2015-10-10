var Core = CardFantasy.Core;
CardFantasy.TestRP = {};

(function(Me) {

$(document).on("pageinit", "#test-rp", function(event) {
    $(event.target).find('a.test-rp-button').attr('href', 'javascript:CardFantasy.TestRP.testRP();');
    
    var testRPResultText = $.cookie('test-rp-result');
    if (!testRPResultText) {
        showToTestRPUI();
        return;
    }
    console.log('testRPResult = ' + testRPResultText);
    var testRPResult = JSON.parse(testRPResultText);
    var testDateText = testRPResult.testDate;
    var testDate = null;
    if (testDateText) {
        var parts = testDateText.split('-');
        testDate = new Date();
        testDate.setFullYear(parts[0]);
        testDate.setMonth(parts[1] - 1);
        testDate.setDate(parts[2]);
    }
    var today = new Date();
    console.log('testDate = ' + testDate + '; today = ' + today);
    //alert('testDate = ' + testDate);
    //alert('today = ' + today);
    var todayTested =
        testDate.getDate() == today.getDate() &&
        testDate.getMonth() == today.getMonth() &&
        testDate.getFullYear() == today.getFullYear();
    var forceRefresh = window.location.href.indexOf('forceRefresh') >= 0;
    console.log('forceRefresh = ' + forceRefresh);
    if (todayTested && !forceRefresh) {
        showTestRPResult(testRPResult);
    } else {
        showToTestRPUI();
    }
});

var testRP = function() {
    Core.sendJsonRequest({
        url: 'TestRP',
        postData: {},
        dataHandler: function(context, data) {
            var dataText = JSON.stringify(data);
            console.debug(dataText);
            if (data.error) {
                context.result = data.message;
            } else {
                $.cookie('test-rp-result', dataText);
                showTestRPResult();
            }
        },
        errorHandler: function(context, xhr, status, detail) {
            $('#test-rp').find('div.remark').text('人品测试机似乎碰到了一些问题：' + status + ', ' + detail + '。请稍后再试。');
        }
    });
};
Me.testRP = testRP;
    
var showToTestRPUI = function() {
    var page = $('#test-rp');
    page.find('div.to-test-rp').show();
    page.find('div.test-rp-result').hide();
};
    
var showTestRPResult = function(testRpResult) {
    var page = $('#test-rp');
    var dataText = $.cookie('test-rp-result');
    if (!dataText) { return; }
    var data = JSON.parse(dataText);
    if (!data) { return; }
    page.find('div.to-test-rp').hide();
    page.find('div.test-date').text('测试日期: ' + data.testDate + '(每天只能测一次哦)');
    page.find('div.portrait img').attr('src', resDir + '/img/cardportrait/' + data.cardId + '.jpg');
    page.find('div.test-rp-card-name').text('人品实力代表: ' + data.cardName);
    page.find('div.rp-index').text('人品指数(最高100): ' + data.rpIndex);
    page.find('div.remark').text(data.remark);
    page.find('div.test-rp-result').show();
};
})(CardFantasy.TestRP);