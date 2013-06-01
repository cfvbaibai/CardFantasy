package cfvbaibai.cardfantasy.engine;

public abstract class Cause {

    private static class TooLong extends Cause {
        
    }
    
    public static Cause tooLong() {
        return new TooLong();
    }
}
