package com.progressoft.jip11.tools;

import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;
import com.progressoft.jip11.tools.studentsreader.dataformat.StudentInfoFormat;

import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        System.out.println("Welcome to Z-Score app. Please insert your file path:");
        Scanner scanner = new Scanner(System.in);
        StudentsReader studentsReader = new CsvReader(new StudentInfoFormat());
        List<StudentInfo> studentsInfo = studentsReader.parse(scanner.next());
    }
}
