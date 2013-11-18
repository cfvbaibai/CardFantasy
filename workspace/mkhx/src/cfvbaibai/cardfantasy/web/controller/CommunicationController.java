package cfvbaibai.cardfantasy.web.controller;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cfvbaibai.cardfantasy.web.Utils;
import cfvbaibai.cardfantasy.web.beans.JsonHandler;
import cfvbaibai.cardfantasy.web.beans.Logger;
import cfvbaibai.cardfantasy.web.beans.UserAction;
import cfvbaibai.cardfantasy.web.beans.UserActionRecorder;

@Controller
public class CommunicationController {

    @Autowired
    private JsonHandler jsonHandler;
    
    @Autowired
    private UserActionRecorder userActionRecorder;
    
    @Autowired
    private Logger logger;
    
    @Autowired
    @Qualifier("communication-logger")
    private java.util.logging.Logger julLogger;

    @RequestMapping(value = "/SendFeedback")
    public void monitor(HttpServletRequest request, HttpServletResponse response,
            @RequestParam("sender") String sender,
            @RequestParam("feedback") String feedback) {
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        String remoteAddress = request.getRemoteAddr();
        String trace = "[FEEDBACK] IP: " + remoteAddress + ", SENDER: " + sender + ", CONTENT: " + feedback;
        julLogger.info(trace);
        logger.info(trace);
        userActionRecorder.addAction(new UserAction(new Date(), remoteAddress, "Send Feedback", sender + ": " + feedback));
        try {
            response.getWriter().println("[" + Utils.getCurrentDateTime() + "] 感谢提供意见和建议！");
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
