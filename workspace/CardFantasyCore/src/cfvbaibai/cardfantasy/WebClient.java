package cfvbaibai.cardfantasy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebClient {
    private String userAgent;

    public WebClient(String userAgent) {
        this.userAgent = userAgent;
    }

    // HTTP GET request
    public WebResponse sendGet(String url) throws IOException {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", this.userAgent);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        try {
            String inputLine;
            StringBuffer response = new StringBuffer();
    
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            String body = response.toString();
            WebResponse content = new WebResponse(body, responseCode);
            return content;
        } finally {
            in.close();
        }
    }
}
