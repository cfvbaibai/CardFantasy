package cfvbaibai.cardfantasy.web.dao;

import java.util.List;
import java.util.Map;


public interface PostMapper {
    List<List<?>> getThreads(PostRange range);
    /**
     * Get thread count.
     * @param parameters "count": OUTPUT the thread count.
     */
    void getThreadCount(Map<String, Object> parameters);
    int newPost(Post post);
    void newReply(Reply reply);
}
