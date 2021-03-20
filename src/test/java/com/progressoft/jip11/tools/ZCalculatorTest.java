package com.progressoft.jip11.tools;

import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;
import com.progressoft.jip11.tools.studentsreader.dataformat.StudentInfoFormat;
import com.progressoft.jip11.tools.utilities.ZCalculator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

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
        assertEquals(7.905694150420948, result);
    }

    @Test
    void givenFile_whenFindZscore_thenReturnZscoreForEachStudent() {
        studentsInfo = studentsReader.parse("src/test/resources/z-calc.csv");
        zCalculator = new ZCalculator(studentsInfo);
        String expected = "123456789,A,5,-1.26\n" +
                "987654321,B,10,-0.63\n" +
                "123456780,A,15,0\n" +
                "987654320,B,20,0.63\n" +
                "987654322,B,25,1.26";
        String result = zCalculator.findAllZscores();
        assertEquals(expected, result);
    }
}
