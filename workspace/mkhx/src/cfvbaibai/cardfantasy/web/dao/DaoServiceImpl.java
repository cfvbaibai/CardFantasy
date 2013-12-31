package cfvbaibai.cardfantasy.web.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;


public class DaoServiceImpl implements CommunicationService {

    @Autowired
    private PostMapper postMapper;
    
    public DaoServiceImpl() {
    }
    
    @SuppressWarnings("unchecked")
    public List<Thread> getThreads(int begin, int end) {
        PostRange range = new PostRange(begin, end);
        List<?> rawThreads = this.postMapper.getThreads(range);
        List<Post> posts = (List<Post>)rawThreads.get(0);
        List<Reply> replies = (List<Reply>)rawThreads.get(1);
        List<Thread> threads = new ArrayList<Thread>();
        for (Post post : posts) {
            Thread thread = new Thread();
            thread.setPost(post);
            threads.add(thread);
        }
        for (Reply reply : replies) {
            for (Thread thread : threads) {
                if (thread.getPost().getId() == reply.getReplyTo()) {
                    thread.addReply(reply);
                }
            }
        }
        return threads;
    }
    
    public int getThreadCount() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        this.postMapper.getThreadCount(parameters);
        return (int)parameters.get("count");
    }
    
    public int newPost(Post post) {
        postMapper.newPost(post);
        return post.getId();
    }
    
    public void newReply(Reply reply) {
        postMapper.newReply(reply);
    }
}
