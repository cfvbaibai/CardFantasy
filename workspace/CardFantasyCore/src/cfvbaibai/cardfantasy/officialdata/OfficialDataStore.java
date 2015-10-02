package cfvbaibai.cardfantasy.officialdata;

import java.io.IOException;
import java.io.InputStreamReader;

import com.google.gson.Gson;

public class OfficialDataStore {
    public OfficialSkillDataStore skillStore;
    public OfficialCardDataStore cardStore;

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
}
