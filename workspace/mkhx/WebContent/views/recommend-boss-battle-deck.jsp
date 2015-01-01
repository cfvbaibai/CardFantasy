<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <div id="recommend-boss-battle-deck" class="main-page" data-role="page" data-title="魔神卡组推荐" data-mini="true">
        <div id="recommend-boss-battle-deck-content" data-role="content">
            <table class="form">
                <tr><td>魔神</td><td>
                <select data-theme="c" name="boss-name" data-mini="true" data-native-menu="false">
                    <option value="复仇女神">新魔神：复仇女神</option>
                    <option value="邪龙之神">新魔神：邪龙之神</option>
                    <option value="噩梦之主">新魔神：噩梦之主</option>
                    <option value="毁灭之神">新魔神：毁灭之神</option>
                    <option value="深渊影魔">新魔神：深渊影魔</option>
                    <option value="万蛛之后">新魔神：万蛛之后</option>
                    <option value="旧复仇女神">旧魔神：复仇女神</option>
                    <option value="旧邪龙之神">旧魔神：邪龙之神</option>
                    <option value="旧噩梦之主">旧魔神：噩梦之主</option>
                    <option value="旧毁灭之神">旧魔神：毁灭之神</option>
                    <option value="旧深渊影魔">旧魔神：深渊影魔</option>
                    <option value="旧万蛛之后">旧魔神：万蛛之后</option>
                </select>
                </td></tr>
                <tr><td>最高英雄等级</td><td>
                    <input data-theme="c" type="number" name="max-hero-lv" data-mini="true" value="75" />
                </td></tr>
            </table>
            <div class="ui-grid-a" id="deck-builder-control-panel" data-mini="true">
                <div class="ui-block-a">
                    <a id="recommend-boss-battle-deck-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">帮我推荐</a>
                </div>
                <div class="ui-block-b">
                    <a data-role="button" data-mini="true" data-theme="c" data-type="bug" href="#">提BUG</a>
                </div>
            </div>
            <div class="result-frame" data-role="collapsible-set" data-theme="c" data-content-theme="d">
                <div data-role="collapsible" data-mini="true" data-collapsed="false">
                    <h3>最高[平均伤害]卡组</h3>
                    <div class="result-inner-frame best-avg-damage-deck"></div>
                </div>
                <div data-role="collapsible" data-mini="true" data-collapsed="false">
                    <h3>最高[最大伤害]卡组</h3>
                    <div class="result-inner-frame best-max-damage-deck"></div>
                </div>
            </div>
        </div>
    </div>
