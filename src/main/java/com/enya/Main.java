package com.enya;

import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {

        ArgParser argParser = new ArgParser();

        SortParams sortParams = argParser.parseArgs(args);

        StringSort sortInstance;

        if (sortParams.isStringType()) {
            sortInstance = new StringSort(sortParams.isAscendingDirection());
        } else {
            sortInstance = new NumberSort(sortParams.isAscendingDirection());
        }

        sortInstance.mergeFiles(sortParams.getFileList(), sortParams.getOutputFile());

    }

}
