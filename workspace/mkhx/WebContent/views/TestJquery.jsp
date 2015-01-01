<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp"%>
<title>Test Canvas</title>
<script type="text/javascript">
$(document).ready(function() {
    alert(12);
    var n = $('.Hey').each(function(i, a) {
        a.href = a.innerText;
    });
    /*
    for (var i = 0; i < n.length; ++i) {
        n[i].attr('href', n[i].innerText);
    }
    */
});
</script>
</head>
<body>
    <a class="Hey" href="#">AAA</a>
    <a class="Hey" href="#">BBB</a>
</body>
</html>