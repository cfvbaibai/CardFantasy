package cfvbaibai.cardfantasy.web.beans;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.officialdata.OfficialCard;

public class SubCategory {
    private String name;
    private List<OfficialCard> cards = new ArrayList<OfficialCard>();
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<OfficialCard> getCards() {
        return this.cards;
    }
    public void addCard(OfficialCard card) {
        this.cards.add(card);
    }
}
