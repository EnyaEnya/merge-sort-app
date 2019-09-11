package com.enya;

import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class Sort {

    public static void merge(File firstFile, File secondFile, File output) throws IOException {
        long len_1 = countString(firstFile), len_2 = countString(secondFile);
        long a = 0, b = 0, len = len_1 + len_2;
        for (long i = 0; i < len; i++) {
            if (b < len_2 && a < len_1) {
                if (getSpecificString(firstFile, a).compareTo(getSpecificString(secondFile, b)) == 1) {
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

    public static void createOutputFile(String firstFilePath, String secondFilePath, String outputPath) throws IOException {
        File firstFile = new File(firstFilePath);
        File secondFile = new File(secondFilePath);
        File outputFile = new File(outputPath);
        try {
            FileUtils.touch(outputFile);
            FileUtils.write(outputFile, "", Charset.defaultCharset());  //todo check encoding
        } catch (Exception e) {
            e.printStackTrace();
        }
        merge(firstFile, secondFile, outputFile);
    }

    public static long countString(File file) throws IOException {
        try (Stream<String> lines = Files.lines(file.toPath())) {
            return lines.count();
        }
    }

    public static String getSpecificString(File file, long stringNumber) throws IOException {
        String specificLine;
        try (Stream<String> lines = Files.lines(file.toPath())) {
            specificLine = lines.skip(stringNumber).findFirst().orElseThrow(RuntimeException::new); //todo custom exception
        }
        return specificLine;
    }


    public static void writeToFile(File file, String recordedValue) {
        String newRecordedValue = recordedValue + "\n";
        try {
            Files.write(file.toPath(), newRecordedValue.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}


