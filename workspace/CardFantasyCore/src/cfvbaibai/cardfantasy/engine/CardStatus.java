package cfvbaibai.cardfantasy.engine;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class CardStatus {
    private List<CardStatusItem> items;

    public CardStatus() {
        this.items = new LinkedList<CardStatusItem>();
    }

    public void add(CardStatusItem item) {
        this.items.add(item);
    }

    public boolean remove(CardStatusType type) {
        Iterator<CardStatusItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CardStatusItem next = iterator.next();
            if (next.getType() == type) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    public boolean containsStatus(CardStatusType type) {
        for (CardStatusItem item : items) {
            if (item.getType() == type) {
                return true;
            }
        }
        return false;
    }

    public List<CardStatusItem> getStatusOf(CardStatusType type) {
        List<CardStatusItem> result = new LinkedList<CardStatusItem>();
        for (CardStatusItem item : items) {
            if (item.getType() == type) {
                result.add(item);
            }
        }
        return result;
    }

    public String getShortDesc() {
        return getShortDescOfItems(this.items);
    }
    
    public String getShortDescOfType(CardStatusType type) {
        return getShortDescOfItems(this.getStatusOf(type));
    }
    
    private String getShortDescOfItems(List<CardStatusItem> items) {
        if (items.size() == 0) {
            return "-";
        }
        StringBuffer sb = new StringBuffer();
        sb.append("【");
        for (CardStatusItem item : items) {
            sb.append(item.getShortDesc());
            sb.append(", ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("】");
        return sb.toString();
    }

    public boolean containsStatusCausedBy(SkillUseInfo feature, CardStatusType type) {
        List<CardStatusItem> items = this.getStatusOf(type);
        for (CardStatusItem item : items) {
            if (item.getCause().equals(feature)) {
                return true;
            }
        }
        return false;
    }
}
