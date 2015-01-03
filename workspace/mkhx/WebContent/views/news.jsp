<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <div id="news" class="main-page" data-role="page" data-category="main-page" data-title="公告" data-mini="true">
        <div class="dashboard" data-role="content" data-theme="c">
            <div>
                <a href="#news-left-panel" data-role="button" data-mini="true" data-theme="b">点击左上角导航按钮开始使用模拟器</a>
            </div>
            <!--
            <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>模拟器代码开源声明</h3>
                <p>模拟器的代码已开源，希望能有能力和爱心兼备的程序员玩家们一起来努力。详情点击<a href="#open-source">这里</a>。</p>
            </div>
             -->
            <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>公告</h3>
                <ul>
                    <li>本人百度贴吧ID技术壕，刚从白白那边接过了模拟器的维护任务，域名保持不变，服务器换了阿里云，备案已完毕。</li>
                    <li>更新了一批新卡，不过限于本人diao丝很多卡没有没法实测，新技能不知道结算方法，一些五星卡没有更新，先凑合吧。</li>
                    <li><a href="#" data-type="bug">魔卡幻想模拟器贴吧</a>已成立，欢迎大家来提BUG、提需求、提供技能结算细节。</li>
                </ul>
            </div>
            <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>技能细节问答，欢迎大家来补完技能细节，使模拟器更准确</h3>
                <ul>
                    <c:forEach items="${questions}" var="question">
                    <li>
                        <span>【<a href="http://tieba.baidu.com/p/<c:out value="${question.tiebaId}" />" target="_blank">我去回答</a>】</span>
                        <c:out value="${question.title}" />
                    </li>
                    </c:forEach>
                    <li><a href="#" data-type="bug">更多问题...</a></li>
                </ul>
            </div>
            <div data-role="collapsible" data-collapsed="false" data-mini="true" data-content-theme="d" data-theme="c">
                <h3>更新日志</h3>
                <ul class="news-content">
                    <li>2015-01-03: <ul>
                        <li>添加新卡【铁血剑豪】、【铸造大师】、【巨龙剑士】、【重装半鹿人】、【太古魔狼】</li>
                        <li>添加技能【不屈】，感谢<x>aquoo66</x>, <x>NoMoCol</x>, <x>程love翔</x>提供信息</li>
                        <li>修复【鬼步】激活条件的BUG，感谢<x>出发1985</x>报告BUG</li>
                        <li>修复【龙吟】会造成null错误的BUG，感谢<x>cxqlyt</x>报告BUG</li>
                        <li>修复【降临-全体阻碍】技能未发动的BUG，感谢<x>出发1985</x>报告BUG</li>
                        <li>修复【峦龙】的种族错误，感谢<x>出发1985</x>报告BUG</li>
                        <li>添加符文【神祈】，感谢<x>出发1985</x>提供信息</li>
                    </ul></li>
                    <li>2015-01-02: <ul>
                        <li>修复【陨星魔法使】召唤时候会null错误的BUG，感谢<x>为何打后</x>报告BUG</li>
                        <li>修复召唤物死亡时秽土会尝试转生的BUG，感谢<x>susss222</x>报告BUG</li>
                        <li>添加符文【玄石】、【龙吟】，感谢<x>麋鹿吉特</x>, <x>susss222</x>提供信息</li>
                    </ul></li>
                    <li>2015-01-01: <ul>
                        <li>添加新卡【湿地黏龙】蛮荒四星</li>
                        <li>添加新卡【怒雪咆哮】蛮荒五星，感谢<x>麋鹿吉特</x>提供数据。</li>
                        <li>添加新技能【大地之盾】，感谢<x>雪拾玖</x>, <x>zx16792007</x>, <x>快到碗里来mys</x>, <x>为何打后</x>, <x>暗之影5</x>提供信息</li>
                        <li>完成【复仇女神】魔神、【万蛛之后】魔神，感谢<x>麋鹿吉特</x>提供数据。</li>
                        <li>添加新卡【血色骑士】王国四星、【无尽噩梦】地狱五星</li>
                    </ul></li>
                    <li>2014-12-31: 添加各级【莉莉丝】、【星夜女神】森林五星，完成【噩梦之主】魔神</li>
                    <li>2014-12-30: 添加【陨星魔法师】王国五星、【梦境耳语者】森林四星、【乌鸦人长老】蛮荒四星、【谎言之神】地狱五星、【瓦尔基里英灵】王国四星</li>
                    <li>2014-12-28: 添加【骨灵巫女】地狱五星、【月蚀兽】地狱五星</li>
                    <li>2014-12-27: 添加【逐月饿狼】地狱五星、【蝗虫公爵】地狱五星、【镜魔】地狱四星、【蝠王恶灵】地狱四星</li>
                    <li>2014-12-26: 添加【死域军神】地狱五星、【圆月魔女】地狱五星、【魅灵吞噬者】地狱四星、【峦龙】地狱五星</li>
                    <li>2014-12-25: 添加【暗影牧师】地狱四星、【地狱炎车手】地狱四星、【炼狱玩具熊】地狱四星、【血妖舞女】地狱四星</li>
                    <li>2014-12-24: 添加【逐日凶狼】蛮荒五星、【刃羽钢隼】蛮荒四星、【摩羯大祭司】蛮荒五星、【荒野巫医】蛮荒四星</li>
                    <li>2014-12-23: 添加【兽灵使者】蛮荒四星、【盾墙巨兽】蛮荒四星、【狂野雷电】蛮荒四星、【野猪人萨满】蛮荒四星</li>
                    <li>2014-12-22: 添加【浅海水兽】蛮荒四星、【蛮族酋长】蛮荒四星、【神谕火狐】蛮荒五星、【山羊人先锋】蛮荒四星</li>
                    <li>2014-12-21: 添加【幸运星贤者】森林四星、【破晓守卫】森林五星、【洞察之鹰】森林四星、【人马大贤者】森林四星</li>
                    <li>2014-12-20: 添加【浮云青鸟】森林五星、【尖啸曼陀罗】森林四星、【雷雕之魂】森林五星、【星辰之蝶】森林四星</li> 
                    <li>2014-12-19: 添加【圣炎魔导师】王国五星、【红月女仆】王国三星、【飞行器机师】王国四星、【琴舞之风】森林四星</li>
                    <li>2014-12-18: 添加【幻术舞姬】王国四星、【钢铁巨神兵】王国五星、【鬼斩破】王国四星、【星辰主宰】王国五星</li>
                    <li>2014-12-17: 添加【死兆星】地狱五星、【东方幻术师】王国四星、【大图书馆长】王国四星、【重甲冲锋兵】王国四星</li>
                    <li>2014-02-06: 添加【浴火龙女】王国五星</li>
                    <li>2014-01-24: 添加【熊老师】（同时也会加入到人品测试）<ul>
                        <li>种族：萌货，星数：五星，速度：4，10级COST：0，15级COST：0</li>
                        <li>10级二维：AT=800, HP=2000</li>
                        <li>15级二维：AT=1000, HP=2250</li>
                        <li>技能1：[降临]关小黑屋1 - 随机送还对手场上一张卡片，关入小黑屋，可被免疫、不动抵抗。</li>
                        <li>技能2: 吐槽2 - 随机吐槽对手场上两张卡片，导致其精神崩溃，对自己造成当前攻击力的伤害，可被免疫、脱困抵抗。</li>
                        <li>技能3: 被插出五星1 - 被物理攻击命中并承受伤害时，30%几率随机召唤卡堆中的一张五星卡片上场，可发动降临技。</li>
                        </ul>
                    </li>
                    <li>2014-01-23: 在动画中给魔神加上了卡图，魔神现在终于有脸了。</li>
                    <li>2014-01-18: 添加符文 - 【鬼步】（群体脱困），场上地狱大于2张发动，感谢<a data-type="user">高端大气上档次</a>提供情报。</li>
                    <li>2014-01-15:<ul>
                        <li>新卡加入 - 【海军水手】王国三星</li>
                        <li>数据修复 - 【世界树之灵】10级COST修正为16。多谢<a data-type="user">长天渔歌</a>提供情报。</li>
                        <li>数据修复 - 【恐惧之王】15级COST修正为17。多谢<a data-type="user">高端大气上档次</a>提供情报。</li>
                        </ul>
                    </li>
                    <li>2014-01-12: 新功能 - 增加<a href="#test-rp">人品测试房</a>。
                    <li>2014-01-11: 数据修复 - 【世界树之灵】15级COST修正为17。多谢<a data-type="user">里根</a>和<a data-type="user">乌鸦</a>提供情报。</li>
                    <li>2014-01-10:<ul>
                        <li>BUG修复 - 修正交流区的留言时间的时区问题，统一改到北京时间。</li>
                        <li>BUG修复 - 现在【横扫】遇上【邪灵汲取】后，后续的攻击按照削弱后的攻击力算。感谢<a data-type="user">乌鸦</a>提供情报。</li>
                        </ul>
                    </li>
                    <li>2014-01-07:<ul>
                        <li>BUG修复 - 现在回合数正确地从1开始，而不是之前的0，感谢<a data-type="user">jjkkt</a>提供情报。</li>
                        <li>BUG修复 - 现在被死契技能控制（冰冻、麻痹、锁定、迷惑）的卡牌会在正确的时机解除控制，感谢<a data-type="user">jjkkt</a>提供情报。</li>
                        <li>BUG修复 - 现在【吸血】能正确地在死契技能发动前发动，感谢<a data-type="user">a0026881</a>提供情报。</li>
                        </ul>
                    </li>
                    <li>2014-01-06: 魔神战【卡组强度分析】添加【总体平均每分钟伤害】指标，方便大家计算卡组的平均强度。</li>
                    <li>2014-01-05:<ul>
                        <li>BUG修复 - 被控制并且被【迷魂】的卡片现在会打自己英雄了。用吧友<a data-type="user">w13210886303</a>的话说就是“被绳子绑着被虐待着还要自己用头撞墙！”感谢<a data-type="user">地花出吠喊</a>提供情报。</li>
                        <li>BUG修复 - 被【复活】上来处于【复活】状态的卡牌现在不享受符文效果，感谢<a data-type="user">LLOO</a>、<a data-type="user">a0026881</a>等朋友提供情报。</li>
                        <li>BUG修复 - 现在【九头妖蛇】在被【复活节兔女郎】复活后能正确的献祭【复活节兔女郎】，感谢<a data-type="user">老头是废柴</a>等朋友提供情报。</li>
                        <li>优化 - 魔神战的文字战斗现在会在首行显示对魔神造成的伤害，不必再翻页查看。</li>
                        </ul>
                    </li>
                    <li>2014-01-04: 添加新卡【海军司令】王国五星，感谢<a data-type="user">我就是那么叼</a>和<a data-type="user">寒风</a>提供情报，感谢<a data-type="user">上行之风</a>发现BUG。</li>
                    <li>2013-12-30: 添加新卡【天界守护者】王国五星，感谢<a data-type="user">轩</a>和<a data-type="user">UII</a>提供情报。</li>
                    <li>2013-12-25: 更新5张新卡，感谢<a data-type="user">a272295516</a>等朋友热情提供情报。
                        <ul><li>森林：蒲公英仙子</li><li>蛮荒：荒漠仙人掌 熊人巫医 半狮人武士 齐天美猴王</li></ul>
                    </li>
                    <li>2013-12-10: 修复【透支】之后没有立即死亡被【回春】救活的BUG，感谢<b>IP为118.*.*.129</b>的朋友提供情报</li>
                    <li>2013-12-09: 应大家的需求，魔神战添加更多统计值（最大、最小、平均伤害，不稳定度）</li>
                    <li>2013-12-08: 修复【脱困】无法防止【迷魂】的BUG，感谢<a data-type="user">a0026881</a>提供情报</li>
                    <li>2013-12-07: BUG修复
                        <ul>
                            <li>修复【背刺】技能的一个BUG，感谢<b>IP为222.*.*.174</b>的朋友提供情报</li>
                            <li>感谢大家的回复，【毁灭之龙】的HP已修正</li>
                            <li>修复一个【趁胜追击】和【复仇】攻击英雄时候的BUG，感谢<a data-type="user">DdiTp</a>提供情报</li>
                        </ul>
                    </li>
                    <li>2013-12-06: 更新导航界面</li>
                    <li>2013-12-04: 添加旧魔神的模拟
                        <ul>
                            <li>应广大WP玩家的强烈要求，在魔神战中复了添加了旧魔神的模拟</li>
                            <li>修复一个封印技能某些时候会导致错误的BUG</li>
                            <li>感谢<a data-type="user">福哥马林</a>积极提出各种建议</li>
                        </ul>
                    </li>
                    <li>2013-12-03: 魔神更新以及BUG修复
                        <ul>
                            <li>六大魔神技能全面升级，感谢<a data-type="user">cwal18</a>提供情报</li>
                            <li>修复【虚空假面】的攻击力BUG，感谢<a data-type="user">slbtsbc</a>和<b>守望月磐</b>提供情报</li>
                            <li>修复【魔法协会长】复活带【封印】或【背刺】卡牌时候的BUG，感谢<a data-type="user">a0026881</a>提供情报</li>
                        </ul>
                    </li>
                    <li>2013-11-30: BUG修复
                        <ul>
                            <li>修复【横扫】触发额外【裂伤】的BUG，感谢<a data-type="user">沸腾的冰红茶</a>提供情报</li>
                            <li>修复【世界树之灵】【神圣守护】的BUG，感谢<a data-type="user">hejiangting8</a>提供情报</li>
                            <li>修复【世界树之灵】COST的BUG，感谢<b>IP为221.*.*.158的朋友</b>提供情报</li>
                            <li>更新【骸骨大将】的第二技能为【[死契]暴风雪6】，感谢<b>IP为112.*.*.134的朋友</b>提供情报</li>
                        </ul>
                    </li>
                    <li>2013-11-28: 全面修复17张新卡的COST
                        <ul>
                            <li>感谢魔卡WIKI的及时更新和各位朋友的提醒</li>
                        </ul>
                    </li>
                    <li>2013-11-27: 修复几个BUG
                        <ul>
                            <li>【精灵女王】的COST从19改为17，15级COST改为19（感谢吧友<a data-type="user">uiiysss1</a>提供情报）</li>
                            <li>修复地图战中<b>无符文</b>的条件判断错误（感谢吧友<a data-type="user">勿忘干将</a>和<a data-type="user">ycphoenix</a>提供情报）</li>
                            <li>修复【魔法协会长】总是复活最后死亡的卡的BUG（感谢吧友<a data-type="user">PandaGM</a>提供情报）</li>
                            <li>新加的一些卡片的数据都来源于魔卡WIKI，但是这次WIKI上的COST数值都很诡异，多数卡10级和15级的COST没有区别，可能有错，希望大家一起帮忙排查</li>
                        </ul>
                    </li>
                    <li>2013-11-25: 添加17张新卡
                        <ul>
                            <li>王国：血瞳魔剑师 王城巡逻犬 魔能巨石像 科学怪人 驯鹰射手</li>
                            <li>森林：复仇血精灵 裁决巨石像 高等暗精灵 梦境女神 精灵女王</li>
                            <li>蛮荒：仙狐巫女 尖牙捕食者 冰雪巨人 龙角将军</li>
                            <li>地狱：赤红地狱战马 骸骨大将 末日预言师</li>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
    </div>