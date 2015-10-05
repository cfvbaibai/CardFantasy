package cfvbaibai.cardfantasy.web.beans;

import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialRune;
import cfvbaibai.cardfantasy.officialdata.OfficialSkill;

public class OfficialRuneInfo {
    private OfficialRune rune;
    public OfficialSkill skill1;
    public OfficialSkill skill2;
    public OfficialSkill skill3;
    public OfficialSkill skill4;
    public OfficialSkill skill5;
    public OfficialRuneInfo(OfficialRune rune, OfficialDataStore store) {
        this.rune = rune;
        for (OfficialSkill skill : store.skillStore.data.Skills) {
            if (skill.SkillId.equals(rune.getSkill1())) {
                this.skill1 = skill;
            }
            if (skill.SkillId.equals(rune.getSkill2())) {
                this.skill2 = skill;
            }
            if (skill.SkillId.equals(rune.getSkill3())) {
                this.skill3 = skill;
            }
            if (skill.SkillId.equals(rune.getSkill4())) {
                this.skill4 = skill;
            }
            if (skill.SkillId.equals(rune.getSkill5())) {
                this.skill5 = skill;
            }
        }
    }
    public String getRuneName() {
        return this.rune.getRuneName();
    }
    public String getPropertyName() {
        return this.rune.getPropertyName();
    }
    public int getStar() {
        return this.rune.getColor();
    }
    public int getPrice() {
        return this.rune.Price;
    }
    public boolean getThinkGet() {
        return this.rune.ThinkGet == 1;
    }
    public String getCondition() {
        return this.rune.Condition;
    }
    public String getFragmentColor() {
        return this.rune.getFragmentColor();
    }
    public String getFragmentDesc() {
        if (this.rune.Fragment == 0) {
            return "无法兑换";
        }
        return this.rune.getFragmentColor() + "色碎片" + this.rune.Fragment + "个";
    }
    public OfficialSkill getSkill1() {
        return this.skill1;
    }
    public OfficialSkill getSkill2() {
        return this.skill2;
    }
    public OfficialSkill getSkill3() {
        return this.skill3;
    }
    public OfficialSkill getSkill4() {
        return this.skill4;
    }
    public OfficialSkill getSkill5() {
        return this.skill5;
    }
    public String getSmallIconUrl() {
        return this.rune.getSmallIconUrl();
    }
}
