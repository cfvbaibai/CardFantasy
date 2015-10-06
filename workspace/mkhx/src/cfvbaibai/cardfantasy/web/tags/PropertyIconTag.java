package cfvbaibai.cardfantasy.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.annotation.Autowired;

import cfvbaibai.cardfantasy.web.beans.Logger;

public class PropertyIconTag extends TagSupport {
    private static final long serialVersionUID = -3839474520661100426L;
    private String propertyName;
    private String cssClass;
    public String getPropertyName() {
        return propertyName;
    }
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
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
                    "<img src='%s/resources/img/frame/Property_%s_Icon.png' class='%s' />",
                    this.pageContext.getServletContext().getContextPath(),
                    this.getPropertyName(),
                    (this.cssClass == null || this.cssClass.isEmpty()) ? "property-icon" : this.cssClass);
            writer.print(tagText);
        } catch (IOException e) {
            logger.error(e);
        }
        return Tag.SKIP_BODY;
    }
}
