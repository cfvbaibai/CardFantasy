package cfvbaibai.cardfantasy.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.annotation.Autowired;

import cfvbaibai.cardfantasy.web.beans.Logger;

public class StarIconTag extends TagSupport {
    private static final long serialVersionUID = -5758993025527821584L;
    private int star;
    private String cssClass;
    public int getStar() {
        return star;
    }
    public void setStar(int star) {
        this.star = star;
    }
    public String getCssClass() {
        return this.cssClass;
    }
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    @Autowired
    private Logger logger;

    @Override
    public int doEndTag() {
        JspWriter writer = this.pageContext.getOut();
        try {
            String tagText = String.format(
                    "<img src='%s/resources/img/frame/Star_%d_Icon.png' class='%s' />",
                    this.pageContext.getServletContext().getContextPath(),
                    this.getStar(),
                    (this.cssClass == null || this.cssClass.isEmpty()) ? "star-icon" : this.cssClass);
            writer.println(tagText);
        } catch (IOException e) {
            logger.error(e);
        }
        return Tag.SKIP_BODY;
    }
}
