<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>
<title>魔卡幻想WIKI</title>
<meta name="description" content="魔卡幻想WIKI,魔卡幻想百科" />
<meta name="keywords" content="魔卡幻想WIKI,魔卡幻想百科" />
</head>
<body class="ui-mobile-viewport">
    <div id="wiki" class="main-page" data-role="page">
        <%@ include file="wiki-header.jsp" %>
        <div data-role="content">
            <div id="wiki-card-pedia" data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>卡牌图鉴</h3>
                <div id="wiki-card-pedia" class="wiki-result">
                    <div class="wiki-categories">
                        <div><a href="Wiki/Cards/Stars/5">五星卡牌</a></div>
                        <div><a href="Wiki/Cards/Stars/4">四星卡牌</a></div>
                        <div><a href="Wiki/Cards/Stars/3">三星卡牌</a></div>
                        <div><a href="Wiki/Cards/Stars/2">二星卡牌</a></div>
                        <div><a href="Wiki/Cards/Stars/1">一星卡牌</a></div>
                        <div></div>
                    </div>
                    <div class="wiki-categories">
                        <div><a href="Wiki/Cards/Races/1">王国卡牌</a></div>
                        <div><a href="Wiki/Cards/Races/2">森林卡牌</a></div>
                        <div><a href="Wiki/Cards/Races/3">蛮荒卡牌</a></div>
                        <div><a href="Wiki/Cards/Races/4">地狱卡牌</a></div>
                        <div><a href="Wiki/Cards/Races/97">魔王卡牌</a></div>
                        <div><a href="Wiki/Cards/Races/100">魔神卡牌</a></div>
                        <div></div>
                    </div>
                </div>
            </div>
            <div id="wiki-rune-pedia" data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>符文图鉴</h3>
                <div id="wiki-rune-pedia" class="wiki-result">
                    <div class="wiki-categories">
                        <div><a href="Wiki/Runes/Stars/5">五星符文</a></div>
                        <div><a href="Wiki/Runes/Stars/4">四星符文</a></div>
                        <div><a href="Wiki/Runes/Stars/3">三星符文</a></div>
                        <div><a href="Wiki/Runes/Stars/2">二星符文</a></div>
                        <div><a href="Wiki/Runes/Stars/1">一星符文</a></div>
                        <div></div>
                    </div>
                    <div class="wiki-categories">
                        <div><a href="Wiki/Runes/Properties/1">地属性符文</a></div>
                        <div><a href="Wiki/Runes/Properties/2">水属性符文</a></div>
                        <div><a href="Wiki/Runes/Properties/3">风属性符文</a></div>
                        <div><a href="Wiki/Runes/Properties/4">火属性符文</a></div>
                        <div></div>
                    </div>
                </div>
            </div>
            <div id="wiki-skill-pedia" data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>技能图鉴</h3>
                <div class="wiki-result">
                    <div class="wiki-categories">
                        <c:forEach var="skillCategory" items="${skillCategories}">
                            <div><a href="Wiki/Skills/Categories/${skillCategory.id}">${skillCategory.name}技能</a></div>
                        </c:forEach>
                        <div></div>
                    </div>
                </div>
            </div>
            <div id="wiki-stage-pedia" data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>地图图鉴</h3>
                <div id="wiki-stage-pedia" class="wiki-result">
                    <div class="wiki-categories">
                        <c:forEach var="stageInfo" items="${stageInfos}">
                            <div><a href="Wiki/Stages/${stageInfo.mapStageId}">${stageInfo.fullName}</a></div>
                        </c:forEach>
                        <div></div>
                    </div>
                </div>
            </div>
            <div id="wiki-search-box" data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>搜索卡牌</h3>
                <div>
                    <fieldset class="select-2" data-theme="c" data-role="controlgroup" data-type="horizontal">
                        <select id="wiki-card-star-filter" data-theme="c" data-mini="true" data-native-menu="false">
                            <option value="0">全部星数</option>
                            <option value="1">1星</option>
                            <option value="2">2星</option>
                            <option value="3">3星</option>
                            <option value="4">4星</option>
                            <option value="5" selected="selected">5星</option>
                        </select>
                        <select id="wiki-card-race-filter" data-theme="c" data-mini="true" data-native-menu="false">
                            <option value="0">全部种族</option>
                            <option value="1" selected="selected">王国</option>
                            <option value="2">森林</option>
                            <option value="3">蛮荒</option>
                            <option value="4">地狱</option>
                            <option value="97">魔王</option>
                            <option value="100">魔神</option>
                        </select>
                    </fieldset>
                    <table class="form">
                        <tr>
                            <td>技能</td>
                            <td>
                                <input id="wiki-card-skill-type-filter" data-theme="c" data-mini="true" type="text"
                                    placeholder="在这里输入技能搜索关键字，例如：复活" />
                            </td>
                        </tr>
                    </table>
                </div>
                <a data-role="button" id="wiki-card-search" data-theme="c" data-mini="true">搜索卡牌</a>
                <div id="wiki-card-result" class="wiki-result" style="padding-top: 5px;">
                </div>
            </div>
        </div>
        <!-- 
        <div class="wiki-footer" data-role="footer" data-position="fixed" data-content-theme="d" data-theme="c">  
            <div data-role="navbar">
                <ul>  
                    <li><a href="#wiki-card-pedia" target="_self">卡牌</a></li>  
                    <li><a href="#wiki-rune-pedia" target="_self">符文</a></li>  
                    <li><a href="#wiki-skill-pedia" target="_self">技能</a></li>
                    <li><a href="#wiki-map-pedia" target="_self">地图</a></li>
                    <li><a href="#wiki-search-box" target="_self">搜索</a></li>  
                </ul>  
            </div>  
        </div>
         -->
    </div>
</body>
</html>