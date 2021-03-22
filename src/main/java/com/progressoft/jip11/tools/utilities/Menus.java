package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentswriter.CsvWriter;
import com.progressoft.jip11.tools.studentswriter.StudentsWriter;
import com.progressoft.jip11.tools.studentswriter.StudentsWriterException;

import java.util.List;
import java.util.Scanner;

public class Menus {

    private final List<StudentInfo> allStudents;

    public Menus(List<StudentInfo> list) {
        this.allStudents = list;
    }

    public static void displayWelcome() {
        System.out.println("-----------------------------------------------------\n" +
                "Welcome to Z-Score app. Please insert your file path:\n" +
                "-----------------------------------------------------");
    }

    public String getMainOption() {
        displayMainMenu();
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public void displaySummary() {
        doDisplaySummary(allStudents);
    }

    public void disSpecSummary() {
        supplyMenu(this::disSpecSummary, 1);
    }

    public void displayZScores() {
        doDisplayZScores(allStudents);
    }

    public void disSpecZScores() {
        supplyMenu(this::disSpecZScores, 2);
    }

    public void catStudents() {
        doCatStudents(allStudents);
    }

    public void catSpecClass() {
        supplyMenu(this::catSpecClass, 3);
    }

    private void supplyMenu(MenuSupplier menuSupplier, int methodNo) {
        System.out.println("Enter class_no:");
        String input = getClassNo();
        char classNo = input.charAt(0);
        ListUtility listUtility = new ListUtility();
        if (!listUtility.isClassExist(classNo, allStudents)) {
            System.out.println("Classroom does not exist, try again...");
            menuSupplier.supply();
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
        System.out.println("Enter Elite Deviations: ");
        double eliteDev = getDev();
        System.out.println("Enter Failed Deviations: ");
        double failedDev = getDev();
        if (failedDev > eliteDev) {
            System.out.println("Failed deviations cannot be higher than Elite deviations, try again...");
            catStudents();
        } else {
            ZCalculator zCalculator = new ZCalculator(list);
            String[] result = zCalculator.findAllZScores().split("[,\\n]");
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
                String categories = listUtility.findAllCategories(result, eliteDev, failedDev);
                StudentsWriter writer = new CsvWriter();
                String fileName = getFileName();
                try {
                    writer.write(categories, fileName);
                } catch (StudentsWriterException e) {
                    System.out.println(e.getMessage() + ", try again...");;
                }
            }
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
        String[] result = zCalculator.findAllZScores().split("[,\\n]");
        for (int i = 0; i < result.length; i += 4) {
            System.out.format("%15s%15s%15s%15s%n", result[i], result[i+1], result[i+2], result[i+3]);
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
    interface MenuSupplier {

        void supply();
    }
}
