package cfvbaibai.cardfantasy.web.dao;

import java.util.ArrayList;
import java.util.List;

public class Thread {
    private Post post;
    private List <Reply> replies = new ArrayList <Reply>();
    public Post getPost() {
        return post;
    }
    public void setPost(Post post) {
        this.post = post;
    }
    public List<Reply> getReplies() {
        return replies;
    }
    public void addReply(Reply reply) {
        this.replies.add(reply);
    }
}
