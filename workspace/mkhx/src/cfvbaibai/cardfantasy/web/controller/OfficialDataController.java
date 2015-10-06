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

import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialCard;
import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialRune;
import cfvbaibai.cardfantasy.officialdata.OfficialRuneProperty;
import cfvbaibai.cardfantasy.officialdata.OfficialSkill;
import cfvbaibai.cardfantasy.officialdata.OfficialSkillCategory;
import cfvbaibai.cardfantasy.officialdata.OfficialStage;
import cfvbaibai.cardfantasy.web.ErrorHelper;
import cfvbaibai.cardfantasy.web.beans.JsonHandler;
import cfvbaibai.cardfantasy.web.beans.Logger;
import cfvbaibai.cardfantasy.web.beans.OfficialCardInfo;
import cfvbaibai.cardfantasy.web.beans.OfficialRuneInfo;
import cfvbaibai.cardfantasy.web.beans.OfficialSkillInfo;
import cfvbaibai.cardfantasy.web.beans.OfficialStageInfo;
import cfvbaibai.cardfantasy.web.beans.SubCategory;
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

    @RequestMapping(value = "/Wiki/Runes/Stars/{star}")
    public ModelAndView queryRuneOfStars(HttpServletRequest request,
            @PathVariable("star") int star, HttpServletResponse response) throws IOException {
        this.logger.info("Getting runes of star: " + star);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view-rune-category");
        mv.addObject("category", star + "星符文");
        List<SubCategory<OfficialRune>> subCategories = new ArrayList<SubCategory<OfficialRune>>();
        try {
            List<OfficialRune> runes = this.officialStore.getRunesOfStar(star);
            for (String propertyName : this.officialStore.getPropertyNames()) {
                SubCategory<OfficialRune> subCategory = new SubCategory<OfficialRune>();
                subCategory.setName(propertyName);
                for (OfficialRune rune : runes) {
                    if (rune.getPropertyName().equals(propertyName)) {
                        subCategory.addItem(rune);
                    }
                }
                subCategories.add(subCategory);
            }
            mv.addObject("subCategories", subCategories);
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/Wiki/Runes/Properties/{propertyName}")
    public ModelAndView queryRuneOfProperties(HttpServletRequest request,
            @PathVariable("propertyName") String propertyName, HttpServletResponse response) throws IOException {
        this.logger.info("Getting runes of property: " + propertyName);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view-rune-category");
        mv.addObject("category", propertyName + "属性符文");
        List<SubCategory<OfficialRune>> subCategories = new ArrayList<SubCategory<OfficialRune>>();
        try {
            List<OfficialRune> runes = this.officialStore.getRunesOfPropertyName(propertyName);
            for (int i = 1; i <= 5; ++i) {
                SubCategory<OfficialRune> subCategory = new SubCategory<OfficialRune>();
                subCategory.setName(i + "星");
                for (OfficialRune rune : runes) {
                    if (rune.getColor() == i) {
                        subCategory.addItem(rune);
                    }
                }
                subCategories.add(subCategory);
            }
            mv.addObject("subCategories", subCategories);
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/Wiki/Cards/Stars/{star}")
    public ModelAndView queryCardOfStars(HttpServletRequest request,
            @PathVariable("star") int star, HttpServletResponse response) throws IOException {
        this.logger.info("Getting cards of star: " + star);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view-card-category");
        mv.addObject("category", star + "星卡牌");
        List<SubCategory<OfficialCard>> subCategories = new ArrayList<SubCategory<OfficialCard>>();
        try {
            List<OfficialCard> cards = this.officialStore.getCardOfStar(star);
            for (String raceName : this.officialStore.getRaceNames()) {
                SubCategory<OfficialCard> subCategory = new SubCategory<OfficialCard>();
                subCategory.setName(raceName);
                for (OfficialCard card : cards) {
                    if (card.getRaceName().equals(raceName) && !card.isMaterial()) {
                        subCategory.addItem(card);
                    }
                }
                subCategories.add(subCategory);
            }
            SubCategory<OfficialCard> materialSubCategory = new SubCategory<OfficialCard>();
            materialSubCategory.setName("素材");
            for (OfficialCard card : cards) {
                if (card.isMaterial()) {
                    materialSubCategory.addItem(card);
                }
            }
            subCategories.add(materialSubCategory);
            mv.addObject("subCategories", subCategories);
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/Wiki/Cards/Races/{race}")
    public ModelAndView queryCardOfRaces(HttpServletRequest request,
            @PathVariable("race") String raceName, HttpServletResponse response) throws IOException {
        this.logger.info("Getting cards of race: " + raceName);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view-card-category");
        mv.addObject("category", raceName + "卡牌");
        List<SubCategory<OfficialCard>> subCategories = new ArrayList<SubCategory<OfficialCard>>();
        try {
            List<OfficialCard> cards = this.officialStore.getCardsOfRaceName(raceName);
            for (int i = 1; i <= 5; ++i) {
                SubCategory<OfficialCard> subCategory = new SubCategory<OfficialCard>();
                subCategory.setName(i + "星");
                for (OfficialCard card : cards) {
                    if (card.getColor() == i && !card.isMaterial()) {
                        subCategory.addItem(card);
                    }
                }
                subCategories.add(subCategory);
            }
            SubCategory<OfficialCard> materialSubCategory = new SubCategory<OfficialCard>();
            materialSubCategory.setName("素材");
            for (OfficialCard card : cards) {
                if (card.isMaterial()) {
                    materialSubCategory.addItem(card);
                }
            }
            subCategories.add(materialSubCategory);
            mv.addObject("subCategories", subCategories);
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/Wiki/Skills/Categories/{category}")
    public ModelAndView querySkillsOfCategories(HttpServletRequest request,
            @PathVariable("category") int categoryId, HttpServletResponse response) throws IOException {
        this.logger.info("Getting skills of category: " + categoryId);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view-skill-category");
        try {
            String categoryName = OfficialSkillCategory.getCategoryNameFromId(categoryId);
            mv.addObject("category", categoryName + "技能");
            List<SubCategory<String>> subCategories = new ArrayList<SubCategory<String>>();
            SubCategory<String> materialSubCategory = new SubCategory<String>();
            materialSubCategory.setName("素材");
            List<String> skillTypes = this.officialStore.getSkillTypesByCategory(categoryId);
            for (String skillType : skillTypes) {
                String subCategoryName = "普通";
                if (skillType.startsWith("[降临]")) {
                    subCategoryName = "降临";
                } else if (skillType.startsWith("[死契]")) {
                    subCategoryName = "死契";
                } else if (skillType.startsWith("召唤")) {
                    subCategoryName = "召唤";
                } else if (skillType.startsWith("觉醒")) {
                    subCategoryName = "觉醒";
                }
                OfficialSkill[] skills = this.officialStore.getSkillsByType(skillType);
                if (skills.length > 0 && skills[0].isMaterial()) {
                    materialSubCategory.addItem(skillType);
                } else {
                    boolean subCategoryFound = false;
                    for (SubCategory<String> subCategory : subCategories) {
                        if (subCategory.getName().equals(subCategoryName)) {
                            subCategory.addItem(skillType);
                            subCategoryFound = true;
                            break;
                        }
                    }
                    if (!subCategoryFound) {
                        SubCategory<String> newSubCategory = new SubCategory<String>();
                        newSubCategory.setName(subCategoryName);
                        newSubCategory.addItem(skillType);
                        subCategories.add(newSubCategory);
                    }
                }
            }
            subCategories.add(materialSubCategory);
            mv.addObject("subCategories", subCategories);
        } catch (Exception e) {
            this.logger.error(e);
        }
        try {
            List<String> skillTypes = this.officialStore.getSkillTypesByCategory(categoryId);
            mv.addObject("skillTypes", skillTypes);
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/Wiki/Runes/{runeName}")
    public ModelAndView queryRune(HttpServletRequest request,
            @PathVariable("runeName") String runeName, HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView();
        try {
            this.logger.info("Getting official rune data: " + runeName);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "View Rune", runeName));
            if (runeName == null) {
                response.setStatus(404);
                return mv;
            }
            mv.setViewName("view-rune");
            OfficialRune rune = this.officialStore.getRuneByName(runeName);
            if (rune == null) {
                response.setStatus(404);
                return mv;
            }
            OfficialRuneInfo runeInfo = new OfficialRuneInfo(rune, officialStore);
            mv.addObject("runeInfo", runeInfo);
            mv.addObject("runeName", runeName);
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/Wiki/Cards/{cardName}")
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
            OfficialCardInfo cardInfo = OfficialCardInfo.build(card, officialStore.skillStore.data);
            mv.addObject("cardInfo", cardInfo);
            mv.addObject("cardName", cardName);
            mv.addObject("defaultLogoUrl", OfficialDataStore.DEFAULT_LOGO_110x110_URL);
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/Wiki/Skills/{skillName}")
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

    @RequestMapping(value = "/Wiki/Stages/{stageId}")
    public ModelAndView queryStage(HttpServletRequest request, @PathVariable("stageId") int stageId,
            HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView();
        OfficialStage stage = this.officialStore.getStageById(stageId);
        if (stage == null) {
            response.setStatus(404);
            return mv;
        }
        mv.setViewName("view-stage");
        OfficialStageInfo stageInfo = new OfficialStageInfo(stage, officialStore);
        mv.addObject("stageInfo", stageInfo);
        return mv;
    }

    @RequestMapping(value = "/Wiki/SkillTypes", headers = "Accept=application/json")
    public void querySkills(HttpServletRequest request, HttpServletResponse response,
            @RequestParam(value = "categories", required = false) String categoriesFilter,
            @RequestParam(value = "names", required = false) String namesFilter) throws IOException {
        if ("0".equals(categoriesFilter)) {
            categoriesFilter = null;
        }
        categoriesFilter = normalizeCommaDelimitedFilter(categoriesFilter);
        namesFilter = normalizeCommaDelimitedFilter(namesFilter);
        String[] desiredNames = null;
        if (namesFilter != null) {
            desiredNames = namesFilter.split(",");
        }
        List<String> result = new ArrayList<String>();
        for (OfficialSkill skill : officialStore.skillStore.data.Skills) {
            if (categoriesFilter != null && !categoriesFilter.contains("," + skill.getCategory() + ",")) {
                continue;
            }
            if (desiredNames != null) {
                boolean nameFound = false;
                for (String desiredName : desiredNames) {
                    if (skill.getName().contains(desiredName)) {
                        nameFound = true;
                        break;
                    }
                }
                if (!nameFound) {
                    continue;
                }
            }
            String skillType = officialStore.getSkillTypeFromName(skill.getName());
            if (!result.contains(skillType)) {
                result.add(skillType);
            }
        }
        response.setContentType("application/json");
        response.getWriter().println(jsonHandler.toJson(result));
    }

    @RequestMapping(value = "/Wiki/Cards", headers = "Accept=application/json")
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
            OfficialCardInfo cardInfo = OfficialCardInfo.build(card, officialStore.skillStore.data);
            if (skillTypeFilter != null && !skillTypeFilter.equals("")) {
                boolean skillDesired = false;
                for (String desiredSkillType : desiredSkillTypes) {
                    if (cardInfo.getSkill1() != null && cardInfo.getSkill1().Name.contains(desiredSkillType) ||
                        cardInfo.getSkill2() != null && cardInfo.getSkill2().Name.contains(desiredSkillType) ||
                        cardInfo.getSkill3() != null && cardInfo.getSkill3().Name.contains(desiredSkillType) ||
                        cardInfo.getSkill4() != null && cardInfo.getSkill4().Name.contains(desiredSkillType) ||
                        cardInfo.getSkill5() != null && cardInfo.getSkill5().Name.contains(desiredSkillType)
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