package cfvbaibai.cardfantasy.data;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.Randomizer;

public class CardDataStore {
    private Map<String, CardData> cardMap;
    private List<String> allKeys;

    private CardDataStore() {
        this.cardMap = new HashMap<String, CardData>();
        this.allKeys = new ArrayList<String>();
    }

    public static CardDataStore loadDefault() {
        CardDataStore store = new CardDataStore();

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
            }
            return store;
        } catch (DocumentException e) {
            throw new CardFantasyRuntimeException("Cannot load card info XML.", e);
        }
    }

    private void addCard(CardData cardData) {
        this.cardMap.put(cardData.getName(), cardData);
        this.allKeys.add(cardData.getName());
    }
    
    public CardData getCard(String name) {
        if (this.cardMap.containsKey(name)) {
            return this.cardMap.get(name);
        } else {
            return null;
        }
    }

    public static String[] fiveStarBossGuardians  = new String[] {
        "金属巨龙",
        "凤凰",
        "降临天使",
        "光明之龙",
        "雷兽",
        "九头妖蛇",
        "毁灭之龙",
        "灵魂收割者",
        "月亮女神",
        "战神",
        "远古蝎皇",
        "恐惧之王",
        "圣诞老人",
        "背主之影",
        "元素灵龙",
        "麒麟兽",
        "纯洁圣女",
        "隐世先知",
        "幽灵巨龙",
        "巫妖领主",
        "暴怒霸龙",
        "毒雾羽龙",
        "羽翼化蛇",
        "迷魅灵狐",
        "刀锋女王",
        "世界树之灵",
        "精灵女王",
        "海军司令",
        "浴火龙女",
        "幻翼命运神",
        "唤雨师",
        "亡灵守护神",
        "混沌之龙",
        "不死鸟化身",
        "堕落天使",
        "梦境女神",
        "龙角将军",
        "末日预言师",
        "战场女武神",
        "时光女神",
        "圣堂执政官",
        "天界守护者"
    };

    public static String[] allBossGuardians = new String[] {
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
}