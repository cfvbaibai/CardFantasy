package cfvbaibai.cardfantasy.web.beans;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.officialdata.OfficialReadyCard;
import cfvbaibai.cardfantasy.officialdata.OfficialReadyRune;

public class OfficialStageDeckInfo {
    private List<OfficialReadyCard> readyCards;
    private List<OfficialReadyRune> readyRunes;
    public OfficialStageDeckInfo() {
        this.readyCards = new ArrayList<OfficialReadyCard>();
        this.readyRunes = new ArrayList<OfficialReadyRune>();
    }
    public void addCard(OfficialReadyCard card) {
        this.readyCards.add(card);
    }
    public void addRune(OfficialReadyRune rune) {
        this.readyRunes.add(rune);
    }
    public List<OfficialReadyCard> getCards() {
        return this.readyCards;
    }
    public List<OfficialReadyRune> getRunes() {
        return this.readyRunes;
    }
}
