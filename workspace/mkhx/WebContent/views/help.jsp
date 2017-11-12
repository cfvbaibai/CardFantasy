<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="help" class="main-page" data-role="page" data-title="帮助" data-mini="true" data-theme="${theme}">
    <div data-role="content">
        <div data-role="collapsible" data-collapsed="false" data-mini="true">
            <h3>友情支持</h3>
            <div id="help">
                <ul>
                    <li><a href="http://mkhx.humphreyj.com/" target="_blank">魔卡幻想百科</a></li>
                    <li><a href="<c:url value="/Wiki/index.shtml" />" target="_blank">魔卡幻想WIKI</a>
                    <li><a href="http://tieba.baidu.com/f?kw=%C4%A7%BF%A8%BB%C3%CF%EB" target="_blank">魔卡幻想贴吧</a></li>
                    <li><a data-type="bug">魔卡幻想模拟器贴吧</a></li>
                </ul>
            </div>
        </div>
        <div data-role="collapsible" data-collapsed="false" data-mini="true">
            <h3>输入胜利条件的方法</h3>
            <div id="help">
                <table class="2d" style="width: 100%; max-width: 600px; text-align: left">
                    <tr><td width="40%">胜利条件</td><td width="30%">写法</td><td>举例</td></tr>
                    <tr><td>战斗胜利</td><td>Any</td><td>Any</td></tr>
                    <tr><td>敌方卡牌全灭</td><td>EnemyAllCardsDie</td><td>EnemyAllCardsDie</td></tr>
                    <tr><td>对方英雄死亡</td><td>EnemyHeroDie</td><td>EnemyHeroDie</td></tr>
                    <tr><td>胜利时，己方英雄生命值不低于X%</td><td>MyHeroHP:X</td><td>MyHeroHP:80</td></tr>
                    <tr><td>X回合数内取得胜利</td><td>Round:X</td><td>Round:48</td></tr>
                    <tr><td>己方阵亡卡牌小于X张</td><td>MyDeadCard:X</td><td>MyDeadCard:3</td></tr>
                    <tr><td>卡组中S星卡牌不小于X张</td><td>CardOfStar:S:X</td><td>CardOfStar:5:3</td></tr>
                    <tr><td>卡组中R种族卡牌不小于X张</td><td>CardOfRace:R:X</td><td>CardOfRace:K:3</td></tr>
                    <tr><td colspan="3" style="padding-left: 10px">种族写法: 王国=K, 森林=F, 蛮荒=S, 地狱=H</td></tr>
                    <tr><td>卡组中不包含T属性符文</td><td>NoRune:T</td><td>NoRune:I</td></tr>
                    <tr><td>卡组中包含T属性符文</td><td>HasRune:T</td><td>HasRune:F</td></tr>
                    <tr><td colspan="3" style="padding-left: 10px">属性写法: 任意=A, 冰=I, 风=W, 地=G, 火=F</td></tr>
                </table>
            </div>
        </div>
        <div data-role="collapsible" data-collapsed="false" data-mini="true">
            <h3>手动输入卡组的方法</h3>
            <div id="help">
                <ul>
                    <li>在输入框里输入卡组符文的信息，卡牌与符文之间用逗号隔开，不要留多余的空格，例如： 金属巨龙,降临天使,冰封,永冻</li>
                    <li>默认卡牌等级10级，符文等级4级，想要改变等级的话，在名称后面添加"-数字"。例如10级金属就是"金属巨龙-10"。</li>
                    <li>重复添加同一个卡牌可以使用星号，例如"凤凰*5"表示5张10级凤凰，"凤凰-15*5"表示5张15级凤凰，注意符文不能重复。</li>
                    <li>可以使用加号设置卡牌的洗炼技能，例如"凤凰+转生5-15*5"表示5张15级的转生5的凤凰。</li>
                    <li>洗炼技能也可以设定成降临或者死契，例如"骷髅法师+降临火墙3"表示有了降临火墙的骷髅法师，"独眼巨人+死契摧毁"表示有了死契摧毁的独眼巨人。</li>
                    <li>设置了洗炼技能的卡牌默认15级，所以"凤凰+转生5"等同于"凤凰+转生5-15"。</li>
                </ul>
            </div>
        </div>
    </div>
</div>