package com.progressoft.jip11.tools.studentsreader;

import com.progressoft.jip11.tools.exceptions.StudentsReaderException;
import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.dataformat.DataFormat;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CsvReader implements StudentsReader {

    // TODO good separation but you could have the DataFormat inside the validator,
    // if you look at your code you are injecting the dataformat to pass it for the validator
    // you might think of injecting the validator and isolate this reader from dataformat class
    private final DataFormat dataFormat;
    private final FileValidator validator = new FileValidator();

    public CsvReader(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

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
            validator.isEmptyFile(studentsInfo);// TODO I think this is not needed
            return studentsInfo;
        } catch (IOException e) {
            throw new StudentsReaderException(e.getMessage(), e);
        }
    }

    private StudentInfo mapLine(String line, int lineNo) {
        String[] attributes = line.split(",");
        validator.validateFields(dataFormat, attributes, lineNo);
        String studentId = attributes[0];
        char classNo = attributes[1].charAt(0);
        int mark = Integer.parseInt(attributes[2]);
        return new StudentInfo(studentId, classNo, mark);
    }
}
