package cfvbaibai.cardfantasy.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialCard;
import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;
import cfvbaibai.cardfantasy.web.ErrorHelper;
import cfvbaibai.cardfantasy.web.beans.JsonHandler;
import cfvbaibai.cardfantasy.web.beans.Logger;
import cfvbaibai.cardfantasy.web.beans.OfficialCardInfo;

@Controller
public class OfficialDataController {
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
            if (cardNameFilter != null && !cardNameFilter.contains("," + card.CardName + ",")) {
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
                    if (cardInfo.skill1 != null && cardInfo.skill1.Name.startsWith(desiredSkillType) ||
                        cardInfo.skill2 != null && cardInfo.skill2.Name.startsWith(desiredSkillType) ||
                        cardInfo.skill3 != null && cardInfo.skill3.Name.startsWith(desiredSkillType) ||
                        cardInfo.skill4 != null && cardInfo.skill4.Name.startsWith(desiredSkillType) ||
                        cardInfo.skill5 != null && cardInfo.skill5.Name.startsWith(desiredSkillType)
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