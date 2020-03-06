package com.jdbc.config;

import java.util.List;
import java.util.Set;

public interface Command {

    String command();
    void process();

    default boolean canProcess(String input) {
        return input.trim().equals(command());
    }

    default boolean matchInt(int i, List<Integer> list) {

        boolean match = false;
        for (int id : list)
            if (id == i) {
                match = true;
                break;
            }
        return match;
    }

    default boolean matchString(String s, Set<String> set) {

        boolean match = false;
        for (String string : set)
            if(string.equals(s)) {
                match = true;
                break;
            }
        return match;
    }
}
