package com.progressoft.jip11.tools.studentswriter;

import com.progressoft.jip11.tools.objects.CategorizedStudent;

import java.util.List;

public interface StudentsWriter {

    void write(List<CategorizedStudent> info, String fileName);
}
