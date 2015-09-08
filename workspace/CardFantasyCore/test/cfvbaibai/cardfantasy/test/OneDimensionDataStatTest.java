package cfvbaibai.cardfantasy.test;

import org.junit.Assert;

import org.junit.Test;

import cfvbaibai.cardfantasy.OneDimensionDataStat;
import cfvbaibai.cardfantasy.OneDimensionDataStat.ChartDataItem;

public class OneDimensionDataStatTest {
    private static void printChartDataItems(ChartDataItem[] items) {
        for (ChartDataItem item : items) {
            System.out.println(item.getLabel() + ": " + item.getCount());
        }
    }

    @Test
    public void testBasic() {
        OneDimensionDataStat stat = new OneDimensionDataStat();
        stat.addData(14031, 20300, 27800, 32557);
        ChartDataItem[] items = stat.getChartDataItems(5, 2);
        printChartDataItems(items);
        Assert.assertEquals(9, items.length);
        Assert.assertEquals("6400-10200", items[0].getLabel());
        Assert.assertEquals(0, items[0].getCount());
        Assert.assertEquals("10200-14000", items[1].getLabel());
        Assert.assertEquals(0, items[1].getCount());
        Assert.assertEquals("14000-17800", items[2].getLabel());
        Assert.assertEquals(1, items[2].getCount());
        Assert.assertEquals("17800-21600", items[3].getLabel());
        Assert.assertEquals(1, items[3].getCount());
        Assert.assertEquals("21600-25400", items[4].getLabel());
        Assert.assertEquals(0, items[4].getCount());
        Assert.assertEquals("25400-29200", items[5].getLabel());
        Assert.assertEquals(1, items[5].getCount());
        Assert.assertEquals("29200-33000", items[6].getLabel());
        Assert.assertEquals(1, items[6].getCount());
        Assert.assertEquals("33000-36800", items[7].getLabel());
        Assert.assertEquals(0, items[7].getCount());
        Assert.assertEquals("36800-40600", items[8].getLabel());
        Assert.assertEquals(0, items[8].getCount());
    }
}
