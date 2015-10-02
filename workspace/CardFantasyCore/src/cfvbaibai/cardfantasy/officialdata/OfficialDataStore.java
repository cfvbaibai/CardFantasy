package cfvbaibai.cardfantasy.officialdata;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class OfficialDataStore {
    public OfficialSkillDataStore skillStore;
    public OfficialCardDataStore cardStore;

    private final static String SKILL_REGEX = 
            "^" +
            "(?<SkillType>[^\\d]+)" +
            "(?<SkillLevel>\\d*)" +
            "$";

    private final static Pattern SKILL_PATTERN = Pattern.compile(SKILL_REGEX);

    private static OfficialDataStore instance;
    public static OfficialDataStore getInstance() throws IOException {
        if (instance != null) {
            return instance;
        }
        Gson gson = new Gson();
        OfficialDataStore newInstance = new OfficialDataStore();
        InputStreamReader skillDataReader = new InputStreamReader(
            OfficialDataStore.class.getClassLoader().getResourceAsStream("cfvbaibai/cardfantasy/officialdata/allskill"),
            "UTF-8");
        try {
            newInstance.skillStore = gson.fromJson(skillDataReader, OfficialSkillDataStore.class);
        } finally {
            skillDataReader.close();
        }
        InputStreamReader cardDataReader = new InputStreamReader(
            OfficialDataStore.class.getClassLoader().getResourceAsStream("cfvbaibai/cardfantasy/officialdata/allcard"),
            "UTF-8");
        try {
            newInstance.cardStore = gson.fromJson(cardDataReader, OfficialCardDataStore.class);
        } finally {
            cardDataReader.close();
        }
        instance = newInstance;
        return newInstance;
    }
    
    public OfficialCard getCardByName(String cardName) {
        for (OfficialCard card : cardStore.data.Cards) {
            if (card.getCardName().equalsIgnoreCase(cardName)) {
                return card;
            }
        }
        return null;
    }
    
    public OfficialSkill getSkillByName(String skillName) {
        for (OfficialSkill skill : skillStore.data.Skills) {
            if (skill.getName().equalsIgnoreCase(skillName)) {
                return skill;
            }
        }
        return null;
    }
    
    public OfficialSkill[] getSkillsByType(String skillType) {
        List<OfficialSkill> skills = new ArrayList<OfficialSkill>();
        for (OfficialSkill skill : skillStore.data.Skills) {
            Matcher matcher = SKILL_PATTERN.matcher(skill.getName());
            if (!matcher.find()) {
                continue;
            }
            if (skillType.equalsIgnoreCase(matcher.group("SkillType"))) {
                skills.add(skill);
            }
        }
        OfficialSkill[] result = new OfficialSkill[skills.size()];
        for (int i = 0; i < skills.size(); ++i) {
            result[i] = skills.get(i);
        }
        return result;
    }
    
    public String getSkillTypeFromName(String skillName) {
        Matcher matcher = SKILL_PATTERN.matcher(skillName);
        if (!matcher.find()) {
            return null;
        }
        String skillType = matcher.group("SkillType");
        return skillType;
    }
}
