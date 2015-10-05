package cfvbaibai.cardfantasy.web.beans;

import cfvbaibai.cardfantasy.officialdata.OfficialCard;
import cfvbaibai.cardfantasy.officialdata.OfficialSkill;
import cfvbaibai.cardfantasy.officialdata.OfficialSkillData;

public class OfficialCardInfo {
    private OfficialCard card;
    private OfficialSkill skill1;
    private OfficialSkill skill2;
    private OfficialSkill skill3;
    private OfficialSkill skill4;
    private OfficialSkill skill5;

    public OfficialCard getCard() {
        return this.card;
    }
    public String getCardName() {
        return this.card.getCardName();
    }
    public String getRaceName() {
        return this.card.getRaceName();
    }
    public int getColor() {
        return this.card.getColor();
    }
    public int getWait() {
        return this.card.getWait();
    }
    public int getCost() {
        return this.card.getCost();
    }
    public int getEvoCost() {
        return this.card.getEvoCost();
    }
    public int[] getAttackArray() {
        return this.card.getAttackArray();
    }
    public int[] getHpArray() {
        return this.card.getHpArray();
    }
    public int[] getExpArray() {
        return this.card.getExpArray();
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
    public int getMaxInDeck() {
        return this.card.getMaxInDeck();
    }
    public String getImageId() {
        return this.card.ImageId;
    }
    public String getFullImageId() {
        return this.card.FullImageId;
    }
    public int getCardId() {
        return this.card.CardId;
    }
    public String getLogoUrl() {
        return this.card.getLogoUrl();
    }
    public String getLargePortraitUrl() {
        return this.card.getLargePortraitUrl();
    }
    public boolean isMaterial() {
        return this.card.isMaterial();
    }
    public static OfficialCardInfo build(OfficialCard card, OfficialSkillData skillData) {
        OfficialCardInfo cardInfo = new OfficialCardInfo();
        cardInfo.card = card;
        for (OfficialSkill skill : skillData.Skills) {
            if (skill.SkillId.equals(card.getSkill1())) {
                cardInfo.skill1 = skill;
            }
            if (skill.SkillId.equals(card.getSkill2())) {
                cardInfo.skill2 = skill;
            }
            if (skill.SkillId.equals(card.getSkill3())) {
                cardInfo.skill3 = skill;
            }
            if (skill.SkillId.equals(card.getSkill4())) {
                cardInfo.skill4 = skill;
            }
            if (skill.SkillId.equals(card.getSkill5())) {
                cardInfo.skill5 = skill;
            }
        }
        return cardInfo;
    }
}
