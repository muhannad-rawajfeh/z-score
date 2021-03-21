package com.progressoft.jip11.tools.studentswriter;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter implements StudentsWriter {

    @Override
    public void write(String info) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Students_Categories.csv", true))) {
            writer.write("Student_id,class_no,mark,z_score,category\n");
            writer.write(info);
        } catch (IOException e) {
            throw new StudentsWriterException(e.getMessage(), e);
        }
    }
}
