package cfvbaibai.cardfantasy;

public class WebResponse {
    private String body;
    private int statusCode;
    
    public WebResponse(String body, int statusCode) {
        super();
        this.body = body;
        this.statusCode = statusCode;
    }

    public String getBody() {
        return body;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
