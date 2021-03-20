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

    private boolean isEqual(char c1, char c2) {
        return Character.toLowerCase(c1) == c2 || Character.toUpperCase(c1) == c2;
    }
}
