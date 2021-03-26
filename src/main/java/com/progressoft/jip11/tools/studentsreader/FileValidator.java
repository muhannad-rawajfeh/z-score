package com.progressoft.jip11.tools.studentsreader;

import com.progressoft.jip11.tools.exceptions.StudentsReaderException;
import com.progressoft.jip11.tools.studentsreader.dataformat.DataFormat;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileValidator {

    private final DataFormat dataFormat;

    public FileValidator(DataFormat dataFormat) {
        this.dataFormat = dataFormat;
    }

    public void validateFile(String path) {
        if (path == null) {
            throw new StudentsReaderException("Path cannot be null");
        }
        if (path.equals("") || Files.notExists(Paths.get(path))) {
            throw new StudentsReaderException("File does not exist");
        }
    }

    public void validateFields(String[] attributes, int lineNo) {
        if (!isValidNoOfFields(attributes)) {
            throw new StudentsReaderException("Invalid number of fields in line " + lineNo);
        }
        if (!isValidStudentId(attributes[0])) {
            throw new StudentsReaderException("Invalid student_id in line " + lineNo);
        }
        if (!isValidClassNo(attributes[1])) {
            throw new StudentsReaderException("Invalid class_no in line " + lineNo);
        }
        if (!isValidMark(attributes[2])) {
            throw new StudentsReaderException("Invalid mark in line " + lineNo);
        }
    }

    private boolean isValidMark(String mark) {
        if (!dataFormat.getMarkPattern().matcher(mark).matches()) {
            return false;
        }
        return Integer.parseInt(mark) <= 100;
    }

    private boolean isValidClassNo(String classNo) {
        return dataFormat.getClassNoPattern().matcher(classNo).matches();
    }

    private boolean isValidStudentId(String studentId) {
        return dataFormat.getStudentIdPattern().matcher(studentId).matches();
    }

    private boolean isValidNoOfFields(String[] attributes) {
        return dataFormat.getNoOfFields() == attributes.length;
    }
}
