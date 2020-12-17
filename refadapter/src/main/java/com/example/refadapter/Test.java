package com.example.refadapter;

import java.util.ArrayList;

public class Test {
    public ArrayList<String> strings = new ArrayList<>();

    private ArrayList<?> objects = strings;
}
