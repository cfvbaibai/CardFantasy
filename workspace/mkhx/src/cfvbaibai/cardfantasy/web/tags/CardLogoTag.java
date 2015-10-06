package cfvbaibai.cardfantasy.web.tags;

import java.io.IOException;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.beans.factory.annotation.Autowired;

import cfvbaibai.cardfantasy.officialdata.OfficialCard;
import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;
import cfvbaibai.cardfantasy.web.beans.Logger;

public class CardLogoTag extends TagSupport {
    private static final long serialVersionUID = -6869551922232242199L;
    private String cardName;
    private boolean cardNameVisible = true;
    private boolean starBarVisible = true;
    private boolean frameVisible = true;

    public String getCardName() {
        return cardName;
    }
    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public boolean isCardNameVisible() {
        return cardNameVisible;
    }
    public void setCardNameVisible(boolean cardNameVisible) {
        this.cardNameVisible = cardNameVisible;
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
            OfficialCard card = OfficialDataStore.getInstance().getCardByName(this.getCardName());
            String contextPath = this.pageContext.getServletContext().getContextPath();
            String cardPageUrl = contextPath + "/Wiki/Cards/" + this.getCardName();
            String frameUrlFormat = contextPath + "/resources/img/frame/Square_%s_Frame.png";
            String invisibleFrameUrl = contextPath + "/resources/img/frame/Invisible_Square_Frame.png";
            String starUrl = contextPath + "/resources/img/frame/Star_" + card.getColor() + "_Bar.png";
            String logoUrl = card.getLogoUrl();
            
            /*
             *                     <div class="card-logo-container">
                        <div class="card-logo">
                            <a href="<c:url value="/Wiki" />/Cards/${card.cardName}"><cf:cardLogo cardName="${card.cardName}" /></a>
                        </div>
                        <div class="card-name">
                            ${card.cardName}
                        </div>
                    </div>
             */
            writer.println(
                "<div class='card-logo-container'>");
            writer.println(String.format(
                    "<div class='card-frame'><img src='%s' style='width: 100%%' /></div>",
                    this.frameVisible ? String.format(frameUrlFormat, card.getRaceName()) : invisibleFrameUrl));
            writer.println(String.format(
                    "<div class='card-logo'>" +
                        "<a href='%s' target='_self'>" +
                            "<img src='%s' alt='%s' title='%s' style='width: 100%%' />" +
                        "</a>" +
                    "</div>",
                cardPageUrl,
                logoUrl, this.getCardName(), this.getCardName()));
            if (this.cardNameVisible) {
                writer.println(String.format(
                    "<div class='card-name'>%s</div>", this.getCardName()));
            }
            if (this.starBarVisible) {
                writer.println(String.format(
                    "<div class='card-star'><img src='%s' style='width: 100%%' /></div>", starUrl)); 
            }
            writer.println(
                "</div>");
        } catch (IOException e) {
            logger.error(e);
        }
        return Tag.SKIP_BODY;
    }
}
