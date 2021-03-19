package com.progressoft.jip11.tools;

import java.util.Arrays;
import java.util.List;

public class ZCalculator {

    private final List<StudentInfo> studentsInfo;

    public ZCalculator(List<StudentInfo> studentsInfo) {
        this.studentsInfo = studentsInfo;
    }

    public double findMedian() {
        int length = studentsInfo.size();
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
        return 0;
    }
}
