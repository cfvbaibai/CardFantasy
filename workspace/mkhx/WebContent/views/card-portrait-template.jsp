<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="cf" uri="/WEB-INF/CardFantasyTags.tld" %>
<div style="position: relative; display: inline-block; width: 300px; margin: 10px">
    <div style="position: absolute; left: 0; top: 0; width: 100%">
        <img class="cpt-large-portrait-img" src="${cardInfo.largePortraitUrl}" style="width: 100%" />
    </div>
    <div style="position: absolute; left: 0; top: 0; width: 100%">
        <img class="cpt-border-img" src="" style="width: 100%" />
    </div>
    <div style="position: absolute; left: 0; top: 0; width: 100%">
        <img class="cpt-star-img" src="" style="width: 100%" />
    </div>
    <div style="position: absolute; left: 85px; top: 0; color: white; font-size: 22px; text-shadow: 1px 0 1px #000000, 1px 1px 1px #000000, -1px -1px 1px #000000, 0 1px 1px #000000, -1px 1px 1px #000000, -1px 0 1px #000000, 0 -1px 1px #000000, 0 0 1px #000000; font-family:微软雅黑">
        <span class="cpt-card-name"></span>
    </div>
    <div style="position: absolute; left: 225px; top: 23px; color: #ede739; font-style: italic; width: 70px; font-size: 26px; font-family: Arial">
        <span class="cpt-cost"></span> 
    </div>
    <div style="position: absolute; left: 32px; top: 320px; width: 32px">
        <img src="<c:url value="/resources/img/frame/blueblock.png" />" />
    </div>
    <div style="position: absolute; left: 8px; top: 320px; width: 42px">
        <img src="<c:url value="/resources/img/frame/timeglass.png" />" />
    </div>
    <div style="position: absolute; left: 45px; top: 330px; color: white; font-style: italic; font-size: 26px; font-family :Arial; text-shadow: 1px 0 1px #000000, 1px 1px 1px #000000, -1px -1px 1px #000000, 0 1px 1px #000000, -1px 1px 1px #000000, -1px 0 1px #000000, 0 -1px 1px #000000, 0 0 1px #000000">
        <span class="cpt-wait"></span>
    </div>
    <div style="position: absolute; left: 25px; top: 390px; color: white; font-style: italic; font-size: 26px; font-family: Arial"> 
        10
    </div>
    <div style="position: absolute; left: 120px; top: 333px">
        <img src="<c:url value="/resources/img/frame/ATK.png" />" style="width: 55px" />
    </div>
    <div class="cpt-ability-text" style="left: 185px; top: 333px">
        <span class="cpt-atk"></span>
    </div>
    <div style="position: absolute; left: 75px; top: 380px">
        <img src="<c:url value="/resources/img/frame/HP.png" />" style="width: 55px" />
    </div>
    <div class="cpt-ability-text" style="left: 125px; top: 380px">
        <span class="cpt-hp"></span>
    </div>
    <!-- FIFTH LINE -->
    <div class="cpt-skill-line-0 cpt-skill-line">
        <div style="position: absolute; right: 15px; top: 295px">
            <img src="<c:url value="/resources/img/frame/blackshadow.png" />" style="width: 130px" />
        </div>
        <div class="cpt-skill-name-0 cpt-skill-name" style="right: 52px; top: 298px">
        </div>
        <div style="position: absolute; right: 15px; top: 295px">
            <img class="cpt-skill-category-0" src="" style="width: 30px" />
        </div>
    </div>
    <div class="cpt-skill-line-1 cpt-skill-line">
        <div style="position: absolute; right: 15px; top: 266px">
            <img src="<c:url value="/resources/img/frame/blackshadow.png" />" style="width: 130px" />
        </div>
        <div class="cpt-skill-name-1 cpt-skill-name" style="right: 52px; top: 269px">
        </div>
        <div style="position: absolute; right: 15px; top: 266px">
            <img class="cpt-skill-category-1" src="" style="width: 30px" />
        </div>
    </div>
    <div class="cpt-skill-line-2 cpt-skill-line">
        <div style="position: absolute; right: 15px; top: 237px">
            <img src="<c:url value="/resources/img/frame/blackshadow.png" />" style="width: 130px" />
        </div>
        <div class="cpt-skill-name-2 cpt-skill-name" style="right: 52px; top: 240px">
        </div>
        <div style="position: absolute; right: 15px; top: 237px">
            <img class="cpt-skill-category-2" src="" style="width: 30px" />
        </div>
    </div>
    <div class="cpt-skill-line-3 cpt-skill-line">
        <div style="position: absolute; right: 15px; top: 208px">
            <img src="<c:url value="/resources/img/frame/blackshadow.png" />" style="width: 130px" />
        </div>
        <div class="cpt-skill-name-3 cpt-skill-name" style="right: 52px; top: 211px">
        </div>
        <div style="position: absolute; right: 15px; top: 208px">
            <img class="cpt-skill-category-3" src="" style="width: 30px" />
        </div>
    </div>
    <div class="cpt-skill-line-4 cpt-skill-line">
        <div style="position: absolute; right: 15px; top: 178px">
            <img src="<c:url value="/resources/img/frame/blackshadow.png" />" style="width: 130px" />
        </div>
        <div class="cpt-skill-name-4 cpt-skill-name" style="right: 52px; top: 182px">
        </div>
        <div style="position: absolute; right: 15px; top: 178px">
            <img class="cpt-skill-category-4" src="" style="width: 30px" />
        </div>
    </div>
</div>