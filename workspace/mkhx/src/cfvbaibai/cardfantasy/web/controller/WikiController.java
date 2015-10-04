package cfvbaibai.cardfantasy.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;
import cfvbaibai.cardfantasy.web.beans.JsonHandler;
import cfvbaibai.cardfantasy.web.beans.Logger;
import cfvbaibai.cardfantasy.web.beans.UserActionRecorder;

@Controller
public class WikiController {
    @Autowired
    private UserActionRecorder userActionRecorder;
    @Autowired
    private Logger logger;
    @Autowired
    private OfficialDataStore officialStore;
    @Autowired
    private CardDataStore myStore;
    @Autowired
    private JsonHandler jsonHandler;

    @RequestMapping(value = "/wiki")
    public ModelAndView wiki(HttpServletRequest request) {
        return wiki();
    }
    private ModelAndView wiki() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("wiki");
        mv.addObject("officialCardData", officialStore.cardStore.data.Cards);
        mv.addObject("skillCategories", officialStore.getSkillCategories());
        return mv;
    }
}