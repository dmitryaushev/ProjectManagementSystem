package com.jdbc.config;

import java.util.Set;

public interface Command {

    String command();
    void process();

    default boolean canProcess(String input) {
        return input.trim().equals(command());
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

    default void question (String answer) {
        switch (answer) {
            case "Y":
                break;
            case "N":
                throw new UnsupportedOperationException("Exit");
            default:
                throw new IllegalArgumentException("Wrong input");
        }
    }

    default void sleep() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
