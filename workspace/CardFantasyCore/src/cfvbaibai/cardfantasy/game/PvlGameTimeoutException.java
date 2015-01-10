package cfvbaibai.cardfantasy.game;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public class PvlGameTimeoutException extends CardFantasyRuntimeException {

    private static final long serialVersionUID = -3029656422246428087L;

    public PvlGameTimeoutException(String message) {
        super(message);
    }

    public PvlGameTimeoutException() {
    }
}
