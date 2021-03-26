package com.progressoft.jip11.tools.studentsreader;

import com.progressoft.jip11.tools.exceptions.StudentsReaderException;
import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.dataformat.StudentInfoFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReader implements StudentsReader {

    private final FileValidator validator = new FileValidator(new StudentInfoFormat());

    @Override
    public List<StudentInfo> parse(String path) {
        validator.validateFile(path);
        List<StudentInfo> studentsInfo = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(path), StandardCharsets.UTF_8)) {
            String line;
            int lineNo = 1;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                lineNo++;
                if (line.isBlank()) continue;
                studentsInfo.add(mapLine(line, lineNo));
            }
            return studentsInfo;
        } catch (IOException e) {
            throw new StudentsReaderException(e.getMessage(), e);
        }
    }

    private StudentInfo mapLine(String line, int lineNo) {
        String[] attributes = line.split(",");
        validator.validateFields(attributes, lineNo);
        String studentId = attributes[0];
        char classNo = attributes[1].charAt(0);
        int mark = Integer.parseInt(attributes[2]);
        return new StudentInfo(studentId, classNo, mark);
    }
}
