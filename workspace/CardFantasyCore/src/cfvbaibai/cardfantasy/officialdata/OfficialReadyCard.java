package cfvbaibai.cardfantasy.officialdata;

public class OfficialReadyCard {
    private OfficialCard card;
    private int level;
    private OfficialSkill extraSkill;
    public OfficialReadyCard(OfficialCard card) {
        this(card, 10, null);
    }

    public OfficialReadyCard(OfficialCard card, int level, OfficialSkill extraSkill) {
        super();
        this.card = card;
        this.level = level;
        this.extraSkill = extraSkill;
    }

    public OfficialCard getCard() {
        return card;
    }
    public void setCard(OfficialCard card) {
        this.card = card;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public OfficialSkill getExtraSkill() {
        return extraSkill;
    }
    public void setExtraSkill(OfficialSkill extraSkill) {
        this.extraSkill = extraSkill;
    }
    public String getCardName() {
        return this.card.getCardName();
    }
}
