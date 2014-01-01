package cfvbaibai.cardfantasy.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import junit.framework.Assert;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import cfvbaibai.cardfantasy.DeckBuildRuntimeException;
import cfvbaibai.cardfantasy.game.DeckBuilder;

public class BossBattleStatTest {
    private static SqlSessionFactory sqlSessionFactory;

    @BeforeClass
    public static void initDatabase() throws Exception {
        Reader reader = Resources.getResourceAsReader("cfvbaibai/cardfantasy/test/cardfantasy-ibatis-config.xml");
        try {
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        } finally {
            reader.close();
        }
    }

    @Test
    public void testDeckSort_Basic() {
        String desc = DeckBuilder.getSortedDeckDesc("凤凰，大主教");
        System.out.println(desc);
        Assert.assertEquals("凤凰-10,大主教-10,", desc);
    }

    @Test
    public void testDeckSort_SameCard_DifferentLevel() {
        String desc = DeckBuilder.getSortedDeckDesc("凤凰，凤凰-15，大主教，大主教-5");
        System.out.println(desc);
        Assert.assertEquals("凤凰-10,凤凰-15,大主教-5,大主教-10,", desc);
    }
    
    @Test
    public void testDeckSort_SameCard_SameLevel_DifferentExtraFeature() {
        String desc = DeckBuilder.getSortedDeckDesc("凤凰-15，凤凰+暗杀5-15，凤凰+暗杀10-15，凤凰+不动-15，凤凰+暗杀8-10");
        System.out.println(desc);
        Assert.assertEquals("凤凰+暗杀8-10,凤凰-15,凤凰+不动-15,凤凰+暗杀5-15,凤凰+暗杀10-15,", desc);
    }
    
    //@Test
    public void buildBossBattleStat() throws IOException, ParseException {
        File logFolder = new File("D:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/logs");
        File[] files = logFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().startsWith("tomcat7-stdout.2013-");
            }
        });
        int totalCount = 0;
        for (File file : files) {
            System.out.println("Parsing file " + file.getAbsolutePath());
            totalCount += buildBossBattleStatFromFile(file);
        }
        System.out.println("Total Count: " + totalCount);
    }

    private String prefixPattern = "^\\[(?<Date>\\d\\d\\d\\d\\-\\d\\d\\-\\d\\d \\d\\d:\\d\\d:\\d\\d)\\]\\[[^\\]]+\\] ";
    private Pattern line1Pattern = Pattern.compile(prefixPattern + "PlayBossMassiveGame from .+$");
    private Pattern line2Pattern = Pattern.compile(prefixPattern + "Deck = (?<Deck>.+)$");
    private Pattern line3Pattern = Pattern.compile(prefixPattern + "Count = (?<Count>\\d+), Hero LV = (?<HeroLv>\\d+), Boss = (?<Boss>.+)$");
    private Pattern line4Pattern = Pattern.compile(prefixPattern + "Average damage to boss: (?<AvgDamage>\\d+)$");

    private int buildBossBattleStatFromFile(File file) throws IOException, ParseException {
        int totalCount = 0;
        BufferedReader reader = null;
        SqlSession session = null;
        @SuppressWarnings("unused")
        int lineNumber = 0;
        int errorCount = 0;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "GB2312"));
            session = sqlSessionFactory.openSession();
            CardFantasyMapper mapper = session.getMapper(CardFantasyMapper.class);

            String line1 = null;
            while ((line1 = reader.readLine()) != null) {
                ++lineNumber;
                BossBattleStatEntry entry = new BossBattleStatEntry();

                Matcher matcher1 = line1Pattern.matcher(line1);
                if (!matcher1.matches()) {
                    continue;
                }

                String line2 = reader.readLine();
                ++lineNumber;
                if (line2 == null) {
                    continue;
                }
                Matcher matcher2 = line2Pattern.matcher(line2);
                if (!matcher2.matches()) {
                    continue;
                }
                String deck = matcher2.group("Deck");

                String line3 = reader.readLine();
                ++lineNumber;
                if (line3 == null) {
                    continue;
                }
                Matcher matcher3 = line3Pattern.matcher(line3);
                if (!matcher3.matches()) {
                    continue;
                }
                entry.setBattleCount(Integer.parseInt(matcher3.group("Count")));
                entry.setHeroLv(Integer.parseInt(matcher3.group("HeroLv")));
                entry.setBossName(matcher3.group("Boss"));

                String line4 = reader.readLine();
                ++lineNumber;
                if (line4 == null) {
                    continue;
                }
                Matcher matcher4 = line4Pattern.matcher(line4);
                if (!matcher4.matches()) {
                    continue;
                }
                entry.setAvgDamage(Integer.parseInt(matcher4.group("AvgDamage")));

                try {
                    entry.setSortedDeck(DeckBuilder.getSortedDeckDesc(deck));
                    mapper.newBossBattleStatEntry(entry);
                    ++totalCount;
                } catch (DeckBuildRuntimeException e) {
                    ++errorCount;
                    //System.out.println("Line: " + lineNumber);
                    //System.out.println(e.getMessage());
                    //System.out.println(deck);
                }
            }

            return totalCount;
        } finally {
            if (reader != null) { reader.close(); }
            if (session != null) { session.close(); }
            System.out.println("Error: " + errorCount);
        }
    }
}

interface CardFantasyMapper {
    void newBossBattleStatEntry(BossBattleStatEntry entry);
}

class BossBattleStatEntry {
    private String sortedDeck;
    private int battleCount;
    private int heroLv;
    private String bossName;
    private int avgDamage;

    public String getSortedDeck() {
        return sortedDeck;
    }
    public void setSortedDeck(String sortedDeck) {
        this.sortedDeck = sortedDeck;
    }
    public int getBattleCount() {
        return battleCount;
    }
    public void setBattleCount(int battleCount) {
        this.battleCount = battleCount;
    }
    public int getHeroLv() {
        return heroLv;
    }
    public void setHeroLv(int heroLv) {
        this.heroLv = heroLv;
    }
    public String getBossName() {
        return bossName;
    }
    public void setBossName(String bossName) {
        this.bossName = bossName;
    }
    public int getAvgDamage() {
        return avgDamage;
    }
    public void setAvgDamage(int avgDamage) {
        this.avgDamage = avgDamage;
    }
}