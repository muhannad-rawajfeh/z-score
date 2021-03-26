package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.objects.CategorizeRequest;
import com.progressoft.jip11.tools.objects.CategorizedStudent;
import com.progressoft.jip11.tools.objects.ScoredStudent;
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

    public int countElite(List<ScoredStudent> list, double eliteDev) {
        return countCategories((zScore, dev) -> zScore >= eliteDev, list, eliteDev);
    }

    public int countFailed(List<ScoredStudent> list, double failedDev) {
        return countCategories((zScore, dev) -> zScore <= failedDev, list, failedDev);
    }

    public int countPassed(List<ScoredStudent> list, double eliteDev, double failedDev) {
        int count = 0;
        for (ScoredStudent s : list) {
            if (s.getZScore() < eliteDev && s.getZScore() > failedDev) {
                count++;
            }
        }
        return count;
    }

    public int getPassingScore(List<ScoredStudent> list, double eliteDev, double failedDev) {
        int passingScore = 1000;
        for (ScoredStudent s : list) {
            if (s.getZScore() < eliteDev && s.getZScore() > failedDev) {
                int mark = s.getStudentInfo().getMark();
                if (mark < passingScore) {
                    passingScore = mark;
                }
            }
        }
        return passingScore;
    }

    public int getEliteScore(List<ScoredStudent> list) {
        int eliteScore = -1;
        for (ScoredStudent s : list) {
            int mark = s.getStudentInfo().getMark();
            if (mark > eliteScore) {
                eliteScore = mark;
            }
        }
        return eliteScore;
    }

    public List<CategorizedStudent> findCategories(CategorizeRequest request) {
        List<CategorizedStudent> result = new ArrayList<>();
        for (ScoredStudent s : (List<ScoredStudent>) request.getList()) {
            String category = getCategory(s.getZScore(), request.getEliteDev(), request.getFailedDev());
            CategorizedStudent cs = new CategorizedStudent(s, category);
            result.add(cs);
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

    private int countCategories(Predicate predicate, List<ScoredStudent> list, double dev) {
        int count = 0;
        for (ScoredStudent s : list) {
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
