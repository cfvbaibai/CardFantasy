package cfvbaibai.cardfantasy.test;

import org.junit.Test;

import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.data.RuneData;

public class CardFantasyRuneTest {

    @Test
    public void ¾ÅÍ·ÑıÉßvÔ¶¹ÅĞ«»Ê_»ÄÎß() {
        GameBuilder.play5v5withRunes("¾ÅÍ·ÑıÉß", RuneData.»ÄÎß, "Ô¶¹ÅĞ«»Ê", RuneData.»ÄÎß);
    }

    @Test
    public void ½ğÊô¾ŞÁúvÔ¶¹ÅĞ«»Ê_ÕÓÔó() {
        GameBuilder.play5v5withRunes("½ğÊô¾ŞÁú", RuneData.»ÄÎß, "Ô¶¹ÅĞ«»Ê", RuneData.ÕÓÔó);
    }

    @Test
    public void ·ï»ËvÔ¶¹ÅĞ«»Ê_ÕÓÔó_»ÄÎß() {
        PlayerInfo playerA = PlayerBuilder.build("¡¾A¡¿", 50, "C·ï»Ë-10*5", "RÕÓÔó-4", "R»ÄÎß-4");
        PlayerInfo playerB = PlayerBuilder.build("¡¾B¡¿", 50, "CÔ¶¹ÅĞ«»Ê-10*5", "RÕÓÔó-4", "R»ÄÎß-4");
        GameBuilder.build(playerA, playerB).playGame();
    }

    @Test
    public void ½ğÊô¾ŞÁúvÔ¶¹ÅĞ«»Ê_ÑÒ¾§() {
        GameBuilder.play5v5withRunes("½ğÊô¾ŞÁú", RuneData.ÑÒ¾§, "Ô¶¹ÅĞ«»Ê", RuneData.ÑÒ¾§);
    }

    @Test
    public void ³ÇÕò¹­¼ı±øv¾«Áé¾Ñ»÷Õß_¶¾É°() {
        PlayerInfo playerA = PlayerBuilder.build("¡¾A¡¿", 100, "C³ÇÕò¹­¼ı±ø-10*15", "R¶¾É°-4");
        PlayerInfo playerB = PlayerBuilder.build("¡¾B¡¿", 100, "C¾«Áé¾Ñ»÷Õß-10*15", "R¶¾É°-4");
        GameBuilder.build(playerA, playerB).playGame();
    }

    @Test
    public void Å£Í·ÈËÇõ³¤vsÕ½¶·ÃÍáïÏó_ÑÒ±Ú() {
        GameBuilder.play5v5withRunes("Å£Í·ÈËÇõ³¤", RuneData.ÑÒ±Ú, "Õ½¶·ÃÍáïÏó", RuneData.ÑÒ±Ú);
    }

    @Test
    public void Ë®Ô´ÖÆÔìÕßvsÅ£Í·ÈËÇõ³¤_Ê¯ÁÖ() {
        GameBuilder.play5v5withRunes("Ë®Ô´ÖÆÔìÕß", RuneData.ÑÒ±Ú, "Å£Í·ÈËÇõ³¤", RuneData.Ê¯ÁÖ);
    }

    @Test
    public void ½ğÊô¾ŞÁúvs¶ÀÑÛ¾ŞÈË_³à¹È() {
        GameBuilder.play5v5withRunes("½ğÊô¾ŞÁú", RuneData.ÑÒ±Ú, "¶ÀÑÛ¾ŞÈË", RuneData.³à¹È);
    }

    @Test
    public void ·ï»ËvsÖ©ÖëÈËÅ®Íõ_·ÉÑÒ() {
        GameBuilder.play5v5withRunes("·ï»Ë", RuneData.ÑÒ±Ú, "Ö©ÖëÈËÅ®Íõ", RuneData.·ÉÑÒ);
    }

    @Test
    public void ½ğÊô¾ŞÁúvsÖ©ÖëÈËÅ®Íõ_»àÍÁ() {
        GameBuilder.play5v5withRunes("½ğÊô¾ŞÁú", RuneData.ÑÒ±Ú, "Ö©ÖëÈËÅ®Íõ", RuneData.»àÍÁ);
    }
}
