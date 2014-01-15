package cfvbaibai.cardfantasy.web.beans;


public class TestRPResult {
    private String cardName;
    private String cardId;
    private String remark;
    private String testDate;
    private int rpIndex;
    
    public String getCardId() {
        return cardId;
    }
    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
    public String getCardName() {
        return cardName;
    }
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public int getRPIndex() {
        return rpIndex;
    }
    public void setRPIndex(int rpIndex) {
        this.rpIndex = rpIndex;
    }
    public String getTestDate() {
        return testDate;
    }
    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }
}
