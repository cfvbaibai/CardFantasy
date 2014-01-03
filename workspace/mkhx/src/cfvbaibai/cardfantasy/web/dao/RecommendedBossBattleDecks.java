package cfvbaibai.cardfantasy.web.dao;

import java.util.ArrayList;
import java.util.List;

public class RecommendedBossBattleDecks {
    private List<BossBattleStatEntry> decksWithBestMaxDamage;
    private List<BossBattleStatEntry> decksWithBestAvgDamage;
    public List<BossBattleStatEntry> getDecksWithBestMaxDamage() {
        return decksWithBestMaxDamage;
    }
    public void setDecksWithBestMaxDamage(List<BossBattleStatEntry> decksWithBestMaxDamage) {
        this.decksWithBestMaxDamage = new ArrayList<BossBattleStatEntry>(decksWithBestMaxDamage);
    }
    public List<BossBattleStatEntry> getDecksWithBestAvgDamage() {
        return decksWithBestAvgDamage;
    }
    public void setDecksWithBestAvgDamage(List<BossBattleStatEntry> decksWithBestAvgDamage) {
        this.decksWithBestAvgDamage = new ArrayList<BossBattleStatEntry>(decksWithBestAvgDamage);
    }
}
