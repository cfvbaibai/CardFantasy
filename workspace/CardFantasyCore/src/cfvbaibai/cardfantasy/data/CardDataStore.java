package cfvbaibai.cardfantasy.data;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public class CardDataStore {
    private Map <String, CardData> cardMap;

    private CardDataStore() {
        cardMap = new HashMap <String, CardData>();
    }

    public static CardDataStore loadDefault() {
        CardDataStore store = new CardDataStore();

        URL url = CardDataStore.class.getClassLoader().getResource("cfvbaibai/cardfantasy/data/CardData.xml");
        SAXReader reader = new SAXReader();
        try {
            Document doc = reader.read(url);
            @SuppressWarnings("unchecked")
            List<Node> cardNodes = doc.selectNodes("/Cards/Card");
            for (Node cardNode : cardNodes) {
                CardData cardData = new CardData();
                cardData.setId(cardNode.valueOf("@id"));
                cardData.setWikiId(cardNode.valueOf("@wikiId"));
                cardData.setName(cardNode.valueOf("@name"));
                cardData.setRace(Race.parse(cardNode.valueOf("@race")));
                cardData.setSummonSpeed(Integer.parseInt(cardNode.valueOf("@speed")));
                cardData.setStar(Integer.parseInt(cardNode.valueOf("@star")));
                cardData.setBaseCost(Integer.parseInt(cardNode.valueOf("@cost")));
                cardData.setIncrCost(Integer.parseInt(cardNode.valueOf("@incrCost")));
                cardData.setBaseAT(Integer.parseInt(cardNode.valueOf("@at")));
                cardData.setBaseHP(Integer.parseInt(cardNode.valueOf("@hp")));
                cardData.setIncrAT(Integer.parseInt(cardNode.valueOf("@incrAT")));
                cardData.setIncrHP(Integer.parseInt(cardNode.valueOf("@incrHP")));
                
                @SuppressWarnings("unchecked")
                List<Node> featureNodes = cardNode.selectNodes("Feature");
                for (Node featureNode : featureNodes) {
                    SkillType type = SkillType.valueOf(featureNode.valueOf("@type"));
                    int level = Integer.parseInt(featureNode.valueOf("@level"));
                    int unlockLevel = Integer.parseInt(featureNode.valueOf("@unlock"));
                    String summonText = featureNode.valueOf("@summon");
                    boolean isSummonFeature = summonText == null ? false : Boolean.parseBoolean(summonText);
                    String deathText = featureNode.valueOf("@death");
                    boolean isDeathFeature = deathText == null ? false : Boolean.parseBoolean(deathText);
                    cardData.getFeatures().add(new CardSkill(type, level, unlockLevel, isSummonFeature, isDeathFeature));
                }
                store.addCard(cardData);
            }
            return store;
        } catch (DocumentException e) {
            throw new CardFantasyRuntimeException("Cannot load card info XML.", e);
        }
    }

    private void addCard(CardData cardData) {
        this.cardMap.put(cardData.getName(), cardData);
    }
    
    public CardData getCardInfo(String name) {
        if (this.cardMap.containsKey(name)) {
            return this.cardMap.get(name);
        } else {
            return null;
        }
    }
    
    public List<CardData> getCardOfStar(int star) {
        List<CardData> result = new ArrayList<CardData>();
        for (CardData card : this.cardMap.values()) {
            if (card.getStar() == star) {
                result.add(card);
            }
        }
        return result;
    }
    
    public List<CardData> getAllCards() {
        List<CardData> result = new ArrayList<CardData>();
        for (CardData card : this.cardMap.values()) {
            result.add(card);
        }
        return result;
    }

    public List<CardData> getCardOfRace(Race race) {
        List<CardData> result = new ArrayList<CardData>();
        for (CardData card : this.cardMap.values()) {
            if (card.getRace() == race) {
                result.add(card);
            }
        }
        return result;
    }
}