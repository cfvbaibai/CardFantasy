package cfvbaibai.cardfantasy.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.annotation.Autowired;

import cfvbaibai.cardfantasy.web.beans.Logger;

public class RaceIconTag extends TagSupport {
    private static final long serialVersionUID = 2702378855387262075L;
    private String raceName;
    private String cssClass;
    public String getRaceName() {
        return raceName;
    }
    public void setRaceName(String raceName) {
        this.raceName = raceName;
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
                    "<img src='%s/resources/img/frame/Race_%s_Icon.png' class='%s' />",
                    this.pageContext.getServletContext().getContextPath(),
                    this.getRaceName(),
                    (this.cssClass == null || this.cssClass.isEmpty()) ? "race-icon" : this.cssClass);
            writer.println(tagText);
        } catch (IOException e) {
            logger.error(e);
        }
        return Tag.SKIP_BODY;
    }
}
