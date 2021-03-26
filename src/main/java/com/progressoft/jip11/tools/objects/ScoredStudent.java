package com.progressoft.jip11.tools.objects;

import java.util.Objects;

public class ScoredStudent {

    private final StudentInfo studentInfo;
    private final double zScore;

    public ScoredStudent(StudentInfo studentInfo, double zScore) {
        this.studentInfo = studentInfo;
        this.zScore = zScore;
    }

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public double getZScore() {
        return zScore;
    }

    @Override
    public String toString() {
        return studentInfo.toString() + "," + zScore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoredStudent that = (ScoredStudent) o;
        return Double.compare(that.zScore, zScore) == 0 && Objects.equals(studentInfo, that.studentInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentInfo, zScore);
    }
}
