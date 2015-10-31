package cfvbaibai.cardfantasy.game.launcher;

import cfvbaibai.cardfantasy.OneDimensionDataStat;
import cfvbaibai.cardfantasy.OneDimensionDataStat.ChartDataItem;
import cfvbaibai.cardfantasy.engine.GameResult;

public class BossGameResult {
    private GameResult lastDetail;
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
    public BossGameResult(String validationResult, int coolDown,
            int totalCost, int timeoutCount, OneDimensionDataStat stat, GameResult lastDetail) {
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
        this.lastDetail = lastDetail;
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
    public GameResult getLastDetail() {
        return lastDetail;
    }
    
    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
    }
    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }
    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }
    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
    public void setTimeoutCount(int timeoutCount) {
        this.timeoutCount = timeoutCount;
    }
    public void setMinDamage(double minDamage) {
        this.minDamage = minDamage;
    }
    public void setAvgDamage(double avgDamage) {
        this.avgDamage = avgDamage;
    }
    public void setAvgDamagePerMinute(double avgDamagePerMinute) {
        this.avgDamagePerMinute = avgDamagePerMinute;
    }
    public void setMaxDamage(double maxDamage) {
        this.maxDamage = maxDamage;
    }
    public void setCvDamage(double cvDamage) {
        this.cvDamage = cvDamage;
    }
    public void setDataItems(ChartDataItem[] dataItems) {
        this.dataItems = dataItems;
    }
    public void setLastDetail(GameResult lastDetail) {
        this.lastDetail = lastDetail;
    }
}