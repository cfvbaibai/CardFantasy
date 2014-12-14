<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <div id="help" class="main-page" data-role="page" data-title="帮助" data-mini="true">
        <div data-role="content">
            <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>友情支持</h3>
                <div id="help">
                    <ul data-theme="c">
                        <li><a href="http://www.joyme.com/wiki/mkhx/index.shtml" target="_blank">魔卡幻想WIKI</a>
                        <li><a href="http://tieba.baidu.com/f?kw=%C4%A7%BF%A8%BB%C3%CF%EB" target="_blank">魔卡幻想贴吧</a></li>
                    </ul>
                </div>
            </div>
            <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>手动输入卡组的方法</h3>
                <div id="help">
                    <ul data-theme="c">
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