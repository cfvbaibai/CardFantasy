package cfvbaibai.cardfantasy.web.dao;

import java.util.Date;

public class Post {
    private int id;
    private String senderId;
    private String content;
    private Date created;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    
    private Post() {
        
    }
    
    public static Post createNew(String senderId, String content) {
        Post post = new Post();
        post.setSenderId(senderId);
        post.setContent(content);
        return post;
    }
}
