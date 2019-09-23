package com.enya;

import com.enya.exceptions.InputFilesContainOutputFileException;
import com.enya.exceptions.NoInputFilesException;
import com.enya.exceptions.NoOutputFileException;
import org.apache.commons.cli.*;

import java.io.File;
import java.util.List;
import java.util.Set;
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

        File outputFile = getOutputFile(cmd.getArgList());
        Set<File> inputFiles = getInputFiles(cmd.getArgList());

        if (inputFiles.contains(outputFile)) {
            throw new InputFilesContainOutputFileException();
        }

        return new SortParams(ascending, stringType, inputFiles, outputFile);

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
        if(cmdArgs.size() > 0) {
            return new File(cmdArgs.get(0));
        }
        throw new NoOutputFileException();
    }

    private Set<File> getInputFiles(List<String> cmdArgs) {
        Set<File> fileSet = cmdArgs.stream().skip(1).map(File::new).filter(File::exists).collect(Collectors.toSet());
        if (fileSet.size() == 0) {
            throw new NoInputFilesException();
        }
        return fileSet;
    }

}
