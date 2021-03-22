package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;

public class ZCalculator {

    private static final DecimalFormat df = new DecimalFormat("###.##");
    private final List<StudentInfo> studentsInfo;

    public ZCalculator(List<StudentInfo> studentsInfo) {
        this.studentsInfo = studentsInfo;
    }

    public double findMedian() {
        int length = getCount();
        int[] marks = new int[length];
        int i = 0;
        for (StudentInfo s : studentsInfo) {
            marks[i++] = s.getMark();
        }
        Arrays.sort(marks);
        if (length % 2 != 0) {
            return marks[(length + 1) / 2 - 1];
        }
        return (double) (marks[length / 2 - 1] + marks[length / 2]) / 2;
    }

    public double findVariance() {
        double mean = findMean();
        double sum = 0;
        for (StudentInfo s : studentsInfo) {
            sum += Math.pow(s.getMark() - mean, 2);
        }
        return format(sum / (getCount() - 1));
    }

    public double findDeviation() {
        return format(Math.sqrt(findVariance()));
    }

    public String findAllZScores() {
        StringBuilder result = new StringBuilder();
        for (StudentInfo s : studentsInfo) {
            double zScore = calcZScore(s.getMark());
            result.append(s).append(",").append(df.format(zScore)).append("\n");
        }
        return result.toString();
    }

    public int getCount() {
        return studentsInfo.size();
    }

    private double format(double value) {
        return Double.parseDouble(df.format(value));
    }

    private double calcZScore(int mark) {
        return (mark - findMean()) / findDeviation();
    }

    private double findMean() {
        return (double) findSum() / getCount();
    }

    private int findSum() {
        int sum = 0;
        for (StudentInfo s : studentsInfo) {
            sum += s.getMark();
        }
        return sum;
    }
}
