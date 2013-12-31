CardFantasy.Communication = {};

(function(Me) {

$(document).on("pageinit", "#communication", function(event) {
    console.log('#communication.pageinit');
    $('a.right-nav-button .ui-btn-text').text('发帖');
    $('a.right-nav-button').click(function() {
        $("#new-thread-table")[0].scrollIntoView();
        $("#feedback").focus();
    }).attr('href', null);
    $('#feedback-button').attr('href', 'javascript:CardFantasy.Communication.sendFeedback();');
    $('#feedback-sender').val(loadUserId());
    loadThreads(1);
});

var saveUserId = function(userId) {
    $.cookie('communication.user-id', userId, { expires: 365 });
};

var loadUserId = function() {
    var userId = $.cookie('communication.user-id');
    if (userId == '魔卡爱好者') { userId = null; }
    return userId;
};

var openReply = function(replyButton) {
    var replyDiv = $(replyButton).parent().parent().parent().find('div.thread-reply');
    var replyDivShown = $(replyButton).data('reply-div-shown');
    if (replyDivShown) {
        $(replyButton).text('回复');
        replyDiv.hide('fast');
        $(replyButton).data('reply-div-shown', false);
    } else {
        replyDiv.find('input.thread-replier').val(loadUserId());
        $(replyButton).text('收起回复');
        replyDiv.show('fast');
        $(replyButton).data('reply-div-shown', true);
    }
};
Me.openReply = openReply;

var sendFeedback = function() {
    var sender = $('#feedback-sender').val().trim();
    if (!sender) {
        alert('请输入您的大名');
        $('#feedback-sender').focus();
        return;
    }
    saveUserId(sender);
    var feedback = $('#feedback').val().trim();
    if (!feedback) {
        alert('请输入内容');
        $('#feedback').focus();
        return;
    }
    var postData = { feedback: feedback, sender: sender };
    $.mobile.loading('show');
    $.post('SendFeedback', postData, function(data) {
        if (data.error) { return; }
        alert('发送成功！');
        loadThreads(1);
    })
    .fail(function(xhr, status, error) {
        alert('回复失败！');
    })
    .complete(function () {
        loadThreads(1);
        $.mobile.loading('hide');
        window.scrollTo(0, 0);
    });
};
Me.sendFeedback = sendFeedback;

var sendReply = function(threadId) {
    var threadReplyDiv = $('div#threads div[thread-id=' + threadId + ']');
    var replyContentControl = threadReplyDiv.find('textarea.thread-reply-content');
    var replier = threadReplyDiv.find('input.thread-replier').val().trim();
    if (!replier) {
        alert('请输入您的大名');
        $('input.thread-replier').focus();
        return;
    }
    var replyContent = replyContentControl.val().trim();
    if (!replyContent) {
        alert('请输入内容');
        replyContentControl.focus();
        return;
    }
    saveUserId(replier);
    var postData = {
            replyTo: replyContentControl.data('thread-id'),
            replier: replier,
            content: replyContent
    };
    $.mobile.loading('show');
    $.post('SendReply', postData, function(data) {
        if (data.error) { return; }
        alert('回复成功！');
        loadThreads();
    })
    .fail(function(xhr, status, error) {
        alert('回复失败！' + error);
    })
    .complete(function () {
        $.mobile.loading('hide');
    });
};
// Me.sendReply = sendReply;

var loadThreads = function(pageNum) {
    if (pageNum) {
        this.currentThreadPageNum = pageNum;
    }

    var currentThreadPageNum = this.currentThreadPageNum;
    $.mobile.loading('show');
    $.get('GetThreads?pageNum=' + currentThreadPageNum, function(data) {
        if (data.error) { return; }
        console.log(JSON.stringify(data));

        $("#pager").pager({
            pagenumber: currentThreadPageNum,
            pagecount: data.pageCount,
            buttonClickCallback: function(clickedPageNum) { loadThreads(clickedPageNum); }
        });

        var threadsDiv = $('#threads').html('');
        $.each(data.threads, function(iThread, thread) {
            var post = thread.post;
            console.log(post.id + '[' + iThread + ']: ' + post.content);
            var threadTemplate = $('div.threads-template div.thread-template').clone().appendTo(threadsDiv);
            threadTemplate.removeClass('thread-template');
            threadTemplate.attr('thread-id', post.id);
            threadTemplate.find('div.thread-sender-id').text(post.senderId + ' 说：');
            threadTemplate.find('div.thread-content').text(post.content);
            threadTemplate.find('div.thread-created').text(post.created);
            threadTemplate.find('textarea.thread-reply-content').data('thread-id', post.id);
            threadTemplate.find('a.thread-send-reply-button').click(function(e) { sendReply(post.id); });
            
            var repliesDiv = threadTemplate.find('div.thread-replies');
            if (thread.replies.length == 0) {
                repliesDiv.remove();
            } else {
                $.each(thread.replies, function(iReply, reply) {
                    var replyTemplate = $('div.reply-template').clone().appendTo(repliesDiv);
                    replyTemplate.removeClass('reply-template');
                    replyTemplate.find('div.reply-sender-id').text(reply.senderId + ' 回复：');
                    replyTemplate.find('div.reply-content').text(reply.content);
                    replyTemplate.find('div.reply-created').text(reply.created);
                });
            }
        });
    }, 'json')
    .fail(function(xhr, status, error) {
        // TODO:
    })
    .complete(function () {
        $.mobile.loading('hide');
    });
};
Me.loadThreads = loadThreads;

})(CardFantasy.Communication);