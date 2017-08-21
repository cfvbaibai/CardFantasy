package cfvbaibai.cardfantasy.game;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.CardDataStore;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DungeonsStages {
    private Map <String, MapInfo> dungeonsStages;

    @SuppressWarnings("unchecked")
    public DungeonsStages() {
        this.dungeonsStages = new HashMap<String, MapInfo>();
        URL url = CardDataStore.class.getClassLoader().getResource("cfvbaibai/cardfantasy/data/DungeonsStages.xml");
        SAXReader reader = new SAXReader();
        String currentId = "";
        try {
            Document doc = reader.read(url);
            List<Node> mapNodes = doc.selectNodes("/Maps/Map");
            for (Node mapNode : mapNodes) {
                String id = mapNode.valueOf("@id");
                currentId = id;
                int heroHP = Integer.parseInt(mapNode.valueOf("@heroHP"));
                String descText = mapNode.getText();
                String[] descs = descText.split(",");
                DeckStartupInfo deck = DeckBuilder.build(descs);
                MapEnemyHero hero = new MapEnemyHero(id, heroHP, deck.getRunes(), deck.getCards());
                String victoryText = mapNode.valueOf("@victory");
                VictoryCondition victory = VictoryCondition.parse(victoryText);
                String deckInfo = mapNode.getText();
                MapInfo mapInfo = new MapInfo(hero, victory, deckInfo);
                this.dungeonsStages.put(id, mapInfo);
            }
        } catch (Exception e) {
            throw new CardFantasyRuntimeException("Cannot load card map info XML due to error at " + currentId, e);
        }
    }
    
    private static DungeonsStages instance;
    public static DungeonsStages loadDefault() {
        if (instance == null) {
            instance = new DungeonsStages();
        }
        return instance;
    }
    
    public MapInfo getDungeons(String id) {
        return this.dungeonsStages.get(id);
    }
}
