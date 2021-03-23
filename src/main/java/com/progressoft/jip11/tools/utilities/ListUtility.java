package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.objects.StudentBuilder;
import com.progressoft.jip11.tools.objects.StudentInfo;

import java.util.ArrayList;
import java.util.List;

public class ListUtility {

    public boolean isClassExist(char classNo, List<StudentInfo> list) {
        for (StudentInfo s : list) {
            if (isEqual(classNo, s.getClassNo())) {
                return true;
            }
        }
        return false;
    }

    public List<StudentInfo> getAllInClass(char classNo, List<StudentInfo> list) {
        List<StudentInfo> result = new ArrayList<>();
        for (StudentInfo s : list) {
            if (isEqual(classNo, s.getClassNo())) {
                result.add(new StudentInfo(s.getId(), s.getClassNo(), s.getMark()));
            }
        }
        return result;
    }

    public int countElite(List<StudentInfo> list, double eliteDev) {
        return countCategories((zScore, dev) -> zScore >= eliteDev, list, eliteDev);
    }

    public int countFailed(List<StudentInfo> list, double failedDev) {
        return countCategories((zScore, dev) -> zScore <= failedDev, list, failedDev);
    }

    public int countPassed(List<StudentInfo> list, double eliteDev, double failedDev) {
        int count = 0;
        for (StudentInfo s : list) {
            if (s.getZScore() < eliteDev && s.getZScore() > failedDev) {
                count++;
            }
        }
        return count;
    }

    public int getPassingScore(List<StudentInfo> list, double eliteDev, double failedDev) {
        int passingScore = 1000;
        for (StudentInfo s : list) {
            if (s.getZScore() < eliteDev && s.getZScore() > failedDev) {
                int mark = s.getMark();
                if (mark < passingScore) {
                    passingScore = mark;
                }
            }
        }
        return passingScore;
    }

    public int getEliteScore(List<StudentInfo> list) {
        int eliteScore = -1;
        for (StudentInfo s : list) {
            int mark = s.getMark();
            if (mark > eliteScore) {
                eliteScore = mark;
            }
        }
        return eliteScore;
    }

    public List<StudentInfo> findAllCategories(List<StudentInfo> list, double eliteDev, double failedDev) {
        List<StudentInfo> result = new ArrayList<>();
        for (StudentInfo s : list) {
            String category = getCategory(s.getZScore(), eliteDev, failedDev);
            StudentInfo studentInfo = new StudentInfo(new StudentBuilder(s.getId(), s.getClassNo(), s.getMark())
                    .setZScore(s.getZScore()).setCategory(category));
            result.add(studentInfo);
        }
        return result;
    }

    private String getCategory(double zScore, double eliteDev, double failedDev) {
        if (zScore >= eliteDev) {
            return "Elite";
        }
        if (zScore <= failedDev) {
            return "Failed";
        }
        return "Passed";
    }

    private boolean isEqual(char c1, char c2) {
        return Character.toLowerCase(c1) == c2 || Character.toUpperCase(c1) == c2;
    }

    private int countCategories(Predicate predicate, List<StudentInfo> list, double dev) {
        int count = 0;
        for (StudentInfo s : list) {
            if (predicate.evaluate(s.getZScore(), dev)) {
                count++;
            }
        }
        return count;
    }

    @FunctionalInterface
    interface Predicate {

        boolean evaluate(double zScore, double dev);
    }
}
