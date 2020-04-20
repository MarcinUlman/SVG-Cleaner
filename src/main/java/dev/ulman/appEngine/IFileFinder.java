package dev.ulman.appEngine;

import dev.ulman.exceptions.FileNotSupportExceptions;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface IFileFinder {

    File findFileByPath(String pathToFile) throws FileNotSupportExceptions, FileNotFoundException;

    List<File> findFilesByDirectory() throws FileNotFoundException;
    List<File> findFilesByDirectory(String pathToFileFolder) throws FileNotFoundException;

}
