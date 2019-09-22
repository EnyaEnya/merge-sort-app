package com.enya;

import org.apache.commons.cli.ParseException;

public class Main {

    static void sort(String[] args) throws ParseException {

        ArgParser argParser = new ArgParser();

        SortParams sortParams = argParser.parseArgs(args);

        StringSort sortInstance;

        if (sortParams.isStringType()) {
            sortInstance = new StringSort(sortParams.isAscendingDirection());
        } else {
            sortInstance = new NumberSort(sortParams.isAscendingDirection());
        }

        sortInstance.mergeFiles(sortParams.getFileSet(), sortParams.getOutputFile());

    }

    public static void main(String[] args) {
        try {
            sort(args);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
