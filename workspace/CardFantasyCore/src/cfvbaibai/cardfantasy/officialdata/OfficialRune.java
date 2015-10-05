package cfvbaibai.cardfantasy.officialdata;

/*
{
  "Fragment": "0",
  "LockSkill5": "142",
  "Color": "1",
  "SkillTimes": "3",
  "BaseExp": "20",
  "LockSkill4": "141",
  "ExpArray": [
    "0",
    "40",
    "200",
    "560",
    "1200"
  ],
  "SkillConditionSlide": "1",
  "SkillConditionType": "2",
  "Condition": "我方墓地卡牌数量大于2张",
  "SkillConditionRace": "0",
  "RuneId": "11",
  "SkillConditionColor": "0",
  "RuneName": "霜冻",
  "SkillConditionCompare": "1",
  "Property": "2",
  "SkillConditionValue": "2",
  "LockSkill1": "138",
  "LockSkill3": "140",
  "ThinkGet": "1",
  "LockSkill2": "139",
  "Price": "800"
}
 */
public class OfficialRune {
    public int RuneId;
    public String RuneName;
    public int Fragment;
    public int Color;
    public int SkillTimes;
    public int BaseExp;
    public int[] ExpArray;
    public String Condition;
    public int SkillConditionSlide;
    public int SkillConditionType;
    public int SkillConditionColor;
    public int SkillConditionCompare;
    public int SkillConditionValue;
    // 1: 地, 2: 冰, 3: 风, 4: 火
    public int Property;
    public String LockSkill1;
    public String LockSkill2;
    public String LockSkill3;
    public String LockSkill4;
    public String LockSkill5;
    public int ThinkGet;
    public int Price;
    
    public int getColor() {
        return this.Color;
    }
    public int getProperty() {
        return this.Property;
    }
    public String getPropertyName() {
        return OfficialRuneProperty.getNameFromId(this.Property);
    }
    public String getRuneName() {
        return this.RuneName;
    }
    public String getFragmentColor() {
        switch (this.Color) {
        case 3: return "蓝";
        case 4: return "紫";
        case 5: return "金";
        default: return "未知";
        }
    }
    public String getSkill1() {
        return LockSkill1;
    }
    public String getSkill2() {
        return LockSkill2;
    }
    public String getSkill3() {
        return LockSkill3;
    }
    public String getSkill4() {
        return LockSkill4;
    }
    public String getSkill5() {
        return LockSkill5;
    }
}
