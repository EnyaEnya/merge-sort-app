package com.enya;

public class NumberSort extends StringSort {

    public NumberSort(boolean ascending) {
        super(ascending);
    }

    @Override
    protected int compare(String first, String second) {
        return Long.compare(Long.parseLong(first), Long.parseLong(second));
    }
}
