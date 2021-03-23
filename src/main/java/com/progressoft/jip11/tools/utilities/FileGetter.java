package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.exceptions.StudentsReaderException;
import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;

import java.util.List;
import java.util.Scanner;

public class FileGetter {

    private final StudentsReader studentsReader;

    public FileGetter(StudentsReader studentsReader) {
        this.studentsReader = studentsReader;
    }

    public List<StudentInfo> getFileAndParse() {
        Scanner scanner = new Scanner(System.in);
        try {
            return studentsReader.parse(scanner.next());
        } catch (StudentsReaderException e) {
            System.out.println(e.getMessage() + ", try again...");
            return getFileAndParse();
        }
    }
}
