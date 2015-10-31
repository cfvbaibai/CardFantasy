package cfvbaibai.cardfantasy.game.launcher;

import cfvbaibai.cardfantasy.OneDimensionDataStat.ChartDataItem;

public class TrivialBossGameResult {
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
    public TrivialBossGameResult(BossGameResult result) {
        this.validationResult = result.getValidationResult();
        this.gameCount = result.getGameCount();
        this.coolDown = result.getCoolDown();
        this.totalCost = result.getTotalCost();
        this.timeoutCount = result.getTimeoutCount();
        this.minDamage = result.getMinDamage();
        this.avgDamage = result.getAvgDamage();
        this.avgDamagePerMinute = result.getAvgDamagePerMinute();
        this.maxDamage = result.getMaxDamage();
        this.cvDamage = result.getCvDamage();
        this.dataItems = result.getDataItems();
    }
    public String getValidationResult() {
        return validationResult;
    }
    public void setValidationResult(String validationResult) {
        this.validationResult = validationResult;
    }
    public int getGameCount() {
        return gameCount;
    }
    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }
    public int getCoolDown() {
        return coolDown;
    }
    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }
    public int getTotalCost() {
        return totalCost;
    }
    public void setTotalCost(int totalCost) {
        this.totalCost = totalCost;
    }
    public int getTimeoutCount() {
        return timeoutCount;
    }
    public void setTimeoutCount(int timeoutCount) {
        this.timeoutCount = timeoutCount;
    }
    public double getMinDamage() {
        return minDamage;
    }
    public void setMinDamage(double minDamage) {
        this.minDamage = minDamage;
    }
    public double getAvgDamage() {
        return avgDamage;
    }
    public void setAvgDamage(double avgDamage) {
        this.avgDamage = avgDamage;
    }
    public double getAvgDamagePerMinute() {
        return avgDamagePerMinute;
    }
    public void setAvgDamagePerMinute(double avgDamagePerMinute) {
        this.avgDamagePerMinute = avgDamagePerMinute;
    }
    public double getMaxDamage() {
        return maxDamage;
    }
    public void setMaxDamage(double maxDamage) {
        this.maxDamage = maxDamage;
    }
    public double getCvDamage() {
        return cvDamage;
    }
    public void setCvDamage(double cvDamage) {
        this.cvDamage = cvDamage;
    }
    public ChartDataItem[] getDataItems() {
        return dataItems;
    }
    public void setDataItems(ChartDataItem[] dataItems) {
        this.dataItems = dataItems;
    }
    
    
}
