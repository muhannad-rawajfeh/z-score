package com.progressoft.jip11.tools.objects;

public class SummaryResults {

    private final double median;
    private final double variance;
    private final double stDeviation;
    private final int totalCount;

    public SummaryResults(double median, double variance, double stDeviation, int totalCount) {
        this.median = median;
        this.variance = variance;
        this.stDeviation = stDeviation;
        this.totalCount = totalCount;
    }

    public double getMedian() {
        return median;
    }

    public double getVariance() {
        return variance;
    }

    public double getStDeviation() {
        return stDeviation;
    }

    public int getTotalCount() {
        return totalCount;
    }
}
