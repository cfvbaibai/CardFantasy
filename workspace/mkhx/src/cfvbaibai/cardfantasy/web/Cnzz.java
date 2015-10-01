package cfvbaibai.cardfantasy.web;

import java.util.Random;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.WebClient;
import cfvbaibai.cardfantasy.WebResponse;
import cfvbaibai.cardfantasy.web.beans.Logger;

public class Cnzz {
    private WebClient webClient;
    private Random random;
    private Logger logger;

    public Cnzz(Logger logger) {
        this.webClient = new WebClient("MKHX Emulator Server");
        this.random = new Random();
        this.logger = logger;
    }

    public void recordUsage(String key) {
        int seed = random.nextInt(Integer.MAX_VALUE);
        String url = "http://cnrdn.com/rd.htm?id=1344758&r=" + key + "&seed=" + seed;
        try {
            WebResponse response = webClient.sendGet(url);
            if (response.getStatusCode() != 200) {
                throw new CardFantasyRuntimeException(
                        "Failed to upload usage data to CNZZ. Status code: " + response.getStatusCode() +
                        " Response body: " + response.getBody());
            }
        } catch (Exception e) {
            logger.error(e);
        }
    }
}
