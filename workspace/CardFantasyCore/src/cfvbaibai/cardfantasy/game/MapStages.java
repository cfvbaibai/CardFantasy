package cfvbaibai.cardfantasy.game;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;
import cfvbaibai.cardfantasy.data.CardDataStore;

public class MapStages {
    private Map <String, MapInfo> mapStages;
    
    @SuppressWarnings("unchecked")
    public MapStages() {
        this.mapStages = new HashMap<String, MapInfo>();
        URL url = CardDataStore.class.getClassLoader().getResource("cfvbaibai/cardfantasy/data/MapStages.xml");
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
                this.mapStages.put(id, mapInfo);
            }
        } catch (Exception e) {
            throw new CardFantasyRuntimeException("Cannot load card map info XML due to error at " + currentId, e);
        }
    }
    
    public MapInfo getMap(String id) {
        return this.mapStages.get(id);
    }
}
