package cfvbaibai.cardfantasy.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialCard;
import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialSkill;
import cfvbaibai.cardfantasy.web.ErrorHelper;
import cfvbaibai.cardfantasy.web.beans.JsonHandler;
import cfvbaibai.cardfantasy.web.beans.Logger;
import cfvbaibai.cardfantasy.web.beans.OfficialCardInfo;
import cfvbaibai.cardfantasy.web.beans.OfficialSkillInfo;
import cfvbaibai.cardfantasy.web.beans.UserAction;
import cfvbaibai.cardfantasy.web.beans.UserActionRecorder;

@Controller
public class OfficialDataController {
    @Autowired
    private UserActionRecorder userActionRecorder;
    @Autowired
    private Logger logger;
    @Autowired
    private ErrorHelper errorHelper;
    @Autowired
    private OfficialDataStore officialStore;
    @Autowired
    private CardDataStore myStore;
    @Autowired
    private JsonHandler jsonHandler;

    private static String normalizeCommaDelimitedFilter(String filterRawText) {
        String filterText = filterRawText;
        if (filterText != null) {
            if (!filterText.startsWith(",")) {
                filterText = "," + filterText;
            }
            if (!filterText.endsWith(",")) {
                filterText += ",";
            }
        }
        return filterText;
    }
    
    @RequestMapping(value = "/OfficialData/Cards/{cardName}")
    public ModelAndView queryCard(HttpServletRequest request,
            @PathVariable("cardName") String cardName, HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView();
        try {
            this.logger.info("Getting official card data: " + cardName);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "View Card", cardName));
            if (cardName == null) {
                response.setStatus(404);
                return mv;
            }
            mv.setViewName("view-card");
            OfficialCard card = this.officialStore.getCardByName(cardName);
            if (card == null) {
                response.setStatus(404);
                return mv;
            }
            CardData myCardData = this.myStore.getCard(cardName);
            if (myCardData != null) {
                mv.addObject("internalId", myCardData.getId());
            }
            OfficialCardInfo cardInfo = OfficialCardInfo.build(card, myStore, officialStore.skillStore.data);
            mv.addObject("cardInfo", cardInfo);
            mv.addObject("cardName", cardName);
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/OfficialData/Skills/{skillName}")
    public ModelAndView querySkill(HttpServletRequest request, @PathVariable("skillName") String skillName,
            HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView();
        try {
            this.logger.info("Getting official skill data: " + skillName);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "View Skill", skillName));
            if (skillName == null) {
                response.setStatus(404);
                return mv;
            }
            mv.setViewName("view-skill");
            String skillType = this.officialStore.getSkillTypeFromName(skillName);
            if (skillType == null) {
                response.setStatus(404);
                return mv;
            }
            OfficialSkill[] skills = this.officialStore.getSkillsByType(skillType);
            if (skills.length == 0) {
                response.setStatus(404);
                return mv;
            }
            OfficialSkillInfo[] skillInfos = new OfficialSkillInfo[skills.length];
            for (int i = 0; i < skills.length; ++i) {
                OfficialSkillInfo skillInfo = OfficialSkillInfo.build(skills[i], officialStore);
                skillInfos[i] = skillInfo;
            }
            
            mv.addObject("skillInfos", skillInfos);
            mv.addObject("skillType", skillType);
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/OfficialData/Skills", headers = "Accept=application/json")
    public void querySkills(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<OfficialSkill> result = new ArrayList<OfficialSkill>();
        result.addAll(officialStore.skillStore.data.Skills);
        response.setContentType("application/json");
        response.getWriter().println(jsonHandler.toJson(result));
    }

    @RequestMapping(value = "/OfficialData/Cards", headers = "Accept=application/json")
    public void queryCards(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "stars", required = false) String starFilter,
            @RequestParam(value = "races", required = false) String raceFilter,
            @RequestParam(value = "skillTypes", required = false) String skillTypeFilter,
            @RequestParam(value = "names", required = false) String cardNameFilter) throws IOException {
        if ("0".equals(starFilter)) {
            starFilter = null;
        }
        if ("0".equals(raceFilter)) {
            raceFilter = null;
        }
        starFilter = normalizeCommaDelimitedFilter(starFilter);
        raceFilter = normalizeCommaDelimitedFilter(raceFilter);
        cardNameFilter = normalizeCommaDelimitedFilter(cardNameFilter);
        String[] desiredSkillTypes = null;
        if (skillTypeFilter != null) {
            desiredSkillTypes = skillTypeFilter.split(",");
        }
        List<OfficialCardInfo> result = new ArrayList<OfficialCardInfo>();
        for (OfficialCard card : officialStore.cardStore.data.Cards) {
            if (cardNameFilter != null && !cardNameFilter.contains("," + card.getCardName() + ",")) {
                continue;
            }
            if (starFilter != null && !starFilter.contains("," + card.Color + ",")) {
                continue;
            }
            if (raceFilter != null && !raceFilter.contains("," + card.Race + ",")) {
                continue;
            }
            OfficialCardInfo cardInfo = OfficialCardInfo.build(card, myStore, officialStore.skillStore.data);
            if (skillTypeFilter != null && !skillTypeFilter.equals("")) {
                boolean skillDesired = false;
                for (String desiredSkillType : desiredSkillTypes) {
                    if (cardInfo.skill1 != null && cardInfo.skill1.Name.contains(desiredSkillType) ||
                        cardInfo.skill2 != null && cardInfo.skill2.Name.contains(desiredSkillType) ||
                        cardInfo.skill3 != null && cardInfo.skill3.Name.contains(desiredSkillType) ||
                        cardInfo.skill4 != null && cardInfo.skill4.Name.contains(desiredSkillType) ||
                        cardInfo.skill5 != null && cardInfo.skill5.Name.contains(desiredSkillType)
                        ) {
                        skillDesired = true;
                        break;
                    }
                }
                if (!skillDesired) {
                    continue;
                }
            }
            result.add(cardInfo);
        }
        response.setContentType("application/json");
        response.getWriter().println(jsonHandler.toJson(result));
    }
}