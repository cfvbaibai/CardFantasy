package cfvbaibai.cardfantasy.web.beans;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.PlayerInfo;

public class OfficialHeroSetting {
    public static OfficialHeroSetting instance;
    public static OfficialHeroSetting getInstance() {
        if (instance == null) {
            instance = new OfficialHeroSetting();
        }
        return instance;
    }
    private List<OfficialHeroLevelSetting> levelSettings;
    public List<OfficialHeroLevelSetting> getLevelSettings() {
        return levelSettings;
    }
    public OfficialHeroSetting() {
        levelSettings = new ArrayList<OfficialHeroLevelSetting>();
        for (int level = 0; level <= 130; ++level) {
            OfficialHeroLevelSetting setting = new OfficialHeroLevelSetting();
            setting.setLevel(level);
            setting.setMaxHP(PlayerInfo.getHpAtLevel(level));
            setting.setMaxCost(PlayerInfo.getMaxCost(level));
            levelSettings.add(setting);
        }
    }
}
