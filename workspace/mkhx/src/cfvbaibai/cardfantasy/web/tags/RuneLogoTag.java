package cfvbaibai.cardfantasy.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.annotation.Autowired;

import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;
import cfvbaibai.cardfantasy.officialdata.OfficialRune;
import cfvbaibai.cardfantasy.web.beans.Logger;

public class RuneLogoTag extends TagSupport {
    private static final long serialVersionUID = -6869551922232242199L;
    private String runeName;
    private boolean runeNameVisible = true;
    private boolean starBarVisible = true;
    private boolean frameVisible = true;
    private boolean responsive = false;
    
    public boolean isResponsive() {
        return responsive;
    }
    public void setResponsive(boolean responsive) {
        this.responsive = responsive;
    }
    public String getRuneName() {
        return runeName;
    }
    public void setRuneName(String runeName) {
        this.runeName = runeName;
    }
    public boolean isRuneNameVisible() {
        return runeNameVisible;
    }
    public void setRuneNameVisible(boolean runeNameVisible) {
        this.runeNameVisible = runeNameVisible;
    }
    public boolean isStarBarVisible() {
        return starBarVisible;
    }
    public void setStarBarVisible(boolean starBarVisible) {
        this.starBarVisible = starBarVisible;
    }
    public boolean isFrameVisible() {
        return frameVisible;
    }
    public void setFrameVisible(boolean frameVisible) {
        this.frameVisible = frameVisible;
    }

    @Autowired
    private Logger logger;

    @Override
    public int doEndTag() {
        JspWriter writer = this.pageContext.getOut();
        try {
            OfficialRune rune = OfficialDataStore.getInstance().getRuneByName(this.getRuneName());
            String contextPath = this.pageContext.getServletContext().getContextPath();
            String runePageUrl = contextPath + "/Wiki/Runes/" + rune.getRuneId() + ".shtml";
            String frameUrlFormat = contextPath + "/resources/img/frame/Square_Frame.png";
            String invisibleFrameUrl = contextPath + "/resources/img/frame/Invisible_Square_Frame.png";
            String starUrl = contextPath + "/resources/img/frame/Star_" + rune.getColor() + "_Bar.png";
            String logoUrl = rune.getSmallIconUrl();
            
            /*
                    <div class="card-logo-container">
                        <div class="card-logo">
                            <a href="<c:url value="/Wiki" />/Cards/${card.cardName}"><cf:cardLogo cardName="${card.cardName}" /></a>
                        </div>
                        <div class="card-name">
                            ${card.cardName}
                        </div>
                    </div>
             */
            writer.println(String.format(
                "<div class='rune-logo-container%s'>",
                this.isResponsive() ? " responsive" : ""));
            writer.println(String.format(
                    "<div class='rune-frame'><img src='%s' style='width: 100%%' /></div>",
                    this.frameVisible ? frameUrlFormat : invisibleFrameUrl));
            writer.println(String.format(
                    "<div class='rune-logo'>" +
                        "<a href='%s' target='_self'>" +
                            "<img src='%s' alt='%s' title='%s' style='width: 100%%' />" +
                        "</a>" +
                    "</div>",
                runePageUrl,
                logoUrl, this.getRuneName(), this.getRuneName()));
            if (this.runeNameVisible) {
                writer.println(String.format(
                    "<div class='rune-name'>%s</div>", this.getRuneName()));
            }
            if (this.starBarVisible) {
                writer.println(String.format(
                    "<div class='rune-star'><img src='%s' style='width: 100%%' /></div>", starUrl)); 
            }
            writer.println(
                "</div>");
        } catch (IOException e) {
            logger.error(e);
        }
        return Tag.SKIP_BODY;
    }
}
