package cfvbaibai.cardfantasy.web;

import java.util.ArrayList;
import java.util.List;

import cfvbaibai.cardfantasy.CardFantasyRuntimeException;

public class OneDimensionDataStat {

    private List<Double> dataList = new ArrayList <Double>();
    
    public OneDimensionDataStat() {
        this.reset();
    }

    public void addData(double data) {
        this.dataList.add(data);
    }
    
    public void reset() {
        this.dataList.clear();
    }
    
    public double getMin() {
        if (this.dataList.isEmpty()) {
            return 0.0;
        }
        double min = Double.MAX_VALUE;
        for (double data : this.dataList) {
            if (data < min) {
                min = data;
            }
        }
        return min;
    }
    
    public double getMax() {
        if (this.dataList.isEmpty()) {
            return 0.0;
        }
        double min = Double.MIN_VALUE;
        for (double data : this.dataList) {
            if (data > min) {
                min = data;
            }
        }
        return min;
    }
    
    public int getCount() {
        return this.dataList.size();
    }
    
    public double getSum() {
        double sum = 0.0;
        for (double data : this.dataList) {
            sum += data;
        }
        return sum;
    }
    
    public double getAverage() {
        int count = this.getCount();
        if (count == 0) {
            throw new CardFantasyRuntimeException("Cannot get average on empty data set.");
        }
        return this.getSum() / count;
    }
    
    public double getCoefficientOfVariation() {
        int count = this.getCount();
        if (count == 0) {
            throw new CardFantasyRuntimeException("Cannot get square deviation on empty data set.");
        }
        double s = 0.0;
        double avg = this.getAverage();
        for (double data : this.dataList) {
            s += (data - avg) * (data - avg);
        }
        return Math.sqrt(s / count) / avg;
    }
}
