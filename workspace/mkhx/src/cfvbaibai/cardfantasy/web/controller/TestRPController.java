package cfvbaibai.cardfantasy.web.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cfvbaibai.cardfantasy.Randomizer;
import cfvbaibai.cardfantasy.WeightedRandomizer;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.Race;
import cfvbaibai.cardfantasy.web.ErrorHelper;
import cfvbaibai.cardfantasy.web.Utils;
import cfvbaibai.cardfantasy.web.beans.JsonHandler;
import cfvbaibai.cardfantasy.web.beans.Logger;
import cfvbaibai.cardfantasy.web.beans.TestRPResult;

@Controller
public class TestRPController {
    @Autowired
    private Randomizer randomizer;
    @Autowired
    private CardDataStore store;
    @Autowired
    private JsonHandler json;
    @Autowired
    private Logger logger;
    @Autowired
    private ErrorHelper errorHelper;
    
    private WeightedRandomizer<Integer> starPicker;
    private WeightedRandomizer<Race> racePicker;
    
    //private String[][] remarks;
    
    @PostConstruct
    public void init() {
        starPicker = new WeightedRandomizer<Integer>(randomizer);
        starPicker.addRange(50, 1);
        starPicker.addRange(50, 2);
        starPicker.addRange(50, 3);
        starPicker.addRange(50, 4);
        starPicker.addRange(500, 5);
        
        racePicker = new WeightedRandomizer<Race>(randomizer);
        racePicker.addRange(100, Race.KINGDOM);
        racePicker.addRange(100, Race.FOREST);
        racePicker.addRange(100, Race.SAVAGE);
        racePicker.addRange(100, Race.HELL);
        racePicker.addRange(1000, Race.BOSS);
        racePicker.addRange(1000, Race.MOE);
        /*
        String oneStar = "嗯，虽然明天会是平凡的一天，但努力每一天，就会有惊喜哦。";
        remarks = new String[][] {
                { oneStar, oneStar, oneStar, oneStar, },
                {
                    "明天是个好日子，处处好风光，好风光。",
                    "要不明天就放下手机，和家人一起聊聊天吧。",
                    "热情洋溢地去和小伙伴切磋吧，经常交流友谊才会长久。",
                    "明天也许会碰到些许挫折，不过不要紧，只要和朋友齐心协力，就会渡过难关。",
                    "", ""
                },
                {
                    "运势好像开始好转了呢，记得抓住机会哦。",
                    "明天你应该能抓住稍纵即逝的瞬间，"
                }
        };
        */
    }

    @RequestMapping(value = "/TestRP", headers = "Accept=application/json")
    public void testRP(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        try {
            Race race = racePicker.pickRandom();
            int rpIndex = 0;
            List<CardData> raceFiltered = store.getCardOfRace(race);
            List<CardData> starRaceFiltered = null;
            if (race == Race.BOSS || race == Race.MOE) {
                starRaceFiltered = raceFiltered;
                rpIndex = 100;
            } else {
                int star = starPicker.pickRandom();
                starRaceFiltered = new ArrayList<CardData>();
                for (CardData card : raceFiltered) {
                    if (card.getStar() == star) {
                        starRaceFiltered.add(card);
                    }
                }
                rpIndex = randomizer.next((star - 1) * 20, star * 20);
            }
    
            int candidateIndex = randomizer.next(0, starRaceFiltered.size());
            CardData candidate = starRaceFiltered.get(candidateIndex);
            TestRPResult result = new TestRPResult();
            result.setCardName(candidate.getName());
            result.setCardId(candidate.getId());
            if (candidate.getName().equals("纯洁圣女")) {
                result.setRPIndex(-10);
                result.setRemark("我大圣女显灵，还不赶快烧香拜佛，不然让你一个月不出货。");
            } else {
                result.setRPIndex(rpIndex);
                if (rpIndex < 20) {
                    result.setRemark("赶紧洗洗睡吧，今天诸事不宜。");
                } else if (rpIndex < 40) {
                    result.setRemark("好像运势不是很好的样子，想抽包的今天还是罢手吧。");
                } else if (rpIndex < 60) {
                    result.setRemark("看来是平凡的一天呐，和往常一样过吧。");
                } else if (rpIndex < 80) {
                    result.setRemark("似乎有好事要发生呢，是不是抽几包败家包或者开几张吊丝券？");
                } else if (rpIndex < 100) {
                    result.setRemark("大吉大利的一天，塔里要出货的赶脚！");
                } else {
                    result.setRemark("人品掉渣天！朋友你已经超神了吧！");
                }
            }
            result.setTestDate(Utils.getCurrentDate());
            String resultText = json.toJson(result);
            logger.info("TestRP(" + request.getRemoteAddr() + "): " + resultText);
            writer.print(resultText);
        } catch (Exception e) {
            writer.print(errorHelper.handleError(e, true));
        }
    }
}
