package com.enya;

import org.apache.commons.cli.*;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

public class ArgParser {

    private Options options;

    public ArgParser() throws AlreadySelectedException {
        prepareOptions();
    }

    public SortParams parseArgs(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        boolean ascending = true;
        if (cmd.hasOption("d")) {
            ascending = false;
        }

        boolean stringType = true;
        if (cmd.hasOption("i")) {
            stringType = false;
        }

        return new SortParams(ascending, stringType, getInputFiles(cmd.getArgList()), getOutputFile(cmd.getArgList()));

    }

    private void prepareOptions() throws AlreadySelectedException {
        options = new Options();
        OptionGroup sortGroup = new OptionGroup();
        Option ascOption = new Option("a", false, "Ascending sort");
        Option descOption = new Option("d", false, "Descending sort");
        sortGroup.addOption(ascOption);
        sortGroup.addOption(descOption);
        sortGroup.setRequired(false);
        sortGroup.setSelected(ascOption);
        options.addOptionGroup(sortGroup);

        OptionGroup typeGroup = new OptionGroup();
        Option stringOption = new Option("s", false, "String sort");
        Option intOption = new Option("i", false, "Integer sort");
        typeGroup.addOption(stringOption);
        typeGroup.addOption(intOption);
        typeGroup.setRequired(true);
        options.addOptionGroup(typeGroup);
    }

    private File getOutputFile(List<String> cmdArgs) {
        File outputFile = new File(cmdArgs.get(0));
        try {
            FileUtils.touch(outputFile);
            FileUtils.write(outputFile, "", Charset.defaultCharset());  //todo check encoding
        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputFile;
    }

    private List<File> getInputFiles(List<String> cmdArgs) {
        List<File> fileList = cmdArgs.stream().skip(1).map(File::new).filter(File::exists).collect(Collectors.toList());
        if (fileList.size() == 0) {
            throw new RuntimeException(); //todo custom exception
        }
        return fileList;
    }

}
