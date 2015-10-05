package cfvbaibai.cardfantasy.officialdata;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.gson.Gson;

public class OfficialDataStore {
    public final static String IFREE_CDN_BASE = "http://cache.ifreecdn.com/mkhx/public/swf";
    public OfficialSkillDataStore skillStore;
    public OfficialCardDataStore cardStore;
    public OfficialRuneDataStore runeStore;

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
        InputStreamReader runeDataReader = new InputStreamReader(
                OfficialDataStore.class.getClassLoader().getResourceAsStream("cfvbaibai/cardfantasy/officialdata/allrune"),
                "UTF-8");
            try {
                newInstance.runeStore = gson.fromJson(runeDataReader, OfficialRuneDataStore.class);
            } finally {
                runeDataReader.close();
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

    public List<OfficialCard> getCardOfStar(int star) {
        List<OfficialCard> result = new ArrayList<OfficialCard>();
        for (OfficialCard card : cardStore.data.Cards) {
            if (card.getColor() == star) {
                result.add(card);
            }
        }
        return result;
    }

    public String[] getRaceNames() {
        return new String[] { "王国", "森林", "蛮荒", "地狱", "魔王", "魔神" };
    }

    public String getRaceNameById(int raceId) {
        return OfficialCard.getRaceNameById(raceId);
    }

    public List<OfficialCard> getCardOfRace(int race) {
        List<OfficialCard> result = new ArrayList<OfficialCard>();
        for (OfficialCard card : cardStore.data.Cards) {
            if (card.getRace() == race) {
                result.add(card);
            }
        }
        return result;
    }

    private List<OfficialSkillCategory> skillCategoriesCache;
    public List<OfficialSkillCategory> getSkillCategories() {
        if (this.skillCategoriesCache != null) {
            return this.skillCategoriesCache;
        }
        List<Integer> skillCategoryIds = new ArrayList<Integer>();
        for (OfficialSkill skill : this.skillStore.data.Skills) {
            if (!skillCategoryIds.contains(Integer.valueOf(skill.getCategory()))) {
                skillCategoryIds.add(skill.getCategory());
            }
        }
        List<OfficialSkillCategory> skillCategories = new ArrayList<OfficialSkillCategory>();
        for (Integer skillCategoryId : skillCategoryIds) {
            skillCategories.add(new OfficialSkillCategory(skillCategoryId));
        }
        this.skillCategoriesCache = skillCategories;
        return this.skillCategoriesCache;
    }

    public List<String> getSkillTypesByCategory(int category) {
        List<String> result = new ArrayList<String>();
        for (OfficialSkill skill : this.skillStore.data.Skills) {
            if (skill.getCategory() == category) {
                String skillType = this.getSkillTypeFromName(skill.getName());
                if (!result.contains(skillType)) {
                    result.add(skillType);
                }
            }
        }
        return result;
    }

    public List<OfficialRune> getRunesOfStar(int star) {
        List<OfficialRune> result = new ArrayList<OfficialRune>();
        for (OfficialRune rune : this.runeStore.data.Runes) {
            if (rune.getColor() == star) {
                result.add(rune);
            }
        }
        return result;
    }

    public String[] getPropertyNames() {
        return new String[] { "地", "水", "风", "火" };
    }

    public List<OfficialRune> getRunesOfProperty(int property) {
        List<OfficialRune> result = new ArrayList<OfficialRune>();
        for (OfficialRune rune : this.runeStore.data.Runes) {
            if (rune.getProperty() == property) {
                result.add(rune);
            }
        }
        return result;
    }

    public OfficialRune getRuneByName(String runeName) {
        for (OfficialRune rune : this.runeStore.data.Runes) {
            if (rune.getRuneName().equalsIgnoreCase(runeName)) {
                return rune;
            }
        }
        return null;
    }
}
