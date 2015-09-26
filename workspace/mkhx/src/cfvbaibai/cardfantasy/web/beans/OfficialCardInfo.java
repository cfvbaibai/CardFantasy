package cfvbaibai.cardfantasy.web.beans;

import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialCard;
import cfvbaibai.cardfantasy.officialdata.OfficialSkill;
import cfvbaibai.cardfantasy.officialdata.OfficialSkillData;

public class OfficialCardInfo {
    public String internalId;
    public OfficialCard card;
    public OfficialSkill skill1;
    public OfficialSkill skill2;
    public OfficialSkill skill3;
    public OfficialSkill skill4;
    public OfficialSkill skill5;

    public static OfficialCardInfo build(OfficialCard card, CardDataStore myStore, OfficialSkillData skillData) {
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
        CardData data = myStore.getCard(card.CardName);
        if (data != null) {
            cardInfo.internalId = data.getId();
        }
        return cardInfo;
    }
}
