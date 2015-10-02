package cfvbaibai.cardfantasy.officialdata;

/*
{
  "LanchCondition": 0, 
  "Type": 125, 
  "LanchConditionValue": 0, 
  "AffectType": 101, 
  "SkillId": 1912, 
  "AffectValue": 93, 
  "Desc": "卡牌上场后，召唤2张骷髅战士上场战斗（召唤出的卡牌不会进入墓地，牌堆）。如果场上已经存在自身召唤的卡牌时，则不发动召唤", 
  "AffectValue2": 93, 
  "LanchType": 4, 
  "Name": "召唤：骷髅战士", 
  "SkillCategory": 2
}
*/
public class OfficialSkill {
    public int LanchCondition;
    public int Type;
    public int LanchConditionValue;
    public int AffectType;
    public String SkillId;
    public String AffectValue;
    public String Desc;
    public int AffectValue2;
    public int LanchType;
    public String Name;
    public int SkillCategory;

    public String getName() {
        return this.Name;
    }
    public String getDescription() {
        return this.Desc;
    }
}
