package cfvbaibai.cardfantasy;

public class DeckBuildRuntimeException extends CardFantasyRuntimeException {

    private static final long serialVersionUID = 193208372894017945L;
    private static final String HELP_MSG =  ". Çë×ÐÏ¸ÔÄ¶Á°ïÖúÐÅÏ¢¡£";
    public DeckBuildRuntimeException(String message) {
        super(message + HELP_MSG);
    }

    public DeckBuildRuntimeException(String message, Exception inner) {
        super(message + HELP_MSG, inner);
    }

}
