<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <div id="map-battle" class="main-page" data-role="page" data-title="地图战" data-mini="true" data-theme="${theme}">
        <div data-role="content">
            <div data-role="collapsible" data-collapsed="false" data-mini="true">
                <h3>设置阵容</h3>
                <div>
                    <table class="form">
                        <tr>
                            <td>地图</td>
                            <td>
                                <select name="map-id" id="map-id" class="map-select" data-mini="true" data-native-menu="true">
                                    <optgroup label="19-精灵世界">
                                        <option value="19-1" selected="selected">19-1 水晶塔</option>
                                        <option value="19-2">19-2 水晶之桥</option>
                                        <option value="19-3">19-3 冻骨峰</option>
                                        <option value="19-4">19-4 海之绝壁</option>
                                        <option value="19-5">19-5 精灵府</option>
                                        <option value="19-6">19-6 水晶街</option>
                                        <option value="19-7">19-7 水晶国度</option>
                                        <option value="19-8">19-8 深海洞穴</option>
                                        <option value="19-9">19-9 蓝海玉树</option>
                                        <option value="19-10">19-10 玉棘丛林</option>
                                        <option value="19-11">19-11 冰山危路</option>
                                        <option value="19-H">19-H 水晶溶洞</option>
                                    </optgroup>
                                    <optgroup label="18-万耀国">
                                        <option value="18-1" selected="selected">18-1 万妖之门</option>
                                        <option value="18-2">18-2 万足街</option>
                                        <option value="18-3">18-3 荆棘丛林</option>
                                        <option value="18-4">18-4 毒雾森林</option>
                                        <option value="18-5">18-5 东风黄沙原</option>
                                        <option value="18-6">18-6 百里石山</option>
                                        <option value="18-6-wp">18-6-wp 百里石山(WP版)</option>
                                        <option value="18-7">18-7 陨耀坑</option>
                                        <option value="18-8">18-8 烈焰帝怒</option>
                                        <option value="18-9">18-9 落九千</option>
                                        <option value="18-10">18-10 通天桥</option>
                                        <option value="18-11">18-11 万耀森林</option>
                                        <option value="18-H">18-H 万耀亭</option>
                                        <option value="18-H2">18-H2 万耀城</option>
                                        <option value="18-H2-wp">18-H2-wp 万耀城(WP版)</option>
                                    </optgroup>
                                    <optgroup label="17-星河之森">
                                        <option value="17-1" selected="selected">17-1 异界之门</option>
                                        <option value="17-2">17-2 挽歌丘陵</option>
                                        <option value="17-3">17-3 星树池塘</option>
                                        <option value="17-4">17-4 刹那树海</option>
                                        <option value="17-5">17-5 神启遗迹</option>
                                        <option value="17-5-wp">17-5-wp 神启遗迹(WP版)</option>
                                        <option value="17-6">17-6 荆棘古堡</option>
                                        <option value="17-7">17-7 伤痕裂谷</option>
                                        <option value="17-8">17-8 魔晶盆地</option>
                                        <option value="17-9">17-9 魔眼灯塔</option>
                                        <option value="17-10">17-10 流萤瀑布</option>
                                        <option value="17-11">17-11 星灵沼</option>
                                        <option value="17-H">17-H 风言迹地</option>
                                    </optgroup>
                                    <optgroup label="16-炎狱山">
                                        <option value="16-1">16-1 烈焰谷</option>
                                        <option value="16-2">16-2 火魔阵</option>
                                        <option value="16-3">16-3 冰魔阵</option>
                                        <option value="16-4">16-4 风魔阵</option>
                                        <option value="16-5">16-5 雷魔阵</option>
                                        <option value="16-6">16-6 暗影长廊</option>
                                        <option value="16-7">16-7 烈焰之核</option>
                                        <option value="16-8">16-8 智慧之厅</option>
                                        <option value="16-9">16-9 祈祷之塔</option>
                                        <option value="16-10">16-10 炎狱山巅</option>
                                        <option value="16-11">16-11 火焰矩阵</option>
                                        <option value="16-H">16-H 四魔阵</option>
                                        <option value="16-H-wp">16-H-wp 四魔阵(WP版)</option>
                                    </optgroup>
                                    <optgroup label="15-后翡翠森林">
                                        <option value="15-1">15-1 巨门关口</option>
                                        <option value="15-2">15-2 幽影森林</option>
                                        <option value="15-3">15-3 鬼影山谷</option>
                                        <option value="15-4">15-4 军团先锋阵地</option>
                                        <option value="15-5">15-5 巨神山</option>
                                        <option value="15-6">15-6 巨神塔</option>
                                        <option value="15-7">15-7 刀锋谷</option>
                                        <option value="15-8">15-8 传送器</option>
                                        <option value="15-9">15-9 领主大门</option>
                                        <option value="15-10">15-10 魔神王座</option>
                                        <option value="15-11">15-11 混沌界</option>
                                        <option value="15-H">15-H 巨神试炼场</option>
                                    </optgroup>
                                    <optgroup label="14-后翡翠森林">
                                         <option value="14-1">14-1 汇海口</option>
                                         <option value="14-2">14-2 蘑菇岭</option>
                                         <option value="14-3">14-3 坠星湖底</option>
                                         <option value="14-4">14-4 湍流口</option>
                                         <option value="14-5">14-5 刺海滩</option>
                                         <option value="14-6">14-6 兽神林</option>
                                         <option value="14-7">14-7 镰刀湾</option>
                                         <option value="14-8">14-8 精灵石碑</option>
                                         <option value="14-9">14-9 月井瀑布</option>
                                         <option value="14-10">14-10 月神高台</option>
                                         <option value="14-11">14-11 祭坛深处</option>
                                         <option value="14-H">14-H 雷云之地</option>
                                     </optgroup>
                                     <optgroup label="13-后燃烧平原">
                                          <option value="13-1">13-1 漆黑岭 </option>
                                          <option value="13-2">13-2 黑石山梯</option>
                                          <option value="13-3">13-3 泥泞小道</option>
                                          <option value="13-4">13-4 干涸小湖</option>
                                          <option value="13-5">13-5 伐木林</option>
                                          <option value="13-6">13-6 横尸坑</option>
                                          <option value="13-7">13-7 剧毒之池心</option>
                                          <option value="13-8">13-8 召唤门</option>
                                          <option value="13-9">13-9 小屋废墟</option>
                                          <option value="13-10">13-10 恶魔海湾</option>
                                          <option value="13-11">13-11 地岭神庙</option>
                                          <option value="13-H">13-H 古船遗迹</option>
                                      </optgroup>
                                      <optgroup label="12-后燃烧平原">
                                          <option value="12-1">12-1 火腹石</option>
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
                                          <option value="12-H">12-H 熔岩口</option>
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
                                          <option value="11-H">11-H 巨树之根</option>
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
                                          <option value="10-H">10-H 鱼人村</option>
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
                                        <option value="9-H">9-H 象形石门</option>
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
                                        <option value="8-H">8-H 陨石坑</option>
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
                                        <option value="7-H">7-H 恐惧回廊</option>
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
                                        <option value="6-H">6-H 魔力之泉</option>
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
                                        <option value="5-H">5-H 焦炭遗迹</option>
                                    </optgroup>
                                    <optgroup label="4-翡翠森林">
                                        <option value="4-1">4-1 微风湾</option>
                                        <option value="4-2">4-2 巨木村</option>
                                        <option value="4-3">4-3 坠星湖</option>
                                        <option value="4-4">4-4 蓝鹰瀑布</option>
                                        <option value="4-5">4-5 月神祭坛</option>
                                        <option value="4-6">4-6 月影之井</option>
                                        <option value="4-7">4-7 耳语渡口</option>
                                        <option value="4-H">4-H 迷雾之谷</option>
                                    </optgroup>
                                    <optgroup label="3-西风岛">
                                        <option value="3-1">3-1 风暴岛</option>
                                        <option value="3-2">3-2 南港</option>
                                        <option value="3-3">3-3 星辰学院</option>
                                        <option value="3-4">3-4 竞技场</option>
                                        <option value="3-5">3-5 星象塔</option>
                                        <option value="3-6">3-6 龙牙山</option>
                                        <option value="3-7">3-7 神秘山洞</option>
                                        <option value="3-H">3-H 地下图书馆</option>
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
                                <select name="map-difficulty" id="map-difficulty" class="map-select" data-mini="true" data-native-menu="true">
                                    <option value="1" selected="selected">简单</option>
                                    <option value="2">普通</option>
                                    <option value="3">困难</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>过关条件</td>
                            <td>
                                <span id="map-victory-condition">未知</span>
                                <a id="view-map-deck-link" data-rel="dialog" data-mini="true">查看关卡阵容</a>
                            </td>
                        </tr>
                    </table>
                    <div id="player" class="player ui-grid-c">
                        <div class="ui-block-a ui-block-label-number">
                            <span>玩家等级: </span>
                        </div>
                        <div class="ui-block-b">
                            <input type="number" id="map-hero-lv" name="map-hero-lv" data-mini="true" value="75" />
                        </div>
                        <div class="ui-block-c ui-block-label-number">
                            <span>玩家卡组: </span>
                        </div>
                        <div class="ui-block-d">
                            <a id="build-map-deck-button" data-role="button" data-rel="dialog" data-mini="true">组卡</a>
                        </div>
                    </div>
                    <div>
                        <textarea id="map-deck" name="map-deck" rows="5" cols="40" data-mini="true">熊人武士+蛮荒之力3-15,熊人武士+不动-12,蜘蛛人女王+不动-15,蜘蛛人女王+暴击5-12,水源制造者+森林之力4-15,水源制造者+森林守护4-14,元素灵龙+不动-15,小矮人狙击者+森林守护3-15,雷兽+格挡8-11,暴怒霸龙+吸血6-15,石林-3,扬旗-3,雷盾-3,赤谷-3</textarea>
                    </div>
                </div>
            </div>
            <div data-mini="true" data-role="controlgroup" data-type="horizontal" data-disabled="false">
                <a id="play-map-1-game-button" class="battle-button" data-role="button" data-mini="true">文字战斗</a>
                <a id="simulate-map-1-game-button" class="battle-button" data-role="button" data-mini="true">动画战斗</a>
                <a id="play-map-massive-game-button" class="battle-button" data-role="button" data-mini="true">连续千场</a>
                <a data-role="button" data-mini="true" data-type="bug" href="#">提BUG</a>
            </div>
            <div id="map-battle-div" data-mini="true" data-role="collapsible" data-collapsed="false">
                <h3>战斗记录</h3>
                <div id="map-battle-output" class="battle-output">没有战斗</div>
            </div>
        </div>
    </div>
    <div data-role="page" data-title="查看关卡阵容" data-mini="true" id="view-map-deck-page" class="fixed-width">
        <div data-role="header" data-position="fixed">
            <h3 style="text-align: center">查看关卡阵容</h3>
        </div>
        <div data-role="content">
            <div id="map-deck-info" style="height: 200px; padding: 10px"></div>
            <div style="width: 100%">
                <a data-role="button" data-mini="true" href="javascript:history.go(-1)">返回</a>
            </div>
        </div>
    </div>