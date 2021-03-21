package com.progressoft.jip11.tools.utilities;

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

    public int countElite(String[] zScores, double eliteDev) {
        int count = 0;
        for (int i = 3; i < zScores.length; i += 4) {
            double z = Double.parseDouble(zScores[i]);
            if (z >= eliteDev) {
                count++;
            }
        }
        return count;
    }

    public int countFailed(String[] zScores, double failedDev) {
        int count = 0;
        for (int i = 3; i < zScores.length; i += 4) {
            double z = Double.parseDouble(zScores[i]);
            if (z <= failedDev) {
                count++;
            }
        }
        return count;
    }

    public int countPassed(String[] zScores, double eliteDev, double failedDev) {
        int count = 0;
        for (int i = 3; i < zScores.length; i += 4) {
            double z = Double.parseDouble(zScores[i]);
            if (z < eliteDev && z > failedDev) {
                count++;
            }
        }
        return count;
    }

    public int getPassingScore(String[] zScores, double eliteDev, double failedDev) {
        int passingScore = 1000;
        for (int i = 3; i < zScores.length; i += 4) {
            double z = Double.parseDouble(zScores[i]);
            if (z < eliteDev && z > failedDev) {
                int mark = Integer.parseInt(zScores[i - 1]);
                if (mark < passingScore) {
                    passingScore = mark;
                }
            }
        }
        return passingScore;
    }

    public int getEliteScore(String[] zScores) {
        int eliteScore = -1;
        for (int i = 2; i < zScores.length; i += 4) {
            int mark = Integer.parseInt(zScores[i]);
            if (mark > eliteScore) {
                eliteScore = mark;
            }
        }
        return eliteScore;
    }

    public String findAllCategories(String[] zScores, double eliteDev, double failedDev) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < zScores.length; i += 4) {
            String category = getCategory(zScores[i+3], eliteDev, failedDev);
            stringBuilder.append(zScores[i]).append(",").append(zScores[i+1]).append(",")
                    .append(zScores[i+2]).append(",").append(zScores[i+3]).append(",").append(category).append("\n");
        }
        stringBuilder.delete(stringBuilder.length() - 1, stringBuilder.length());
        return stringBuilder.toString();
    }

    private String getCategory(String zScore, double eliteDev, double failedDev) {
        double zs = Double.parseDouble(zScore);
        if (zs >= eliteDev) {
            return "Elite";
        }
        if (zs <= failedDev) {
            return "Failed";
        }
        return "Passed";
    }

    private boolean isEqual(char c1, char c2) {
        return Character.toLowerCase(c1) == c2 || Character.toUpperCase(c1) == c2;
    }
}
