package cfvbaibai.cardfantasy;

import java.util.ArrayList;
import java.util.List;

public class OneDimensionDataStat {

    private List<Double> dataList = new ArrayList <Double>();

    public class ChartDataItem {
        private String label;
        private int count;
        public String getLabel() {
            return label;
        }
        public void setLabel(String label) {
            this.label = label;
        }
        public int getCount() {
            return count;
        }
        public void setCount(int count) {
            this.count = count;
        }
        public void increaseCount() {
            ++this.count;
        }
        public ChartDataItem(double start, double end, int count) {
            this.label = (int)start + "-" + (int)end;
            this.count = count;
        }
        @Override
        public String toString() {
            return this.getLabel() + ": " + this.getCount();
        }
    }

    public OneDimensionDataStat() {
        this.reset();
    }

    public void addData(double ... dataList) {
        for (double data : dataList) {
            this.dataList.add(data);
        }
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
        double max = 0.0;
        for (double data : this.dataList) {
            if (data > max) {
                max = data;
            }
        }
        return max;
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
        if (avg == 0.0) {
            return 0.0;
        }
        for (double data : this.dataList) {
            s += (data - avg) * (data - avg);
        }
        return Math.sqrt(s / count) / avg;
    }

    public ChartDataItem[] getChartDataItems(int labelCount, int padding) {
        if (labelCount <= 0) {
            throw new CardFantasyRuntimeException("Invalid labelCount: " + labelCount);
        }
        if (this.getCount() == 0) {
            return new ChartDataItem[0];
        }
        if (this.getCount() == 1) {
            labelCount = 1;
        }
        double min = (int)(this.getMin() / 1000) * 1000;
        double max = (int)(this.getMax() / 1000) * 1000 + 1000;
        double interval = (max - min) / labelCount;
        ChartDataItem[] result = new ChartDataItem[labelCount + padding * 2];
        for (int i = 0; i < result.length; ++i) {
            double start = min + (i - padding) * interval;
            double end = start + interval;
            result[i] = new ChartDataItem(start, end, 0);
        }
        for (double data : this.dataList) {
            int bucket = (int)((data - min) / interval) + padding;
            result[bucket].increaseCount();
        }
        return result;
    }
}
