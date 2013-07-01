package cfvbaibai.cardfantasy.game;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public class DeckEvaluation implements Comparable <DeckEvaluation>{
    private PveGameResultStat stat;
    private DeckStartupInfo deck;
    public DeckEvaluation(PveGameResultStat stat, DeckStartupInfo deck) {
        super();
        this.stat = stat;
        this.deck = deck;
    }
    public PveGameResultStat getStat() {
        return stat;
    }
    public DeckStartupInfo getDeck() {
        return deck;
    }
    @Override
    public int compareTo(DeckEvaluation other) {
        if (other == null) {
            throw new CardFantasyRuntimeException("other could not be null!");
        }
        return other.stat.getAdvWinRate() - this.stat.getAdvWinRate();
    }
}
