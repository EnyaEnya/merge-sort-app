package com.enya;

import org.apache.commons.cli.ParseException;

public class Main {

    public static void main(String[] args) throws ParseException {

        ArgParser argParser = new ArgParser();

        SortParams sortParams = argParser.parseArgs(args);

        System.out.println(sortParams);

    }

}