package cfvbaibai.cardfantasy.web;

import java.util.ArrayList;
import java.util.List;

public class BattleEvent {
    private String name;
    private List<Object> data;
    
    public BattleEvent(String name, Object ... data) {
        this.name = name;
        this.data = new ArrayList<Object>();
        for (Object datum : data) {
            this.data.add(datum);
        }
    }

    public String getName() {
        return this.name;
    }
    
    public List<Object> getData() {
        return new ArrayList<Object>(data);
    }
}
