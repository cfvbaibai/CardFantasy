package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
import java.util.List;

public class OutField extends CardPile {
    public List<CardInfo> getAllCards() {
        List<CardInfo> result = new ArrayList<CardInfo>();
        for (CardInfo card : getCards()) {
            if (card != null) {
                result.add(card);
            }
        }
        return result;
    }
}
