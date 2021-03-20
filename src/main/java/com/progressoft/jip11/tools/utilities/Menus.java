package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.objects.StudentInfo;

import java.util.List;
import java.util.Scanner;

public class Menus {

    private final List<StudentInfo> list;

    public Menus(List<StudentInfo> list) {
        this.list = list;
    }

    public static void displayWelcome() {
        System.out.println("-----------------------------------------------------\n" +
                "Welcome to Z-Score app. Please insert your file path:\n" +
                "-----------------------------------------------------");
    }

    public int getMainOption() {
        displayMainMenu();
        Scanner scanner = new Scanner(System.in);
        try {
            int choice = scanner.nextInt();
            if (choice > 0 && choice < 8) {
                return choice;
            }
            System.out.println("Invalid input, please try again...");
            return getMainOption();
        } catch (Exception e) {
            System.out.println("Invalid input, please try again...");
            return getMainOption();
        }
    }

    public void displaySummary() {
        findSummary(list);
    }

    public void displaySpecificSummary() {
        supplyMenu(this::displaySpecificZscores, 1);
    }

    public void displayZscores() {
        findZscores(list);
    }

    public void displaySpecificZscores() {
        supplyMenu(this::displaySpecificZscores, 2);
    }

    private void supplyMenu(MenuSupplier menuSupplier, int methodNo) {
        System.out.println("Enter class_no:");
        String input = getClassNo();
        char classNo = input.charAt(0);
        ListUtility listUtility = new ListUtility();
        if (!listUtility.isClassExist(classNo, list)) {
            System.out.println("Classroom does not exist, try again...");
            menuSupplier.supply();
        } else {
            List<StudentInfo> specificClassList = listUtility.getAllInClass(classNo, list);
            if (methodNo == 1) {
                findSummary(specificClassList);
            } else if (methodNo == 2) {
                findZscores(specificClassList);
            }
        }
    }

    @FunctionalInterface
    interface MenuSupplier {

        void supply();
    }

    private void findZscores(List<StudentInfo> list) {
        System.out.format("%15s%15s%15s%15s%n", "Student ID", "Classroom", "Mark", "Z-Score");
        ZCalculator zCalculator = new ZCalculator(list);
        String[] result = zCalculator.findAllZscores().split(",|\\n");
        for (int i = 0; i < result.length; i += 4) {
            System.out.format("%15s%15s%15s%15s%n", result[i], result[i+1], result[i+2], result[i+3]);
        }
    }

    private void findSummary(List<StudentInfo> list) {
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
                "Choose by inserting an option number...\n" +
                "---------------------------------------\n" +
                "1- Summary\n" +
                "2- Summary for a specific class\n" +
                "3- Display Z-scores\n" +
                "4- Display Z-scores for a specific class\n" +
                "5- Categorize students\n" +
                "6- Categorize students in a specific class\n" +
                "7- Exit\n");
    }
}
