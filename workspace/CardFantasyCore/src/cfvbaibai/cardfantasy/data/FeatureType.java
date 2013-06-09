package cfvbaibai.cardfantasy.data;

import java.util.HashSet;

public enum FeatureType {
    ¾Ñ»÷(25),
    Á¬»·ÉÁµç(25, FeatureTag.Ä§·¨),
    ´©´Ì(15),
    Ê¥¹â(15, 15),
    ¸ñµ²(20),
    ÏİÚå(1, FeatureTag.ÏİÚå),
    ·´»÷(20),
    Ï÷Èõ(10),
    »ğÇò(25, FeatureTag.Ä§·¨),
    ±ùµ¯(20, FeatureTag.Ä§·¨),
    ±©»÷(20),
    ÊØ»¤(0),
    »Ø´º(30),
    ¿ñÈÈ(10),
    ºáÉ¨(0),
    ÉÁ±Ü(20, 5),
    ÖÎÁÆ(25),
    ¸ÊÁØ(25),
    Æíµ»(50),
    Íõ¹úÖ®Á¦(25),
    ·¨Á¦·´Éä(30),
    Ä§¼×(140, -10),
    ¶Ü´Ì(20),
    È¼ÉÕ(25),
    »ğÇ½(25, FeatureTag.Ä§·¨),
    ÁÒÑæ·ç±©(25, FeatureTag.Ä§·¨),
    À×±©(25, FeatureTag.Ä§·¨),
    ×ªÉú(30, 5),
    Íõ¹úÊØ»¤(50),
    Ëª¶³ĞÂĞÇ(20, FeatureTag.Ä§·¨),
    ±©·çÑ©(20, FeatureTag.Ä§·¨),
    ÁÑÉË(0),
    ÂäÀ×(25, FeatureTag.Ä§·¨),
    Çî×·ÃÍ´ò(15),
    ÍÑÀ§(0),
    ²»¶¯(0),
    ¸´»î(0, FeatureTag.¸´»î),
    ±³´Ì(40),
    ËÍ»¹(0, FeatureTag.¼´ËÀ),
    ÈºÌåÏ÷Èõ(5),

    // Unimplemented.
    ·âÓ¡(0, FeatureTag.ÏİÚå),
    ÃÔ»ê(0, FeatureTag.ÏİÚå),
    Î´Öª(0);
    

    private int initImpact;
    private int incrImpact;
    private HashSet <FeatureTag> tags;
    
    FeatureType(int incrImpact, FeatureTag ... tags) {
        this(0, incrImpact, tags);
    }
    
    FeatureType(int initImpact, int incrImpact, FeatureTag ... tags) {
        this.initImpact = initImpact;
        this.incrImpact = incrImpact;
        this.tags = new HashSet <FeatureTag> ();
        for (FeatureTag tag : tags) {
            this.tags.add(tag);
        }
    }
    
    public String getDisplayName() {
        return this.name();
    }

    public int getImpact(int level) {
        return initImpact + level * incrImpact;
    }
    
    public boolean containsTag(FeatureTag tag) {
        return this.tags.contains(tag);
    }
}
