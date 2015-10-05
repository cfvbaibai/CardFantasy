package cfvbaibai.cardfantasy.web.beans;

import java.util.ArrayList;
import java.util.List;

public class SubCategory<T> {
    private String name;
    private List<T> items = new ArrayList<T>();
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<T> getItems() {
        return this.items;
    }
    public void addItem(T item) {
        this.items.add(item);
    }
}
