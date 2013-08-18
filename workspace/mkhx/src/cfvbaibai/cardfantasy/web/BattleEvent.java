package cfvbaibai.cardfantasy.web;

import java.util.ArrayList;
import java.util.List;

public class BattleEvent {
    private String name;
    private List<Object> dataList;
    
    public BattleEvent(String name, Object ... data) {
        this.name = name;
        this.dataList = new ArrayList<Object>();
        for (Object datum : data) {
            this.dataList.add(datum);
        }
    }
    
    public void addData(Object data) {
        this.dataList.add(data);
    }

    public String getName() {
        return this.name;
    }
    
    public List<Object> getDataList() {
        return new ArrayList<Object>(dataList);
    }
}
