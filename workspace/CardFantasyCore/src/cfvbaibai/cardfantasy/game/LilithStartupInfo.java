package cfvbaibai.cardfantasy.game;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.data.Card;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.data.Skill;

public class LilithStartupInfo {
    private DeckStartupInfo dsi;
    private String bossId;
    private List<Skill> cardBuffs;
    public LilithStartupInfo(String bossId, int bossHP, String deckDescs, List<Skill> cardBuffs) {
        this.bossId = bossId;
        this.dsi = DeckBuilder.multiBuild(deckDescs);
        if (bossHP > 0) {
            for (Card card : dsi.getCards()) {
                if (card.getRace() == Race.BOSS) {
                    card.setOverrideHP(bossHP);
                }
            }
        }
        if (cardBuffs == null) {
            this.cardBuffs = new ArrayList<Skill>();
        } else {
            this.cardBuffs = new ArrayList<Skill>(cardBuffs);
        }
    }
    public List<Skill> getCardBuffs() {
        return new ArrayList<Skill>(this.cardBuffs);
    }
    public DeckStartupInfo getDeckStartupInfo() {
        return this.dsi;
    }
    public String getBossId() {
        return this.bossId;
    }
}