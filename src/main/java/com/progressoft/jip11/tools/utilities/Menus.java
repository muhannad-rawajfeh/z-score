package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentswriter.CsvWriter;
import com.progressoft.jip11.tools.studentswriter.StudentsWriter;
import com.progressoft.jip11.tools.exceptions.StudentsWriterException;

import java.util.List;
import java.util.Scanner;

public class Menus {

    private final List<StudentInfo> allStudents;

    public Menus(List<StudentInfo> list) {
        this.allStudents = list;
    }

    public static void displayWelcome() {
        System.out.println("-----------------------------------------------------\n" +
                "Welcome to Z-Score App. Please insert your file path:\n" +
                "-----------------------------------------------------");
    }

    public void goToMainMenu() {
        String choice = getOptionNo();
        switch (choice) {
            case "1":
                displaySummary();
                goToMainMenu();
            case "2":
                disSpecSummary();
                goToMainMenu();
            case "3":
                displayZScores();
                goToMainMenu();
            case "4":
                disSpecZScores();
                goToMainMenu();
            case "5":
                catStudents();
                goToMainMenu();
            case "6":
                catSpecClass();
                goToMainMenu();
            case "7":
                System.exit(0);
            default:
                System.out.println("Invalid input, try again...");
                goToMainMenu();
        }
    }

    private String getOptionNo() {
        displayMainMenu();
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    private void displaySummary() {
        doDisplaySummary(allStudents);
    }

    private void disSpecSummary() {
        consumeMenu(this::disSpecSummary, 1);
    }

    private void displayZScores() {
        doDisplayZScores(allStudents);
    }

    private void disSpecZScores() {
        consumeMenu(this::disSpecZScores, 2);
    }

    private void catStudents() {
        doCatStudents(allStudents);
    }

    private void catSpecClass() {
        consumeMenu(this::catSpecClass, 3);
    }

    private void consumeMenu(MenuConsumer menuConsumer, int methodNo) {
        System.out.println("Enter class_no:");
        String input = getClassNo();
        char classNo = input.charAt(0);
        ListUtility listUtility = new ListUtility();
        if (!listUtility.isClassExist(classNo, allStudents)) {
            System.out.println("Classroom does not exist, try again...");
            menuConsumer.consume();
        } else {
            List<StudentInfo> specificClassList = listUtility.getAllInClass(classNo, allStudents);
            if (methodNo == 1) {
                doDisplaySummary(specificClassList);
            } else if (methodNo == 2) {
                doDisplayZScores(specificClassList);
            } else if (methodNo == 3) {
                doCatStudents(specificClassList);
            }
        }
    }

    private void doCatStudents(List<StudentInfo> list) {
        System.out.println("Enter Elite Deviations:");
        double eliteDev = getDev();
        System.out.println("Enter Failed Deviations:");
        double failedDev = getDev();
        if (failedDev >= eliteDev) {
            System.out.println("Failed deviations cannot be higher than or equal Elite deviations, try again...");
            catStudents();
        } else {
            ZCalculator zCalculator = new ZCalculator(list);
            List<StudentInfo> result = zCalculator.findAllZScores();
            ListUtility listUtility = new ListUtility();
            int c1 = listUtility.countElite(result, eliteDev);
            int c2 = listUtility.countPassed(result, eliteDev, failedDev);
            int c3 = listUtility.countFailed(result, failedDev);
            int c4 = listUtility.getPassingScore(result, eliteDev, failedDev);
            int c5 = listUtility.getEliteScore(result);
            printSummary(list, c1, c2, c3, c4, c5);
            System.out.println("Do you want to save the results to a file (yes/no):");
            String answer = getAnswer();
            if (answer.equalsIgnoreCase("yes")) {
                List<StudentInfo> categories = listUtility.findAllCategories(result, eliteDev, failedDev);
                StudentsWriter writer = new CsvWriter();
                saveToFile(categories, writer);
            }
        }
    }

    private void saveToFile(List<StudentInfo> categories, StudentsWriter writer) {
        String fileName = getFileName();
        try {
            writer.write(categories, fileName);
        } catch (StudentsWriterException e) {
            System.out.println(e.getMessage() + ", try again...");
            saveToFile(categories, writer);
        }
    }

    private String getFileName() {
        System.out.println("Name your file:");
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    private void printSummary(List<StudentInfo> list, int c1, int c2, int c3, int c4, int c5) {
        doDisplaySummary(list);
        System.out.println("Elite students count: " + c1 + "\n" +
                "Passed students count: " + c2 + "\n" +
                "Failed students count: " + c3 + "\n" +
                "Passing score: " + c4 + "\n" +
                "Elite score: " + c5 + "\n");
    }

    private void doDisplayZScores(List<StudentInfo> list) {
        System.out.format("%15s%15s%15s%15s%n", "Student_ID", "Classroom", "Mark", "Z-Score");
        ZCalculator zCalculator = new ZCalculator(list);
        List<StudentInfo> result = zCalculator.findAllZScores();
        for (StudentInfo s : result) {
            System.out.format("%15s%15s%15s%15s%n", s.getId(), s.getClassNo(), s.getMark(), s.getZScore());
        }
    }

    private void doDisplaySummary(List<StudentInfo> list) {
        ZCalculator zCalculator = new ZCalculator(list);
        double median = zCalculator.findMedian();
        double variance = zCalculator.findVariance();
        double deviation = zCalculator.findDeviation();
        int count = zCalculator.getCount();
        System.out.println("Median: " + median + "\n" +
                "Variance: " + variance + "\n" +
                "Standard Deviation: " + deviation + "\n" +
                "Total Count: " + count);
    }

    private String getAnswer() {
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.next();
        if (isValidAnswer(answer)) {
            return answer;
        }
        System.out.println("Invalid Input, try again...");
        return getAnswer();
    }

    private boolean isValidAnswer(String answer) {
        return answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no");
    }

    private double getDev() {
        Scanner scanner = new Scanner(System.in);
        try {
            return scanner.nextDouble();
        } catch (Exception e) {
            System.out.println("Invalid input, try again...");
            return getDev();
        }
    }

    private String getClassNo() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        if (!input.matches("[a-zA-z]")) {
            System.out.println("Invalid input, try again...");
            return getClassNo();
        }
        return input;
    }

    private void displayMainMenu() {
        System.out.println("---------------------------------------\n" +
                "What would you like to do?\n" +
                "Choose by inserting an option number:\n" +
                "---------------------------------------\n" +
                "1- Summary\n" +
                "2- Summary for a specific class\n" +
                "3- Display Z-scores\n" +
                "4- Display Z-scores for a specific class\n" +
                "5- Categorize students\n" +
                "6- Categorize students in a specific class\n" +
                "7- Exit\n");
    }

    @FunctionalInterface
    interface MenuConsumer {

        void consume();
    }
}
