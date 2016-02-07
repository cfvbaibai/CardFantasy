package cfvbaibai.cardfantasy.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import cfvbaibai.cardfantasy.CardFantasyUserRuntimeException;
import cfvbaibai.cardfantasy.web.beans.Logger;

public class ErrorHelper {
    private Logger logger;
    //private java.util.logging.Logger userErrorLogger;

    public ErrorHelper(Logger logger) {
        this.logger = logger;
    }

    /*
    public ErrorHelper(Logger logger, java.util.logging.Logger userErrorLogger) {
        this.logger = logger;
        this.userErrorLogger = userErrorLogger;
    }
    */
    private static String getStackTrace(Throwable e) {
        StringWriter sw = null;
        PrintWriter pw = null;
        try {
            sw = new StringWriter();
            pw = new PrintWriter(sw);
            while (e != null) {
                e.printStackTrace(pw);
                pw.println("======================================");
                e = e.getCause();
            }
            pw.flush();
            sw.flush();
        } finally {
            if (sw != null) {
                try {
                    sw.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (pw != null) {
                pw.close();
            }
        }
        return sw.toString();
    }
    
    public String handleError(Exception e, boolean isJson) throws IOException {
        String errorMessage;
        if (e instanceof CardFantasyUserRuntimeException) {
            CardFantasyUserRuntimeException cfure = (CardFantasyUserRuntimeException)e;
            //userErrorLogger.severe(cfure.getMessage());
            errorMessage = cfure.getMessage() + cfure.getHelpMessage();
        } else {
            logger.error(e);
            errorMessage = getStackTrace(e);
        }

        String message = String.format("<font color='red'>%s<br />发生错误！<br />%s<br />",
                Utils.getCurrentDateTime(), errorMessage.replace(System.lineSeparator(), "<br />"));
        if (isJson) {
            message = "{ \"error\": true, \"message\": \"" + message + "\" }";
        }
        return message;
    }
}
