package cfvbaibai.cardfantasy.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import cfvbaibai.cardfantasy.Table;
import cfvbaibai.cardfantasy.data.CardData;
import cfvbaibai.cardfantasy.data.CardDataStore;
import cfvbaibai.cardfantasy.data.Legion;
import cfvbaibai.cardfantasy.data.PlayerInfo;
import cfvbaibai.cardfantasy.engine.GameEngine;
import cfvbaibai.cardfantasy.engine.GameResult;
import cfvbaibai.cardfantasy.engine.Rule;
import cfvbaibai.cardfantasy.game.DummyGameUI;
import cfvbaibai.cardfantasy.game.GameResultStat;
import cfvbaibai.cardfantasy.game.PlayerBuilder;

public class FreeTest extends PveEngineTest {

    private static GameResultStat massivePlay10v10(String card1, String card2, int level) {
        return play(PlayerBuilder.build("Ó¢ÐÛ" + card1, level, "C" + card1 + "-10*10"),
                PlayerBuilder.build("Ó¢ÐÛ" + card2, level, "C" + card2 + "-10*10"), 1000);
    }

    private static GameResultStat massivePlay1v1(String card1, String card2, int level) {
        return play(PlayerBuilder.build("Ó¢ÐÛ" + card1, level, "C" + card1 + "-10*1"),
                PlayerBuilder.build("Ó¢ÐÛ" + card2, level, "C" + card2 + "-10*1"), 1000);
    }

    private static GameResultStat massivePlay1v1Lv15(String card1, String card2, int level) {
        return play(PlayerBuilder.build("Ó¢ÐÛ" + card1, level, "C" + card1 + "-15"),
                PlayerBuilder.build("Ó¢ÐÛ" + card2, level, "C" + card2 + "-10"), 1000);
    }

    private static GameResultStat massivePlay10v10Lv15(String card1, String card2, int level) {
        return play(PlayerBuilder.build("Ó¢ÐÛ" + card1, level, "C" + card1 + "-15*10"),
                PlayerBuilder.build("Ó¢ÐÛ" + card2, level, "C" + card2 + "-10*10"), 1000);
    }

    private static void showStat(GameResultStat stat) {
        System.out.println("Total: " + stat.count());
        System.out.println(String.format("%s wins %d (%d %%)", stat.getP1().getId(), stat.getP1Win(),
                stat.getP1WinRate()));
        System.out.println(String.format("%s wins %d (%d %%)", stat.getP2().getId(), stat.getP2Win(),
                stat.getP2WinRate()));
    }

    private static GameResultStat play(PlayerInfo p1, PlayerInfo p2, int count) {
        GameResultStat stat = new GameResultStat(p1, p2);
        for (int i = 0; i < count; ++i) {
            GameEngine engine = new GameEngine(new DummyGameUI(), Rule.getDefault());
            engine.RegisterPlayers(p1, p2);
            GameResult result = engine.playGame();
            stat.addResult(result);
        }
        return stat;
    }

    @Test
    public void ¶éÂä¾«ÁévsÎåÐÇ_1000() throws IOException {
        FiveStarChallenge("¶éÂä¾«Áé", true, false);
    }

    @Test
    public void ±³Ö÷Ö®Ó°vsÎåÐÇ_1000() throws IOException {
        FiveStarChallenge("±³Ö÷Ö®Ó°", true, false);
    }

    @Test
    public void ´ó½£Ê¥vsÎåÐÇ_1000() throws IOException {
        FiveStarChallenge("´ó½£Ê¥", true, false);
    }

    @Test
    public void ½µÁÙÌìÊ¹vsÎåÐÇ_1000() throws IOException {
        FiveStarChallenge("½µÁÙÌìÊ¹", true, false);
    }

    @Test
    public void ÓÄÁé¾ÞÁúvsÎåÐÇ_1000() throws IOException {
        FiveStarChallenge("ÓÄÁé¾ÞÁú", true, false);
    }

    @Test
    public void ¿Ö¾åÖ®ÍõvsÎåÐÇ_1000() throws IOException {
        FiveStarChallenge("¿Ö¾åÖ®Íõ", true, false);
    }

    @Test
    public void ´©´Ì¹íÊ÷ÈËvsÎåÐÇ_1000_10v10() throws IOException {
        FiveStarChallenge("´©´Ì¹íÊ÷ÈË", true, true);
    }

    @Test
    public void ±©Å­°ÔÁúvsÎåÐÇ_1000() throws IOException {
        FiveStarChallenge("±©Å­°ÔÁú", false, false);
    }

    @Test
    public void ±©Å­°ÔÁúvsÎåÐÇ_10vs10_1000() throws IOException {
        FiveStarChallenge("±©Å­°ÔÁú", true, false);
    }

    @Test
    public void ´¿½àÊ¥Å®vsÎåÐÇ_1000() throws IOException {
        FiveStarChallenge("´¿½àÊ¥Å®", false, false);
    }

    @Test
    public void ´¿½àÊ¥Å®vsÎåÐÇ_10vs10_1000() throws IOException {
        FiveStarChallenge("´¿½àÊ¥Å®", true, false);
    }

    @Test
    public void ¶ÀÑÛ¾ÞÈËvsÎåÐÇ_1000() throws IOException {
        FiveStarChallenge("¶ÀÑÛ¾ÞÈË", false, false);
    }

    @Test
    public void ¶ÀÑÛ¾ÞÈËvsÎåÐÇ_10vs10_1000() throws IOException {
        FiveStarChallenge("¶ÀÑÛ¾ÞÈË", true, false);
    }

    @Test
    public void É­ÁÖÅ®ÉñvsÎåÐÇ_10vs10_1000() throws IOException {
        FiveStarChallenge("É­ÁÖÅ®Éñ", true, false);
    }

    @Test
    public void ²»¶¯Ö©ÖëvsÎåÐÇ_10vs10_1000() throws IOException {
        FiveStarChallenge("²»¶¯Ö©Öë", true, true);
    }

    @Test
    public void ¾ÅÍ·ÑýÉßvsÎåÐÇ_2vs2_1000() throws IOException {
        FiveStarChallenge("¾ÅÍ·ÑýÉß", false, false);
    }

    @Test
    public void »Ê¼ÒÑ±ÊÞÊ¦vsÎåÐÇ_10vs10_1000() throws IOException {
        FiveStarChallenge("»Ê¼ÒÑ±ÊÞÊ¦", true, false);
    }
    
    @Test
    public void Ä§ÉñÕ½() {
        PlayerInfo player = PlayerBuilder.build("Íæ¼Ò", 75, new Legion(10, 10, 10, 10), "¶éÂä¾«Áé*2", "´ãÁ¶");
        TestGameBuilder.playBossBattle(player, "¸´³ðÅ®Éñ");
    }
    
    @Test
    public void ºáÉ¨Bug() {
        TestGameBuilder.play5v5("¹âÃ÷Ö®Áú", "½ðÊô¾ÞÁú");
    }
    
    @Test
    public void ºáÉ¨ÑÒ±ÚBug() {
        PlayerInfo p1 = PlayerBuilder.build("Íõ¹úÓ¢ÐÛ", 75, "¹âÃ÷Ö®Áú*5");
        PlayerInfo p2 = PlayerBuilder.build("Âù»ÄÓ¢ÐÛ", 75, "Å£Í·ÈËÇõ³¤*5", "ÑÒ±Ú");
        TestGameBuilder.play(p1, p2);
    }
    
    @Test
    public void ºáÉ¨Õý³£() {
        TestGameBuilder.play5v5("¹âÃ÷Ö®Áú", "½µÁÙÌìÊ¹");
    }
    
    @Test
    public void ¾øÉ±Bug() {
        PlayerInfo player = PlayerBuilder.build("Íæ¼Ò", "½µÁÙÌìÊ¹*4,¹âÃ÷Ö®Áú*1,»ÙÃðÖ®Áú*3,×Æ»ê,±ù·â,ÇåÈª,¾øÉ±", 75);
        TestGameBuilder.playBossBattle(player, "¸´³ðÅ®Éñ");
    }
    
    @Test
    public void ËÀÆõ×çÖäBug() {
        PlayerInfo p1 = PlayerBuilder.build("Íæ¼Ò1", "½ðÊô¾ÞÁú*5,À×¶Ü", 75);
        PlayerInfo p2 = PlayerBuilder.build("Íæ¼Ò2", "Õ½¶·ÃÍáïÏó+ËÀÆõ×çÖä1,»àÍÁ", 75);
        TestGameBuilder.play(p1, p2);
    }
    
    @Test
    public void ÊØ»¤Æíµ»Bug() {
        PlayerInfo p1 = PlayerBuilder.build("Íæ¼Ò1", "Ð°ÁúÖ®Éñ", 75);
        PlayerInfo p2 = PlayerBuilder.build("Íæ¼Ò2", "Ä§½£Ê¿+Æíµ»7*2", 75);
        TestGameBuilder.play(p1, p2);
    }
    
    @Test
    public void Íõ¹ú×îÇ¿vsÉ­ÁÖ×îÇ¿_1000() {
        for (int i = 6; i < 10; ++i) {
            System.out.println("Level: " + (i * 10));
            PlayerInfo p1 = PlayerBuilder.build("Íõ¹úÓ¢ÐÛ", i * 10, "C¹âÃ÷Ö®Áú-10*10", "Rº®ÉË-4", "RÇåÈª-4", "R±ù·â-4");// ,
                                                                                                          // "RÅ­ÌÎ-4");
            PlayerInfo p2 = PlayerBuilder.build("É­ÁÖÓ¢ÐÛ", i * 10, "C½ðÊô¾ÞÁú-10*10", "RÀ×¶Ü-4", "R´º·ç-4", "RÇáÁé-4");// ,
                                                                                                          // "R´ãÁ¶-4");
            showStat(play(p1, p2, 1000));
        }
    }

    @Test
    public void Íõ¹ú×îÇ¿vsÍõÉ­×îÇ¿_1000() {
        for (int i = 6; i < 10; ++i) {
            System.out.println("Level: " + (i * 10));
            PlayerInfo p1 = PlayerBuilder.build("Íõ¹úÓ¢ÐÛ", i * 10, "C¹âÃ÷Ö®Áú-10*10", "Rº®ÉË-4", "RÇåÈª-4", "R±ù·â-4", "RÅ­ÌÎ-4");
            PlayerInfo p2 = PlayerBuilder.build("ÍõÉ­Ó¢ÐÛ", i * 10, "C½ðÊô¾ÞÁú-10*5", "C½µÁÙÌìÊ¹-10*5", "RÀ×¶Ü-4", "R´º·ç-4", "R±ù·â-4",
                    "RÇåÈª-4");
            showStat(play(p2, p1, 10000));
        }
    }

    @Test
    public void Íõ¹ú×îÇ¿vsÂù»Ä×îÇ¿_1000() {
        for (int i = 6; i < 10; ++i) {
            System.out.println("Level: " + (i * 10));
            PlayerInfo p1 = PlayerBuilder.build("Íõ¹úÓ¢ÐÛ", i * 10, "C¹âÃ÷Ö®Áú-10*10", "Rº®ÉË-4", "RÇåÈª-4", "R±ù·â-4", "R¶´²ì-4");
            PlayerInfo p2 = PlayerBuilder.build("Âù»ÄÓ¢ÐÛ", i * 10, "CÀ×ÊÞ-10*10", "RÑÒ±Ú-4", "R³à¹È-4", "R·ÉÑÒ-4", "R»àÍÁ-4");
            showStat(play(p2, p1, 1000));
        }
    }

    @Test
    public void ×ªÉúÒ»µ¶vsÊ®Îå½£Ê¥_1000() {
        for (int i = 6; i < 10; ++i) {
            System.out.println("Level: " + (i * 10));
            PlayerInfo p1 = PlayerBuilder.build("Ò»µ¶Ó¢ÐÛ", i * 10, "C×ªÉú¶éÂä¾«Áé-15*10", "R×Æ»ê-4", "RÃðÊÀ-4", "R´ãÁ¶-4", "Rì«·ç-4");
            PlayerInfo p2 = PlayerBuilder.build("½£Ê¥Ó¢ÐÛ", i * 10, "C´ó½£Ê¥-15*10", "RÓÀ¶³-4", "R±ù·â-4", "RÇåÈª-4", "Rº®ÉË-4");
            showStat(play(p1, p2, 1000));
        }
    }

    @Test
    public void ¹âÁúvsÊ÷ÈË_1000() {
        for (int i = 6; i < 10; ++i) {
            System.out.println("Level: " + (i * 10));
            PlayerInfo p1 = PlayerBuilder.build("¹âÁúÓ¢ÐÛ", i * 10, "C¹âÃ÷Ö®Áú-10*10", "R±ù·â-4", "RÇåÈª-4", "Rº®ÉË-4", "RÓÀ¶³-4");
            PlayerInfo p2 = PlayerBuilder.build("Ê÷ÈËÓ¢ÐÛ", i * 10, "CËªÑ©Ê÷ÈË-10*6", "CÊ÷ÈË¼ÀË¾-10*4", "RÀ×¶Ü-4", "R´º·ç-4", "RÇáÁé-4");
            showStat(play(p1, p2, 1000));
        }
    }

    @Test
    public void ¹âÁúvs´óÏó_1000() {
        for (int i = 6; i < 10; ++i) {
            System.out.println("Level: " + (i * 10));
            PlayerInfo p1 = PlayerBuilder.build("¹âÁúÓ¢ÐÛ", i * 10, "C¹âÃ÷Ö®Áú-10*10", "R±ù·â-4", "RÇåÈª-4", "Rì«·ç-4", "RÓÀ¶³-4");
            PlayerInfo p2 = PlayerBuilder.build("´óÏóÓ¢ÐÛ", i * 10, "CÕ½¶·ÃÍáïÏó-10*10", "R³à¹È-4", "R·ÉÑÒ-4", "R»àÍÁ-4", "RÅ­ÌÎ-4");
            showStat(play(p2, p1, 1000));
        }
    }

    @Test
    public void ¹âÁúvs´óÏó() {
        PlayerInfo p1 = PlayerBuilder.build("¹âÁúÓ¢ÐÛ", 90, "C¹âÃ÷Ö®Áú-10*10", "R±ù·â-4", "RÇåÈª-4", "Rì«·ç-4", "RÓÀ¶³-4");
        PlayerInfo p2 = PlayerBuilder.build("´óÏóÓ¢ÐÛ", 90, "CÕ½¶·ÃÍáïÏó-10*10", "R³à¹È-4", "R·ÉÑÒ-4", "R»àÍÁ-4", "RÅ­ÌÎ-4");
        TestGameBuilder.play(p1, p2);
    }

    @Test
    public void ÌìÊ¹vsÖ©Öë() {
        PlayerInfo p1 = PlayerBuilder.build("ÌìÊ¹Ó¢ÐÛ", 90, "C½µÁÙÌìÊ¹-10*10");
        PlayerInfo p2 = PlayerBuilder.build("Ö©ÖëÓ¢ÐÛ", 90, "CÖ©ÖëÈËÅ®Íõ-10*10");
        showStat(play(p1, p2, 1000));
        showStat(play(p2, p1, 1000));
    }

    @Test
    public void ²»¶¯´óÏóvs×ªÉúÌìÊ¹() {
        PlayerInfo p1 = PlayerBuilder.build("´óÏóÓ¢ÐÛ", "Õ½¶·ÃÍáïÏó+²»¶¯*5,Õ½¶·ÃÍáïÏó+ºáÉ¨*5,³à¹È", 90);
        PlayerInfo p2 = PlayerBuilder.build("ÌìÊ¹Ó¢ÐÛ", "½µÁÙÌìÊ¹+×ªÉú5*5,½µÁÙÌìÊ¹+ÏÝÚå2*5,±ù·â", 90);
        TestGameBuilder.play(p1, p2);
    }
    
    @Test
    public void ºáÉ¨´óÏóvs×ªÉúÌìÊ¹() { 
        PlayerInfo p1 = PlayerBuilder.build("´óÏóÓ¢ÐÛ", "Õ½¶·ÃÍáïÏó+ºáÉ¨*5", 90);
        PlayerInfo p2 = PlayerBuilder.build("ÌìÊ¹Ó¢ÐÛ", "½µÁÙÌìÊ¹+×ªÉú5*5", 90);
        TestGameBuilder.play(p1, p2);
    }
    
    @Test
    public void ·ÅÐÄÍ×Í×¶ùµÄ() {
        PlayerInfo p1 = PlayerBuilder.build("Íæ¼Ò1", "½µÁÙÌìÊ¹*3,½ðÊô¾ÞÁú*3,Ä§·¨Ð­»á³¤,ÃØÒø¾ÞÊ¯Ïñ-15,ÕðÔ´ÑÒó¸-15,¸´»î½ÚÍÃÅ®ÀÉ,±ù·â,ÓÀ¶³,ÇåÈª,À×¶Ü", 68);
        PlayerInfo p2 = PlayerBuilder.build("Íæ¼Ò2", "½µÁÙÌìÊ¹*5,ÊÀ½çÊ÷Ö®Áé*2,½ðÊô¾ÞÁú*2,±ù·â,ÓÀ¶³,ÇåÈª,À×¶Ü", 64);
        TestGameBuilder.play(p1, p2);
    }

    @Test
    public void ¾º¼¼³¡²âÊÔ1() {
        PlayerInfo p1 = PlayerBuilder.build("ÎÒ·½Ó¢ÐÛ", 74, "C½ðÊô¾ÞÁú-10*4", "C¿Ö¾åÖ®Íõ-10*4", "CÁé»êÊÕ¸îÕß-10*2", "RÀ×¶Ü-4", "RÀ×Óü-4",
                "R×Æ»ê-4", "R¾øÉ±-4");
        PlayerInfo p2 = PlayerBuilder.build("µÐ·½Ó¢ÐÛ", 84, "C½ðÊô¾ÞÁú-10*4", "C½µÁÙÌìÊ¹-10*6", "R±ù·â-4", "RÇåÈª-4", "RÀ×¶Ü-4", "RÓÀ¶³-4");
        showStat(play(p1, p2, 1000));
        showStat(play(p2, p1, 1000));
        PlayerInfo p3 = PlayerBuilder.build("µÐ·½Ó¢ÐÛ", 84, "C½ðÊô¾ÞÁú-10*5", "C½µÁÙÌìÊ¹-10*5", "R±ù·â-4", "RÇåÈª-4", "RÀ×¶Ü-4", "RÇáÁé-4");
        showStat(play(p1, p3, 1000));
        showStat(play(p3, p1, 1000));
    }

    @Test
    public void ¾º¼¼³¡²âÊÔ2() {
        PlayerInfo p0 = PlayerBuilder.build("µÐ·½Ó¢ÐÛ", 75, "C½ðÊô¾ÞÁú-10*5", "C½µÁÙÌìÊ¹-10*5", "R±ù·â-4", "RÇåÈª-4", "RÀ×¶Ü-4", "RÇáÁé-4");
        PlayerInfo p1 = PlayerBuilder.build("ÎÒ·½Ó¢ÐÛ", 75, "C¹âÃ÷Ö®Áú-10*2", "C½µÁÙÌìÊ¹-10*2", "CÄ§·¨Ð­»á³¤-10", "CÁé»êÊÕ¸îÕß-10*2",
                "C½ðÊô¾ÞÁú-10*3", "RÀ×¶Ü-4", "RÇåÈª-4", "RÓÀ¶³-4", "R±ù·â-4");
        showStat(play(p0, p1, 1000));
        showStat(play(p1, p0, 1000));
        PlayerInfo p2 = PlayerBuilder.build("ÎÒ·½Ó¢ÐÛ", 75, "C¹âÃ÷Ö®Áú-10*2", "C½µÁÙÌìÊ¹-10*2", "CÄ§·¨Ð­»á³¤-10", "CÁé»êÊÕ¸îÕß-10",
                "C¿Ö¾åÖ®Íõ-10", "C½ðÊô¾ÞÁú-10*3", "RÀ×¶Ü-4", "RÇåÈª-4", "RÓÀ¶³-4", "R±ù·â-4");
        showStat(play(p0, p2, 1000));
        showStat(play(p2, p0, 1000));
        PlayerInfo p3 = PlayerBuilder.build("ÎÒ·½Ó¢ÐÛ", 75, "C¹âÃ÷Ö®Áú-10", "C½µÁÙÌìÊ¹-10*3", "CÄ§·¨Ð­»á³¤-10", "CÁé»êÊÕ¸îÕß-10*2",
                "C½ðÊô¾ÞÁú-10*3", "RÀ×¶Ü-4", "RÇåÈª-4", "RÓÀ¶³-4", "R±ù·â-4");
        showStat(play(p0, p3, 1000));
        showStat(play(p3, p0, 1000));
        PlayerInfo p4 = PlayerBuilder.build("ÎÒ·½Ó¢ÐÛ", 75, "C¹âÃ÷Ö®Áú-10", "C½µÁÙÌìÊ¹-10*3", "CÄ§·¨Ð­»á³¤-10", "CÁé»êÊÕ¸îÕß-10", "C¿Ö¾åÖ®Íõ-10",
                "C½ðÊô¾ÞÁú-10*3", "RÀ×¶Ü-4", "RÇåÈª-4", "RÓÀ¶³-4", "R±ù·â-4");
        showStat(play(p0, p4, 1000));
        showStat(play(p4, p0, 1000));
    }

    @Test
    public void ¾º¼¼³¡²âÊÔ3() {
        PlayerInfo p0 = PlayerBuilder.build("µÐ·½Ó¢ÐÛ", 75, "C½ðÊô¾ÞÁú-10*6", "C½µÁÙÌìÊ¹-10*4", "R±ù·â-4", "RÇåÈª-4", "RÀ×¶Ü-4", "RÓÀ¶³-4");
        PlayerInfo p1 = PlayerBuilder.build("ÎÒ·½Ó¢ÐÛ", 75, "C½ðÊô¾ÞÁú-10*3", "C¸´»î½ÚÍÃÅ®ÀÉ-10*2", "CÀ×ÊÞ-10*3", "CÕðÔ´ÑÒó¸-10*1",
                "CÓðÒí»¯Éß-10*1", "RÀ×¶Ü-4", "R´º·ç-4", "RÑÒ±Ú-4", "R³à¹È-4");
        showStat(play(p0, p1, 1000));
        showStat(play(p1, p0, 1000));
        PlayerInfo p2 = PlayerBuilder.build("ÎÒ·½Ó¢ÐÛ", 75, "C½ðÊô¾ÞÁú-10*3", "C¸´»î½ÚÍÃÅ®ÀÉ-10*1", "CÀ×ÊÞ-10*4", "CÕðÔ´ÑÒó¸-10*1",
                "CÓðÒí»¯Éß-10*1", "RÀ×¶Ü-4", "R´º·ç-4", "RÑÒ±Ú-4", "R³à¹È-4");
        showStat(play(p0, p2, 1000));
        showStat(play(p2, p0, 1000));
        PlayerInfo p3 = PlayerBuilder.build("ÎÒ·½Ó¢ÐÛ", 75, "C½ðÊô¾ÞÁú-10*4", "CÀ×ÊÞ-10*4", "CÕðÔ´ÑÒó¸-10*1", "CÓðÒí»¯Éß-10*1", "RÀ×¶Ü-4",
                "R´º·ç-4", "RÑÒ±Ú-4", "R³à¹È-4");
        showStat(play(p0, p3, 1000));
        showStat(play(p3, p0, 1000));
        PlayerInfo p4 = PlayerBuilder.build("ÎÒ·½Ó¢ÐÛ", 75, "C½ðÊô¾ÞÁú-10*4", "C¸´»î½ÚÍÃÅ®ÀÉ-10*1", "CÀ×ÊÞ-10*4", "CÓðÒí»¯Éß-10*1",
                "RÀ×¶Ü-4", "R´º·ç-4", "RÑÒ±Ú-4", "R³à¹È-4");
        showStat(play(p0, p4, 1000));
        showStat(play(p4, p0, 1000));
    }

    @Test
    public void ¾º¼¼³¡²âÊÔ4() {
        PlayerInfo p0 = PlayerBuilder.build("ÕóÈÝ1", 75, "C½ðÊô¾ÞÁú-10*5", "C½µÁÙÌìÊ¹-10*5", "R±ù·â-4", "RÇåÈª-4", "RÀ×¶Ü-4", "RÀ×Óü-4");
        PlayerInfo p1 = PlayerBuilder.build("ÕóÈÝ2", 75, "C²»¶¯ÃÍáï-15*10", "R»àÍÁ-4", "RÊ¯ÁÖ-4", "R´ãÁ¶-4", "R³à¹È-4");
        PlayerInfo p2 = PlayerBuilder.build("ÕóÈÝ3", 75, "C½µÁÙÌìÊ¹-10*5", "CÁé»êÊÕ¸îÕß-10*2", "CÍöÁéÊØ»¤Éñ-10", "C¿Ö¾åÖ®Íõ-10*2", "R±ù·â-4",
                "RÇåÈª-4", "R×Æ»ê-4", "R¾øÉ±-4");
        PlayerInfo p3 = PlayerBuilder.build("ÕóÈÝ4", 75, "C²»¶¯ÃÍáï-15*4", "C´©´Ì¸òó¡-10*2", "C¿Ö¾åÖ®Íõ-10*3", "CÍöÁéÊØ»¤Éñ-10", "RÊ¯ÁÖ-4",
                "R×Æ»ê-4", "R¾øÉ±-4", "R³à¹È-4");
        showStat(play(p0, p1, 1000));
        showStat(play(p0, p2, 1000));
        showStat(play(p0, p3, 1000));

        showStat(play(p1, p0, 1000));
        showStat(play(p1, p2, 1000));
        showStat(play(p1, p3, 1000));

        showStat(play(p2, p0, 1000));
        showStat(play(p2, p1, 1000));
        showStat(play(p2, p3, 1000));

        showStat(play(p3, p0, 1000));
        showStat(play(p3, p1, 1000));
        showStat(play(p3, p2, 1000));
    }
    
    @Test
    public void ¾º¼¼³¡²âÊÔ5() {
        PlayerInfo p0 = PlayerBuilder.build("×îÇ¿ÍõÉ­", 75, "C½ðÊô¾ÞÁú-10*5", "C½µÁÙÌìÊ¹-10*5", "R±ù·â-4", "RÇåÈª-4", "RÀ×¶Ü-4", "RÓÀ¶³-4");
        PlayerInfo p1 = PlayerBuilder.build("´¬·ò¶Ó", 75, "CÚ¤ºÓ´¬·ò-10","CÁé»êÊÕ¸îÕß-10*3", "CÄ§·¨Ð­»á³¤-10", "C½µÁÙÌìÊ¹-10*5",
                "R±ù·â-4", "RÇåÈª-4", "RÓÀ¶³-4", "R×Æ»ê-4");
        PlayerInfo p2 = PlayerBuilder.build("ÀÏÍ·Õ½Éñ1¶Ó", 75, "CÄ§·¨Ð­»á³¤-10", "CÕ½Éñ-10*2", "C½µÁÙÌìÊ¹-10*3", "CÁé»êÊÕ¸îÕß-10*4",
                "R±ù·â-4", "RÇåÈª-4", "RÓÀ¶³-4", "R×Æ»ê-4");
        PlayerInfo p3 = PlayerBuilder.build("ÀÏÍ·Õ½Éñ2¶Ó", 75, "CÄ§·¨Ð­»á³¤-10", "CÕ½Éñ-10*2", "C½µÁÙÌìÊ¹-10*3", "CÁé»êÊÕ¸îÕß-10*3", "CÚ¤ºÓ´¬·ò-10",
                "R±ù·â-4", "RÇåÈª-4", "RÓÀ¶³-4", "R×Æ»ê-4");
        showStat(play(p0, p1, 1000));
        showStat(play(p1, p0, 1000));
        showStat(play(p0, p2, 1000));
        showStat(play(p2, p0, 1000));
        showStat(play(p0, p3, 1000));
        showStat(play(p3, p0, 1000));
    }
    
    @Test
    public void ¾º¼¼³¡²âÊÔ6() {
        PlayerInfo p0 = PlayerBuilder.build("×îÇ¿ÍõÉ­", 75, "C½ðÊô¾ÞÁú-10*5", "C½µÁÙÌìÊ¹-10*5", "R±ù·â-4", "R´º·ç-4", "RÀ×¶Ü-4", "RÓÀ¶³-4");
        //4À×ÊÞ1»¯Éß3½ðÊô2ÍÃ×Ó ³à¹ÈÑÒ±ÚÀ×¶Ü´º·ç
        PlayerInfo p1 = PlayerBuilder.build("ÂùÉ­Ò»¶Ó", 75, "CÀ×ÊÞ-10*4", "CÓðÒí»¯Éß-10", "C½ðÊô¾ÞÁú-10*3", "C¸´»î½ÚÍÃÅ®ÀÉ-10*2",
                "R³à¹È-4", "RÑÒ±Ú-4", "RÀ×¶Ü-4", "R´º·ç-4");
        //2À×ÊÞ2¶¾Îí1»¯Éß3½ðÊô2ÍÃ×Ó
        PlayerInfo p2 = PlayerBuilder.build("ÂùÉ­¶þ¶Ó", 75, "CÀ×ÊÞ-10*2", "C¶¾ÎíÓðÁú-10*2", "CÓðÒí»¯Éß-10", "C½ðÊô¾ÞÁú-10*3", "C¸´»î½ÚÍÃÅ®ÀÉ-10*2",
                "R³à¹È-4", "RÑÒ±Ú-4", "RÀ×¶Ü-4", "R´º·ç-4");
        showStat(play(p0, p1, 1000));
        showStat(play(p1, p0, 1000));
        showStat(play(p0, p2, 1000));
        showStat(play(p2, p0, 1000));
    }
    
    
    @Test
    public void ¾º¼¼³¡²âÊÔ7() {
        PlayerInfo p0 = PlayerBuilder.build("×îÇ¿ÍõÉ­", 75, "C½ðÊô¾ÞÁú-10*5", "C½µÁÙÌìÊ¹-10*5", "R±ù·â-4", "R´º·ç-4", "RÀ×¶Ü-4", "RÓÀ¶³-4");
        // 3À×ÊÞ1¶¾Áú1¸òó¡3½ðÊô2ÍÃ×Ó À×¶Ü´º·ç³à¹ÈÑÒ±Ú
        PlayerInfo p00 = PlayerBuilder.build("×îÇ¿ÂùÉ­", 75, "CÀ×ÊÞ-10*3", "C¶¾ÎíÓðÁú-10", "CÕðÔ´ÑÒó¸-10", "C½ðÊô¾ÞÁú-10*3", "C¸´»î½ÚÍÃÅ®ÀÉ-10*2",
                "R³à¹È-4", "R´º·ç-4", "RÀ×¶Ü-4", "RÑÒ±Ú-4");
        //4ÌìÊ¹£¬ÀÏÍ·£¬2¿Ö¾å£¬2ÂÜÀò£¬1ºÚÁú£¬·ûÎÄ×Æ»ê±ù·âÇåÈªÓÀ¶³
        PlayerInfo p1 = PlayerBuilder.build("´òÁ³Ò»¶Ó", 75, "C½µÁÙÌìÊ¹-10*4", "CÄ§·¨Ð­»á³¤-10", "C¿Ö¾åÖ®Íõ-10*2", "CÁé»êÊÕ¸îÕß-10*2", "C»ÙÃðÖ®Áú-10",
                "R×Æ»ê-4", "R±ù·â-4", "RÇåÈª-4", "RÓÀ¶³-4");
        //4ÌìÊ¹£¬ÀÏÍ·£¬4¿Ö¾å£¬1ºÚÁú
        PlayerInfo p2 = PlayerBuilder.build("´òÁ³¶þ¶Ó", 75, "C½µÁÙÌìÊ¹-10*4", "CÄ§·¨Ð­»á³¤-10", "C¿Ö¾åÖ®Íõ-10*4", "C»ÙÃðÖ®Áú-10",
                "R×Æ»ê-4", "R±ù·â-4", "RÇåÈª-4", "RÓÀ¶³-4");
        showStat(play(p0, p1, 1000));
        showStat(play(p1, p0, 1000));
        showStat(play(p0, p2, 1000));
        showStat(play(p2, p0, 1000));
        
        showStat(play(p00, p1, 1000));
        showStat(play(p1, p00, 1000));
        showStat(play(p00, p2, 1000));
        showStat(play(p2, p00, 1000));
    }
    
    
    @Test
    public void ¾º¼¼³¡²âÊÔ8() {
        PlayerInfo p0 = PlayerBuilder.build("×îÇ¿ÍõÉ­", 85, "C½ðÊô¾ÞÁú-10*5", "C½µÁÙÌìÊ¹-10*5", "R±ù·â-4", "R´º·ç-4", "RÀ×¶Ü-4", "RÓÀ¶³-4");
        // ½ðÊô*1£¬Ë®Ô´*1£¬·ï»Ë*2£¬ÀÏÍ·*1£¬ ÃØÒø*1£¬»úÐµ·ÉÁú*1£¬½£Ê¥*1£¬ÂÜÀò*1£¬¸òó¡*1, À×¶Ü£¬´º·ç£¬±ù·â£¬´ãÁ¶
        PlayerInfo p1 = PlayerBuilder.build("ÕóÈÝ1", 85, "C½ðÊô¾ÞÁú-10", "CË®Ô´ÖÆÔìÕß-10", "C·ï»Ë-10*2", "CÄ§·¨Ð­»á³¤-10", "CÃØÒø¾ÞÊ¯Ïñ-10", "C»úÐµ·ÉÁú-10",
                "C´ó½£Ê¥-10", "CÁé»êÊÕ¸îÕß-10", "CÕðÔ´ÑÒó¸-10", "R±ù·â-4", "R´º·ç-4", "RÀ×¶Ü-4", "R´ãÁ¶-4");
        // ½ðÊô*1£¬Ë®Ô´*1£¬·ï»Ë*2£¬ÀÏÍ·*1£¬ ÃØÒø*1£¬»úÐµ·ÉÁú*1£¬»Ê¼ÒÎÀ¶Ó³¤*1£¬ÂÜÀò*1£¬¸òó¡*1, À×¶Ü£¬´º·ç£¬±ù·â£¬´ãÁ¶
        PlayerInfo p2 = PlayerBuilder.build("ÕóÈÝ2", 85, "C½ðÊô¾ÞÁú-10", "CË®Ô´ÖÆÔìÕß-10", "C·ï»Ë-10*2", "CÄ§·¨Ð­»á³¤-10", "CÃØÒø¾ÞÊ¯Ïñ-10", "C»úÐµ·ÉÁú-10",
                "C»Ê¼ÒÎÀ¶Ó½«Áì-10", "CÁé»êÊÕ¸îÕß-10", "CÕðÔ´ÑÒó¸-10", "R±ù·â-4", "R´º·ç-4", "RÀ×¶Ü-4", "R´ãÁ¶-4");
        // ½ðÊô*1£¬Ë®Ô´*1£¬·ï»Ë*2£¬ÀÏÍ·*1£¬ ÃØÒø*1£¬»úÐµ·ÉÁú*1£¬´óÖ÷½Ì*1£¬ÂÜÀò*1£¬¸òó¡*1, À×¶Ü£¬´º·ç£¬±ù·â£¬´ãÁ¶
        PlayerInfo p3 = PlayerBuilder.build("ÕóÈÝ3", 85, "C½ðÊô¾ÞÁú-10", "CË®Ô´ÖÆÔìÕß-10", "C·ï»Ë-10*2", "CÄ§·¨Ð­»á³¤-10", "CÃØÒø¾ÞÊ¯Ïñ-10", "C»úÐµ·ÉÁú-10",
                "C´óÖ÷½Ì-10", "CÁé»êÊÕ¸îÕß-10", "CÕðÔ´ÑÒó¸-10", "R±ù·â-4", "R´º·ç-4", "RÀ×¶Ü-4", "R´ãÁ¶-4");
        // ½ðÊô*1£¬Ë®Ô´*1£¬·ï»Ë*2£¬ÀÏÍ·*1£¬ ÃØÒø*1£¬»úÐµ·ÉÁú*1£¬»Ê¼ÒÑ±ÊÞÊ¦£¬ÂÜÀò*1£¬¸òó¡*1, À×¶Ü£¬´º·ç£¬±ù·â£¬´ãÁ¶
        PlayerInfo p4 = PlayerBuilder.build("ÕóÈÝ4", 85, "C½ðÊô¾ÞÁú-10", "CË®Ô´ÖÆÔìÕß-10", "C·ï»Ë-10*2", "CÄ§·¨Ð­»á³¤-10", "CÃØÒø¾ÞÊ¯Ïñ-10", "C»úÐµ·ÉÁú-10",
                "C»Ê¼ÒÑ±ÊÞÊ¦-10", "CÁé»êÊÕ¸îÕß-10", "CÕðÔ´ÑÒó¸-10", "R±ù·â-4", "R´º·ç-4", "RÀ×¶Ü-4", "R´ãÁ¶-4");
        // ½ðÊô1£¬Ë®Ô´1,·ï»Ë2£¬É­Å®1£¬ÀÏÍ·1£¬ÂÜÀò1£¬¸òó¡1£¬ÐÜÈËÎäÊ¿1£¬ÃÍáï1, ´º·çÀ×¶Ü³à¹È´ãÁ¶
        PlayerInfo p5 = PlayerBuilder.build("ÕóÈÝ5", 85, "C½ðÊô¾ÞÁú-10", "CË®Ô´ÖÆÔìÕß-10", "C·ï»Ë-10*2", "CÉ­ÁÖÅ®Éñ-10", "CÄ§·¨Ð­»á³¤-10", 
                "CÁé»êÊÕ¸îÕß-10", "CÕðÔ´ÑÒó¸-10", "CÐÜÈËÎäÊ¿-10", "CÕ½¶·ÃÍáïÏó-10", "R³à¹È-4", "R´º·ç-4", "RÀ×¶Ü-4", "R´ãÁ¶-4");
        // ½ðÊô1£¬Ë®Ô´2,·ï»Ë2£¬ÀÏÍ·1£¬ÂÜÀò1£¬¸òó¡1£¬ÐÜÈËÎäÊ¿1£¬ÃÍáï1, ´º·çÀ×¶Ü³à¹È´ãÁ¶
        PlayerInfo p6 = PlayerBuilder.build("ÕóÈÝ6", 85, "C½ðÊô¾ÞÁú-10", "CË®Ô´ÖÆÔìÕß-10*2", "C·ï»Ë-10*2", "CÄ§·¨Ð­»á³¤-10", 
                "CÁé»êÊÕ¸îÕß-10", "CÕðÔ´ÑÒó¸-10", "CÐÜÈËÎäÊ¿-10", "CÕ½¶·ÃÍáïÏó-10", "R³à¹È-4", "R´º·ç-4", "RÀ×¶Ü-4", "R´ãÁ¶-4");
        
        showStat(play(p0, p1, 1000));
        showStat(play(p1, p0, 1000));
        showStat(play(p0, p2, 1000));
        showStat(play(p2, p0, 1000));
        showStat(play(p0, p3, 1000));
        showStat(play(p3, p0, 1000));
        showStat(play(p0, p4, 1000));
        showStat(play(p4, p0, 1000));
        showStat(play(p0, p5, 1000));
        showStat(play(p5, p0, 1000));
        showStat(play(p0, p6, 1000));
        showStat(play(p6, p0, 1000));
        
        showStat(play(p1, p2, 1000));
        showStat(play(p1, p3, 1000));
        showStat(play(p1, p4, 1000));
        showStat(play(p1, p5, 1000));
        showStat(play(p1, p6, 1000));
        
        showStat(play(p2, p1, 1000));
        showStat(play(p2, p3, 1000));
        showStat(play(p2, p4, 1000));
        showStat(play(p2, p5, 1000));
        showStat(play(p2, p6, 1000));
        
        showStat(play(p3, p1, 1000));
        showStat(play(p3, p2, 1000));
        showStat(play(p3, p4, 1000));
        showStat(play(p3, p5, 1000));
        showStat(play(p3, p6, 1000));
        
        showStat(play(p4, p1, 1000));
        showStat(play(p4, p2, 1000));
        showStat(play(p4, p3, 1000));
        showStat(play(p4, p5, 1000));
        showStat(play(p4, p6, 1000));
        
        showStat(play(p5, p1, 1000));
        showStat(play(p5, p2, 1000));
        showStat(play(p5, p3, 1000));
        showStat(play(p5, p4, 1000));
        showStat(play(p5, p6, 1000));
        
        showStat(play(p6, p1, 1000));
        showStat(play(p6, p2, 1000));
        showStat(play(p6, p3, 1000));
        showStat(play(p6, p4, 1000));
        showStat(play(p6, p5, 1000));
        
        
    }
    
    @Test
    public void ÓÄÁéÖ®Áúvs¿Ö¾å_1000() {
        PlayerInfo p1 = PlayerBuilder.build("Ó¢ÐÛÓÄÁéÁú", 80, "CÓÄÁé¾ÞÁú-10*1");
        PlayerInfo p2 = PlayerBuilder.build("Ó¢ÐÛ¿Ö¾åÖ®Íõ", 80, "C¿Ö¾åÖ®Íõ-10*1");
        showStat(play(p2, p1, 1000));
    }

    private void FiveStarChallenge(String cardName, boolean is10v10, boolean isLevel15) throws IOException {
        CardDataStore store = CardDataStore.loadDefault();
        int[] heroLevels = new int[] { 60, 70, 80, 90 };
        Table<String> table = new Table<String>();
        table.setCell(0, 0, "Ó¢ÐÛµÈ¼¶");
        for (int i = 0; i < heroLevels.length; ++i) {
            table.setCell(0, i + 1, String.valueOf(heroLevels[i]));
        }
        List<CardData> cards = store.getCardOfStar(5);
        for (int i = 0; i < cards.size(); ++i) {
            table.setCell(i + 1, 0, cards.get(i).getName());
        }
        for (int i = 0; i < heroLevels.length; ++i) {
            int heroLevel = heroLevels[i];
            for (int j = 0; j < cards.size(); ++j) {
                System.out.println("Level: " + heroLevel + ", Card: " + cards.get(j).getName());
                GameResultStat stat = null;
                if (is10v10) {
                    if (isLevel15) {
                        stat = massivePlay10v10Lv15(cardName, cards.get(j).getName(), heroLevel);
                    } else {
                        stat = massivePlay10v10(cardName, cards.get(j).getName(), heroLevel);
                    }
                } else {
                    if (isLevel15) {
                        stat = massivePlay1v1Lv15(cardName, cards.get(j).getName(), heroLevel);
                    } else {
                        stat = massivePlay1v1(cardName, cards.get(j).getName(), heroLevel);
                    }
                }
                table.setCell(j + 1, i + 1, String.valueOf(stat.getP1Win()));
            }
        }
        table.outputToCsv(new File("E:\\My\\Documents\\FallenElfvsStar5s.csv"));
    }

    @Test
    public void TestCsvWriter() throws IOException {
        CsvWriter writer = new CsvWriter(new File("E:\\My\\Documents\\Test.csv"));
        writer.writeFields(new Object[] { "A", "b", "C" });
        writer.writeFields(new Object[] { "A", "b", "C" });
        writer.writeFields(new Object[] { "A", "b", "C" });
        writer.close();
    }

    @Test
    public void ¶éÂä¾«Áévs´¿½àÊ¥Å®() {
        TestGameBuilder.play10v10("¶éÂä¾«Áé", "½ðÊô¾ÞÁú");
    }

    @Test
    public void ¶éÂä¾«Áévs¿Ö¾åÖ®Íõ() {
        TestGameBuilder.play10v10("¶éÂä¾«Áé", "¿Ö¾åÖ®Íõ");
    }

    @Test
    public void ¿Ö¾åÖ®Íõvs¶éÂä¾«Áé() {
        TestGameBuilder.play10v10("¿Ö¾åÖ®Íõ", "¶éÂä¾«Áé");
    }

    @Test
    public void ´ó½£Ê¥vsÕ½Éñ() {
        TestGameBuilder.play10v10("´ó½£Ê¥", "Õ½Éñ");
    }

    @Test
    public void ½µÁÙÌìÊ¹vsÔÂÁÁÅ®Éñ() {
        TestGameBuilder.play10v10("½µÁÙÌìÊ¹", "ÔÂÁÁÅ®Éñ");
    }

    @Test
    public void ½µÁÙÌìÊ¹vsÒþÊÀÏÈÖª() {
        TestGameBuilder.play10v10("½µÁÙÌìÊ¹", "ÒþÊÀÏÈÖª");
    }

    @Test
    public void ¶éÂä¾«ÁévsÊ¥µ®ÀÏÈË() {
        TestGameBuilder.play1v1("¶éÂä¾«Áé", "Ê¥µ®ÀÏÈË");
    }

    @Test
    public void ¾ÅÍ·ÑýÉßvs·ï»Ë() {
        TestGameBuilder.play2v2("¾ÅÍ·ÑýÉß", "·ï»Ë");
    }
    

    @Test
    public void ¾ÅÍ·ÑýÉßvs¾ÅÍ·ÑýÉß() {
        TestGameBuilder.play2v2("¾ÅÍ·ÑýÉß", "¾ÅÍ·ÑýÉß");
    }
    
    @Test
    public void »Ê¼ÒÑ±ÊÞÊ¦vs¾ÅÍ·ÑýÉß() {
        TestGameBuilder.play10v10("»Ê¼ÒÑ±ÊÞÊ¦", "¾ÅÍ·ÑýÉß", 62);
    }
}
