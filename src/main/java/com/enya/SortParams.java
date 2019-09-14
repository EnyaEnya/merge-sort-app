package com.enya;


import java.io.File;
import java.util.List;

public class SortParams {

    private boolean ascendingDirection;
    private boolean stringType;
    private List<File> fileList;
    private File outputFile;

    SortParams(boolean ascendingDirection, boolean stringType, List<File> fileList, File outputFile) {
        this.ascendingDirection = ascendingDirection;
        this.stringType = stringType;
        this.fileList = fileList;
        this.outputFile = outputFile;
    }

    public boolean isAscendingDirection() {
        return ascendingDirection;
    }

    public boolean isStringType() {
        return stringType;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public File getOutputFile() {
        return outputFile;
    }
}
