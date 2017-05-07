package cfvbaibai.cardfantasy.web.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cfvbaibai.cardfantasy.game.LilithDataStore;
import cfvbaibai.cardfantasy.game.LilithStartupInfo;
import cfvbaibai.cardfantasy.web.QuestionStore;
import cfvbaibai.cardfantasy.web.beans.UserAction;
import cfvbaibai.cardfantasy.web.beans.UserActionRecorder;

@Controller
public class HomeController {
    @Autowired
    private UserActionRecorder userActionRecorder;

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
        mv.addObject("theme", "a");
        List<LilithStartupInfo> allLilithData = lilithDataStore.getAll();
        mv.addObject("lilithDatas", allLilithData);
        this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "Visit Home", ""));
        return mv;
    }

    @RequestMapping(value = "/Stat/BossBattle/{bossName}/view")
    public ModelAndView statRecord(HttpServletRequest request, @PathVariable("bossName") String bossName) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("stat-recorder");
        return mv;
    }
}
