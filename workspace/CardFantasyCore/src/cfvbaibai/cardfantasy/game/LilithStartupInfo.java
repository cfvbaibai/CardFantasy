package cfvbaibai.cardfantasy.game;


public class LilithStartupInfo {
    private String deckDescsText;
    private String bossId;
    private int cardAtBuff;
    private int cardHpBuff;
    public LilithStartupInfo(String bossId, String deckDescsText, int cardAtBuff, int cardHpBuff) {
        this.bossId = bossId;
        this.deckDescsText = deckDescsText;
        this.cardAtBuff = cardAtBuff;
        this.cardHpBuff = cardHpBuff;
    }
    public String getBossId() {
        return this.bossId;
    }
    public String[] getDeckDescs(boolean withGuards) {
        String[] parts = this.deckDescsText.split(",");
        if (withGuards) {
            return parts;
        }
        if (parts.length == 0) {
            return new String[0];
        }
        return new String[] { parts[0] };
    }
    public int getCardAtBuff() {
        return cardAtBuff;
    }
    public int getCardHpBuff() {
        return cardHpBuff;
    }
    public String getDeckDescsText(boolean withGuards) {
        String[] deckDescs = this.getDeckDescs(withGuards);
        return String.join(",", deckDescs);
    }
}