<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <div id="map-battle" class="main-page" data-role="page" data-title="地图战" data-mini="true">
        <div data-role="content">
            <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>设置阵容</h3>
                <div>
                    <table class="form">
                        <tr>
                            <td>地图</td>
                            <td>
                                <select data-theme="c" name="map-id" id="map-id" class="map-select" data-mini="true" data-native-menu="false">
                                    <optgroup label="12-后燃烧平原">
                                        <option value="12-1" selected="selected">12-1 火腹石</option>
                                        <option value="12-2">12-2 绝望壁</option>
                                        <option value="12-3">12-3 焦炭岭</option>
                                        <option value="12-4">12-4 乌鼻熔炉</option>
                                        <option value="12-5">12-5 火晶炼场</option>
                                        <option value="12-6">12-6 残渣废墟</option>
                                        <option value="12-7">12-7 熔火之心</option>
                                        <option value="12-8">12-8 毁灭之锤</option>
                                        <option value="12-9">12-9 流桨河</option>
                                        <option value="12-10">12-10 烙铁牢笼</option>
                                        <option value="12-11">12-11 红龙巢穴</option>
                                        <option value="12-H">12-H (隐藏)熔岩口</option>
                                    </optgroup>
                                    <optgroup label="11-蛮荒火山">
                                        <option value="11-1">11-1 石浪岸</option>
                                        <option value="11-2">11-2 火龙遗骸</option>
                                        <option value="11-3">11-3 火象人村</option>
                                        <option value="11-4">11-4 石像堡</option>
                                        <option value="11-5">11-5 荒芜之地</option>
                                        <option value="11-6">11-6 火海之森</option>
                                        <option value="11-7">11-7 古湖遗迹</option>
                                        <option value="11-8">11-8 炙热荆林</option>
                                        <option value="11-9">11-9 赤红裂谷</option>
                                        <option value="11-10">11-10 断石桥</option>
                                        <option value="11-11">11-11 火山口</option>
                                        <option value="11-H">11-H (隐藏)巨树之根</option>
                                    </optgroup>
                                    <optgroup label="10-海底界">
                                        <option value="10-1">10-1 迷雾礁石</option>
                                        <option value="10-2">10-2 失落神像</option>
                                        <option value="10-3">10-3 深渊海沟</option>
                                        <option value="10-4">10-4 鲸鱼坟场</option>
                                        <option value="10-5">10-5 远古沉船</option>
                                        <option value="10-6">10-6 海神之叉</option>
                                        <option value="10-7">10-7 海底古城</option>
                                        <option value="10-8">10-8 幽冥古洞</option>
                                        <option value="10-9">10-9 恐怖漏斗</option>
                                        <option value="10-10">10-10 海底山</option>
                                        <option value="10-H">10-H (隐藏)鱼人村</option>
                                    </optgroup>
                                    <optgroup label="9-海龟岛">
                                        <option value="9-1">9-1 巨大石块</option>
                                        <option value="9-2">9-2 长脊山</option>
                                        <option value="9-3">9-3 灰熊森林</option>
                                        <option value="9-4">9-4 鱼人小屋</option>
                                        <option value="9-5">9-5 菊石巨螺</option>
                                        <option value="9-6">9-6 神秘蛋坑</option>
                                        <option value="9-7">9-7 木船废墟</option>
                                        <option value="9-8">9-8 废弃战场</option>
                                        <option value="9-9">9-9 珊瑚山</option>
                                        <option value="9-10">9-10 东侧平原</option>
                                        <option value="9-H">9-H (隐藏)象形石门</option>
                                    </optgroup>
                                    <optgroup label="8-天空之城">
                                        <option value="8-1">8-1 巨人旷野</option>
                                        <option value="8-2">8-2 金色沙漠</option>
                                        <option value="8-3">8-3 脚印绿洲</option>
                                        <option value="8-4">8-4 绿色通道</option>
                                        <option value="8-5">8-5 三岔路口</option>
                                        <option value="8-6">8-6 水晶湖</option>
                                        <option value="8-7">8-7 闪耀之碑</option>
                                        <option value="8-8">8-8 传送门</option>
                                        <option value="8-9">8-9 静谧宫殿</option>
                                        <option value="8-10">8-10 远古避难所</option>
                                        <option value="8-H">8-H (隐藏)陨石坑</option>
                                    </optgroup>
                                    <optgroup label="7-末日峡谷">
                                        <option value="7-1">7-1 赤色沼泽</option>
                                        <option value="7-2">7-2 迷雾湿地</option>
                                        <option value="7-3">7-3 回声森林</option>
                                        <option value="7-4">7-4 暗黑之门</option>
                                        <option value="7-5">7-5 幽暗山脉</option>
                                        <option value="7-6">7-6 无光之峰</option>
                                        <option value="7-7">7-7 抉择峭壁</option>
                                        <option value="7-8">7-8 末日之镜</option>
                                        <option value="7-9">7-9 远古通道</option>
                                        <option value="7-H">7-H (隐藏)恐惧回廊</option>
                                    </optgroup>
                                    <optgroup label="6-乌木地下城">
                                        <option value="6-1">6-1 大裂痕</option>
                                        <option value="6-2">6-2 嚎叫深渊</option>
                                        <option value="6-3">6-3 尖叫长桥</option>
                                        <option value="6-4">6-4 乌木城</option>
                                        <option value="6-5">6-5 乌木神庙</option>
                                        <option value="6-6">6-6 暗影长廊</option>
                                        <option value="6-7">6-7 暗影祭坛</option>
                                        <option value="6-8">6-8 永生渡口</option>
                                        <option value="6-H">6-H (隐藏)魔力之泉</option>
                                    </optgroup>
                                    <optgroup label="5-燃烧平原">
                                        <option value="5-1">5-1 冒险者营地</option>
                                        <option value="5-2">5-2 冒险者岗哨</option>
                                        <option value="5-3">5-3 黑石矿坑</option>
                                        <option value="5-4">5-4 灰烬之谷</option>
                                        <option value="5-5">5-5 黑炭洞窟</option>
                                        <option value="5-6">5-6 遗忘神庙</option>
                                        <option value="5-7">5-7 废弃墓园</option>
                                        <option value="5-8">5-8 灼热小径</option>
                                        <option value="5-H">5-H (隐藏)焦炭遗迹</option>
                                    </optgroup>
                                    <optgroup label="4-翡翠森林">
                                        <option value="4-1">4-1 微风湾</option>
                                        <option value="4-2">4-2 巨木村</option>
                                        <option value="4-3">4-3 坠星湖</option>
                                        <option value="4-4">4-4 蓝鹰瀑布</option>
                                        <option value="4-5">4-5 月神祭坛</option>
                                        <option value="4-6">4-6 月影之井</option>
                                        <option value="4-7">4-7 耳语渡口</option>
                                        <option value="4-H">4-H (隐藏)迷雾之谷</option>
                                    </optgroup>
                                    <optgroup label="3-西风岛">
                                        <option value="3-1">3-1 风暴岛</option>
                                        <option value="3-2">3-2 南港</option>
                                        <option value="3-3">3-3 星辰学院</option>
                                        <option value="3-4">3-4 竞技场</option>
                                        <option value="3-5">3-5 星象塔</option>
                                        <option value="3-6">3-6 龙牙山</option>
                                        <option value="3-7">3-7 神秘山洞</option>
                                        <option value="3-H">3-H (隐藏)地下图书馆</option>
                                    </optgroup>
                                    <optgroup label="2-落日荒原">
                                        <option value="2-1">2-1 泰坦山道</option>
                                        <option value="2-2">2-2 荒蛮古道</option>
                                        <option value="2-3">2-3 部落遗迹</option>
                                        <option value="2-4">2-4 余晖渡口</option>
                                        <option value="2-5">2-5 黄昏镇</option>
                                        <option value="2-6">2-6 银月港</option>
                                    </optgroup>
                                    <optgroup label="1-试炼森林">
                                        <option value="1-1">1-1 森林入口</option>
                                        <option value="1-2">1-2 森林小径</option>
                                        <option value="1-3">1-3 守林人小屋</option>
                                        <option value="1-4">1-4 小镜湖</option>
                                        <option value="1-5">1-5 密林深处</option>
                                        <option value="1-6">1-6 废弃兽穴</option>
                                    </optgroup>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>难度</td>
                            <td>
                                <select data-theme="c" name="map-difficulty" id="map-difficulty" class="map-select" data-mini="true" data-native-menu="false">
                                    <option value="1" selected="selected">简单</option>
                                    <option value="2">普通</option>
                                    <option value="3">困难</option>
                                </select>
                            </td>
                        </tr>
                        <tr><td>过关条件</td><td><span id="map-victory-condition">未知</span></td></tr>
                    </table>
                    <div id="player" class="player ui-grid-c">
                        <div class="ui-block-a ui-block-label-number">
                            <span>玩家等级: </span>
                        </div>
                        <div class="ui-block-b">
                            <input data-theme="c" type="number" id="map-hero-lv" name="map-hero-lv" data-mini="true" value="75" />
                        </div>
                        <div class="ui-block-c ui-block-label-number">
                            <span>玩家卡组: </span>
                        </div>
                        <div data-theme="c" class="ui-block-d">
                            <a id="build-map-deck-button" data-role="button" data-rel="dialog" data-mini="true">组卡</a>
                        </div>
                    </div>
                    <div>
                        <textarea data-theme="c" id="map-deck" name="map-deck" rows="5" cols="40" data-mini="true">精灵法师-10</textarea>
                    </div>
                </div>
            </div>
            <div data-mini="true" data-role="controlgroup" data-type="horizontal" data-disabled="false">
                <a id="play-map-1-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">文字战斗</a>
                <a id="simulate-map-1-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">动画战斗</a>
                <a id="play-map-massive-game-button" class="battle-button" data-role="button" data-mini="true" data-theme="c">连续千场</a>
                <a data-role="button" data-mini="true" data-theme="c" href="#communication">提BUG</a>
            </div>
            <div id="map-battle-div" data-mini="true" data-role="collapsible" data-collapsed="false" data-theme="c" data-content-theme="d">
                <h3>战斗记录</h3>
                <div id="map-battle-output" class="battle-output">没有战斗</div>
            </div>
        </div>
    </div>