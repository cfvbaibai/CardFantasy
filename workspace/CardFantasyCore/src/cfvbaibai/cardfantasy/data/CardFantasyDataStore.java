package cfvbaibai.cardfantasy.data;

import java.util.List;

public interface CardFantasyDataStore {
    List<CardData> getCardOfStar(int star);
    List<CardData> getAllCards();
    List<CardData> getCardOfRace(Race race);
    CardData getRandomCard();
    List<CardData> getBossHelpers(int strengthLevel);
}
