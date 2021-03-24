package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.dataformat.StudentInfoFormat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FileGetterTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;
    private FileGetter fileGetter;

    @BeforeEach
    void setUp() {
        fileGetter = new FileGetter(new CsvReader(new StudentInfoFormat()));
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    void givenValidFileInput_whenGetFileAndParse_thenReturnListOfStudentsInfo() {
        String input = "src/test/resources/valid-file.csv";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        List<StudentInfo> result = fileGetter.getFileAndParse();

        StudentInfo s1 = new StudentInfo("123456789", 'A', 60);
        StudentInfo s2 = new StudentInfo("987654321", 'B', 80);
        List<StudentInfo> expected = new ArrayList<>();
        expected.add(s1);
        expected.add(s2);

        assertEquals(expected, result);
    }

    @Test
    void givenNoneExistingFile_whenGetFileAndParse_thenReportThatNotExistsThenGetNextInput() {
        String input1 = "test";
        InputStream in1 = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in1);

        // to stop recursion I enter a valid file next
        String input2 = "src/test/resources/valid-file.csv";
        InputStream in2 = new ByteArrayInputStream(input2.getBytes());
        System.setIn(in2);

        List<StudentInfo> result = fileGetter.getFileAndParse();

        assertEquals("File does not exist, try again...\n", outputStream.toString());

        StudentInfo s1 = new StudentInfo("123456789", 'A', 60);
        StudentInfo s2 = new StudentInfo("987654321", 'B', 80);
        List<StudentInfo> expected = new ArrayList<>();
        expected.add(s1);
        expected.add(s2);

        assertEquals(expected, result);
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }
}
