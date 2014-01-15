package cfvbaibai.cardfantasy.web;

import cfvbaibai.cardfantasy.CardFantasyUserRuntimeException;
import cfvbaibai.cardfantasy.web.beans.Logger;

public class ErrorHelper {
    private Logger logger;
    private java.util.logging.Logger userErrorLogger;
    
    public ErrorHelper(Logger logger, java.util.logging.Logger userErrorLogger) {
        this.logger = logger;
        this.userErrorLogger = userErrorLogger;
    }
    
    public String handleError(Exception e, boolean isJson) {
        String errorMessage = "";
        logger.info(errorMessage);
        if (e instanceof CardFantasyUserRuntimeException) {
            CardFantasyUserRuntimeException cfure = (CardFantasyUserRuntimeException)e;
            userErrorLogger.severe(cfure.getMessage());
            errorMessage = cfure.getMessage() + cfure.getHelpMessage();
        } else {
            logger.error(e);
            errorMessage = Utils.getAllMessage(e);
        }
        
        String message = String.format("<font color='red'>%s<br />发生错误！<br />%s<br />",
                Utils.getCurrentDateTime(), errorMessage);
        if (isJson) {
            message = "{ \"error\": true, \"message\": \"" + message + "\" }";
        }
        return message;
    }
}
