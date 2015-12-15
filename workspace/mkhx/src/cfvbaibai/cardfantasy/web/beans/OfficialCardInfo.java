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
    private int getToLevelExp(int level) {
        int[] expArray = this.card.getExpArray();
        int sum = 0; 
        for (int i = 0; i <= level; ++i) {
            sum += expArray[i];
        }
        return sum;
    }
    public int getToLevel5Exp() {
        return this.getToLevelExp(5);
    }
    public int getToLevel10Exp() {
        return this.getToLevelExp(10);
    }
    public int getToLevel15Exp() {
        return this.getToLevelExp(15);
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
    public OfficialSkill getSkill(int i) {
        switch (i) {
        case 1: return this.skill1;
        case 2: return this.skill2;
        case 3: return this.skill3;
        case 4: return this.skill4;
        case 5: return this.skill5;
        default: return null;
        }
    }
    public OfficialSkill[] getSkills() {
        return new OfficialSkill[] { this.skill1, this.skill2, this.skill3, this.skill4, this.skill5 };
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
    public int getRequiredFragmentCount() {
        try {
            return Integer.parseInt(this.card.Fragment);
        } catch (Exception e) {
            return 0;
        }
    }
    public int getUsableGenericFragmentCount() {
        try {
            return this.getRequiredFragmentCount() - Integer.parseInt(this.card.FragmentCanUse);
        } catch (Exception e) {
            return 0;
        }
    }

    public boolean isCanDecompose() {
        return "1".equals(this.card.CanDecompose);
    }

    public boolean isObtainableViaDecomposition() {
        return "1".equals(this.card.DecomposeGet);
    }

    public boolean isFragmentObtainableInMaze() {
        return !"0".equals(this.card.FragMaze);
    }
    
    public boolean isFragmentObtainableInDungeon() {
        return !"0".equals(this.card.DungeonsFrag);
    }

    public boolean isBossHelper() {
        return "1".equals(this.card.BossHelper);
    }

    public boolean isDungeonCard() {
        return "1".equals(this.card.DungeonsCard);
    }

    public String getRank() {
        return this.card.Rank;
    }

    private static String getRankFromPossibility(int p) {
        if (p == 0) {
            return "无";
        }
        if (p < 20) {
            return "极低";
        }
        if (p < 40) {
            return "非诚低";
        }
        if (p < 60) {
            return "低";
        }
        if (p < 80) {
            return "稍低";
        }
        return "普通";
    }

    public String getActivityPackRollRank() {
        int p = Integer.parseInt(this.card.ActivityPacketRoll);
        return getRankFromPossibility(p);
    }
    
    public String getRacePackRollRank() {
        int p = Integer.parseInt(this.card.RacePacketRoll);
        return getRankFromPossibility(p);
    }
    
    public String getComposePrice() {
        return this.card.ComposePrice;
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
