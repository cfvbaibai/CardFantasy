package cfvbaibai.cardfantasy;

public class CardFantasyUserRuntimeException extends CardFantasyRuntimeException {

    private static final long serialVersionUID = -9071192323680099934L;
    
    private String helpMessage = "";
    
    public String getHelpMessage() {
        return this.helpMessage;
    }

    public CardFantasyUserRuntimeException(String message) {
        super(message);
    }

    public CardFantasyUserRuntimeException(String message, Exception inner) {
        super(message, inner);
    }
    
    public CardFantasyUserRuntimeException(String message, String helpMessage) {
        super(message);
        this.helpMessage = helpMessage;
    }
    
    public CardFantasyUserRuntimeException(String message, String helpMessage, Exception inner) {
        super(message, inner);
        this.helpMessage = helpMessage;
    }
}
