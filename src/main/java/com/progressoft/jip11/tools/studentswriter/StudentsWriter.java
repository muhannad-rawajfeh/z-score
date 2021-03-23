package com.progressoft.jip11.tools.studentswriter;

import com.progressoft.jip11.tools.objects.StudentInfo;

import java.util.List;

public interface StudentsWriter {

    void write(List<StudentInfo> info, String fileName);
}
