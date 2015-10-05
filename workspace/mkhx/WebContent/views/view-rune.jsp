<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp"%>
<title>${runeName} - 魔卡幻想WIKI</title>
<meta name="description" content="${runeName}" />
<meta name="keywords" content="${runeName}" />
</head>
<body>
    <%@ include file="wiki-header.jsp" %>
    <table class="view-rune-table wiki-table">
        <tbody>
            <tr><td id="view-rune-name" class="title" colspan="4">${runeName}</td></tr>
            <tr>
                <td class="label">属性</td>
                <td id="view-rune-property" class="value">${runeInfo.propertyName}</td>
                <td class="label">星级</td>
                <td id="view-rune-star" class="value">${runeInfo.star}</td>
            </tr>
            <tr>
                <td class="label">出售价格</td>
                <td id="view-rune-price" class="value">${runeInfo.price}</td>
                <td class="label">冥想可得</td>
                <td id="view-rune-think-get" class="value">${runeInfo.thinkGet ? "是" : "否"}</td>
            </tr>
            <tr>
                <td class="label">发动条件</td>
                <td id="view-rune-condition" class="value">${runeInfo.condition}</td>
                <td class="label">碎片兑换</td>
                <td id="view-rune-fragment" class="value">${runeInfo.fragmentDesc}</td>
            </tr>
            <tr>
                <td class="title" colspan="5">符文技能</td>
            </tr>
            <tr>
                <td class="label">LEVEL 0</td>
                <td id="view-rune-skill1" class="skill" colspan="4">
                    <div><a href="../Skills/${runeInfo.skill1.name}">${runeInfo.skill1.name}</a></div>
                    <div>${runeInfo.skill1.description}</div>
                </td>
            </tr>
            <tr>
                <td class="label">LEVEL 1</td>
                <td id="view-rune-skill2" class="skill" colspan="4">
                    <div><a href="../Skills/${runeInfo.skill2.name}">${runeInfo.skill2.name}</a></div>
                    <div>${runeInfo.skill2.description}</div>
                </td>
            </tr>
            <tr>
                <td class="label">LEVEL 2</td>
                <td id="view-rune-skill3" class="skill" colspan="4">
                  <div><a href="../Skills/${runeInfo.skill3.name}">${runeInfo.skill3.name}</a></div>
                    <div>${runeInfo.skill3.description}</div>
                </td>
            </tr>
            <tr>
                <td class="label">LEVEL 3</td>
                <td id="view-rune-skill4" class="skill" colspan="4">
                    <div><a href="../Skills/${runeInfo.skill4.name}">${runeInfo.skill4.name}</a></div>
                    <div>${runeInfo.skill4.description}</div>
                </td>
            </tr>
            <tr>
                <td class="label">LEVEL 4</td>
                <td id="view-rune-skill5" class="skill" colspan="4">
                    <div><a href="../Skills/${runeInfo.skill5.name}">${runeInfo.skill5.name}</a></div>
                    <div>${runeInfo.skill5.description}</div>
                </td>
            </tr>
        </tbody>
    </table>
    <!-- CNZZ Begins -->
    <script src="http://s25.cnzz.com/stat.php?id=5496691&web_id=5496691&online=1"></script>
    <!-- CNZZ Ends -->
</body>
</html>