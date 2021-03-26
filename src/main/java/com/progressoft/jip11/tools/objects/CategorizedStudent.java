package com.progressoft.jip11.tools.objects;

import java.util.Objects;

public class CategorizedStudent {

    private final ScoredStudent scoredStudent;
    private final String category;

    public CategorizedStudent(ScoredStudent scoredStudent, String category) {
        this.scoredStudent = scoredStudent;
        this.category = category;
    }

    @Override
    public String toString() {
        return scoredStudent.toString() + "," + category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CategorizedStudent that = (CategorizedStudent) o;
        return Objects.equals(scoredStudent, that.scoredStudent) && Objects.equals(category, that.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(scoredStudent, category);
    }
}
