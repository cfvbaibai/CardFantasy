package cfvbaibai.cardfantasy.web.beans;

import java.util.Date;

public class UserAction {
    private Date timeStamp;
    private String userId;
    private String actionType;
    private String actionContent;
    
    public UserAction(Date timeStamp, String userId, String actionType, String actionContent) {
        this.timeStamp = timeStamp;
        this.userId = userId;
        this.actionType = actionType;
        this.actionContent = actionContent;
    }
    
    public String getUserId() {
        return this.userId;
    }
    public String getActionType() {
        return this.actionType;
    }
    public String getActionContent() {
        return this.actionContent;
    }
    public Date getTimeStamp() {
        return this.timeStamp;
    }
}