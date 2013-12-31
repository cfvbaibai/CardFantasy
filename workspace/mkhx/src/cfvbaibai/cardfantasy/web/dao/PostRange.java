package cfvbaibai.cardfantasy.web.dao;

public class PostRange {
    private int begin;
    private int end;
    public int getBegin() {
        return begin;
    }
    public void setBegin(int begin) {
        this.begin = begin;
    }
    public long getEnd() {
        return end;
    }
    public void setEnd(int end) {
        this.end = end;
    }
    public PostRange() {
        
    }
    public PostRange(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }
}
