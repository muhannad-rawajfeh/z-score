package com.progressoft.jip11.tools;

import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;
import com.progressoft.jip11.tools.studentsreader.dataformat.StudentInfoFormat;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ZCalculatorTest {

    private final StudentsReader studentsReader = new CsvReader(new StudentInfoFormat());
    private List<StudentInfo> studentsInfo;
    private ZCalculator zCalculator;

    @Test
    void givenFileWithOddNoOfElements_whenFindMedianTest_thenReturnMedian() {
        studentsInfo = studentsReader.parse("src/test/resources/odd.csv");
        zCalculator = new ZCalculator(studentsInfo);
        double result = zCalculator.findMedian();
        assertEquals(78, result);
    }

    @Test
    void givenFileWithEvenNoOfElements_whenFindMedianTest_thenReturnMedian() {
        studentsInfo = studentsReader.parse("src/test/resources/even.csv");
        zCalculator = new ZCalculator(studentsInfo);
        double result = zCalculator.findMedian();
        assertEquals(70.5, result);
    }
}
