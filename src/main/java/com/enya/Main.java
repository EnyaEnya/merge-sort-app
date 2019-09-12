package com.enya;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {

        NumberSort sort = new NumberSort();

        sort.createOutputFile("input1.txt",
                "input2.txt", "output.txt");

    }

}
