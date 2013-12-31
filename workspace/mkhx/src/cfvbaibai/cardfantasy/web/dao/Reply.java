package cfvbaibai.cardfantasy.web.dao;

import java.util.Date;

public class Reply {
    private int replyTo;
    private String senderId;
    private String content;
    private Date created;

    public int getReplyTo() {
        return this.replyTo;
    }
    public void setReplyTo(int replyTo) {
        this.replyTo = replyTo;
    }
    public String getSenderId() {
        return senderId;
    }
    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    
    public Reply() {
    }
    
    public Reply(int replyTo, String senderId, String content) {
        this.replyTo = replyTo;
        this.senderId = senderId;
        this.content = content;
    }
}
