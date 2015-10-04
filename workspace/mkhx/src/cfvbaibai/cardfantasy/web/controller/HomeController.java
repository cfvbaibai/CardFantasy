package cfvbaibai.cardfantasy.web.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cfvbaibai.cardfantasy.game.LilithDataStore;
import cfvbaibai.cardfantasy.game.LilithStartupInfo;
import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;
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

    @Autowired
    private OfficialDataStore officialDataStore;

    @RequestMapping(value = "/")
    public ModelAndView home(HttpServletRequest request
            //, HttpServletResponse response // Spring MVC conflicts with AOP?
    ) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("home");
        mv.addObject("isNewSession", request.getSession().isNew());
        mv.addObject("questions", questionStore.pickRandom());
        List<LilithStartupInfo> allLilithData = lilithDataStore.getAll();
        Collections.sort(allLilithData, new LilithDataComparator());
        mv.addObject("lilithDatas", allLilithData);
        this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Visit Home", ""));
        return mv;
    }
    
    private class LilithDataComparator implements Comparator<LilithStartupInfo> {
        @Override
        public int compare(LilithStartupInfo a, LilithStartupInfo b) {
            return a.getCardBuffs().get(0).getLevel() - b.getCardBuffs().get(0).getLevel();
        }
    }
}
