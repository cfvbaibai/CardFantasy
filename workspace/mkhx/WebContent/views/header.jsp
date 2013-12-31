<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
<link href='<c:url value="/resources/img/logo.png" />' rel="bookmark" type="image/x-icon" /> 
<link href='<c:url value="/resources/img/logo.png" />' rel="icon" type="image/x-icon" /> 
<link href='<c:url value="/resources/img/logo.png" />' rel="shortcut icon" type="image/x-icon" /> 
<link rel="stylesheet" href="http://code.jquery.com/ui/1.10.3/themes/smoothness/jquery-ui.css" />
<link rel="stylesheet" href="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.css" />
<link rel="stylesheet" href='<c:url value="/resources/css/jquery.pager.css" />?version=<%= java.util.Calendar.getInstance().getTimeInMillis() %>' />
<link rel="stylesheet" href='<c:url value="/resources/css/style.css" />?version=<%= java.util.Calendar.getInstance().getTimeInMillis() %>' />

<script src="http://code.jquery.com/jquery-1.9.1.min.js"></script>
<script src='<c:url value="/resources/js/jquery.browser.js" />'></script>
<script src='<c:url value="/resources/js/jquery.cookie.js" />'></script>
<script src='<c:url value="/resources/js/jquery.pager.js" />'></script>
<script src="http://code.jquery.com/ui/1.10.3/jquery-ui.js"></script>
<script src="http://code.jquery.com/mobile/1.3.1/jquery.mobile-1.3.1.min.js"></script>
<script src="http://d3lp1msu2r81bx.cloudfront.net/kjs/js/lib/kinetic-v4.5.4.min.js"></script>

<script src='<c:url value="/resources/js/core.js" />?version=<%= java.util.Calendar.getInstance().getTimeInMillis() %>'></script>
<script src='<c:url value="/resources/js/battle-animation.js" />?version=<%= java.util.Calendar.getInstance().getTimeInMillis() %>'></script>
<script src='<c:url value="/resources/js/deck-builder.js" />?version=<%= java.util.Calendar.getInstance().getTimeInMillis() %>'></script>
<script src='<c:url value="/resources/js/communication.js" />?version=<%= java.util.Calendar.getInstance().getTimeInMillis() %>'></script>
<script>var resDir = '<c:url value="/resources" />'; var seed = new Date().getTime();</script>
<meta name="description" content="魔卡幻想战斗模拟器">
<meta name="keywords" content="魔卡幻想,模拟器,四国战机,魔法卡牌,竞技场">
<!-- 
Note above that there is a meta viewport tag in the head to specify how the browser should display the page zoom level and dimensions.
If this isn't set, many mobile browsers will use a "virtual" page width around 900 pixels to make it work well with existing desktop
sites but the screens may look zoomed out and too wide. By setting the viewport attributes to 
content="width=device-width, initial-scale=1", the width will be set to the pixel width of the device screen.
 -->
<meta name="viewport" content="width=device-width, initial-scale=1">