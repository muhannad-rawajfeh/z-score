package com.progressoft.jip11.tools;

import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReaderException;
import com.progressoft.jip11.tools.studentsreader.dataformat.DataFormat;
import com.progressoft.jip11.tools.studentsreader.dataformat.StudentInfoFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentsReaderTest {

    private static final String dirPath = "src/test/resources/";
    private StudentsReader reader;

    @BeforeEach
    void setUp() {
        DataFormat dataFormat = new StudentInfoFormat();
        reader = new CsvReader(dataFormat);
    }

    @Test
    void givenNullPath_whenParse_thenThrowException() {
        String message = assertThrows(StudentsReaderException.class, () -> reader.parse(null)).getMessage();
        assertEquals("Path cannot be null", message);
    }

    @Test
    void givenNoneExistingFile_whenParse_thenThrowException() {
        String message = assertThrows(StudentsReaderException.class,
                () -> reader.parse(dirPath + "file.csv")).getMessage();
        assertEquals("File does not exist", message);

        String message2 = assertThrows(StudentsReaderException.class,
                () -> reader.parse("")).getMessage();
        assertEquals("File does not exist", message2);
    }

    @Test
    void givenEmptyFile_whenParse_thenThrowException() {
        String message = assertThrows(StudentsReaderException.class,
                () -> reader.parse(dirPath + "empty-file.csv")).getMessage();
        assertEquals("File is empty", message);
    }

    @Test
    void givenFileWithARecordWithAnInvalidNumberOfFields_whenParse_thenThrowException() {
        String path = dirPath + "invalid-no-of-fields.csv";

        String message = assertThrows(StudentsReaderException.class,
                () -> reader.parse(path)).getMessage();
        assertEquals("Invalid number of fields in line 3", message);
    }

    @Test
    void givenFileWithARecordWithAnInvalidStudentId_whenParse_thenThrowException() {
        String path = dirPath + "invalid-student-id.csv";

        String message = assertThrows(StudentsReaderException.class,
                () -> reader.parse(path)).getMessage();
        assertEquals("Invalid student_id in line 4", message);
    }

    @Test
    void givenFileWithARecordWithAnInvalidClassNo_whenParse_thenThrowException() {
        String path = dirPath + "invalid-class-no.csv";

        String message = assertThrows(StudentsReaderException.class,
                () -> reader.parse(path)).getMessage();
        assertEquals("Invalid class_no in line 2", message);
    }

    @Test
    void givenFileWithARecordWithAnInvalidMark_whenParse_thenThrowException() {
        String path = dirPath + "invalid-mark.csv";

        String message = assertThrows(StudentsReaderException.class,
                () -> reader.parse(path)).getMessage();
        assertEquals("Invalid mark in line 4", message);
    }

    @Test
    void givenValidFile_whenParse_thenReturnListOfStudentsInfo() {
        List<StudentInfo> expected = new ArrayList<>();
        StudentInfo s1 = new StudentInfo("123456789", 'A', 60);
        StudentInfo s2 = new StudentInfo("987654321", 'B', 80);
        expected.add(s1);
        expected.add(s2);

        String path = dirPath + "valid-file.csv";

        List<StudentInfo> result = reader.parse(path);

        assertEquals(result, expected);
    }
}
