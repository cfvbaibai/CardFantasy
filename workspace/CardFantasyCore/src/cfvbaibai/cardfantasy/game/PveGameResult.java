package cfvbaibai.cardfantasy.game;

public enum PveGameResult {
    UNKNOWN("战斗出错"),
    LOSE("失败，过关失败"),
    BASIC_WIN("胜利，过关条件未符合"),
    ADVANCED_WIN("胜利，过关条件符合"), 
    TIMEOUT("战斗超时");
    
    private String description;
    
    PveGameResult(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
}
