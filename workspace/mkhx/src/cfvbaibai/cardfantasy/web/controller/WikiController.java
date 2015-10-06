package cfvbaibai.cardfantasy.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialStage;
import cfvbaibai.cardfantasy.web.beans.JsonHandler;
import cfvbaibai.cardfantasy.web.beans.Logger;
import cfvbaibai.cardfantasy.web.beans.OfficialStageInfo;
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
        mv.addObject("raceNames", officialStore.getRaceNames());
        mv.addObject("propertyNames", officialStore.getPropertyNames());
        mv.addObject("skillCategories", officialStore.getSkillCategories());
        List<OfficialStageInfo> stageInfos = new ArrayList<OfficialStageInfo>();
        for (OfficialStage stage : officialStore.stageStore.data) {
            stageInfos.add(new OfficialStageInfo(stage, officialStore));
        }
        mv.addObject("stageInfos", stageInfos);
        return mv;
    }
}