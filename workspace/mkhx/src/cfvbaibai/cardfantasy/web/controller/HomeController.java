package cfvbaibai.cardfantasy.web.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cfvbaibai.cardfantasy.game.LilithDataStore;
import cfvbaibai.cardfantasy.web.QuestionStore;
import cfvbaibai.cardfantasy.web.beans.Logger;
import cfvbaibai.cardfantasy.web.beans.UserAction;
import cfvbaibai.cardfantasy.web.beans.UserActionRecorder;

@Controller
public class HomeController {
    @Autowired
    private UserActionRecorder userActionRecorder;

    //@Autowired
    //private AtomicInteger activeSessionCount;

    @Autowired(required = true)
    private Logger logger;

    @Autowired(required = true)
    private QuestionStore questionStore;
    
    @Autowired
    private LilithDataStore lilithDataStore;

    @RequestMapping(value = "/")
    public ModelAndView home(HttpServletRequest request
            //, HttpServletResponse response // Spring MVC conflicts with AOP?
    ) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home");
        mv.addObject("isNewSession", request.getSession().isNew());
        mv.addObject("questions", questionStore.pickRandom());
        mv.addObject("lilithDatas", lilithDataStore.getAll());
        //int activeSessionCountValue = activeSessionCount.intValue();
        //mv.addObject("activeSessionCount", activeSessionCountValue);
        //logger.info("Active Session Count: " + activeSessionCountValue);
        this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Visit Home", ""));
        return mv;
    }
}
