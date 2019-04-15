package cfvbaibai.cardfantasy.data;

public class Indenture implements Comparable<Indenture> {
    private IndentureData data;
    private Card card;
    private int level;
    public Indenture(IndentureData data, Card card,int level) {
        this.data = data;
        this.card = card;
        this.level = level;
    }
    
    public int getLevel() {
        return this.level;
    }

    public int getEffectNumber() {
        return (this.data.getInitLevel()-this.data.getIncrLevel()*this.level);
    }

    public IndentureActivator getIndentureActivator() {
        return this.data.getIndentureActivator();
    }

    public Card getCard() {
        return card;
    }

    public String getName() {
        return this.data.name();
    }

    public boolean is(IndentureData data) {
        return this.data == data;
    }

    @Override
    public int compareTo(Indenture another) {
        if (another == null) {
            throw new IllegalArgumentException("another should not be null");
        }
        int result = this.getName().compareToIgnoreCase(another.getName());
        if (result != 0) {
            return result;
        }
        return this.getLevel() - another.getLevel();
    }

    public Object getParsableDesc() {
        return String.format("%s-%d", this.getName(), this.getLevel());
    }
}
