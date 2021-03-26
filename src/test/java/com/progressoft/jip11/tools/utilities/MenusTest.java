package com.progressoft.jip11.tools.utilities;

import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.studentsreader.StudentsReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MenusTest {

    private final StudentsReader studentsReader = new CsvReader();
    private final List<StudentInfo> list = studentsReader.parse("src/test/resources/valid-file-2.csv");
    private final Menus menus = new Menus(list);
    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    @Disabled
    void givenOption1Input_whenGoToMainMenu_thenDisplaySummary() {
        String input1 = "1";
        InputStream in1 = new ByteArrayInputStream(input1.getBytes());
        System.setIn(in1);

        // to stop recursion I enter 7 to exit
        String input2 = "7";
        InputStream in2 = new ByteArrayInputStream(input2.getBytes());

        menus.callMainMenu();

        System.setIn(in2);

        assertEquals("foo", outputStream.toString());
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }
}