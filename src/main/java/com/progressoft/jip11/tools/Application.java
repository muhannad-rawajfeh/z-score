package com.progressoft.jip11.tools;

import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReaderException;
import com.progressoft.jip11.tools.studentsreader.dataformat.StudentInfoFormat;
import com.progressoft.jip11.tools.utilities.Menus;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) throws IOException {
        Menus.displayWelcome();
        StudentsReader studentsReader = new CsvReader(new StudentInfoFormat());
        List<StudentInfo> list = getFilePath(studentsReader);
        Menus menus = new Menus(list);
        callMainMenu(menus);
    }

    private static void callMainMenu(Menus menus) throws IOException {
        String choice = menus.getMainOption();
        switch (choice) {
            case "1":
                menus.displaySummary();
                callMainMenu(menus);
            case "2":
                menus.displaySpecificSummary();
                callMainMenu(menus);
            case "3":
                menus.displayZScores();
                callMainMenu(menus);
            case "4":
                menus.displaySpecificZScores();
                callMainMenu(menus);
            case "5":
                menus.categorizeStudents();
                callMainMenu(menus);
            case "7":
                System.exit(0);
            default:
                System.out.println("Invalid input, try again...");
                callMainMenu(menus);
        }
    }

    private static List<StudentInfo> getFilePath(StudentsReader studentsReader) {
        Scanner scanner = new Scanner(System.in);
        try {
            return studentsReader.parse(scanner.next());
        } catch (StudentsReaderException e) {
            System.out.println(e.getMessage() + ", " + "try again...");
            return getFilePath(studentsReader);
        }
    }
}
