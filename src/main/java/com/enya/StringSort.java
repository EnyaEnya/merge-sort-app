package com.enya;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class StringSort {

    private boolean ascending;

    public StringSort(boolean ascending) {
        this.ascending = ascending;
    }

    private void merge(File output, File firstFile, File secondFile) throws IOException {
        long len_1 = countString(firstFile), len_2 = countString(secondFile);
        long a = 0, b = 0, len = len_1 + len_2;
        for (long i = 0; i < len; i++) {
            if (b < len_2 && a < len_1) {
                if (compareWithDirection(firstFile, a, secondFile, b)) {
                    writeToFile(output, getSpecificString(secondFile, b));
                    b++;
                } else {
                    writeToFile(output, getSpecificString(firstFile, a));
                    a++;
                }
            } else if (b < len_2) {
                writeToFile(output, getSpecificString(secondFile, b));
                b++;
            } else {
                writeToFile(output, getSpecificString(firstFile, a));
                a++;
            }
        }
    }

    public void createOutputFile(String outputPath, String firstFilePath, String secondFilePath) throws IOException {
        File outputFile = new File(outputPath);
        File firstFile = new File(firstFilePath);
        File secondFile = new File(secondFilePath);
        try {
            FileUtils.touch(outputFile);
            FileUtils.write(outputFile, "", Charset.defaultCharset());  //todo check encoding
        } catch (Exception e) {
            e.printStackTrace();
        }
        merge(outputFile, firstFile, secondFile);
    }

    private long countString(File file) throws IOException {
        try (Stream<String> lines = Files.lines(file.toPath())) {
            return lines.count();
        }
    }

    private String getSpecificString(File file, long stringNumber) throws IOException {
        String specificLine;
        try (Stream<String> lines = Files.lines(file.toPath())) {
            specificLine = lines.skip(stringNumber).findFirst().orElseThrow(RuntimeException::new); //todo custom exception
        }
        return specificLine;
    }


    private void writeToFile(File file, String recordedValue) {
        String newRecordedValue = recordedValue + "\n";
        try {
            Files.write(file.toPath(), newRecordedValue.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private boolean compareWithDirection(File firstFile, Long index1, File secondFile, Long index2) throws IOException {
        return compare(getSpecificString(firstFile, index1), getSpecificString(secondFile, index2)) == ascending;
    }

    protected boolean compare(String first, String second) {
        return first.compareTo(second) > 0;
    }

}


