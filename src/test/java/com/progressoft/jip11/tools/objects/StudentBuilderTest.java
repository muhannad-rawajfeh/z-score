package com.progressoft.jip11.tools.objects;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StudentBuilderTest {

    @Test
    void givenStudentInfoAttributes_whenBuild_thenReturnObjectWithCorrectInfo() {
        String id = "1234";
        char classNo = 'B';
        int mark = 60;
        double zScore = -0.2;
        String category = "Passed";

        StudentInfo studentInfo = new StudentInfo(new StudentBuilder(id, classNo, mark)
                .setZScore(zScore).setCategory(category));

        assertEquals(id, studentInfo.getId());
        assertEquals(classNo, studentInfo.getClassNo());
        assertEquals(mark, studentInfo.getMark());
        assertEquals(zScore, studentInfo.getZScore());
        assertEquals(category, studentInfo.getCategory());
    }
}
