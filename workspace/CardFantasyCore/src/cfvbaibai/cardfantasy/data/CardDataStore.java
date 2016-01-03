package cfvbaibai.cardfantasy.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;

public class CardDataStore {
    private Map<String, CardData> cardMap;
    private Map<String, CardData> aliasCardMap;
    // Only used in getRandomCard
    private List<String> allKeys;

    private CardDataStore() {
        this.cardMap = new HashMap<>();
        this.aliasCardMap = new HashMap<>();
        this.allKeys = new ArrayList<>();
    }

    public static CardDataStore loadDefault() {
        CardDataStore store = new CardDataStore();

        Map<String, List<String>> aliasMap = new HashMap<>(); // Key: card name, Value: alias list.
        Set<String> aliasSet = new HashSet<>(); // To check whether duplicate alias exists.
        InputStream aliasStream = CardDataStore.class.getClassLoader().getResourceAsStream("cfvbaibai/cardfantasy/data/alias.txt");
        BufferedReader aliasReader;
        try {
            aliasReader = new BufferedReader(new InputStreamReader(aliasStream, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new CardFantasyRuntimeException("UTF-8 is not supported on the machine", e);
        }
        String line = null;
        int lineNumber = 0;
        try {
            while ((line = aliasReader.readLine()) != null) {
                ++lineNumber;
                if (line.startsWith("#")) {
                    continue;
                }
                if (line.length() == 0) {
                    continue;
                }
                int iEqualSign = line.indexOf('=');
                if (iEqualSign < 0 || iEqualSign == 0 || iEqualSign == line.length() - 1) {
                    throw new CardFantasyRuntimeException("Invalid alias at line " + lineNumber + ": " + line);
                }
                String cardName = line.substring(0, iEqualSign);
                String[] aliases = line.substring(iEqualSign + 1).split(",");
                if (aliases.length == 0) {
                    throw new CardFantasyRuntimeException("Invalid alias at line " + lineNumber + ": " + line);
                }
                List<String> aliasList = new ArrayList<>();
                for (int i = 0; i < aliases.length; ++i) {
                    if (aliasSet.contains(aliases[i])) {
                        throw new CardFantasyRuntimeException("Duplicate alias found at line " + lineNumber + ": " + aliases[i]);
                    }
                    aliasSet.add(aliases[i]);
                    aliasList.add(aliases[i]);
                }
                if (aliasMap.containsKey(cardName)) {
                    aliasMap.get(cardName).addAll(aliasList);
                } else {
                    aliasMap.put(cardName, aliasList);
                }
            }
        } catch (IOException e) {
            throw new CardFantasyRuntimeException("Cannot read line from alias file.", e);
        }
        try {
            aliasReader.close();
        } catch (IOException e) {
            throw new CardFantasyRuntimeException("Cannot close alias reader.", e);
        }

        URL url = CardDataStore.class.getClassLoader().getResource("cfvbaibai/cardfantasy/data/CardData.xml");
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(url);
            @SuppressWarnings("unchecked")
            List<Node> cardNodes = doc.selectNodes("/Cards/Card");
            for (Node cardNode : cardNodes) {
                CardData cardData = new CardData();
                cardData.setId(cardNode.valueOf("@id"));
                cardData.setWikiId(cardNode.valueOf("@wikiId"));
                cardData.setName(cardNode.valueOf("@name"));
                cardData.setRace(Race.parse(cardNode.valueOf("@race")));
                cardData.setSummonSpeed(Integer.parseInt(cardNode.valueOf("@speed")));
                cardData.setStar(Integer.parseInt(cardNode.valueOf("@star")));
                cardData.setBaseCost(Integer.parseInt(cardNode.valueOf("@cost")));
                cardData.setIncrCost(Integer.parseInt(cardNode.valueOf("@incrCost")));
                cardData.setBaseAT(Integer.parseInt(cardNode.valueOf("@at")));
                cardData.setBaseHP(Integer.parseInt(cardNode.valueOf("@hp")));
                cardData.setIncrAT(Integer.parseInt(cardNode.valueOf("@incrAT")));
                cardData.setIncrHP(Integer.parseInt(cardNode.valueOf("@incrHP")));
                
                @SuppressWarnings("unchecked")
                List<Node> skillNodes = cardNode.selectNodes("Skill");
                for (Node skillNode : skillNodes) {
                    SkillType type = SkillType.valueOf(skillNode.valueOf("@type"));
                    int level = Integer.parseInt(skillNode.valueOf("@level"));
                    int unlockLevel = Integer.parseInt(skillNode.valueOf("@unlock"));
                    String summonText = skillNode.valueOf("@summon");
                    boolean isSummonSkill = summonText == null ? false : Boolean.parseBoolean(summonText);
                    String deathText = skillNode.valueOf("@death");
                    boolean isDeathSkill = deathText == null ? false : Boolean.parseBoolean(deathText);
                    cardData.getSkills().add(new CardSkill(type, level, unlockLevel, isSummonSkill, isDeathSkill));
                }
                store.addCard(cardData);
                if (aliasMap.containsKey(cardData.getName())) {
                    for (String alias : aliasMap.get(cardData.getName())) {
                        store.addAlias(alias, cardData);
                    }
                }
            }
            return store;
        } catch (DocumentException e) {
            throw new CardFantasyRuntimeException("Cannot load card info XML.", e);
        }
    }

    private void addAlias(String alias, CardData cardData) {
        this.aliasCardMap.put(alias, cardData);
        // allKeys used in getRandomCard, so we should add alias to it. 
        //this.allKeys.add(alias);
    }

    private void addCard(CardData cardData) {
        this.cardMap.put(cardData.getName(), cardData);
        this.allKeys.add(cardData.getName());
    }
    
    public CardData getCard(String name) {
        if (this.cardMap.containsKey(name)) {
            return this.cardMap.get(name);
        } else if (this.aliasCardMap.containsKey(name)) {
            return this.aliasCardMap.get(name);
        } else {
            return null;
        }
    }

    public static String[] fiveStarBossGuardians  = new String[] {
        "降临天使",
        "光明之龙",
        "战神",
        "圣诞老人",
        "纯洁圣女",
        "隐世先知",
        "圣城卫士",
        "海军司令",
        "浴火龙女",
        "陨星魔法使",
        "战场扫荡者",
        "魔幻人偶师",
        "最终兵器",
        "钢铁巨神兵",
        "制裁之雷神",
        "魔装机神",
        "月樱公主",
        "灵峰剑姬",
        "叹惋之歌",
        "制裁之锤",
        "红莲魔女",
        "战场女武神",
        "圣堂执政官",
        "天界守护者",
        "残翼羽神",
        "爱之使者",
        "圣剑持有者",
        "巨蟹座",

        "金属巨龙",
        "凤凰",
        "月亮女神",
        "元素灵龙",
        "麒麟兽",
        "梦境守护者",
        "圣灵大祭司",
        "世界树之灵",
        "精灵女王",
        "黄金金属巨龙",
        "唤雨师",
        "梦境女神",
        "圣泉元神",
        "浮云青鸟",
        "雷雕之魂",
        "破晓守卫",
        "晓之奏者",
        "龙灵使者",
        "不幸青鸟",
        "时光女神",
        "幻翼神丽雅",
        "星夜女神",
        "荆棘妖姬",

        "雷兽",
        "九头妖蛇",
        "远古蝎皇",
        "暴怒霸龙",
        "毒雾羽龙",
        "羽翼化蛇",
        "迷魅灵狐",
        "神谕火狐",
        "幻翼命运神",
        "远古海妖",
        "战意斗神",
        "七彩之影",
        "龙角将军",
        "太古魔狼",
        "峦龙",
        "逐日凶狼",
        "摩羯大祭司",
        "黄金毒龙",
        "羽蛇神",
        "九命猫神",
        "断罪之镰",
        "风暴海皇",
        "创世神女",
        "贪吃少女",

        "毁灭之龙",
        "灵魂收割者",
        "恐惧之王",
        "背主之影",
        "幽灵巨龙",
        "巫妖领主",
        "刀锋女王",
        "月蚀兽",
        "赤面天狗",
        "亡灵守护神",
        "混沌之龙",
        "不死鸟化身",
        "堕落天使",
        "精灵王亡灵",
        "欲望惩罚者",
        "末日预言师",
        "无尽噩梦",
        "死域军神",
        "逐月恶狼",
        "蝗虫公爵",
        "彼岸使者",
        "永夜真祖",
        "骨灵巫女",
        "爱神",
        "雪月花",
        "骑士王血魂",
        "邪月翼魔"
    };

    public static String[] weakBossGuardians = new String[] {
        "小矮人狙击者",
        "精灵法师",
        "精灵暗杀者",
        "兽语驾驭者",
        "独角兽",
        "神秘花精灵",
        "精灵长者",
        "守卫古树",
        "精灵祭司",
        "地岭拥有者",
        "水源制造者",
        "风暴召唤者",
        "火灵操控者",
        "翡翠龙",
        "金属巨龙",
        "凤凰",
        "魔剑士",
        "重甲骑兵",
        "喷火装甲车手",
        "火焰乌鸦",
        "狮鹫",
        "魔导师",
        "魔法结晶体",
        "圣骑士",
        "暴雪召唤士",
        "皇家卫队将领",
        "大主教",
        "秘银巨石像",
        "荣耀巨人",
        "大剑圣",
        "降临天使",
        "光明之龙",
        "蜘蛛人女王",
        "鳄鱼人酋长",
        "山羊人萨满",
        "犀牛人武士",
        "哥布林术士",
        "食人魔术士",
        "食人魔战士",
        "牛头人卫士",
        "蝎尾狮",
        "兽人将领",
        "狂暴狼人酋长",
        "牛头人酋长",
        "独眼巨人",
        "巨象人武士",
        "战斗猛犸象",
        "雷兽",
        "九头妖蛇",
        "地狱魔兽",
        "骷髅法师",
        "堕落精灵",
        "堕落精灵法师",
        "黑曜石像鬼",
        "恶魔伯爵",
        "邪眼",
        "梦魇",
        "木乃伊法老王",
        "美杜莎女王",
        "黑甲铁骑士",
        "大恶魔",
        "骨龙",
        "地狱红龙",
        "毁灭之龙",
        "灵魂收割者",
        "时空旅者",
        "东方僧人",
        "月之神兽",
        "沙漠吞虫",
        "魅魔",
        "暗影巨蟒",
        "痛苦之王",
        "霜雪树人",
        "皇家雄狮",
        "半鹿人守护者",
        "狮鹫骑士",
        "荒野狂虫",
        "奇美拉",
        "月亮女神",
        "战神",
        "远古蝎皇",
        "恐惧之王",
        "树人祭司",
        "雷电树人",
        "半鹿人祭司",
        "精灵审判长",
        "独角兽之王",
        "海盗船长",
        "巨型电鳗",
        "机械飞龙",
        "钻石巨石像",
        "火象人卫士",
        "火象人萨满",
        "蛮鱼人",
        "火象人领主",
        "洪荒巨熊",
        "鬼树人",
        "幽灵巨鲸",
        "黑魔导士",
        "巫妖学徒",
        "圣诞树人",
        "圣诞老人",
        "圣诞麋鹿",
        "背主之影",
        "震源岩蟾",
        "疾奔刺猬",
        "半鹿人长老",
        "月神弩炮车手",
        "元素灵龙",
        "麒麟兽",
        "光影魔术师",
        "圣堂武士",
        "炼金机甲",
        "纯洁圣女",
        "隐世先知",
        "雷电幽魂",
        "暗夜魔影",
        "恶灵之瞳",
        "幽灵巨龙",
        "巫妖领主",
        "荒原猎手",
        "熊人武士",
        "地行龙骑士",
        "暴怒霸龙",
        "毒雾羽龙",
        "羽翼化蛇",
        "森林丘比特",
        "新年爆竹",
        "梦境治愈师",
        "象牙角虫",
        "蒲公英仙子",
        "叶莲河童",
        "驯鹰射手",
        "迷魅灵狐",
        "半鹿人号角手",
        "巨岛龟幼崽",
        "枭羊族祭司",
        "刀锋女王",
        "高等暗精灵",
        "世界树之灵",
        "精灵女王",
        "枪炮玫瑰",
        "海军水手",
        "皇室舞者",
        "炎刃暗骑士",
        "王城巡逻犬",
        "彩翼公主",
        "冰峰女猎",
        "科学怪人",
        "机械兵团长",
        "鬼眼斗士",
        "海军司令",
        "浴火龙女",
        "半鱼人士卒",
        "黯灭制裁者",
        "幻翼命运神",
        "魔鹰族萨满",
        "尖牙捕食者",
        "犀角领主",
        "海潮歌姬",
        "唤雨师",
        "荒漠仙人掌",
        "魔能射手",
        "半狮人武士",
        "蛮族酋长",
        "冰雪巨人",
        "淘气灯灵",
        "尸腐守卫",
        "斗角恶魔",
        "恶灵之剑",
        "赤红地狱战马",
        "影子魔",
        "虚空假面",
        "地狱狮鹫",
        "鬼武者",
        "幽境裁决官",
        "赤炎鬼武士",
        "亡灵守护神",
        "混沌之龙",
        "不死鸟化身",
        "堕落天使",
        "小矮人工匠",
        "密纹骑士",
        "东方禅师",
        "皇家驯兽师",
        "赤瞳魔剑师",
        "裁决巨石像",
        "复仇血精灵",
        "梦境女神",
        "龙角将军",
        "仙狐巫女",
        "骸骨大将",
        "末日预言师",
        "魔能巨石像",
        "战场女武神",
        "时光女神",
        "圣堂执政官",
        "天界守护者"
    };

    public List<CardData> getCardOfStar(int star) {
        List<CardData> result = new ArrayList<CardData>();
        for (CardData card : this.cardMap.values()) {
            if (card.getStar() == star) {
                result.add(card);
            }
        }
        return result;
    }
    
    public List<CardData> getAllCards() {
        List<CardData> result = new ArrayList<CardData>();
        for (CardData card : this.cardMap.values()) {
            result.add(card);
        }
        return result;
    }

    public List<CardData> getCardOfRace(Race race) {
        List<CardData> result = new ArrayList<CardData>();
        for (CardData card : this.cardMap.values()) {
            if (card.getRace() == race) {
                result.add(card);
            }
        }
        return result;
    }

    public CardData getRandomCard() {
        String key = this.allKeys.get(Randomizer.getRandomizer().next(0, this.allKeys.size()));
        return this.cardMap.get(key);
    }
    
    public String[] getWeakBossHelpers() {
        return null;
    }
}