package cfvbaibai.cardfantasy.engine;

import java.util.ArrayList;
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
        boolean removedAny = false;
        boolean multipleEnd = true;
        Iterator<CardStatusItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CardStatusItem next = iterator.next();
            if (next.getType() == type) {
                //加个if控制下buff的持续回合数
                if(next.getEffectNumber()>1)
                {
                    next.setEffectNumber(next.getEffectNumber()-1);
                    multipleEnd = false;
                    continue;
                }
                iterator.remove();
                removedAny = true;
            }
        }
        return removedAny&&multipleEnd;
    }

    //强制移除负面状态
    public boolean removeForce(CardStatusType type) {
        boolean removedAny = false;
        Iterator<CardStatusItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            CardStatusItem next = iterator.next();
            if (next.getType() == type) {
                iterator.remove();
                removedAny = true;
            }
        }
        return removedAny;
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
    
    public List<CardStatusItem> getAllItems() {
        return new ArrayList<CardStatusItem>(this.items);
    }

    public boolean containsStatusCausedBy(SkillUseInfo skillUseInfo, CardStatusType type) {
        List<CardStatusItem> items = this.getStatusOf(type);
        for (CardStatusItem item : items) {
            if (item.getCause().equals(skillUseInfo)) {
                return true;
            }
        }
        return false;
    }
}
