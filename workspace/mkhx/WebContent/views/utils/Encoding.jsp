<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/views/header.jsp"%>
<title>Encoding</title>
<script type="text/javascript">
$(document).ready(function() {
    $('#unescape-button').click(function() {
        var unescaped = eval('"' + $('#escaped').val() + '"');
        $('#unescaped').val(unescaped);
    });
    
    $('#convert-timestamp-button').click(function () {
        var date = new Date($('#timestamp'));
        $('#converted-timestamp').text(date);
    });
});
</script>
<style>
textarea.input {
    font-size: smaller !important; 
}
</style>
</head>
<body>
    <div data-role="collapsible" data-collapsed="false" data-content-theme="d" data-mini="true" style="margin: 20px">
        <h3>Javascript UTF-8 Unescaping</h3>
        <div>
            <textarea class="input" id="escaped"></textarea>
            <a id="unescape-button" data-role="button" data-mini="true">Unescape</a>
            <textarea class="input" id="unescaped"></textarea>
        </div>
    </div>
    <div data-role="collapsible" data-collapsed="false" data-content-theme="d" data-mini="true" style="margin: 20px">
        <h3>Javascript UTF-8 Unescaping</h3>
        <div>
            <input type="text" id="timestamp" />
            <a id="convert-timestamp-button" data-role="button" data-mini="true">Convert</a>
            <div id="converted-timestamp"></div>
        </div>
    </div>
</body>
</html>