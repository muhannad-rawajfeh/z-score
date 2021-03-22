package com.progressoft.jip11.tools;

import com.progressoft.jip11.tools.studentswriter.CsvWriter;
import com.progressoft.jip11.tools.studentswriter.StudentsWriter;
import com.progressoft.jip11.tools.studentswriter.StudentsWriterException;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StudentsWriterTest {

    private final StudentsWriter studentsWriter = new CsvWriter();

    @Test
    void givenExistingFileName_whenWrite_thenShouldThrowException() throws IOException {
        Files.createFile(Paths.get("temp.xls"));

        String message = assertThrows(StudentsWriterException.class,
                () -> studentsWriter.write("hello", "temp")).getMessage();

        assertEquals("File already exists", message);

        Files.delete(Paths.get("temp.xls"));
    }

    @Test
    void givenToWriteAndFileName_whenWrite_thenWriteToFile() throws IOException {
        String toWrite = "1234,A,80,1.2,Elite\n" +"4321,B,60,-0.4,Passed\n";
        String fileName = "test";

        studentsWriter.write(toWrite, fileName);

        List<String> result = Files.readAllLines(Paths.get("test.xls"));

        List<String> expected = new ArrayList<>();
        expected.add("Student_id,class_no,mark,z_score,category");
        expected.add("1234,A,80,1.2,Elite");
        expected.add("4321,B,60,-0.4,Passed");

        assertEquals(expected, result);

        Files.delete(Paths.get("test.xls"));
    }
}
