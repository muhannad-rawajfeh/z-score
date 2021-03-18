package com.progressoft.jip11.tools.studentsreader;

import com.progressoft.jip11.tools.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.dataformat.DataFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CsvReader implements StudentsReader {

    private final DataFormat dataFormat;
    private final FileValidator validator = new FileValidator();

    public CsvReader(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    @Override
    public List<StudentInfo> parse(Path path) {
        validator.validateFile(path);

        List<StudentInfo> studentsInfo = new ArrayList<>();

        try (BufferedReader bReader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            int lineNo = 0;
            bReader.readLine();
            while ((line = bReader.readLine()) != null) {
                lineNo++;                
                studentsInfo.add(mapLine(line, lineNo));
            }
            return studentsInfo;
        } catch (IOException e) {
            throw new StudentsReaderException(e.getMessage(), e);
        }
    }

    private StudentInfo mapLine(String line, int lineNo) {
        String[] attributes = line.split(", ");

        validator.validateFields(dataFormat, attributes, lineNo);

        String studentId = attributes[0];
        char classNo = attributes[1].charAt(0);
        int mark = Integer.parseInt(attributes[2]);

        return new StudentInfo(studentId, classNo, mark);
    }
}
