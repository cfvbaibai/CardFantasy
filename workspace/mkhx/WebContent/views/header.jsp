<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<% String version = "20150815001"; %>
<!DOCTYPE html>
<html>
<head>
<link href='<c:url value="/resources/img/logo.png" />' rel="bookmark" type="image/x-icon" /> 
<link href='<c:url value="/resources/img/logo.png" />' rel="icon" type="image/x-icon" /> 
<link href='<c:url value="/resources/img/logo.png" />' rel="shortcut icon" type="image/x-icon" /> 
<link rel="stylesheet" href="http://libs.useso.com/js/jqueryui/1.10.3/css/base/jquery-ui.css" />
<link rel="stylesheet" href="http://libs.useso.com/js/jquery-mobile/1.3.1/jquery.mobile.min.css" />
<link rel="stylesheet" href='<c:url value="/resources/css/jquery.pager.css" />?version=<%= version %>' />
<link rel="stylesheet" href='<c:url value="/resources/css/style.css" />?version=<%= version %>' />

<script src="http://libs.useso.com/js/jquery/1.9.1/jquery.min.js"></script>
<script src='<c:url value="/resources/js/jquery.browser.js" />'></script>
<script src='<c:url value="/resources/js/jquery.cookie.js" />'></script>
<script src='<c:url value="/resources/js/jquery.pager.js" />'></script>
<script src='http://cdnjs.cloudflare.com/ajax/libs/zeroclipboard/2.1.6/ZeroClipboard.min.js'></script>
<script src="http://libs.useso.com/js/jqueryui/1.10.3/jquery-ui.js"></script>
<script src="http://libs.useso.com/js/jquery-mobile/1.3.1/jquery.mobile.min.js"></script>
<script src='<c:url value="/resources/js/kinetic-v4.5.4.min.js" />'></script>

<script src='<c:url value="/resources/js/core.js" />?version=<%= version %>'></script>
<script src='<c:url value="/resources/js/battle-animation.js" />?version=<%= version %>'></script>
<script src='<c:url value="/resources/js/deck-builder.js" />?version=<%= version %>'></script>
<script src='<c:url value="/resources/js/communication.js" />?version=<%= version %>'></script>
<script src='<c:url value="/resources/js/test-rp.js" />?version=<%= version %>'></script>
<script src='<c:url value="/resources/js/wiki.js" />?version=<%= version %>'></script>

<script>var resDir = '<c:url value="/resources" />'; var rootDir = '<c:url value="/" />'; var seed = new Date().getTime();</script>
<meta name="description" content="魔卡幻想战斗模拟器">
<meta name="keywords" content="魔卡幻想,模拟器,四国战机,魔法卡牌,竞技场,魔神战,莉莉丝,Lies of Astaroth">
<!-- 
Note above that there is a meta viewport tag in the head to specify how the browser should display the page zoom level and dimensions.
If this isn't set, many mobile browsers will use a "virtual" page width around 900 pixels to make it work well with existing desktop
sites but the screens may look zoomed out and too wide. By setting the viewport attributes to 
content="width=device-width, initial-scale=1", the width will be set to the pixel width of the device screen.
 -->
<meta name="viewport" content="width=device-width, initial-scale=1">