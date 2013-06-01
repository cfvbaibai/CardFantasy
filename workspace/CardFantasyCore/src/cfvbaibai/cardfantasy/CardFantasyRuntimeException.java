package cfvbaibai.cardfantasy;

public class CardFantasyRuntimeException extends RuntimeException {

    private static final long serialVersionUID = 6985908695358786483L;

    public CardFantasyRuntimeException() {
        super("Generic Card Fantasy exception");
    }

    public CardFantasyRuntimeException(String message) {
        super(message);
    }

    public CardFantasyRuntimeException(String message, Exception inner) {
        super(message, inner);
    }
}
