package com.progressoft.jip11.tools;

import java.util.Objects;

public class StudentInfo {

    private final String id;
    private final char classNo;
    private final int mark;

    public StudentInfo(String id, char classNo, int mark) {
        this.id = id;
        this.classNo = classNo;
        this.mark = mark;
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

    @Override
    public String toString() {
        return id + "," + classNo + "," + mark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentInfo that = (StudentInfo) o;
        return classNo == that.classNo && mark == that.mark && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, classNo, mark);
    }
}
