package com.progressoft.jip11.tools;

import java.util.List;
import java.util.Scanner;

public class Menus {

    public void displayWelcome() {
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

    public void displaySummary(List<StudentInfo> result) {
        ZCalculator zCalculator = new ZCalculator(result);
        double median = zCalculator.findMedian();
        double variance = zCalculator.findVariance();
        double deviation = zCalculator.findDeviation();
        int count = zCalculator.getCount();
        System.out.println("Median: " + median + "\n" +
                "Variance: " + variance + "\n" +
                "Standard Deviation: " + deviation + "\n" +
                "Total Count: " + count);
        getMainOption();
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
                "7- Exit");
    }
}
