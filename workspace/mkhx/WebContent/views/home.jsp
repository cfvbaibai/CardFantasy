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
<!--
    <div>
        <script type="text/javascript">BAIDU_CLB_fillSlot("1136893");</script>
    </div>
-->
    <%@ include file="news.jsp" %>
    <%@ include file="map-battle.jsp" %>
    <%@ include file="arena-battle.jsp" %>
    <%@ include file="boss-battle.jsp" %>
    <%@ include file="lilith-battle.jsp" %>
    <%@ include file="view-video.jsp" %>
    <%@ include file="test-rp.jsp" %>
    <%@ include file="wiki.jsp" %>
    <%@ include file="help.jsp" %>
    <%@ include file="open-source.jsp" %>

    <%@ include file="arena.jsp" %>
    <%@ include file="deck-builder.jsp" %>
    <%@ include file="arena-battle-options.jsp" %>

    <!-- CNZZ Begins -->
    <script src="http://s25.cnzz.com/stat.php?id=5496691&web_id=5496691&online=1&show=line"></script>
    <!-- CNZZ Ends -->
    <!-- JiaThis Button BEGIN -->
    <!-- 
    <script type="text/javascript" >
    var jiathis_config={
        url:"http://www.mkhx.cc:8080",
        summary:"有了魔卡幻想模拟器，轻松打魔神，切基友！",
        title:"魔卡幻想模拟器 ##魔卡幻想##",
        showClose:true,
        shortUrl:true,
        hideMore:false
    }
    </script>
    <script type="text/javascript" src="http://v3.jiathis.com/code/jiathis_r.js?btn=r.gif&move=0" charset="utf-8"></script>
     -->
    <!-- JiaThis Button END -->
</body>
</html>