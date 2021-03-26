package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.objects.ScoredStudent;
import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZCalculatorTest {

    private final StudentsReader studentsReader = new CsvReader();
    private List<StudentInfo> studentsInfo;
    private ZCalculator zCalculator;

    @Test
    void givenFileWithOddNoOfElements_whenFindMedian_thenReturnMedian() {
        studentsInfo = studentsReader.parse("src/test/resources/odd.csv");
        zCalculator = new ZCalculator(studentsInfo);
        double result = zCalculator.findMedian();
        assertEquals(78, result);
    }

    @Test
    void givenFileWithEvenNoOfElements_whenFindMedian_thenReturnMedian() {
        studentsInfo = studentsReader.parse("src/test/resources/even.csv");
        zCalculator = new ZCalculator(studentsInfo);
        double result = zCalculator.findMedian();
        assertEquals(70.5, result);
    }

    @Test
    void givenFile_whenFindVariance_thenReturnVariance() {
        studentsInfo = studentsReader.parse("src/test/resources/valid-file-2.csv");
        zCalculator = new ZCalculator(studentsInfo);
        double result = zCalculator.findVariance();
        assertEquals(62.5, result);
    }

    @Test
    void givenFile_whenFindStandardDeviation_thenReturnStandardDeviation() {
        studentsInfo = studentsReader.parse("src/test/resources/valid-file-2.csv");
        zCalculator = new ZCalculator(studentsInfo);
        double result = zCalculator.findStDeviation();
        assertEquals(7.91, result);
    }

    @Test
    void givenFile_whenFindZScores_thenReturnZScoreForEachStudent() {
        studentsInfo = studentsReader.parse("src/test/resources/valid-file-2.csv");
        zCalculator = new ZCalculator(studentsInfo);

        List<ScoredStudent> result = zCalculator.findZScores();

        StudentInfo s1 = new StudentInfo("123456789", 'A', 5);
        StudentInfo s2 = new StudentInfo("987654321", 'B', 10);
        StudentInfo s3 = new StudentInfo("123456780", 'A', 15);
        StudentInfo s4 = new StudentInfo("987654320", 'B', 20);
        StudentInfo s5 = new StudentInfo("987654322", 'B', 25);
        ScoredStudent ss1 = new ScoredStudent(s1, -1.26);
        ScoredStudent ss2 = new ScoredStudent(s2, -0.63);
        ScoredStudent ss3 = new ScoredStudent(s3, 0);
        ScoredStudent ss4 = new ScoredStudent(s4, 0.63);
        ScoredStudent ss5 = new ScoredStudent(s5, 1.26);
        List<ScoredStudent> expected = new ArrayList<>();
        expected.add(ss1);
        expected.add(ss2);
        expected.add(ss3);
        expected.add(ss4);
        expected.add(ss5);

        assertEquals(expected, result);
    }
}
