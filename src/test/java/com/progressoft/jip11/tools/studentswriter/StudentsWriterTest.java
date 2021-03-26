package com.progressoft.jip11.tools.studentswriter;

import com.progressoft.jip11.tools.exceptions.StudentsWriterException;
import com.progressoft.jip11.tools.objects.CategorizedStudent;
import com.progressoft.jip11.tools.objects.ScoredStudent;
import com.progressoft.jip11.tools.objects.StudentInfo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StudentsWriterTest {

    private final StudentsWriter studentsWriter = new CsvWriter();

    @Test
    void givenExistingFileName_whenWrite_thenShouldThrowException() throws IOException {
        Files.createFile(Paths.get("temp.xls"));

        StudentInfo s = new StudentInfo("1234", 'A', 80);
        CategorizedStudent cs = new CategorizedStudent(new ScoredStudent(s, 1.2), "Passed");
        List<CategorizedStudent> info = new ArrayList<>();
        info.add(cs);

        String message = assertThrows(StudentsWriterException.class,
                () -> studentsWriter.write(info, "temp")).getMessage();

        assertEquals("File already exists", message);

        Files.delete(Paths.get("temp.xls"));
    }

    @Test
    void givenToWriteAndFileName_whenWrite_thenWriteToFile() throws IOException {
        StudentInfo s = new StudentInfo("1234", 'A', 80);
        CategorizedStudent cs = new CategorizedStudent(new ScoredStudent(s, 1.2), "Passed");
        List<CategorizedStudent> info = new ArrayList<>();
        info.add(cs);
        String fileName = "test";

        studentsWriter.write(info, fileName);

        List<String> result = Files.readAllLines(Paths.get("test.xls"));

        List<String> expected = new ArrayList<>();
        expected.add("Student_id,class_no,mark,z_score,category");
        expected.add("1234,A,80,1.2,Passed");

        assertEquals(expected, result);

        Files.delete(Paths.get("test.xls"));
    }
}
