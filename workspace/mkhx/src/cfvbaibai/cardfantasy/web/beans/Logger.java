package cfvbaibai.cardfantasy.web.beans;

import cfvbaibai.cardfantasy.web.Utils;

public class Logger {

    public Logger() {
    }

    public void info(String message) {
        String tid = String.valueOf(Thread.currentThread().getId());
        String trace = "[" + Utils.getCurrentDateTime() + "][" + tid + "] " + message;
        System.out.println(trace);
    }

    public void error(Exception e) {
        String tid = String.valueOf(Thread.currentThread().getId());
        System.err.print("[" + Utils.getCurrentDateTime() + "][" + tid + "]");
        e.printStackTrace();
        System.err.flush();
    }

}
