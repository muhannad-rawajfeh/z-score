package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.objects.StudentBuilder;
import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;
import com.progressoft.jip11.tools.studentsreader.dataformat.StudentInfoFormat;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListUtilityTest {

    ListUtility listUtility = new ListUtility();

    @Test
    void givenListOfStudentsInfo_whenIsClassExist_thenReturnIfClassNoExists() {
        List<StudentInfo> studentsInfo = new ArrayList<>();
        StudentInfo s1 = new StudentInfo("123456789", 'A', 60);
        StudentInfo s2 = new StudentInfo("987654321", 'B', 80);
        studentsInfo.add(s1);
        studentsInfo.add(s2);

        assertTrue(listUtility.isClassExist('a', studentsInfo));
        assertFalse(listUtility.isClassExist('c', studentsInfo));
    }

    @Test
    void givenListOfStudentsInfo_whenGetAllInClass_theReturnListOfStudentsInfoInGivenClass() {
        List<StudentInfo> given = new ArrayList<>();
        StudentInfo s1 = new StudentInfo("123456780", 'A', 70);
        StudentInfo s2 = new StudentInfo("987654321", 'B', 80);
        StudentInfo s3 = new StudentInfo("123456789", 'A', 60);
        StudentInfo s4 = new StudentInfo("987654321", 'B', 80);
        given.add(s1);
        given.add(s2);
        given.add(s3);
        given.add(s4);

        List<StudentInfo> result = listUtility.getAllInClass('a', given);

        List<StudentInfo> expected = new ArrayList<>();
        StudentInfo e1 = new StudentInfo("123456780", 'A', 70);
        StudentInfo e2 = new StudentInfo("123456789", 'A', 60);
        expected.add(e1);
        expected.add(e2);

        assertEquals(expected, result);
    }

    @Test
    void givenArrayOfZScores_whenCountDeviations_thenReturnNoOfEachCategory() {
        StudentsReader studentsReader = new CsvReader(new StudentInfoFormat());
        List<StudentInfo> list = studentsReader.parse("src/test/resources/z-calc.csv");
        ZCalculator zCalculator = new ZCalculator(list);

        List<StudentInfo> given = zCalculator.findAllZScores();

        int count1 = listUtility.countElite(given, 0.6);
        assertEquals(2, count1);

        int count2 = listUtility.countFailed(given, 0);
        assertEquals(3, count2);

        int count3 = listUtility.countPassed(given, 1.2, -0.6);
        assertEquals(2, count3);

        int passingScore = listUtility.getPassingScore(given, 1.2, -0.6);
        assertEquals(15, passingScore);

        int eliteScore = listUtility.getEliteScore(given);
        assertEquals(25, eliteScore);
    }

    @Test
    void givenArrayOfZScores_whenFindAllCategories_thenReturnCategoryForEachStudent() {
        StudentsReader studentsReader = new CsvReader(new StudentInfoFormat());
        List<StudentInfo> list = studentsReader.parse("src/test/resources/z-calc.csv");
        ZCalculator zCalculator = new ZCalculator(list);

        List<StudentInfo> given = zCalculator.findAllZScores();

        List<StudentInfo> result = listUtility.findAllCategories(given, 1.2, -0.6);

        StudentInfo s1 = new StudentInfo(new StudentBuilder("123456789", 'A', 5).setZScore(-1.26).setCategory("Failed"));
        StudentInfo s2 = new StudentInfo(new StudentBuilder("987654321", 'B', 10).setZScore(-0.63).setCategory("Failed"));
        StudentInfo s3 = new StudentInfo(new StudentBuilder("123456780", 'A', 15).setZScore(0).setCategory("Passed"));
        StudentInfo s4 = new StudentInfo(new StudentBuilder("987654320", 'B', 20).setZScore(0.63).setCategory("Passed"));
        StudentInfo s5 = new StudentInfo(new StudentBuilder("987654322", 'B', 25).setZScore(1.26).setCategory("Elite"));
        List<StudentInfo> expected = new ArrayList<>();
        expected.add(s1);
        expected.add(s2);
        expected.add(s3);
        expected.add(s4);
        expected.add(s5);

        assertEquals(expected, result);
    }
}
