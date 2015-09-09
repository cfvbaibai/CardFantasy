package cfvbaibai.cardfantasy.web.beans;

import cfvbaibai.cardfantasy.OneDimensionDataStat;
import cfvbaibai.cardfantasy.OneDimensionDataStat.ChartDataItem;

public class BossMassiveGameResult {
    private String validationResult;
    private int gameCount;
    private int coolDown;
    private int totalCost;
    private int timeoutCount;
    private double minDamage;
    private double avgDamage;
    private double avgDamagePerMinute;
    private double maxDamage;
    private double cvDamage;
    private ChartDataItem[] dataItems;
    public BossMassiveGameResult(String validationResult, int coolDown,
            int totalCost, int timeoutCount, OneDimensionDataStat stat) {
        this.validationResult = validationResult;
        this.gameCount = stat.getCount();
        this.coolDown = coolDown;
        this.totalCost = totalCost;
        this.timeoutCount = timeoutCount;
        this.minDamage = stat.getMin();
        this.avgDamage = stat.getAverage();
        this.avgDamagePerMinute = this.avgDamage * (double)60 / (double)coolDown;
        this.maxDamage = stat.getMax();
        this.cvDamage = stat.getCoefficientOfVariation();
        this.dataItems = stat.getChartDataItems(10, 0);
    }
    public String getValidationResult() {
        return validationResult;
    }
    public int getGameCount() {
        return gameCount;
    }
    public int getCoolDown() {
        return coolDown;
    }
    public int getTotalCost() {
        return totalCost;
    }
    public int getTimeoutCount() {
        return timeoutCount;
    }
    public double getMinDamage() {
        return minDamage;
    }
    public double getAvgDamage() {
        return avgDamage;
    }
    public double getAvgDamagePerMinute() {
        return avgDamagePerMinute;
    }
    public double getMaxDamage() {
        return maxDamage;
    }
    public double getCvDamage() {
        return cvDamage;
    }
    public ChartDataItem[] getDataItems() {
        return dataItems;
    }
}