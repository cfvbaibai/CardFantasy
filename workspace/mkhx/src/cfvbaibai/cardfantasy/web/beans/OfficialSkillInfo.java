package cfvbaibai.cardfantasy.web.beans;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.officialdata.OfficialCard;
import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialRune;
import cfvbaibai.cardfantasy.officialdata.OfficialSkill;

public class OfficialSkillInfo {
    public OfficialSkill skill;
    public List<OfficialCard> owningCards;
    public List<OfficialRune> owningRunes;
    public static OfficialSkillInfo build(OfficialSkill skill, OfficialDataStore store) {
        OfficialSkillInfo skillInfo = new OfficialSkillInfo();
        skillInfo.skill = skill;
        skillInfo.owningCards = new ArrayList<OfficialCard>();
        for (OfficialCard card : store.cardStore.data.Cards) {
            if (skill.SkillId.equalsIgnoreCase(card.getSkill1()) ||
                skill.SkillId.equalsIgnoreCase(card.getSkill2()) ||
                skill.SkillId.equalsIgnoreCase(card.getSkill3()) ||
                skill.SkillId.equalsIgnoreCase(card.getSkill4()) ||
                skill.SkillId.equalsIgnoreCase(card.getSkill5())) {
                skillInfo.owningCards.add(card);
            }
        }
        skillInfo.owningRunes = new ArrayList<OfficialRune>();
        for (OfficialRune rune : store.runeStore.data.Runes) {
            if (skill.SkillId.equalsIgnoreCase(rune.getSkill1()) ||
                skill.SkillId.equalsIgnoreCase(rune.getSkill2()) ||
                skill.SkillId.equalsIgnoreCase(rune.getSkill3()) ||
                skill.SkillId.equalsIgnoreCase(rune.getSkill4()) ||
                skill.SkillId.equalsIgnoreCase(rune.getSkill5())) {
                skillInfo.owningRunes.add(rune);
            }
        }
        return skillInfo;
    }

    public List<OfficialCard> getOwningCards() {
        return this.owningCards;
    }

    public List<OfficialRune> getOwningRunes() {
        return this.owningRunes;
    }

    public String getName() {
        return this.skill.getName();
    }

    public String getDescription() {
        return this.skill.getDescription();
    }
    
    public int getCategoryId() {
        return this.skill.getCategory();
    }
}
