package cfvbaibai.cardfantasy.data;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public class Growth {
    private int[] requiredExp;

    public Growth(int... requiredExps) {
        if (requiredExps == null) {
            throw new CardFantasyRuntimeException("requiredExps should not be null.");
        }
        if (requiredExps.length == 0) {
            throw new CardFantasyRuntimeException("requiredExps should not be zero-length.");
        }
        this.requiredExp = new int[requiredExps.length];
        int lastLevelRequiredExp = -1;
        for (int i = 0; i < requiredExps.length; ++i) {
            if (requiredExps[i] <= lastLevelRequiredExp) {
                throw new CardFantasyRuntimeException(String.format("Invalid required exp for level %d(%d) -> %d(%d).",
                        i + 1, lastLevelRequiredExp, i + 2, requiredExps[i]));
            }
            requiredExp[i] = requiredExps[i];
        }
    }

    public int getRequiredExp(int level) {
        if (level < 0 || level >= requiredExp.length) {
            throw new CardFantasyRuntimeException("Invalid level " + level + ". Level must be between " + 0 + " and "
                    + requiredExp.length);
        }
        return requiredExp[level];
    }

    public int getLevel(int exp) {
        for (int i = 0; i < requiredExp.length; ++i) {
            if (exp >= requiredExp[i]) {
                return i;
            }
        }
        return requiredExp.length - 1;
    }
}
