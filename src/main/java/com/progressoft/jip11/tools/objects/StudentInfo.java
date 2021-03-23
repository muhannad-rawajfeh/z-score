package com.progressoft.jip11.tools.objects;

import java.util.Objects;

public class StudentInfo {

    private final String id;
    private final char classNo;
    private final int mark;
    private final double zScore;
    private final String category;

    public StudentInfo(String id, char classNo, int mark) {
        this.id = id;
        this.classNo = classNo;
        this.mark = mark;
        this.zScore = 0;
        this.category = null;
    }

    public StudentInfo(StudentBuilder builder) {
        this.id = builder.getId();
        this.classNo = builder.getClassNo();
        this.mark = builder.getMark();
        this.zScore = builder.getZScore();
        this.category = builder.getCategory();
    }

    public String getId() {
        return id;
    }

    public char getClassNo() {
        return classNo;
    }

    public int getMark() {
        return mark;
    }

    public double getZScore() {
        return zScore;
    }

    public String getCategory() {
        return category;
    }

    @Override
    public String toString() {
        return id + "," + classNo + "," + mark + "," + zScore + "," + category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentInfo that = (StudentInfo) o;
        return classNo == that.classNo && mark == that.mark && Double.compare(that.zScore, zScore) == 0 && Objects.equals(id, that.id) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classNo, mark, zScore, category);
    }
}
