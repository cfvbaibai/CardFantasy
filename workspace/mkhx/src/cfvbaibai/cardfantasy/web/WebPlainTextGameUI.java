package cfvbaibai.cardfantasy.web;

import org.apache.commons.lang3.StringEscapeUtils;

import cfvbaibai.cardfantasy.TextGameUI;

public class WebPlainTextGameUI extends TextGameUI {

    private StringBuffer sb;
    
    public WebPlainTextGameUI() {
        sb = new StringBuffer();
    }

    @Override
    protected void say(String text) {
        if (text == null) {
            return;
        }
        text = StringEscapeUtils.escapeHtml4(text);
        text = text.replace("\n", "<br />");
        sb.append(text);
        sb.append("<br />");
    }

    public String getAllText() {
        return sb.toString();
    }
}
