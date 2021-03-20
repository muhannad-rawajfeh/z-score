package com.progressoft.jip11.tools;

import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReaderException;
import com.progressoft.jip11.tools.studentsreader.dataformat.StudentInfoFormat;
import com.progressoft.jip11.tools.utilities.Menus;

import java.util.List;
import java.util.Scanner;

public class Application {

    public static void main(String[] args) {
        Menus.displayWelcome();
        StudentsReader studentsReader = new CsvReader(new StudentInfoFormat());
        List<StudentInfo> list = getFilePath(studentsReader);
        Menus menus = new Menus(list);
        int choice = menus.getMainOption();
        switch (choice) {
            case 1:
                menus.displaySummary();
            case 2:
                menus.displaySpecificSummary();
            case 3:
                menus.displayZscores();
            case 4:
                menus.displaySpecificZscores();
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
