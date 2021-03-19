package com.progressoft.jip11.tools.studentsreader.dataformat;

import java.util.regex.Pattern;

public class StudentInfoFormat implements DataFormat {

    private static final int NUMBER_OF_FIELDS = 3;
    private static final Pattern studentIdPattern = Pattern.compile("\\d+");
    private static final Pattern classNoPattern = Pattern.compile("([A-F])");
    private static final Pattern markPattern = Pattern.compile("\\d{1,3}");

    @Override
    public int getNoOfFields() {
        return NUMBER_OF_FIELDS;
    }

    @Override
    public Pattern getStudentIdPattern() {
        return studentIdPattern;
    }

    @Override
    public Pattern getClassNoPattern() {
        return classNoPattern;
    }

    @Override
    public Pattern getMarkPattern() {
        return markPattern;
    }
}
