package cfvbaibai.cardfantasy.simulate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

public class SimulateTest {
    @Test
    public void testSimulation() {
        Room room = new Room(500, 1, 90);
        room.initialize();
        room.iterate(50000);
        List<Team> teams = room.getTeams();
        for (Team team : teams) {
            System.out.println(team.getTeamDesc());
        }
    }
    
    @Test
    public void continuousTestSimulation() throws IOException {
        int iterations = 1000;
        Room room = new Room(5000, 1, 90);
        String filePath = "E:/Temp/CTS.txt";
        File outputFile = new File(filePath);
        if (outputFile.exists()) {
            room.initializeFromFile(filePath);
        } else {
            room.initialize();
        }
        int totalIterations = 1000;
        for (int i = 0; i < totalIterations; ++i) {
            System.out.println("[" + i + "/" + totalIterations + "] Iterating...");
            room.iterate(iterations);
            System.out.println("Saving result to file...");
            List<Team> teams = room.getTeams();
            outputFile.createNewFile();
            FileWriter out = new FileWriter(filePath);
            for (Team team : teams) {
                out.write(team.getTeamDesc() + System.lineSeparator());
            }
            out.close();
        }
    }
}
