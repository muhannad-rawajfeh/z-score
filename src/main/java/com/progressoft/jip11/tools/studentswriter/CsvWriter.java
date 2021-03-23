package com.progressoft.jip11.tools.studentswriter;

import com.progressoft.jip11.tools.exceptions.StudentsWriterException;
import com.progressoft.jip11.tools.objects.StudentInfo;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvWriter implements StudentsWriter {

    @Override
    public void write(List<StudentInfo> info, String fileName) {
        Path path = Paths.get(fileName + ".xls");
        createFile(path);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            String header = "Student_id,class_no,mark,z_score,category\n";
            writer.write(header);
            for (StudentInfo s : info) {
                writer.write(s.toString() + "\n");
            }
        } catch (IOException e) {
            throw new StudentsWriterException(e.getMessage(), e);
        }
    }

    private void createFile(Path path) {
        try {
            Files.createFile(path);
        } catch (FileAlreadyExistsException e) {
            throw new StudentsWriterException("File already exists");
        } catch (IOException e) {
            throw new StudentsWriterException(e.getMessage(), e);
        }
    }
}
