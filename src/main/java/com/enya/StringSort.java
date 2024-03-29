package com.enya;

import com.enya.exceptions.WrongStringIndexException;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Stream;

public class StringSort {

    private boolean ascending;

    private List<File> tempFiles;

    public StringSort(boolean ascending) {
        this.ascending = ascending;
        this.tempFiles = new ArrayList<>();
    }

    public void mergeFiles(Set<File> fileSet, File outputFile) throws IOException {
        prepareOutputFile(outputFile);
        Set<File> newFileSet = new HashSet<>();
        try {
            fileSet.stream().map(file -> {
                File newFile = new File(UUID.randomUUID().toString() + ".txt");
                newFileSet.add(newFile);
                try {
                    FileUtils.touch(newFile);
                    FileUtils.copyFile(file, newFile);
                    if (!isSorted(file)) {
                        sort(newFile);
                    }
                    return newFile;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).forEach(file -> {
                try {
                    mergeWithAcc(file, outputFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            });
        } finally {
            newFileSet.forEach(FileUtils::deleteQuietly);
        }
    }

    private void prepareOutputFile(File outputFile) throws IOException {
        FileUtils.touch(outputFile);
        FileUtils.write(outputFile, "", Charset.defaultCharset());
    }

    private boolean isSorted(File file) throws IOException {
        long length = countString(file);

        if (length < 2) {
            return true;
        }

        for (long i = 0; i < length - 1; i++) {
            if (!compareWithDirection(file, i, file, i + 1)) {
                return false;
            }
        }
        return true;
    }

    protected int compare(String first, String second) {
        return Integer.compare(first.compareTo(second), 0);
    }

    private void merge(File firstFile, File secondFile, File outputFile) throws IOException {
        long len_1 = countString(firstFile), len_2 = countString(secondFile);
        long a = 0, b = 0, len = len_1 + len_2;
        for (long i = 0; i < len; i++) {
            if (b < len_2 && a < len_1) {
                if (!compareWithDirection(firstFile, a, secondFile, b)) {
                    writeToFile(outputFile, getSpecificString(secondFile, b));
                    b++;
                } else {
                    writeToFile(outputFile, getSpecificString(firstFile, a));
                    a++;
                }
            } else if (b < len_2) {
                writeToFile(outputFile, getSpecificString(secondFile, b));
                b++;
            } else {
                writeToFile(outputFile, getSpecificString(firstFile, a));
                a++;
            }
        }
    }

    private File mergeWithAcc(File file, File acc) throws IOException {
        File tempFile = createTempFile();
        try {
            merge(file, acc, tempFile);
            FileUtils.copyFile(tempFile, acc);
            return acc;
        } finally {
            FileUtils.deleteQuietly(tempFile);
        }
    }

    private File createTempFile() throws IOException {
        File tempFile = new File("temp.txt");
        FileUtils.touch(tempFile);
        return tempFile;
    }

    private void sort(File file) throws IOException {
        try {
            FileUtils.copyFile(recursiveSort(file), file);
        } finally {
            tempFiles.forEach(FileUtils::deleteQuietly);
            tempFiles.clear();
        }
    }

    private File recursiveSort(File file) throws IOException {
        File beforeMiddle = null;
        File afterMiddle = null;
        long len = countString(file);
        if (len < 2) return file;
        long middle = len / 2;
        beforeMiddle = new File(UUID.randomUUID().toString() + ".txt");
        afterMiddle = new File(UUID.randomUUID().toString() + ".txt");
        tempFiles.add(beforeMiddle);
        tempFiles.add(afterMiddle);
        FileUtils.touch(beforeMiddle);
        FileUtils.touch(afterMiddle);
        for (long i = 0; i < middle; i++) {
            FileUtils.writeStringToFile(beforeMiddle, getSpecificString(file, i) + "\n", true);
        }
        for (long j = middle; j < len; j++) {
            FileUtils.writeStringToFile(afterMiddle, getSpecificString(file, j) + "\n", true);
        }
        return mergeWithAcc(recursiveSort(beforeMiddle),
                recursiveSort(afterMiddle));
    }

    private long countString(File file) throws IOException {
        try (Stream<String> lines = Files.lines(file.toPath())) {
            return lines.count();
        }
    }

    private String getSpecificString(File file, long stringNumber) throws IOException {
        String specificLine;
        try (Stream<String> lines = Files.lines(file.toPath())) {
            specificLine = lines.skip(stringNumber).findFirst().orElseThrow(WrongStringIndexException::new);
        }
        return specificLine;
    }

    private void writeToFile(File file, String recordedValue) throws IOException {
        String newRecordedValue = recordedValue + "\n";
        Files.write(file.toPath(), newRecordedValue.getBytes(), StandardOpenOption.APPEND);
    }

    private boolean compareWithDirection(File firstFile, Long index1, File secondFile, Long index2) throws IOException {

        int compared = compare(getSpecificString(firstFile, index1), getSpecificString(secondFile, index2));

        if (ascending) {
            if (compared == 0) {
                return true;
            } else return compared != 1;
        } else {
            if (compared == 0) {
                return true;
            } else return compared != -1;
        }
    }

}
