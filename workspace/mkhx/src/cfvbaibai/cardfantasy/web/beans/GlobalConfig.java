package cfvbaibai.cardfantasy.web.beans;

public class GlobalConfig {
    private int threadsPerPage = 30;
    private String test = "<NULL>";

    public int getThreadsPerPage() {
        return this.threadsPerPage;
    }
    public void setThreadsPerPage(int threadsPerPage) {
        this.threadsPerPage = threadsPerPage;
    }
    public String getTest() {
        return test;
    }
    public void setTest(String test) {
        this.test = test;
    }
    
}
