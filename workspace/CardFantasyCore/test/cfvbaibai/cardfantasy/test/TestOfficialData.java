package cfvbaibai.cardfantasy.test;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;

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
}
