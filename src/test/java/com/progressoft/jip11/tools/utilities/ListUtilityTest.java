package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.objects.CategorizeRequest;
import com.progressoft.jip11.tools.objects.CategorizedStudent;
import com.progressoft.jip11.tools.objects.ScoredStudent;
import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;
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
        StudentsReader studentsReader = new CsvReader();
        List<StudentInfo> list = studentsReader.parse("src/test/resources/valid-file-2.csv");
        ZCalculator zCalculator = new ZCalculator(list);

        List<ScoredStudent> given = zCalculator.findZScores();

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
        StudentsReader studentsReader = new CsvReader();
        List<StudentInfo> list = studentsReader.parse("src/test/resources/valid-file-2.csv");
        ZCalculator zCalculator = new ZCalculator(list);

        List<ScoredStudent> given = zCalculator.findZScores();
        CategorizeRequest<ScoredStudent> request = new CategorizeRequest<>(given, 1.2, -0.6);

        List<CategorizedStudent> result = listUtility.findCategories(request);

        StudentInfo s1 = new StudentInfo("123456789", 'A', 5);
        StudentInfo s2 = new StudentInfo("987654321", 'B', 10);
        StudentInfo s3 = new StudentInfo("123456780", 'A', 15);
        StudentInfo s4 = new StudentInfo("987654320", 'B', 20);
        StudentInfo s5 = new StudentInfo("987654322", 'B', 25);
        CategorizedStudent cs1 = new CategorizedStudent(new ScoredStudent(s1, -1.26), "Failed");
        CategorizedStudent cs2 = new CategorizedStudent(new ScoredStudent(s2, -0.63), "Failed");
        CategorizedStudent cs3 = new CategorizedStudent(new ScoredStudent(s3, 0), "Passed");
        CategorizedStudent cs4 = new CategorizedStudent(new ScoredStudent(s4, 0.63), "Passed");
        CategorizedStudent cs5 = new CategorizedStudent(new ScoredStudent(s5, 1.26), "Elite");

        List<CategorizedStudent> expected = new ArrayList<>();
        expected.add(cs1);
        expected.add(cs2);
        expected.add(cs3);
        expected.add(cs4);
        expected.add(cs5);

        assertEquals(expected, result);
    }
}
