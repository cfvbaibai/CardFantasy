package cfvbaibai.cardfantasy.web.tags;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CardLinkTag extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {
        StringWriter sw = new StringWriter();
        this.getJspBody().invoke(sw);
        String cardName = sw.toString();
        sw.close();
        JspWriter out = this.getJspContext().getOut();
        // Remove link from card logo and let's see if this can improve SEO for baidu.
        out.print("【");
        //out.print("【<a href='Wiki/Cards/");
        //out.print(cardName);
        //out.print("' target='_blank' title='");
        //out.print(cardName);
        //out.print("'>");
        out.print(cardName);
        //out.print("</a>】");
        out.print("】");
    }
}
