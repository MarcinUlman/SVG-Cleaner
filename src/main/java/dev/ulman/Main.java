package dev.ulman;

import dev.ulman.userInterface.CLI;

public class Main {
    public static void main(String[] args) {
        CLI cli = new CLI();
        cli.argsParser(args);
    }
}
