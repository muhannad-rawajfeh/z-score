package com.progressoft.jip11.tools.studentswriter;

public class StudentsWriterException extends RuntimeException {

    public StudentsWriterException(String message) {
        super(message);
    }

    public StudentsWriterException(String message, Exception cause) {
        super(message, cause);
    }
}
