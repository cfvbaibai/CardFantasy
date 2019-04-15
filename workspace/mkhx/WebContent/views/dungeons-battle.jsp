<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <div id="dungeons-battle" class="main-page" data-role="page" data-title="地下城" data-mini="true" data-theme="${theme}">
        <div data-role="content">
            <div data-role="collapsible" data-collapsed="false" data-mini="true">
                <h3>设置阵容</h3>
                <div>
                    <a id="show-dungeons-battle-options-button" data-role="button" data-rel="dialog" data-mini="true">点击设置战斗规则</a>
                    <table class="form">
                        <tr>
                            <td>地下城关卡</td>
                            <td>
                                <select name="dungeons-id" id="dungeons-id" class="dungeons-select" data-mini="true" data-native-menu="true">
                                    <optgroup label="地下城">
                                        <option value="d-11">十二星座关90</option>
                                        <option value="d-12">免疫神风关90</option>
                                        <option value="d-13">传送圣枪关90</option>
                                        <option value="d-14">传送叹惋关90</option>
                                        <option value="d-15">转生龙灵关90</option>
                                        <option value="d-16">免疫森主关90</option>
                                        <option value="d-1">涅槃魅魔关</option>
                                        <option value="d-2">洪荒青龙关</option>
                                        <option value="d-3">不动朱雀关</option>
                                        <option value="d-4">三国英魂关</option>
                                        <option value="d-5">遗回银喵关</option>
                                        <option value="d-6">镜面仲颖关</option>
                                        <option value="d-7">制衡炼金关</option>
                                        <option value="d-8">狼顾魂乐关</option>
                                        <option value="d-9">传送兽王关</option>
                                        <option value="d-10">鬼才镜姬关</option>
                                        <option value="d-17">原素家族关111</option>
                                        <option value="d-18">晴空风樱关111</option>
                                        <option value="d-19">绯炎厨娘关111</option>
                                        <option value="d-20">斩羽机车关111</option>
                                        <option value="d-21">钻石炼金关111</option>
                                        <option value="d-22">公嗣君主关111</option>
                                        <option value="d-23">伯符君主111</option>
                                        <option value="d-24">子桓君主关111</option>
                                        <option value="d-25">单四神关111</option>
                                        <option value="d-26">双四神关111</option>
                                    </optgroup>
                                    <optgroup label="噩梦1图">
                                        <option value="1-1">1-1 森林入口</option>
                                        <option value="1-2">1-2 森林小径</option>
                                        <option value="1-3">1-3 守林人小屋</option>
                                        <option value="1-4">1-4 小镜湖</option>
                                        <option value="1-5">1-5 密林深处</option>
                                        <option value="1-6">1-6 废弃兽穴</option>
                                    </optgroup>
                                    <optgroup label="噩梦2图">
                                        <option value="2-1">2-1 泰坦山道</option>
                                        <option value="2-2">2-2 荒蛮古道</option>
                                        <option value="2-3">2-3 部落遗迹</option>
                                        <option value="2-4">2-4 余晖渡口</option>
                                        <option value="2-5">2-5 黄昏镇</option>
                                        <option value="2-6">2-6 银月港</option>
                                    </optgroup>
                                    <optgroup label="噩梦3图">
                                        <option value="3-1">3-1 风暴岛</option>
                                        <option value="3-2">3-2 南港</option>
                                        <option value="3-3">3-3 星辰学院</option>
                                        <option value="3-4">3-4 竞技场</option>
                                        <option value="3-5">3-5 星象塔</option>
                                        <option value="3-6">3-6 龙牙山</option>
                                        <option value="3-7">3-7 神秘山洞</option>
                                        <option value="3-8">3-8 地下图书馆</option>
                                    </optgroup>
                                    <optgroup label="噩梦4图">
                                        <option value="4-1">4-1 微风湾</option>
                                        <option value="4-2">4-2 巨木村</option>
                                        <option value="4-3">4-3 坠星湖</option>
                                        <option value="4-4">4-4 蓝鹰瀑布</option>
                                        <option value="4-5">4-5 月神祭坛</option>
                                        <option value="4-6">4-6 月影之井</option>
                                        <option value="4-7">4-7 耳语渡口</option>
                                        <option value="4-8">4-8 迷雾之谷</option>
                                    </optgroup>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>难度</td>
                            <td>
                                <select name="dungeons-difficulty" id="dungeons-difficulty" class="map-select"  data-mini="true" data-native-menu="true">
                                    <option value="1" selected="selected">简单</option>
                                    <option value="2">普通</option>
                                    <option value="3">困难</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>层数/噩梦加成</td>
                            <td>
                                <select name="layer-select" id="layer-select" class="layer-select"  data-mini="true" data-native-menu="true">
                                    <option value="0" selected="selected">不选择层数/地图</option>
                                    <option value="1">噩梦地图1图</option>
                                    <option value="2">噩梦地图2图</option>
                                    <option value="3">噩梦地图3图</option>
                                    <option value="4">噩梦地图4图</option>
                                    <option value="90" >地下城90层</option>
                                    <option value="91" >地下城91层</option>
                                    <option value="92" >地下城92层</option>
                                    <option value="93" >地下城93层</option>
                                    <option value="94" >地下城94层</option>
                                    <option value="95" >地下城95层</option>
                                    <option value="96" >地下城96层</option>
                                    <option value="97" >地下城97层</option>
                                    <option value="98" >地下城98层</option>
                                    <option value="99" >地下城99层</option>
                                    <option value="100" >地下城100层</option>
                                    <option value="101">地下城101层</option>
                                    <option value="102">地下城102层</option>
                                    <option value="103">地下城103层</option>
                                    <option value="104">地下城104层</option>
                                    <option value="105">地下城105层</option>
                                    <option value="106">地下城106层</option>
                                    <option value="107">地下城107层</option>
                                    <option value="108">地下城108层</option>
                                    <option value="109">地下城109层</option>
                                    <option value="110">地下城110层</option>
                                    <option value="111">地下城111层</option>
                                    <option value="112">地下城112层</option>
                                    <option value="113">地下城113层</option>
                                    <option value="114">地下城114层</option>
                                    <option value="115">地下城115层</option>
                                    <option value="116">地下城116层</option>
                                    <option value="117">地下城117层</option>
                                    <option value="118">地下城118层</option>
                                    <option value="119">地下城119层</option>
                                    <option value="120">地下城120层</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>过关条件</td>
                            <td>
                                <span id="dungeons-victory-condition">未知</span>
                                <a id="view-dungeons-deck-link" data-rel="dialog" data-mini="true">查看关卡阵容</a>
                            </td>
                        </tr>
                    </table>
                    <div id="player" class="player ui-grid-c">
                        <div class="ui-block-a ui-block-label-number">
                            <span>玩家等级: </span>
                        </div>
                        <div class="ui-block-b">
                            <input type="number" id="dungeons-hero-lv" name="dungeons-hero-lv" data-mini="true" value="75" />
                        </div>
                        <div class="ui-block-c ui-block-label-number">
                            <span>玩家卡组: </span>
                        </div>
                        <div class="ui-block-d">
                            <a id="build-dungeons-deck-button" data-role="button" data-rel="dialog" data-mini="true">组卡</a>
                        </div>
                    </div>
                    <div>
                        <textarea id="dungeons-deck" name="dungeons-deck" rows="5" cols="40" data-mini="true">熊人武士+蛮荒之力3-15,熊人武士+不动-12,蜘蛛人女王+不动-15,蜘蛛人女王+暴击5-12,水源制造者+森林之力4-15,水源制造者+森林守护4-14,元素灵龙+不动-15,小矮人狙击者+森林守护3-15,雷兽+格挡8-11,暴怒霸龙+吸血6-15,石林-3,扬旗-3,雷盾-3,赤谷-3</textarea>
                    </div>
                </div>
            </div>
            <div data-mini="true" data-role="controlgroup" data-type="horizontal" data-disabled="false">
                <a id="play-dungeons-1-game-button" class="battle-button" data-role="button" data-mini="true">文字战斗</a>
                <a id="simulate-dungeons-1-game-button" class="battle-button" data-role="button" data-mini="true">动画战斗</a>
                <a id="play-dungeons-massive-game-button" class="battle-button" data-role="button" data-mini="true">连续千场</a>
                <a data-role="button" data-mini="true" data-type="bug" href="#">提BUG</a>
            </div>
            <div id="dungeons-battle-div" data-mini="true" data-role="collapsible" data-collapsed="false">
                <h3>战斗记录</h3>
                <div id="dungeons-battle-output" class="battle-output">没有战斗</div>
            </div>
        </div>
    </div>
    <div data-role="page" data-title="查看关卡阵容" data-mini="true" id="view-dungeons-deck-page" class="fixed-width">
        <div data-role="header" data-position="fixed">
            <h3 style="text-align: center">查看关卡阵容</h3>
        </div>
        <div data-role="content">
            <div id="dungeons-deck-info" style="height: 200px; padding: 10px"></div>
            <div style="width: 100%">
                <a data-role="button" data-mini="true" href="javascript:history.go(-1)">返回</a>
            </div>
        </div>
    </div>