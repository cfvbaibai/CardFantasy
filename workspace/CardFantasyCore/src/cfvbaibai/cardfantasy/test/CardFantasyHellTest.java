package cfvbaibai.cardfantasy.test;

import org.junit.Test;

public class CardFantasyHellTest {
    @Test
    public void ÑªÁ¶Î×Ñıvs°µÒ¹Ä§Ó°() {
        TestGameBuilder.play5v5("ÑªÁ¶Î×Ñı", "°µÒ¹Ä§Ó°");
    }

    @Test
    public void »ÙÃğÖ®Áúvs°µÒ¹Ä§Ó°() {
        TestGameBuilder.play5v5("»ÙÃğÖ®Áú", "°µÒ¹Ä§Ó°");
    }

    @Test
    public void »ÙÃğÖ®ÁúvsÁé»êÊÕ¸îÕß() {
        TestGameBuilder.play5v5("»ÙÃğÖ®Áú", "Áé»êÊÕ¸îÕß");
    }
}
