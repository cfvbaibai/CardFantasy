package cfvbaibai.cardfantasy.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.annotation.Autowired;

import cfvbaibai.cardfantasy.web.beans.Logger;

public class SkillCategoryIconTag extends TagSupport {
    private static final long serialVersionUID = -9183261810098904015L;
    private int categoryId;
    private String cssClass;
    public int getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
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
                    "<img src='%s/resources/img/frame/Skill_Category_%d.png' class='%s' />",
                    this.pageContext.getServletContext().getContextPath(),
                    this.getCategoryId(),
                    (this.cssClass == null || this.cssClass.isEmpty()) ? "skill-category-icon" : this.cssClass);
            writer.println(tagText);
        } catch (IOException e) {
            logger.error(e);
        }
        return Tag.SKIP_BODY;
    }
}
