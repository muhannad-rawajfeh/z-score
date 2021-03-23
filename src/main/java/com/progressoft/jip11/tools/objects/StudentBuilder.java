package com.progressoft.jip11.tools.objects;

public class StudentBuilder {

    private final String id;
    private final char classNo;
    private final int mark;
    private double zScore;
    private String category;

    public StudentBuilder(String id, char classNo, int mark) {
        this.id = id;
        this.classNo = classNo;
        this.mark = mark;
    }

    public StudentBuilder setZScore(double zScore) {
        this.zScore = zScore;
        return this;
    }

    public StudentBuilder setCategory(String category) {
        this.category = category;
        return this;
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
}
