package com.progressoft.jip11.tools.exceptions;

public class StudentsReaderException extends RuntimeException {

    public StudentsReaderException(String message) {
        super(message);
    }

    public StudentsReaderException(String message, Exception cause) {
        super(message, cause);
    }
}
