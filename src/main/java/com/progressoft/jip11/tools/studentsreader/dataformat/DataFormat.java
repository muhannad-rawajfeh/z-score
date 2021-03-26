package com.progressoft.jip11.tools.studentsreader.dataformat;

import java.util.regex.Pattern;

public interface DataFormat {

    int getNoOfFields();

    Pattern getStudentIdPattern();

    Pattern getClassNoPattern();

    Pattern getMarkPattern();
}
