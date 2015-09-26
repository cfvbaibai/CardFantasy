package cfvbaibai.cardfantasy.test;

import java.io.IOException;

import org.junit.Test;

import cfvbaibai.cardfantasy.officialdata.OfficialDataStore;

public class TestOfficialData {
    @Test
    public void testOfficialDataStore() throws IOException {
        OfficialDataStore store = OfficialDataStore.getInstance();
        System.out.println("Card Size: " + store.cardStore.data.Cards.size());
        System.out.println("Skill Size: " + store.skillStore.data.Skills.size());
    }
}
