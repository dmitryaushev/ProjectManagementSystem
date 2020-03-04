package com.jdbc.config;

public interface Command {

    String command();
    void process();

    default boolean canProcess(String input) {
        return input.trim().equals(command());
    }
}
