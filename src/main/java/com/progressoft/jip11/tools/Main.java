package com.progressoft.jip11.tools;

import com.progressoft.jip11.tools.objects.StudentInfo;
import com.progressoft.jip11.tools.studentsreader.CsvReader;
import com.progressoft.jip11.tools.utilities.FileGetter;
import com.progressoft.jip11.tools.utilities.Menus;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        Menus.displayWelcome();
        FileGetter fileGetter = new FileGetter(new CsvReader());
        List<StudentInfo> list = fileGetter.getFileAndParse();
        Menus menus = new Menus(list);
        menus.callMainMenu();
    }
}
