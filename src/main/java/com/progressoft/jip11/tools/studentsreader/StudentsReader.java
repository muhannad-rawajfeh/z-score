package com.progressoft.jip11.tools.studentsreader;

import com.progressoft.jip11.tools.StudentInfo;

import java.nio.file.Path;
import java.util.List;

public interface StudentsReader {
    List<StudentInfo> parse(Path path);
}
