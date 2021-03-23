package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.dataformat.StudentInfoFormat;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FileGetterTest {

    private final FileGetter fileGetter = new FileGetter(new CsvReader(new StudentInfoFormat()));

    @Test
    void givenFileInput_whenGetFileAndParse_thenReturnListOfStudentsInfo() {
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
}