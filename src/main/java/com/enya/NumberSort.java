package com.enya;

public class NumberSort extends StringSort {

    @Override
    protected boolean compare(String first, String second) {
        return Long.parseLong(first) >= Long.parseLong(second);
    }
}
