package cfvbaibai.cardfantasy.test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import cfvbaibai.cardfantasy.officialdata.OfficialCard;
import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;

import com.google.gson.Gson;

public class TestOfficialData {
    @Test
    public void testOfficialDataStore() throws IOException {
        OfficialDataStore store = OfficialDataStore.getInstance();
        System.out.println("Card Size: " + store.cardStore.data.Cards.size());
        System.out.println("Skill Size: " + store.skillStore.data.Skills.size());
    }
    
    @Test
    public void testSkillPattern() {
        String SKILL_REGEX = 
                "^" +
                "(?<SkillType>[^\\d]+)" +
                "(?<SkillLevel>\\d+)" +
                "$";
        Pattern pattern = Pattern.compile(SKILL_REGEX);
        Matcher matcher = pattern.matcher("暗杀1");
        System.out.println(matcher.find());
        System.out.println(matcher.groupCount());
        System.out.println(matcher.group("SkillType"));
        System.out.println(matcher.group("SkillLevel"));
    }
    
    @Test
    public void testDeserializeCard() {
        String cardText = "{\"Glory\":\"0\",\"Race\":\"3\",\"Wait\":\"2\",\"DustLevel\":\"0\",\"DungeonsCard\":\"0\",\"Skill\":\"1811\",\"DustNumber\":\"0\",\"DungeonsFrag\":\"0\",\"LockSkill1\":\"1034\",\"FragRacePacket\":\"0\",\"MaxInDeck\":\"2\",\"LockSkill2\":\"602\",\"RacePacket\":\"0\",\"ImageId\":\"0\",\"FragRobber\":\"0\",\"RacePacketRoll\":\"0\",\"FullImageId\":\"0\",\"FightRank\":\"0\",\"CanDecompose\":\"0\",\"Price\":\"16000\",\"FragMagicCard\":\"0\",\"Rank\":\"0\",\"HpArray\":[1220,1249,1278,1307,1336,1365,1394,1423,1452,1481,1510,1539,1568,1597,1626,1655],\"AttackArray\":[220,246,272,298,324,350,376,402,428,454,480,506,532,558,584,610],\"FragMasterPacket\":\"0\",\"FragNewYearPacket\":\"0\",\"BossHelper\":\"0\",\"FragmentCanUse\":\"0\",\"BaseExp\":\"1310\",\"Dust\":\"0\",\"ActivityPacket\":\"0\",\"ActivityPacketRoll\":\"0\",\"DecomposeGet\":\"0\",\"ExpArray\":[\"0\",\"120\",\"360\",\"720\",\"1200\",\"2160\",\"3360\",\"4860\",\"6660\",\"8760\",\"12000\",\"20800\",\"26400\",\"32800\",\"40000\",\"49600\"],\"ComposePrice\":\"0\",\"FragMaze\":\"0\",\"CardId\":\"366\",\"FragSeniorPacket\":\"0\",\"CardName\":\"恶魔猎人\",\"PriceRank\":\"0\",\"Attack\":\"220\",\"Cost\":\"14\",\"EvoCost\":\"16\",\"Fragment\":\"0\",\"Color\":\"4\"}";
        Gson gson = new Gson();
        OfficialCard card = gson.fromJson(cardText, OfficialCard.class);
        System.out.println(card.getCardName());
    }
}
