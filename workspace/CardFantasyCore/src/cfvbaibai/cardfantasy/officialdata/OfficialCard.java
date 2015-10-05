package cfvbaibai.cardfantasy.officialdata;


/*
{
    "ActivityPacket": "0", 
    "DungeonsCard": "0",    // 迷宫卡
    "FragMaze": "0", 
    "FragMasterPacket": "0", 
    "DungeonsFrag": "0", 
    "FragNewYearPacket": "0", 
    "FragmentCanUse": "0", 
    "BaseExp": "1450", 
    "Dust": "0", 
    "DustNumber": "0", 
    "ExpArray": [ "0", "190", "580", "1150", "1920", "3460", "5380", "7780", "10660", "14020", "19200", "31200", "39600", "49200", "60000", "74400" ], 
    "FightRank": "0", 
    "FragRobber": "0", 
    "FragRacePacket": "0", 
    "AttackArray": [ 93, 93, 93, 93, 93, 93, 93, 93, 93, 93, 93, 93, 93, 93, 93, 93 ],
    "CardId": "9911", 
    "CardName": "胜利女神", 
    "Attack": "93", 
    "Cost": "99", 
    "FragMagicCard": "0", 
    "EvoCost": "99", 
    "Color": "5", 
    "ActivityPacketRoll": "0", 
    "Glory": "0", 
    "Race": "1", 
    "HpArray": [ 93, 93, 93, 93, 93, 93, 93, 93, 93, 93, 93, 93, 93, 93, 93, 93 ],
    "Rank": "0", 
    "Wait": "4", 
    "Skill": "9911", 
    "PriceRank": "0", 
    "BossHelper": "0", 
    "Fragment": "0", 
    "LockSkill1": "", 
    "MaxInDeck": "0", // 限定
    "LockSkill2": "", 
    "ComposePrice": "0", 
    "RacePacket": "0", // 种族包
    "ImageId": "0", 
    "DustLevel": "0", 
    "RacePacketRoll": "0",
    "FullImageId": "0", 
    "DecomposeGet": "0", 
    "CanDecompose": "0", 
    "Price": "18000", 
    "FragSeniorPacket": "0"
}
*/
public class OfficialCard {
    public String ActivityPacket;
    public String DungeonsCard;
    public String FragMaze;
    public String FragMasterPacket;
    public String DungeonsFrag;
    public String FragNewYearPacket;
    public String FragmentCanUse;
    public String BaseExp;
    public String Dust;
    public String DustNumber;
    public int[] ExpArray;
    public String FightRank;
    public String FragRobber;
    public String FragRacePacket;
    public int[] AttackArray;
    public int CardId;
    private String CardName;
    public String Attack;
    public int Cost;
    public String FragMagicCard;
    public int EvoCost;
    public int Color;
    public String ActivityPacketRoll;
    public String Glory;
    public int Race;
    public int[] HpArray;
    public String Rank;
    public int Wait;
    private String Skill;
    public String PriceRank;
    public String BossHelper;
    public String Fragment;
    private String LockSkill1;
    public int MaxInDeck;
    private String LockSkill2;
    public String ComposePrice;
    public String RacePacket;
    public String ImageId;
    public String DustLevel;
    public String RacePacketRoll;
    public String FullImageId;
    public String DecomposeGet;
    public String CanDecompose;
    public String Price;
    public String FragSeniorPacket;

    public String getSkill1() {
        return this.Skill;
    }
    public String getSkill2() {
        return this.LockSkill1;
    }
    public String getSkill3() {
        if (this.LockSkill2 == null || this.LockSkill2.equals("")) {
            return null;
        }
        String[] lockSkill2Parts = this.LockSkill2.split("_");
        return lockSkill2Parts[0];
    }
    public String getSkill4() {
        if (this.LockSkill2 == null || this.LockSkill2.equals("")) {
            return null;
        }
        String[] lockSkill2Parts = this.LockSkill2.split("_");
        if (lockSkill2Parts.length > 1) {
            return lockSkill2Parts[1];
        }
        return null;
    }
    public String getSkill5() {
        if (this.LockSkill2 == null || this.LockSkill2.equals("")) {
            return null;
        }
        String[] lockSkill2Parts = this.LockSkill2.split("_");
        if (lockSkill2Parts.length > 2) {
            return lockSkill2Parts[2];
        }
        return null;
    }
    public String getCardName() {
        return this.CardName;
    }
    public String getRaceName() {
        return OfficialCard.getRaceNameById(this.getRace());
    }
    public int getColor() {
        return this.Color;
    }
    public int getWait() {
        return this.Wait;
    }
    public int getCost() {
        return this.Cost;
    }
    public int getEvoCost() {
        return this.EvoCost;
    }
    public int[] getAttackArray() {
        return this.AttackArray;
    }
    public int[] getHpArray() {
        return this.HpArray;
    }
    public int[] getExpArray() {
        return this.ExpArray;
    }
    public int getMaxInDeck() {
        return this.MaxInDeck;
    }
    public int getRace() {
        return this.Race;
    }
    public static String getRaceNameById(int raceId) {
        switch (raceId) {
        case 1: return "王国";
        case 2: return "森林";
        case 3: return "蛮荒";
        case 4: return "地狱";
        case 97: return "魔王";
        case 100: return "魔神";
        default: return "未知";
        }
    }
    public String getLogoUrl() {
        return OfficialDataStore.IFREE_CDN_BASE + "/card/110_110/img_photoCard_" + this.CardId + ".jpg";
    }
    public String getLargePortraitUrl() {
        return OfficialDataStore.IFREE_CDN_BASE + "/card/370_570/img_maxCard_" + this.CardId + ".jpg";
    }
}
