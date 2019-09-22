package com.enya;


import java.io.File;
import java.util.Set;

public class SortParams {

    private boolean ascendingDirection;
    private boolean stringType;
    private Set<File> fileSet;
    private File outputFile;

    SortParams(boolean ascendingDirection, boolean stringType, Set<File> fileSet, File outputFile) {

        if (fileSet.contains(outputFile)) {
            throw new RuntimeException();
        }

        this.ascendingDirection = ascendingDirection;
        this.stringType = stringType;
        this.fileSet = fileSet;
        this.outputFile = outputFile;
    }

    public boolean isAscendingDirection() {
        return ascendingDirection;
    }

    public boolean isStringType() {
        return stringType;
    }

    public Set<File> getFileSet() {
        return fileSet;
    }

    public File getOutputFile() {
        return outputFile;
    }
}
