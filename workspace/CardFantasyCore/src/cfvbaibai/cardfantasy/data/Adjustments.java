package cfvbaibai.cardfantasy.data;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Note: this class is immutable.
 */
public class Adjustments {

    private List<Adjustment> adjustments;
    private List<Adjustment> extraAdjustments;
    
    public Adjustments() {
        this.adjustments = new ArrayList<Adjustment>();
        this.extraAdjustments = new ArrayList<Adjustment>();
    }

    public Adjustments(Adjustments base) {
        this.adjustments = new LinkedList<Adjustment>(base.adjustments); 
        this.extraAdjustments = new LinkedList<Adjustment>(base.extraAdjustments);
    }

}
