package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.objects.StudentBuilder;
import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;
import com.progressoft.jip11.tools.studentsreader.dataformat.StudentInfoFormat;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ZCalculatorTest {

    private final StudentsReader studentsReader = new CsvReader(new StudentInfoFormat());
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
        studentsInfo = studentsReader.parse("src/test/resources/z-calc.csv");
        zCalculator = new ZCalculator(studentsInfo);
        double result = zCalculator.findVariance();
        assertEquals(62.5, result);
    }

    @Test
    void givenFile_whenFindStandardDeviation_thenReturnStandardDeviation() {
        studentsInfo = studentsReader.parse("src/test/resources/z-calc.csv");
        zCalculator = new ZCalculator(studentsInfo);
        double result = zCalculator.findDeviation();
        assertEquals(7.91, result);
    }

    @Test
    void givenFile_whenFindZScore_thenReturnZScoreForEachStudent() {
        studentsInfo = studentsReader.parse("src/test/resources/z-calc.csv");
        zCalculator = new ZCalculator(studentsInfo);

        List<StudentInfo> result = zCalculator.findAllZScores();

        StudentInfo s1 = new StudentInfo(new StudentBuilder("123456789", 'A', 5).setZScore(-1.26));
        StudentInfo s2 = new StudentInfo(new StudentBuilder("987654321", 'B', 10).setZScore(-0.63));
        StudentInfo s3 = new StudentInfo(new StudentBuilder("123456780", 'A', 15).setZScore(0));
        StudentInfo s4 = new StudentInfo(new StudentBuilder("987654320", 'B', 20).setZScore(0.63));
        StudentInfo s5 = new StudentInfo(new StudentBuilder("987654322", 'B', 25).setZScore(1.26));
        List<StudentInfo> expected = new ArrayList<>();
        expected.add(s1);
        expected.add(s2);
        expected.add(s3);
        expected.add(s4);
        expected.add(s5);

        assertEquals(expected, result);
    }
}
