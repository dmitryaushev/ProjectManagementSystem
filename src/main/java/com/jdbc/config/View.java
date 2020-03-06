package com.jdbc.config;

public interface View {

    String read ();
    void write(String message);
    void redWrite(String message);
}
