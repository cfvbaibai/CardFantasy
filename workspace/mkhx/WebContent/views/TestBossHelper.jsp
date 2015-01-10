<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp"%>
<script src='<c:url value="/resources/js/all-card.js" />'></script>
<script>
var cards = data["data"]["Cards"];
for (var i = 0; i < cards.length; ++i) {
    if (cards[i]["BossHelper"] == "1") {
        //document.write('"' + cards[i].CardName + '",<br />');
        document.write('' + cards[i].CardName + '<br />');
    }
}
</script>