package cfvbaibai.cardfantasy.web.beans;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserActionRecorder {

    private LinkedList<UserAction> actions;
    private int maxRecordCount;

    public UserActionRecorder() {
        this.actions = new LinkedList <UserAction>();
        this.maxRecordCount = 1000;
    }

    public synchronized void addAction(UserAction action) {
        while (actions.size() > this.getMaxRecordCount()) {
            actions.removeLast();
        }
        this.actions.addFirst(action);
    }
    
    public int getMaxRecordCount() {
        return this.maxRecordCount;
    }
    
    public void setMaxRecordCount(int maxRecordCount) {
        this.maxRecordCount = maxRecordCount;
    }

    public List<UserAction> getAllActions() {
        return new ArrayList<UserAction>(actions);
    }
}
