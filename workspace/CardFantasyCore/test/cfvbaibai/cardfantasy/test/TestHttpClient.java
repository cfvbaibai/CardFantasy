package cfvbaibai.cardfantasy.test;

import java.io.IOException;
import java.util.Random;

import org.junit.Test;

import cfvbaibai.cardfantasy.WebResponse;
import cfvbaibai.cardfantasy.WebClient;

public class TestHttpClient {

    @Test
    public void testHttpWebRequest() throws IOException {
        Random random = new Random();
        int seed = random.nextInt(10000);
        String url = "http://cnrdn.com/rd.htm?id=1344758&r=Test&seed=" + seed;
        WebClient request = new WebClient("TestClient");
        WebResponse content = request.sendGet(url);
        System.out.println("Status: " + content.getStatusCode());
        System.out.println("Body: " + content.getBody());
    }
}
