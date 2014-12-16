package cfvbaibai.cardfantasy.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cfvbaibai.cardfantasy.web.Utils;
import cfvbaibai.cardfantasy.web.beans.GlobalConfig;
import cfvbaibai.cardfantasy.web.beans.JsonHandler;
import cfvbaibai.cardfantasy.web.beans.Logger;
import cfvbaibai.cardfantasy.web.beans.UserAction;
import cfvbaibai.cardfantasy.web.beans.UserActionRecorder;
import cfvbaibai.cardfantasy.web.dao.CommunicationService;
import cfvbaibai.cardfantasy.web.dao.Post;
import cfvbaibai.cardfantasy.web.dao.Reply;
import cfvbaibai.cardfantasy.web.dao.Thread;

//@Controller
public class CommunicationController {

    //@Autowired
    private GlobalConfig config;
    
    //@Autowired
    private JsonHandler jsonHandler;
    
    //@Autowired
    private UserActionRecorder userActionRecorder;
    
    //@Autowired
    private Logger logger;
    
    //@Autowired
    //@Qualifier("communication")
    //private java.util.logging.Logger julLogger;
    
    //@Autowired
    private CommunicationService service;

    @RequestMapping(value = "/SendFeedback")
    public void sendFeedBack(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("sender") String sender,
            @RequestParam("feedback") String feedback) {
        String remoteAddress = request.getRemoteAddr();
        String trace = "[FEEDBACK] IP: " + remoteAddress + ", SENDER: " + sender + ", CONTENT: " + feedback;
        //julLogger.info(trace);
        logger.info(trace);
        userActionRecorder.addAction(new UserAction(new Date(), remoteAddress, "Send Feedback", sender + ": " + feedback));
        service.newPost(Post.createNew(sender, feedback));
        try {
            response.getWriter().println("[" + Utils.getCurrentDateTime() + "] 感谢提供意见和建议！");
        } catch (IOException e) {
            logger.error(e);
        }
    }
    
    @RequestMapping(value = "/SendReply")
    public void sendReply(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("replyTo") int replyTo,
            @RequestParam("replier") String replier,
            @RequestParam("content") String content) {
        service.newReply(new Reply(replyTo, replier, content));
    }

    @RequestMapping(value = "/GetThreads", headers = "Accept=application/json")
    public void getThreads(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("pageNum") int pageNum) throws IOException {
        if (pageNum <= 0) {
            pageNum = 1;
        }
        PrintWriter writer = response.getWriter();
        response.setContentType("application/json");
        int begin = (pageNum - 1) * config.getThreadsPerPage();
        int end = begin + config.getThreadsPerPage();
        Map<String, Object> result = new HashMap<String, Object>();
        List<Thread> threads = service.getThreads(begin, end);
        int threadCount = service.getThreadCount();
        int threadPageCount = threadCount == 0 ? 0 : ((threadCount - 1) / config.getThreadsPerPage() + 1);
        result.put("pageCount", threadPageCount);
        result.put("threads", threads);
        writer.print(jsonHandler.toJson(result));
    }
}
