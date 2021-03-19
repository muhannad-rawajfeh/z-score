package com.progressoft.jip11.tools;

import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReaderException;
import com.progressoft.jip11.tools.studentsreader.dataformat.StudentInfoFormat;

import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        System.out.println("-----------------------------------------------------\n" +
                            "Welcome to Z-Score app. Please insert your file path:\n" +
                            "-----------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        StudentsReader studentsReader = new CsvReader(new StudentInfoFormat());
        List<StudentInfo> result = start(scanner, studentsReader);
        Menus menus = new Menus();
        switch (menus.displayMain()) {
            case 1:
                menus.displaySummary(result);
        }
    }

    private static List<StudentInfo> start(Scanner scanner, StudentsReader studentsReader) {
        try {
            return studentsReader.parse(scanner.next());
        } catch (StudentsReaderException e) {
            System.out.println(e.getMessage() + "\n" + "Try again? enter y if yes or anything else if no...");
            String choice = scanner.next();
            if (!choice.equals("y")) {
                System.exit(0);
            } else {
                System.out.println("Insert your path:");
                return start(scanner, studentsReader);
            }
        }
        throw new IllegalStateException();
    }
}
