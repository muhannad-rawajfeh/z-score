package com.progressoft.jip11.tools.studentsreader;

import com.progressoft.jip11.tools.studentsreader.dataformat.DataFormat;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileValidator {

    public void validateFile(String path) {
        if (path == null) {
            throw new StudentsReaderException("Path cannot be null");
        }
        if (path.equals("") || Files.notExists(Paths.get(path))) {
            throw new StudentsReaderException("File does not exist");
        }
    }

    public void validateFields(DataFormat dataFormat, String[] attributes, int lineNo) {
        if (!isValidNoOfFields(dataFormat, attributes)) {
            throw new StudentsReaderException("Invalid number of fields in line " + lineNo);
        }
        if (!isValidStudentId(dataFormat, attributes[0])) {
            throw new StudentsReaderException("Invalid student_id in line " + lineNo);
        }
        if (!isValidClassNo(dataFormat, attributes[1])) {
            throw new StudentsReaderException("Invalid class_no in line " + lineNo);
        }
        if (!isValidMark(dataFormat, attributes[2])) {
            throw new StudentsReaderException("Invalid mark in line " + lineNo);
        }
    }

    private boolean isValidMark(DataFormat dataFormat, String mark) {
        if (!dataFormat.getMarkPattern().matcher(mark).matches()) {
            return false;
        }
        return Integer.parseInt(mark) <= 100;
    }

    private boolean isValidClassNo(DataFormat dataFormat, String classNo) {
        return dataFormat.getClassNoPattern().matcher(classNo).matches();
    }

    private boolean isValidStudentId(DataFormat dataFormat, String studentId) {
        return dataFormat.getStudentIdPattern().matcher(studentId).matches();
    }

    private boolean isValidNoOfFields(DataFormat dataFormat, String[] attributes) {
        return dataFormat.getNoOfFields() == attributes.length;
    }
}
