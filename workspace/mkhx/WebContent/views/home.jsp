<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="header.jsp" %>
<title>魔卡幻想模拟器</title>
</head>
<body class="ui-mobile-viewport">
    <!-- This div is the shared left navigation panel -->
    <div style="display: none">
        <div id="left-panel-template" data-role="panel" data-position="left" data-display="overlay">
            <ul data-role="listview"></ul>
        </div>

        <div id="header-template" class="header-template" data-role="header" data-theme="c" data-position="fixed">
            <a class="nav-button left-nav-button" href="#" data-icon="bars" data-iconpos="left">导航</a>
            <h3 class="header-title"></h3>
            <a class="nav-button right-nav-button" data-iconpos="right">导航</a>
        </div>
    </div>
    
    <%@ include file="news.jsp" %>
    <%@ include file="map-battle.jsp" %>
    <%@ include file="arena-battle.jsp" %>
    <%@ include file="boss-battle.jsp" %>
    <%@ include file="recommend-boss-battle-deck.jsp" %>
    <%@ include file="communication.jsp" %>
    <%@ include file="help.jsp" %>
    <%@ include file="arena.jsp" %>
    <%@ include file="deck-builder.jsp" %>

    <script src="http://s25.cnzz.com/stat.php?id=5496691&web_id=5496691&online=1&show=line"></script>
</body>
</html>