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
import cfvbaibai.cardfantasy.officialdata.OfficialSkill;
import cfvbaibai.cardfantasy.officialdata.OfficialSkillCategory;
import cfvbaibai.cardfantasy.officialdata.OfficialStage;
import cfvbaibai.cardfantasy.web.ErrorHelper;
import cfvbaibai.cardfantasy.web.beans.JsonHandler;
import cfvbaibai.cardfantasy.web.beans.Logger;
import cfvbaibai.cardfantasy.web.beans.OfficialCardInfo;
import cfvbaibai.cardfantasy.web.beans.OfficialHeroSetting;
import cfvbaibai.cardfantasy.web.beans.OfficialRuneInfo;
import cfvbaibai.cardfantasy.web.beans.OfficialSkillInfo;
import cfvbaibai.cardfantasy.web.beans.OfficialSkillTypeInfo;
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

    @RequestMapping(value = "/Wiki/index.shtml")
    public ModelAndView wiki(HttpServletRequest request) {
        return wiki();
    }

    private ModelAndView wiki() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("wiki");
        mv.addObject("officialCardData", officialStore.cardStore.data.Cards);
        mv.addObject("races", officialStore.getRaces());
        mv.addObject("properties", officialStore.getProperties());
        mv.addObject("skillCategories", officialStore.getSkillCategories());
        List<OfficialStageInfo> stageInfos = new ArrayList<OfficialStageInfo>();
        for (OfficialStage stage : officialStore.stageStore.data) {
            stageInfos.add(new OfficialStageInfo(stage, officialStore));
        }
        mv.addObject("stageInfos", stageInfos);
        return mv;
    }

    @RequestMapping(value = "/Wiki/GenCardPortrait")
    public ModelAndView genCardPortrait(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("gen-card-portrait");
        return mv;
    }
    
    @RequestMapping(value = "/Wiki/Runes/Stars/{star}.shtml")
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

    @RequestMapping(value = "/Wiki/Runes/Properties/{propertyId}.shtml")
    public ModelAndView queryRuneOfProperties(HttpServletRequest request,
            @PathVariable("propertyId") int propertyId, HttpServletResponse response) throws IOException {
        this.logger.info("Getting runes of property: " + propertyId);
        ModelAndView mv = new ModelAndView();
        String propertyName = this.officialStore.getPropertyNameById(propertyId);
        if (propertyName == null) {
            response.setStatus(404);
            return mv;
        }
        this.logger.info("Rune property name: " + propertyName);
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
            mv.addObject("propertyName", propertyName);
            mv.addObject("subCategories", subCategories);
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/Wiki/Cards/Stars/{star}.shtml")
    public ModelAndView queryCardOfStars(HttpServletRequest request,
            @PathVariable("star") int star, HttpServletResponse response) throws IOException {
        this.logger.info("Getting cards of star: " + star);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view-card-category");
        mv.addObject("category", star + "星卡牌");
        mv.addObject("star", star);
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

    @RequestMapping(value = "/Wiki/Cards/Races/{raceId}.shtml")
    public ModelAndView queryCardOfRaces(HttpServletRequest request,
            @PathVariable("raceId") int raceId, HttpServletResponse response) throws IOException {
        String raceName = this.officialStore.getRaceNameById(raceId);
        this.logger.info("Getting cards of race: " + raceId + "-" + raceName);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view-card-category");
        mv.addObject("category", raceName + "卡牌");
        mv.addObject("raceName", raceName);
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

    @RequestMapping(value = "/Wiki/Skills/Categories/{categoryId}.shtml")
    public ModelAndView querySkillsOfCategories(HttpServletRequest request,
            @PathVariable("categoryId") int categoryId, HttpServletResponse response) throws IOException {
        this.logger.info("Getting skills of category: " + categoryId);
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view-skill-category");
        try {
            String categoryName = OfficialSkillCategory.getCategoryNameFromId(categoryId);
            mv.addObject("category", categoryName + "技能");
            mv.addObject("categoryId", categoryId);
            List<SubCategory<OfficialSkillTypeInfo>> subCategories = new ArrayList<>();
            SubCategory<OfficialSkillTypeInfo> materialSubCategory = new SubCategory<>();
            materialSubCategory.setName("素材");
            List<String> skillTypes = this.officialStore.getSkillTypeNamesByCategoryId(categoryId);
            for (String skillType : skillTypes) {
                String subCategoryName = "普通";
                if (skillType.startsWith("[降临]")) {
                    subCategoryName = "降临";
                } else if (skillType.startsWith("[死契]")) {
                    subCategoryName = "死契";
                } else if (skillType.startsWith("[先机]")) {
                    subCategoryName = "先机";
                } else if (skillType.startsWith("[遗志]")) {
                    subCategoryName = "遗志";
                } else if (skillType.startsWith("召唤")) {
                    subCategoryName = "召唤";
                } else if (skillType.startsWith("觉醒")) {
                    subCategoryName = "觉醒";
                }
                OfficialSkill[] skills = this.officialStore.getSkillsByType(skillType);
                if (skills == null || skills.length == 0) {
                    continue;
                }
                OfficialSkillTypeInfo skillTypeInfo = new OfficialSkillTypeInfo(skillType, skills[0].getId()); 
                if (skills[0].isMaterial()) {
                    materialSubCategory.addItem(skillTypeInfo);
                } else {
                    boolean subCategoryFound = false;
                    for (SubCategory<OfficialSkillTypeInfo> subCategory : subCategories) {
                        if (subCategory.getName().equals(subCategoryName)) {
                            subCategory.addItem(skillTypeInfo);
                            subCategoryFound = true;
                            break;
                        }
                    }
                    if (!subCategoryFound) {
                        SubCategory<OfficialSkillTypeInfo> newSubCategory = new SubCategory<>();
                        newSubCategory.setName(subCategoryName);
                        newSubCategory.addItem(skillTypeInfo);
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
            List<String> skillTypes = this.officialStore.getSkillTypeNamesByCategoryId(categoryId);
            mv.addObject("skillTypes", skillTypes);
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/Wiki/Runes/{runeName}")
    public ModelAndView queryRuneByName(HttpServletRequest request,
            @PathVariable("runeName") String runeName, HttpServletResponse response) throws IOException {
        this.logger.info("Getting official rune data: " + runeName);
        OfficialRune rune = this.officialStore.getRuneByName(runeName);
        return queryRune(rune, request, response);
    }

    @RequestMapping(value = "/Wiki/Runes/{runeId}.shtml")
    public ModelAndView queryRuneById(HttpServletRequest request,
            @PathVariable("runeId") int runeId, HttpServletResponse response) throws IOException {
        this.logger.info("Getting official rune data: " + runeId);
        OfficialRune rune = this.officialStore.getRuneById(runeId);
        return queryRune(rune, request, response);
    }

    private ModelAndView queryRune(OfficialRune rune, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView();
        try {
            if (rune == null) {
                response.setStatus(404);
                return mv;
            }
            this.logger.info("Rune name: " + rune.getRuneName());
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "View Rune", rune.getRuneName()));
            mv.setViewName("view-rune");
            OfficialRuneInfo runeInfo = new OfficialRuneInfo(rune, officialStore);
            mv.addObject("runeInfo", runeInfo);
            mv.addObject("runeName", rune.getRuneName());
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/Wiki/Cards/{cardName}")
    public ModelAndView queryCard(HttpServletRequest request,
            @PathVariable("cardName") String cardName, HttpServletResponse response) throws IOException {
        this.logger.info("Getting official card data: " + cardName);
        OfficialCard card = this.officialStore.getCardByName(cardName);
        return queryCard(card, request, response);
    }

    @RequestMapping(value = "/Wiki/Cards/{cardId}.shtml")
    public ModelAndView queryCardById(HttpServletRequest request,
            @PathVariable("cardId") int cardId, HttpServletResponse response) throws IOException {
        this.logger.info("Getting official card data: " + cardId);
        OfficialCard card = this.officialStore.getCardById(cardId);
        return queryCard(card, request, response);
    }

    private ModelAndView queryCard(OfficialCard card, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView();
        try {
            if (card == null) {
                response.setStatus(404);
                return mv;
            }
            mv.setViewName("view-card");
            this.logger.info("Card name = " + card.getCardName());
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "View Card", card.getCardName()));
            if (card.getCardName() == null) {
                response.setStatus(404);
                return mv;
            }
            OfficialCardInfo cardInfo = OfficialCardInfo.build(card, officialStore.skillStore.data);
            mv.addObject("cardInfo", cardInfo);
            mv.addObject("cardName", card.getCardName());
            mv.addObject("defaultLogoUrl", OfficialDataStore.DEFAULT_LOGO_110x110_URL);
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/Wiki/Skills/{skillId}.shtml")
    public ModelAndView querySkillById(HttpServletRequest request, @PathVariable("skillId") String skillId,
            HttpServletResponse response) throws IOException {
        this.logger.info("Getting official skill data: " + skillId);
        OfficialSkill skill = this.officialStore.getSkillById(skillId);
        return querySkill(skill, request, response);
    }

    @RequestMapping(value = "/Wiki/Skills/{skillType}")
    public ModelAndView querySkillByType(HttpServletRequest request, @PathVariable("skillType") String skillType,
            HttpServletResponse response) throws IOException {
        this.logger.info("Getting official skill data: " + skillType);
        OfficialSkill[] skills = this.officialStore.getSkillsByType(skillType);
        OfficialSkill skill = null;
        if (skills != null && skills.length > 0) {
            skill = skills[0];
        }
        return querySkill(skill, request, response);
    }

    private ModelAndView querySkill(OfficialSkill skill, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelAndView mv = new ModelAndView();
        try {
            OfficialSkill[] skills = null;
            String skillTypeName = null;

            if (skill == null) {
                response.setStatus(404);
                return mv;
            }
            skillTypeName = this.officialStore.getSkillTypeNameFromSkillName(skill.getName());
            if (skillTypeName != null) {
                skills = this.officialStore.getSkillsByType(skillTypeName);
            }
            this.logger.info("Skill type: " + skillTypeName);
            this.userActionRecorder.addAction(new UserAction(new Date(), request.getRemoteAddr(), "View Skill", skillTypeName));
            mv.setViewName("view-skill");
            List<OfficialSkillInfo> skillInfos = new ArrayList<OfficialSkillInfo>();
            for (int i = 0; i < skills.length; ++i) {
                OfficialSkillInfo skillInfo = OfficialSkillInfo.build(skills[i], officialStore);
                skillInfos.add(skillInfo);
            }
            mv.addObject("skillInfos", skillInfos);
            mv.addObject("skillType", skillTypeName);
        } catch (Exception e) {
            this.logger.error(e);
        }
        return mv;
    }

    @RequestMapping(value = "/Wiki/Stages/{stageId}.shtml")
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

    @RequestMapping(value = "/Wiki/Skills", headers = "Accept=application/json")
    public void querySkills(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.getWriter().println(jsonHandler.toJson(officialStore.skillStore.data.Skills));
    }

    @RequestMapping(value = "/Wiki/SkillTypes", headers = "Accept=application/json")
    public void querySkillTypes(HttpServletRequest request, HttpServletResponse response,
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
            String skillType = officialStore.getSkillTypeNameFromSkillName(skill.getName());
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
        this.logger.info(String.format("starFilter = %s, raceFilter = %s, skillTypeFilter = %s, cardNameFilter = %s",
                starFilter, raceFilter, skillTypeFilter, cardNameFilter));
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

    @RequestMapping(value = "/Wiki/HeroSetting.shtml")
    public ModelAndView queryHeroInfo(HttpServletRequest request) throws IOException {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("view-hero-setting");
        OfficialHeroSetting heroSetting = OfficialHeroSetting.getInstance();
        mv.addObject("heroSetting", heroSetting);
        return mv;
    }
}