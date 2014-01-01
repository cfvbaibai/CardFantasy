package cfvbaibai.cardfantasy.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.Test;

public class BossBattleStatTest {

    @Test
    public void buildBossBattleStat() throws IOException {
        File logFolder = new File("D:/Program Files (x86)/Apache Software Foundation/Tomcat 7.0/logs");
        File[] files = logFolder.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.getName().startsWith("tomcat7-stdout");
            }
        });
        int totalCount = 0;
        for (File file : files) {
            System.out.println("Parsing file " + file.getAbsolutePath());
            totalCount += buildBossBattleStatFromFile(file);
        }
        System.out.println("Total Count: " + totalCount);
    }

    private int buildBossBattleStatFromFile(File file) throws IOException {
        int totalCount = 0;
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
        try {
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!line.contains("PlayBossMassiveGame from")) {
                    continue;
                }
                // TODO:
                ++totalCount;
            }
            return totalCount;
        } finally {
            reader.close();
        }
    }
}
