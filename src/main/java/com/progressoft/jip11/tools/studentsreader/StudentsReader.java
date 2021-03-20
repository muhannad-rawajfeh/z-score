package com.progressoft.jip11.tools.studentsreader;

import com.progressoft.jip11.tools.objects.StudentInfo;

import java.util.List;

public interface StudentsReader {
    List<StudentInfo> parse(String path);
}
