package cfvbaibai.cardfantasy;

public class StringBufferEx {
    private StringBuffer sb;
    public StringBufferEx() {
        this.sb = new StringBuffer();
    }
    public StringBufferEx append(String string) {
        this.sb.append(string);
        return this;
    }
    public StringBufferEx appendLine(String s) {
        return this.append(s).append(System.lineSeparator());
    }
    public StringBufferEx appendLineFormat(String format, Object ... args) {
        return this.appendLine(String.format(format, args));
    }
    public StringBufferEx deleteLastChars(int charsToDelete) {
        for (int i = 0; i < charsToDelete && this.sb.length() > 0; ++i) {
            this.sb.deleteCharAt(this.sb.length() - 1);
        }
        return this;
    }
    public int length() {
        return this.sb.length();
    }
    @Override
    public String toString() {
        return this.sb.toString();
    }
}
