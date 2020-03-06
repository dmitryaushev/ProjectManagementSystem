package com.jdbc.config;

import java.util.Scanner;

public class Console implements View {
    @Override
    public String read() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    @Override
    public void write(String message) {
        System.out.println(message);
    }

    @Override
    public void redWrite(String message) {
        System.err.println(message);
    }
}
