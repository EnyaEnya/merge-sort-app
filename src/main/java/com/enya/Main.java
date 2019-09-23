package com.enya;

import com.enya.exceptions.InputFilesContainOutputFileException;
import com.enya.exceptions.NoInputFilesException;
import com.enya.exceptions.NoOutputFileException;
import com.enya.exceptions.WrongStringIndexException;
import org.apache.commons.cli.ParseException;

import java.io.IOException;

public class Main {

    private static void sort(String[] args) throws ParseException, IOException {

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

    private static void showExceptionMessage(String message) {
        System.out.println(message);
        System.exit(1);
    }

    public static void main(String[] args) {
        try {
            sort(args);
        } catch (NoInputFilesException e) {
            showExceptionMessage("There are no input files, add one file at least");
        } catch (ParseException e) {
            showExceptionMessage(e.getMessage());
        } catch (InputFilesContainOutputFileException e) {
            showExceptionMessage("List of input files contain output file");
        } catch (NoOutputFileException e) {
            showExceptionMessage("There is no output file");
        } catch (NumberFormatException e) {
            showExceptionMessage("Wrong number symbol " + e.getMessage());
        } catch (WrongStringIndexException e) {
            showExceptionMessage("Internal exception: wrong string index");
        } catch (IOException |
                RuntimeException e) {
            showExceptionMessage("Problem with input-output files");
        }
    }
}
