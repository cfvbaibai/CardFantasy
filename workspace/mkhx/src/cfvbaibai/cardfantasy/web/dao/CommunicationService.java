package cfvbaibai.cardfantasy.web.dao;

import java.util.List;

public interface CommunicationService {
    List<Thread> getThreads(int begin, int end);
    int getThreadCount();
    int newPost(Post post);
    void newReply(Reply reply);
}
