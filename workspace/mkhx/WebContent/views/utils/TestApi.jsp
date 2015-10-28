<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/views/header.jsp"%>
<title>Test REST API</title>
<script type="text/javascript">
    $(document).ready(function() {
        $('#send-request-button').click(function() {
            eval('var data = ' + $('#request-body').val() + ';');
            $.ajax({
                url: $('#request-url').val(),
                data: data,
                type: $('#request-method').val(),
                complete: function(response) {
                    $('#response-status').text(response.status);
                    try {
                        $('#response-body').val(formatJson(response.responseText));
                    } catch (e) {
                        $('#response-body').val(response.responseText);
                    }
                }
            });
        });
    });

    var formatJson = function(json, options) {
        var reg = null,
            formatted = '',
            pad = 0,
            PADDING = '    '; // one can also use '\t' or a different number of spaces
     
        // optional settings
        options = options || {};
        // remove newline where '{' or '[' follows ':'
        options.newlineAfterColonIfBeforeBraceOrBracket = (options.newlineAfterColonIfBeforeBraceOrBracket === true) ? true : false;
        // use a space after a colon
        options.spaceAfterColon = (options.spaceAfterColon === false) ? false : true;
     
        // begin formatting...
        if (typeof json !== 'string') {
            // make sure we start with the JSON as a string
            json = JSON.stringify(json);
        } else {
            // is already a string, so parse and re-stringify in order to remove extra whitespace
            json = JSON.parse(json);
            json = JSON.stringify(json);
        }
     
        // add newline before and after curly braces
        reg = /([\{\}])/g;
        json = json.replace(reg, '\r\n$1\r\n');
     
        // add newline before and after square brackets
        reg = /([\[\]])/g;
        json = json.replace(reg, '\r\n$1\r\n');
     
        // add newline after comma
        reg = /(\,)/g;
        json = json.replace(reg, '$1\r\n');
     
        // remove multiple newlines
        reg = /(\r\n\r\n)/g;
        json = json.replace(reg, '\r\n');
     
        // remove newlines before commas
        reg = /\r\n\,/g;
        json = json.replace(reg, ',');
     
        // optional formatting...
        if (!options.newlineAfterColonIfBeforeBraceOrBracket) {         
            reg = /\:\r\n\{/g;
            json = json.replace(reg, ':{');
            reg = /\:\r\n\[/g;
            json = json.replace(reg, ':[');
        }
        if (options.spaceAfterColon) {          
            reg = /\:/g;
            json = json.replace(reg, ': ');
        }
     
        $.each(json.split('\r\n'), function(index, node) {
            var i = 0,
                indent = 0,
                padding = '';
     
            if (node.match(/\{$/) || node.match(/\[$/)) {
                indent = 1;
            } else if (node.match(/\}/) || node.match(/\]/)) {
                if (pad !== 0) {
                    pad -= 1;
                }
            } else {
                indent = 0;
            }
     
            for (i = 0; i < pad; i++) {
                padding += PADDING;
            }
     
            formatted += padding + node + '\r\n';
            pad += indent;
        });
     
        return formatted;
    };
</script>
</head>
<body>
    <div data-role="collapsible" data-collapsed="false" data-content-theme="d" data-mini="true"
        style="PADDING: 10px; WIDTH: 45%; float: left">
        <h3>REQUEST</h3>
        <div>
            <table class="form">
                <tr>
                    <td>URL</td>
                    <td><input id="request-url" type="text" data-mini="true" value="../PlayBossMassiveGame" /></td>
                </tr>
                <tr>
                    <td>METHOD</td>
                    <td><input id="request-method" type="text" data-mini="true" value="POST" /></td>
                </tr>
                <tr>
                    <td>BODY</td>
                    <td>
                        <textarea id="request-body" data-mini="true" style="height: 400px">
{
    'deck': '金属巨龙*10',
    'hlv': 120,
    'bn': '送还复仇女神',
    'bk': 10,
    'bf': 10,
    'bs': 10,
    'bh': 10,
    'count': 1000,
    'gt': 0
}
                        </textarea>
                    </td>
                </tr>
            </table>
            <a id="send-request-button" data-role="button" data-mini="true">SEND</a>
        </div>
    </div>
    <div data-role="collapsible" data-collapsed="false" data-content-theme="d" data-mini="true"
        style="PADDING: 10px; WIDTH: 45%; float: left">
        <h3>RESPONSE</h3>
        <div>
            <table class="form">
                <tr>
                    <td style="vertical-align: middle">STATUS</td>
                    <td style="vertical-align: middle"><div id="response-status"></div></td>
                </tr>
                <tr>
                    <td style="padding-top: 0">BODY</td>
                    <td><textarea id="response-body" style="width: 100%; height: 500px; font-family: Courier New; font-size: 9px"></textarea></td>
                </tr>
            </table>
        </div>
    </div>
</body>
</html>