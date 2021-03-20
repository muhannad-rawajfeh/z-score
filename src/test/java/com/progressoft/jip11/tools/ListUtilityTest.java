package com.progressoft.jip11.tools;

import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.utilities.ListUtility;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListUtilityTest {

    ListUtility listUtility = new ListUtility();

    @Test
    void givenClassNo_whenIsExist_ReturnIfExists() {
        List<StudentInfo> studentsInfo = new ArrayList<>();
        StudentInfo s1 = new StudentInfo("123456789", 'A', 60);
        StudentInfo s2 = new StudentInfo("987654321", 'B', 80);
        studentsInfo.add(s1);
        studentsInfo.add(s2);
        assertTrue(listUtility.isClassExist('a', studentsInfo));
        assertFalse(listUtility.isClassExist('c', studentsInfo));
    }

    @Test
    void givenClassNoAndList_whenGetAllInClass_ReturnListOfAllInThatClass() {
        List<StudentInfo> given = new ArrayList<>();
        StudentInfo s1 = new StudentInfo("123456780", 'A', 70);
        StudentInfo s2 = new StudentInfo("987654321", 'B', 80);
        StudentInfo s3 = new StudentInfo("123456789", 'A', 60);
        StudentInfo s4 = new StudentInfo("987654321", 'B', 80);
        given.add(s1);
        given.add(s2);
        given.add(s3);
        given.add(s4);

        List<StudentInfo> expected = new ArrayList<>();
        StudentInfo e1 = new StudentInfo("123456780", 'A', 70);
        StudentInfo e2 = new StudentInfo("123456789", 'A', 60);
        expected.add(e1);
        expected.add(e2);

        List<StudentInfo> result = listUtility.getAllInClass('a', given);

        assertEquals(expected, result);
    }
}