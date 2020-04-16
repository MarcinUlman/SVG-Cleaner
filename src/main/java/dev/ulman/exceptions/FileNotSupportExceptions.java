package dev.ulman.exceptions;

import java.io.IOException;

public class FileNotSupportExceptions extends IOException {
    public FileNotSupportExceptions(String message){
        super(message);
    }

    public FileNotSupportExceptions() {
        super("This file type is not supported");
    }
}
