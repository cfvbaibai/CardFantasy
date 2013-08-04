package cfvbaibai.cardfantasy.web;

import java.util.ArrayList;
import java.util.List;

public class BattleRecord {
    private List<BattleEvent> events;

    public BattleRecord() {
        this.events = new ArrayList<BattleEvent>();
    }
    
    public void addEvent(BattleEvent event) {
        this.events.add(event);
    }
    
    public void addEvent(String name, Object ... data) {
        this.events.add(new BattleEvent(name, data));
    }
    
    public List<BattleEvent> getEvents() {
        return new ArrayList<BattleEvent>(this.events);
    }
}
