package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.exceptions.StudentsWriterException;
import com.progressoft.jip11.tools.objects.*;
import com.progressoft.jip11.tools.studentswriter.CsvWriter;
import com.progressoft.jip11.tools.studentswriter.StudentsWriter;

import java.util.List;
import java.util.Scanner;

public class Menus {

    private final ListUtility listUtility = new ListUtility();
    private final List<StudentInfo> allStudents;

    public Menus(List<StudentInfo> list) {
        this.allStudents = list;
    }

    public static void displayWelcome() {
        System.out.println("-----------------------------------------------------\n" +
                "Welcome to Z-Score App. Please insert your file path:\n" +
                "-----------------------------------------------------");
    }

    private static void displayMainMenu() {
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

    public void callMainMenu() {
        String choice = getOptionNo();
        switch (choice) {
            case "1":
                displaySummary();
                break;
            case "2":
                disSpecSummary();
                break;
            case "3":
                displayZScores();
                break;
            case "4":
                disSpecZScores();
                break;
            case "5":
                catStudents();
                break;
            case "6":
                catSpecClass();
                break;
            case "7":
                return;
            default:
                System.out.println("Invalid input, try again...");
        }
        callMainMenu();
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
        consumeMenu(this::disSpecSummary, this::doDisplaySummary);
    }

    private void displayZScores() {
        doDisplayZScores(allStudents);
    }

    private void disSpecZScores() {
        consumeMenu(this::disSpecZScores, this::doDisplayZScores);
    }

    private void catStudents() {
        doCatStudents(allStudents);
    }

    private void catSpecClass() {
        consumeMenu(this::catSpecClass, this::doCatStudents);
    }

    private void consumeMenu(FailedAction failedAction, SuccessAction successAction) {
        System.out.println("Enter class_no:");
        String input = getClassNo();
        char classNo = input.charAt(0);
        if (!listUtility.isClassExist(classNo, allStudents)) {
            System.out.println("Classroom does not exist, try again...");
            failedAction.fail();
        } else {
            List<StudentInfo> specificClassList = listUtility.getAllInClass(classNo, allStudents);
            successAction.succeed(specificClassList);
        }
    }

    private void doCatStudents(List<StudentInfo> list) {
        System.out.println("Enter Elite Deviations:");
        double eliteDev = getDev();
        System.out.println("Enter Failed Deviations:");
        double failedDev = getDev();
        if (failedDev >= eliteDev) {
            System.out.println("Elite deviations should be higher than Failed deviations, try again...");
            catStudents();
        } else {
            CategorizeRequest request = new CategorizeRequest(list, eliteDev, failedDev);
            startCategorizing(request);
        }
    }

    private void startCategorizing(CategorizeRequest request) {
        ZCalculator zCalculator = new ZCalculator((List<StudentInfo>) request.getList());
        List<ScoredStudent> result = zCalculator.findZScores();
        printSummary(request, result);
        System.out.println("Do you want to save the results to a file (yes/no):");
        String answer = getAnswer();
        if (answer.equalsIgnoreCase("yes")) {
            CategorizeRequest request2 = new CategorizeRequest(result, request.getEliteDev(), request.getFailedDev());
            List<CategorizedStudent> categories = listUtility.findCategories(request2);
            StudentsWriter writer = new CsvWriter();
            saveToFile(categories, writer);
        }
    }

    private void printSummary(CategorizeRequest request, List<ScoredStudent> result) {
        double eliteDev = request.getEliteDev();
        double failedDev = request.getFailedDev();
        int c1 = listUtility.countElite(result, eliteDev);
        int c2 = listUtility.countPassed(result, eliteDev, failedDev);
        int c3 = listUtility.countFailed(result, failedDev);
        int c4 = listUtility.getPassingScore(result, eliteDev, failedDev);
        int c5 = listUtility.getEliteScore(result);
        doDisplaySummary((List<StudentInfo>) request.getList());
        System.out.println("Elite students count: " + c1 + "\n" +
                "Passed students count: " + c2 + "\n" +
                "Failed students count: " + c3 + "\n" +
                "Passing score: " + c4 + "\n" +
                "Elite score: " + c5 + "\n");
    }

    private void saveToFile(List<CategorizedStudent> categories, StudentsWriter writer) {
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

    private void doDisplayZScores(List<StudentInfo> list) {
        System.out.format("%15s%15s%15s%15s%n", "Student_ID", "Classroom", "Mark", "Z-Score");
        ZCalculator zCalculator = new ZCalculator(list);
        List<ScoredStudent> result = zCalculator.findZScores();
        for (ScoredStudent s : result) {
            StudentInfo ss = s.getStudentInfo();
            System.out.format("%15s%15s%15s%15s%n", ss.getId(), ss.getClassNo(), ss.getMark(), s.getZScore());
        }
    }

    private void doDisplaySummary(List<StudentInfo> list) {
        SummaryResults results = getSummaryResults(list);
        System.out.println("Median: " + results.getMedian() + "\n" +
                "Variance: " + results.getVariance() + "\n" +
                "Standard Deviation: " + results.getStDeviation() + "\n" +
                "Total Count: " + results.getTotalCount());
    }

    private SummaryResults getSummaryResults(List<StudentInfo> list) {
        ZCalculator zCalculator = new ZCalculator(list);
        double median = zCalculator.findMedian();
        double variance = zCalculator.findVariance();
        double deviation = zCalculator.findStDeviation();
        int count = zCalculator.getCount();
        return new SummaryResults(median, variance, deviation, count);
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

    @FunctionalInterface
    interface SuccessAction {

        void succeed(List<StudentInfo> list);
    }

    @FunctionalInterface
    interface FailedAction {

        void fail();
    }
}
